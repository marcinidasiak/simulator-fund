package akktor;

import play.libs.Akka;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;

/**
 * Market is an Actor.
 */
public class Market extends UntypedActor {

	// Default market.
    static ActorRef defaultMarket = Akka.system().actorOf(new Props(Market.class));
	
	@Override
	public void onReceive(Object arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}
    
   
    
}
