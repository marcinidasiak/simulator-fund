package controllers;

import org.codehaus.jackson.JsonNode;

import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.WebSocket;
import support.SUPPLIER;
import views.html.index;
import actor.Market;

/**
 * Controller to handling communication with the user.
 * 
 * @author Marcin Idasiak
 * 
 */
public class Application extends Controller {

	/**
	 * Generating the homepage of the respective skrypatami javascript
	 * 
	 * @return
	 */
	public static Result index() {
		return ok(index.render("eurobank"));
	}

	/**
	 * Handle valuation websocket.
	 * 
	 * @return
	 */

	public static WebSocket<JsonNode> service() {
		return new WebSocket<JsonNode>() {
			// called when the websocket is established
			public void onReady(WebSocket.In<JsonNode> in,
					WebSocket.Out<JsonNode> out) {
				try {
					Market.join(SUPPLIER.USERNAME, in, out);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
	}
}
