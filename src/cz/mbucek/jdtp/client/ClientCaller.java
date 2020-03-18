package cz.mbucek.jdtp.client;

import cz.mbucek.jdtp.Client;
import cz.mbucek.jdtp.server.data.Message;
import cz.mbucek.jdtp.server.data.Error;

public class ClientCaller {
	protected Client client;
	public ClientCaller(Client client) {
		this.client = client;
	}
	
	public void callOpen() {
		this.client.getEndPoint().onOpen(this.client);
	}
	
	public void callMessage(Message message) {
		this.client.getEndPoint().onMessage(this.client, message);
	}

	public void callError(Error error) {
		this.client.getEndPoint().onError(this.client, error);
	}

	public void callClose() {
		this.client.getEndPoint().onClose(this.client);
	}
}
