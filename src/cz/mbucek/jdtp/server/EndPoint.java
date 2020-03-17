package cz.mbucek.jdtp.server;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation sets the the road for incoming messages.
 *
 * <p>The annotated class
 * must have a public no-arg constructor.
 *
 * <p>For example:
 * When JS WebSocket client is trying to connect to example.com:port/echo, you have to set src for this class to /echo.
 * Message will come to this class.
 * 
 * <pre><code>
 * &#64;EndPoint(src="/echo")
 * public class EchoMethod implements ConfigG(){
 * 		&#64;onOpen
 * 		public void onOpen(String message){
 * 		
 * 		}
 * 		&#64;onMessage
 * 		public void onMessage(String message){
 * 		
 * 		}
 * 		&#64;onError
 * 		public void onError(String message){
 * 		
 * 		}
 *		&#64;onClose
 * 		public void onClose(String message){
 * 		
 * 		}
 * }
 * </code></pre>
 *
 * @author Matěj Bucek
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface EndPoint {
	String src() default "/";
}