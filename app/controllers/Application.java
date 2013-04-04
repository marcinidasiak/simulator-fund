package controllers;

import org.codehaus.jackson.JsonNode;

import actors.Market;
import play.*;
import play.mvc.*;
import play.libs.F.*;

import views.html.*;

public class Application extends Controller {
  
    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }
    
    
    public static WebSocket<JsonNode> sockHandler() {
        return new WebSocket<JsonNode>() {
            // called when the websocket is established
            public void onReady(WebSocket.In<JsonNode> in,
                    WebSocket.Out<JsonNode> out) {
                try {
					Market.join("client", in, out);
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
