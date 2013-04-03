package akktor;

import static akka.pattern.Patterns.ask;
import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;

import play.libs.Akka;
import play.libs.Json;
import play.libs.F.Callback;
import play.libs.F.Callback0;
import play.mvc.WebSocket;
import scala.concurrent.Await;
import scala.concurrent.duration.Duration;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;

/**
 * Market is an Actor.
 */
public class Market extends UntypedActor {

	// Default market.
	static ActorRef defaultMarket = Akka.system().actorOf(
			new Props(Market.class));

	// Create a Robot, just for fun.
	static {
		new Robot(defaultMarket);
	}

	// Members of this market.
	Map<String, WebSocket.Out<JsonNode>> members = new HashMap<String, WebSocket.Out<JsonNode>>();

	/**
	 * Join the default market.
	 */
	public static void join(final String username, WebSocket.In<JsonNode> in,
			WebSocket.Out<JsonNode> out) throws Exception {

		// Send the Join message to the market
		String result = (String) Await.result(
				ask(defaultMarket, new Join(username, out), 1000),
				Duration.create(1, SECONDS));

		if ("OK".equals(result)) {

			// For each event received on the socket,
			in.onMessage(new Callback<JsonNode>() {
				public void invoke(JsonNode event) {

					// Send a Talk message to the room.
					defaultMarket.tell(new Command(username, event.get("text")
							.asText()), defaultMarket);

				}
			});

			// When the socket is closed.
			in.onClose(new Callback0() {
				public void invoke() {

					// Send a Quit message to the room.
					defaultMarket.tell(new Quit(username), defaultMarket);

				}
			});

		} else {

			// Cannot connect, create a Json error.
			ObjectNode error = Json.newObject();
			error.put("error", result);

			// Send the error to the socket.
			out.write(error);

		}

	}

	private void onJoin(Join join) {
		// Check if this username is free.
		if (members.containsKey(join.username)) {
			getSender().tell("This username is already used", getSelf());
		} else {
			members.put(join.username, join.channel);
			notifyAll("join", join.username, "has entered the market");
			getSender().tell("OK", getSelf());
		}
	}
	
	private void onCommand(Command command){
		notifyAll("command", command.username, command.text);
	}
	
	private void onQuit(Quit quit){
		members.remove(quit.username);
		notifyAll("quit", quit.username, "has leaved the room");
	}
	
	private void onValuation(Valuation valuation){
		notifyAll("valuation", valuation.username, "Robot");
	}

	@Override
	public void onReceive(Object message) throws Exception {
		if(message instanceof Valuation){
			
			// valuate new fund price
			onValuation((Valuation)message);
		} else if (message instanceof Join) {
			
			// add new member
			onJoin((Join) message);
		} else if (message instanceof Command) {
			
			// Executed client request
			onCommand((Command) message);
		} else if (message instanceof Quit) {

			// Received a Quit message
			 onQuit((Quit) message);
		} else {
			unhandled(message);

		}
	}

	// Send a Json event to all members
	public void notifyAll(String kind, String user, String text) {
		for (WebSocket.Out<JsonNode> channel : members.values()) {

			ObjectNode event = Json.newObject();
			event.put("kind", kind);
			event.put("user", user);
			event.put("message", text);

			ArrayNode m = event.putArray("members");
			for (String u : members.keySet()) {
				m.add(u);
			}
		
			channel.write(event);
		}
	}

	// -- Messages

	public static class Join {

		final String username;
		final WebSocket.Out<JsonNode> channel;

		public Join(String username, WebSocket.Out<JsonNode> channel) {
			this.username = username;
			this.channel = channel;
		}

	}

	public static class Command {

		final String username;
		final String text;

		public Command(String username, String text) {
			this.username = username;
			this.text = text;
		}

	}

	public static class Valuation {
		final String username;

		public Valuation(String username) {
			this.username = username;
		}
	}

	public static class Quit {

		final String username;

		public Quit(String username) {
			this.username = username;
		}

	}

}
