package cz.mbucek.jdtp.server;

import java.net.Socket;

import cz.mbucek.jdtp.Server;

public class ServerClient {
	protected Socket client;
	protected Server server;
	
	public ServerClient(Server server, Socket client) {
		this.server = server;
		this.client = client;
		Thread dr = new Thread(new DataReader(this.server, this));
		dr.start();
	}

}
