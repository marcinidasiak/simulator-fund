package actor;

import static akka.pattern.Patterns.ask;
import static java.util.concurrent.TimeUnit.SECONDS;
import static support.SUPPLIER.PRICE;
import interfaces.IExecutor;
import interfaces.IValuationUnitFund;
import interfaces.IWalletSaving;

import java.util.HashMap;
import java.util.Map;

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
import play.mvc.WebSocket.Out;
import scala.concurrent.Await;
import scala.concurrent.duration.Duration;
import support.Executor;
import support.FundIteam;
import support.SUPPLIER;
import valuation.RandomPricingModel;
import wallet.WalletFactory;
import actor.Messages.Command;
import actor.Messages.Join;
import actor.Messages.Quit;
import actor.Messages.Valuation;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import enums.InvestmentFund;

/**
 * Market is an Actor.
 * 
 * @author Marcin Idasiak
 */
public class Market extends UntypedActor {

	private class Account {
		private WebSocket.Out<JsonNode> channel;
		private IWalletSaving wallet;

		public Account(Out<JsonNode> channel, IWalletSaving wallet) {
			super();
			this.channel = channel;
			this.wallet = wallet;
		}

		public WebSocket.Out<JsonNode> getChannel() {
			return channel;
		}

		public IWalletSaving getWalletSaving() {
			return wallet;
		}
	}

	// Default market.
	static ActorRef defaultMarket = Akka.system().actorOf(
			new Props(Market.class));
	// Market bussiness logic
	IValuationUnitFund source = new RandomPricingModel();
	IExecutor executor = new Executor();

	InvestmentFund[] data = null;

	DateTime updateDate = DateTime.now(DateTimeZone.forID("Europe/Warsaw"));

	// Members of this market.
	Map<String, Account> members = new HashMap<String, Account>();

	// Create a Symulator,
	static {
		new Symulator(defaultMarket);
	}

	/**
	 * Join the default market.
	 */
	public static void join(final String username, WebSocket.In<JsonNode> in,
			final WebSocket.Out<JsonNode> out) throws Exception {

		// Send the Join message to the market
		String result = (String) Await.result(
				ask(defaultMarket, new Join(username, out), 1000),
				Duration.create(1, SECONDS));

		if ("OK".equals(result)) {
			// For each event received on the socket,
			in.onMessage(new Callback<JsonNode>() {
				public void invoke(JsonNode event) {
					defaultMarket.tell(new Command(username, event),
							defaultMarket);
				}
			});
			// When the socket is closed.
			in.onClose(new Callback0() {
				public void invoke() {
					// Send a Quit message to the room.
					defaultMarket.tell(new Quit(username), defaultMarket);
				}

			});
			defaultMarket.tell(new Messages.Valuation(SUPPLIER.SYMULATOR),
					defaultMarket);
		} else {

			// Cannot connect, create a Json error.
			ObjectNode error = Json.newObject();
			error.put("error", result);

			// Send the error to the socket.
			out.write(error);
		}
	}

	/**
	 * Calculation Wallet
	 * 
	 * @param valuation
	 *            message from Actor
	 */

	private void calculateWallet(Valuation valuation) {
		for (String username : members.keySet()) {
			if (!username.equals(valuation.username)) {
				calculateWalletFor(username);
			}
		}

	}

	/**
	 * Calculation Wallet for one user
	 * 
	 * @param username
	 */

	private void calculateWalletFor(String username) {
		Account account = members.get(username);
		account.wallet.valuationWallet();

		IWalletSaving wallet = account.getWalletSaving();

		notify("wallet", username, username, wallet.createJson());
	}

	/**
	 * Get current valuation
	 * 
	 * @return
	 */

	private InvestmentFund[] getDate() {
		return data;
	}

	/**
	 * Get Data time last valuation
	 * 
	 * @return
	 */

	private DateTime getDateTimeNow() {
		return DateTime.now(DateTimeZone.forID("Europe/Warsaw"));
	}

	private DateTime getDateTimeUpdate() {
		return getDateTimeNow();
	}

	/**
	 * Get Executor
	 * 
	 * @return
	 */

	public IExecutor getExecutor() {
		return executor;
	}

	/**
	 * get new valuation
	 * 
	 * @return
	 */
	public IValuationUnitFund getSource() {
		return source;
	}

	/**
	 * 
	 * Send a Json event to member
	 * 
	 * @param kind
	 * @param fromUser
	 * @param toUser
	 * @param node
	 */
	public void notify(String kind, String fromUser, String toUser,
			ObjectNode node) {
		Account fromUserAccount = members.get(fromUser);
		Account toUserAccount = members.get(toUser);

		if (fromUserAccount != null && toUserAccount != null) {
			ObjectNode event = Json.newObject();
			event.put("kind", kind);
			event.put("user", fromUser);
			event.putAll(node);

			ArrayNode m = event.putArray("members");
			for (String u : members.keySet()) {
				m.add(u);
			}

			toUserAccount.getChannel().write(event);
		}

	}

	/**
	 * 
	 * Send a Json event to all members
	 * 
	 * @param kind
	 * @param user
	 * @param node
	 */
	public void notifyAll(String kind, String user, ObjectNode node) {
		for (Account account : members.values()) {

			ObjectNode event = Json.newObject();
			event.put("kind", kind);
			event.put("user", user);
			event.putAll(node);

			ArrayNode m = event.putArray("members");
			for (String u : members.keySet()) {
				m.add(u);
			}
			account.getChannel().write(event);
		}
	}

	/**
	 * 
	 * Send a Json event to all members
	 * 
	 * @param kind
	 * @param user
	 * @param text
	 */
	public void notifyAll(String kind, String user, String text) {
		for (Account account : members.values()) {

			ObjectNode event = Json.newObject();
			event.put("kind", kind);
			event.put("user", user);
			event.put("message", text);

			ArrayNode m = event.putArray("members");
			for (String u : members.keySet()) {
				m.add(u);
			}

			account.getChannel().write(event);
		}
	}

	/**
	 * Handle Command from user
	 * 
	 * @param command
	 */

	private void onCommand(Command command) {
		IWalletSaving wallet = members.get(command.username).getWalletSaving();
		InvestmentFund fund = executor.decode(command.event);
		if (wallet != null) {
			ObjectNode event = executor.transaction(command.event, wallet,
					fund.getPrice());
			calculateWalletFor(command.username);
			notify("warning", SUPPLIER.SYMULATOR, command.username, event);
		}

	}

	/**
	 * Join new user
	 * 
	 * @param join
	 */

	private void onJoin(Join join) {
		// Check if this username is free.
		if (members.containsKey(join.username)) {
			getSender().tell("This username is already used", getSelf());
		} else {
			IWalletSaving walletSaving = WalletFactory.getFactory().product();
			String message = walletSaving.read(SUPPLIER.LOCATION);
			if (message == null) {
				members.put(join.username, new Account(join.channel,
						walletSaving));
				notifyAll("join", join.username, "has entered the market");
				start();
				getSender().tell("OK", getSelf());
			}
			sendError(message, SUPPLIER.SYMULATOR, SUPPLIER.USERNAME);
		}
	}

	/**
	 * Close connection for user from server
	 * 
	 * @param quit
	 */

	private void onQuit(Quit quit) {

		Account account = members.remove(quit.username);
		account.getWalletSaving().write(SUPPLIER.LOCATION);
		notifyAll("quit", quit.username, "has leaved the room");
		shutdown();
	}

	/**
	 * Main method for handle Actors
	 */
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

	/**
	 * Request new valuation and send response
	 * 
	 * @param valuation
	 */
	private void onValuation(Valuation valuation) {
		if (getDate() != null) {
			FundIteam it = source.newPrice();
			updateDate(it);

			ObjectNode event = Json.newObject();
			event.put("time", getDateTimeUpdate().toString());
			ArrayNode event_kind = event.putArray("valuation");
			for (InvestmentFund fund : getDate()) {
				ObjectNode event_fund = Json.newObject();
				event_fund.put("name", fund.getName());
				event_fund.put("price", fund.getPrice());
				event_fund.put("change", it.get(fund.toString()));
				event_fund.put("currency", fund.getCurrency());
				event_fund.put("code", fund.toString());
				event_kind.add(Json.toJson(event_fund));
			}
			notifyAll("valuation", valuation.username, event);
			calculateWallet(valuation);
		}
	}

	/**
	 * Send error to user
	 * @param message
	 * @param fromUser
	 * @param toUser
	 */
	private void sendError(String message, String fromUser, String toUser) {
		ObjectNode content = Json.newObject();
		content.put("error", message);
		notify("error", fromUser, toUser, content);
	}

	/**
	 * Set current date time
	 */
	private void setDateTimeNow() {
		updateDate = getDateTimeNow();
	}

	/**
	 * Set new Executor
	 * @param executor
	 */
	public void setExecutor(IExecutor executor) {
		this.executor = executor;
	}

	/**
	 * Set new IValuationUnitFund
	 * @param source
	 */
	public void setSource(IValuationUnitFund source) {
		this.source = source;
	}

	/**
	 * Shutdown actors
	 */
	private void shutdown() {
		data = null;
	}

	/**
	 *  Init for Actors
	 */
	private void start() {
		if (data == null) {
			data = InvestmentFund.values();
			for (InvestmentFund fund : data) {
				fund.setPrice(PRICE);
			}
			updateDate = getDateTimeNow();
		}
	}

	/**
	 * Update price new valuation
	 * @param it
	 */
	private void updateDate(FundIteam it) {
		for (InvestmentFund fund : getDate()) {
			fund.addPrice(it.get(fund.toString()));
		}
		setDateTimeNow();
	}
}
