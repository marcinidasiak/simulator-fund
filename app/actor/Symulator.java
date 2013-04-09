package actor;

import static java.util.concurrent.TimeUnit.SECONDS;

import org.codehaus.jackson.JsonNode;

import play.Logger;
import play.libs.Akka;
import play.libs.Json;
import play.mvc.WebSocket;
import scala.concurrent.duration.Duration;
import support.SUPPLIER;
import akka.actor.ActorRef;

/**
 * Class participation allows the actor to communicate with users and to elicit new pricing every 10 seconds
 * @author Marcin Idasiak
 *
 */
public class Symulator {
    
    public Symulator(ActorRef market) {
        
        // Create a Fake socket out for the robot that log events to the console.
        WebSocket.Out<JsonNode> robotChannel = new WebSocket.Out<JsonNode>() {
            
            public void write(JsonNode frame) {
                Logger.of(SUPPLIER.SYMULATOR).info(Json.stringify(frame));
            }
            
            public void close() {}
            
        };
        
        // Join the market
        market.tell(new Messages.Join(SUPPLIER.SYMULATOR, robotChannel), market);
        
		// Make the symulator talk every 10 seconds
        Akka.system().scheduler().schedule(
            Duration.create(10, SECONDS),
            Duration.create(10, SECONDS),
            market,
            new Messages.Valuation(SUPPLIER.SYMULATOR),
            Akka.system().dispatcher()
        );
        
    }
    
}
