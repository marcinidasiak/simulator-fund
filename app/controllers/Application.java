package controllers;

import org.codehaus.jackson.JsonNode;

import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.WebSocket;
import support.SUPPLIER;
import views.html.index;
import actor.Market;

public class Application extends Controller {
  
    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }
    
    
    public static WebSocket<JsonNode> service() {
        return new WebSocket<JsonNode>() {
            // called when the websocket is established
            public void onReady(WebSocket.In<JsonNode> in,
                    WebSocket.Out<JsonNode> out) {
                try {
					Market.join(SUPPLIER.USERNAME, in, out);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        };
    }
    
    /**
     * Handle valuation  websocket.
     */
    public static WebSocket<JsonNode> valuation() {
        return new WebSocket<JsonNode>() {           
            // Called when the Websocket Handshake is done.
            public void onReady(WebSocket.In<JsonNode> in, WebSocket.Out<JsonNode> out){           
                try { 
//                    Market.start(in, out);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };
    }
  
}
