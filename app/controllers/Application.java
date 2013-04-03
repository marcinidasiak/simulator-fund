package controllers;

import org.codehaus.jackson.JsonNode;

import akktor.Market;
import play.*;
import play.mvc.*;
import play.libs.F.*;

import views.html.*;

public class Application extends Controller {
  
    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }
    
    
    public static WebSocket<String> sockHandler() {
        return new WebSocket<String>() {
            // called when the websocket is established
            public void onReady(WebSocket.In<String> in,
                    final WebSocket.Out<String> out) {
                // register a callback for processing instream events
                in.onMessage(new Callback<String>() {
                    public void invoke(String event) {
                    	System.out.println(event);
                    }
                });

                // write out a greeting
                out.write("I'm contacting you regarding your recent websocket.");
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
