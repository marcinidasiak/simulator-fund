package akktor;

import static java.util.concurrent.TimeUnit.SECONDS;

import org.codehaus.jackson.JsonNode;

import play.Logger;
import play.libs.Akka;
import play.libs.Json;
import play.mvc.WebSocket;
import scala.concurrent.duration.Duration;
import akka.actor.ActorRef;

public class Robot {
    
    public Robot(ActorRef market) {
        
        // Create a Fake socket out for the robot that log events to the console.
        WebSocket.Out<JsonNode> robotChannel = new WebSocket.Out<JsonNode>() {
            
            public void write(JsonNode frame) {
                Logger.of("robot").info(Json.stringify(frame));
            }
            
            public void close() {}
            
        };
        
        // Join the market
        market.tell(new Market.Join("Robot", robotChannel), market);
        
        // Make the robot talk every 30 seconds
        Akka.system().scheduler().schedule(
            Duration.create(10, SECONDS),
            Duration.create(10, SECONDS),
            market,
            new Market.Valuation("Robot"),
            Akka.system().dispatcher()
        );
        
    }
    
}
