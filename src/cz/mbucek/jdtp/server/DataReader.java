package cz.mbucek.jdtp.server;

import cz.mbucek.jdtp.Server;

public class DataReader extends Thread{
	private Server server;
	private ServerClient client;

	public DataReader(Server server, ServerClient client) {
		this.server = server;
		this.client = client;
	}
	
	public void run() {
		
	}
}
