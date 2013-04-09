package actor;

import org.codehaus.jackson.JsonNode;

import play.mvc.WebSocket;

/**
 * 
 * Class delivery Messages to communication with Actor. Actor receives messages
 * in method onReceive
 * 
 * @author Marcin Idasiak
 * 
 */

public class Messages {

	/**
	 * Message about joining a new user
	 * 
	 * @author Marcin Idasiak
	 * 
	 */
	public static class Join {

		final String username;
		final WebSocket.Out<JsonNode> channel;

		public Join(String username, WebSocket.Out<JsonNode> channel) {
			this.username = username;
			this.channel = channel;
		}

	}

	/**
	 * Message on the user's request
	 * 
	 * @author Marcin Idasiak
	 * 
	 */
	public static class Command {

		final String username;
		final JsonNode event;

		public Command(String username, JsonNode event) {
			this.username = username;
			this.event = event;
		}

	}

	/**
	 * Message to generate a new valuation.
	 * 
	 * @author Marcin Idasiak
	 * 
	 */

	public static class Valuation {
		final String username;

		public Valuation(String username) {
			this.username = username;
		}
	}

	/**
	 * The news of the end user connection
	 * 
	 * @author Marcin Idasiak
	 * 
	 */
	public static class Quit {

		final String username;

		public Quit(String username) {
			this.username = username;
		}

	}

}
