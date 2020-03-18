package cz.mbucek.jdtp.server;

import cz.mbucek.jdtp.Server;
import cz.mbucek.jdtp.server.data.Message;
import cz.mbucek.jdtp.server.data.Error;

public class Caller {
	protected Server server;
	protected ServerClient client;
	
	public Caller(Server server, ServerClient client) {
		this.server = server;
		this.client = client;
	}
	
	public ServerEndPoint getEndPoint() {
		return this.server.getEndPoint(this.client.getEndPoint());
	}
	
	public void callOpen() {
		this.getEndPoint().onOpen(this.client);
	}
	
	public void callMessage(Message message) {
		this.getEndPoint().onMessage(this.client, message);
	}
	
	public void callError(Error error) {
		this.getEndPoint().onError(this.client, error);
	}
	
	public void callClose() {
		this.getEndPoint().onClose(this.client);
	}
}
