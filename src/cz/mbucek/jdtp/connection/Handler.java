package cz.mbucek.jdtp.connection;

import java.io.IOException;
import java.net.Socket;

import cz.mbucek.jdtp.Server;
import cz.mbucek.jdtp.server.ServerClient;

public class Handler extends Thread{
	
	private Server server;
	
	public Handler(Server server) {
		this.server = server;
		
	}
	
	public void run() {
		while(this.server.shouldRun()) {
			try {
				Socket socket = this.server.getServer().accept();
				this.server.addClient(new ServerClient(this.server, socket));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
