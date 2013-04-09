package actors;

import org.codehaus.jackson.JsonNode;

import play.mvc.WebSocket;

public class Messages {

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
