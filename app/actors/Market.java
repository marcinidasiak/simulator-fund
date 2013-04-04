package actors;

import static akka.pattern.Patterns.ask;
import static java.util.concurrent.TimeUnit.SECONDS;
import interfaces.IValuationUnitFund;

import java.util.HashMap;
import java.util.Map;

import model.FundIteam;
import model.RandomPricingModel;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import play.libs.Akka;
import play.libs.F.Callback;
import play.libs.F.Callback0;
import play.libs.Json;
import play.mvc.WebSocket;
import scala.concurrent.Await;
import scala.concurrent.duration.Duration;
import actors.Messages.Command;
import actors.Messages.Join;
import actors.Messages.Quit;
import actors.Messages.Valuation;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import enums.InvestmentFund;

/**
 * Market is an Actor.
 */
public class Market extends UntypedActor {

	// Default market.
	static ActorRef defaultMarket = Akka.system().actorOf(
			new Props(Market.class));

	IValuationUnitFund source = new RandomPricingModel();
	InvestmentFund[] data = null;
	DateTime updateDate = DateTime.now(DateTimeZone.forID("Europe/Warsaw"));

	// Create a Symulator,
	static {
		new Symulator(defaultMarket);
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
					// Send a Command message to the room.
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

	private void shutdown() {
		data=null;
	}

	private void start() {
		if (data == null) {
			data = InvestmentFund.values();
			updateDate = getDateTimeNow();
		}
	}

	private DateTime getDateTimeNow() {
		return DateTime.now(DateTimeZone.forID("Europe/Warsaw"));
	}
	
	private void  setDateTimeNow(){
		updateDate=getDateTimeNow();
	}
	
	private DateTime getDateTimeUpdate(){
		return getDateTimeNow();
	}

	private void onJoin(Join join) {
		// Check if this username is free.
		if (members.containsKey(join.username)) {
			getSender().tell("This username is already used", getSelf());
		} else {
			members.put(join.username, join.channel);
			notifyAll("join", join.username, "has entered the market");
			start();
			getSender().tell("OK", getSelf());
		}
	}

	private void onCommand(Command command) {
		notifyAll("command", command.username, command.text);
	}

	private void onQuit(Quit quit) {
		members.remove(quit.username);
		notifyAll("quit", quit.username, "has leaved the room");
		shutdown();
	}

	private void onValuation(Valuation valuation) {
		FundIteam it = source.newPrice();
		updateDate(it);
		ObjectNode event = Json.newObject();
		event.put("time", getDateTimeUpdate().toString());
	
		for (InvestmentFund fund : getDate()) {
			ObjectNode event_fund = Json.newObject();;			
			event_fund.put("name",fund.getName());
			event_fund.put("price", fund.getPrice());
			event_fund.put("change", it.get(fund.toString()));
			event_fund.put("currency", fund.getCurrency());
			event.put(fund.toString(), Json.toJson(event_fund));
		}
		notifyAll("valuation", valuation.username, event);
	}
	
	private void updateDate(FundIteam it){
		for(InvestmentFund fund : getDate()){
			fund.addPrice(it.get(fund.toString()));
		}
		setDateTimeNow();
	}

	private InvestmentFund[] getDate(){
		return data;
	}
	
	@Override
	public void onReceive(Object message) throws Exception {
		if (message instanceof Valuation) {

			// valuate new fund price
			onValuation((Valuation) message);
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

	// Send a Json event to all members
	public void notifyAll(String kind, String user, ObjectNode node) {
		for (WebSocket.Out<JsonNode> channel : members.values()) {

			ObjectNode event = Json.newObject();
			event.put("kind", kind);
			event.put("user", user);
			event.putAll(node);

			ArrayNode m = event.putArray("members");
			for (String u : members.keySet()) {
				m.add(u);
			}

			channel.write(event);
		}
	}
}
