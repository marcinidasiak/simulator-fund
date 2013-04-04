package actors;

import static java.util.concurrent.TimeUnit.SECONDS;

import org.codehaus.jackson.JsonNode;

import play.Logger;
import play.libs.Akka;
import play.libs.Json;
import play.mvc.WebSocket;
import scala.concurrent.duration.Duration;
import akka.actor.ActorRef;

public class Symulator {
    
    public Symulator(ActorRef market) {
        
        // Create a Fake socket out for the robot that log events to the console.
        WebSocket.Out<JsonNode> robotChannel = new WebSocket.Out<JsonNode>() {
            
            public void write(JsonNode frame) {
                Logger.of("robot").info(Json.stringify(frame));
            }
            
            public void close() {}
            
        };
        
        // Join the market
        market.tell(new Messages.Join("Symulator", robotChannel), market);
        
        // Make the robot talk every 10 seconds
        Akka.system().scheduler().schedule(
            Duration.create(10, SECONDS),
            Duration.create(10, SECONDS),
            market,
            new Messages.Valuation("Robot"),
            Akka.system().dispatcher()
        );
        
    }
    
}
