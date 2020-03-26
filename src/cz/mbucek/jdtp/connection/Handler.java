package cz.mbucek.jdtp.connection;

import java.io.IOException;
import java.net.Socket;

import javax.net.ssl.SSLSocket;

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
				if(!this.server.isSecure()) {
					Socket socket = this.server.getServer().accept();
					this.server.addClient(new ServerClient(this.server, socket));
				}else if(this.server.isSecure()) {
					SSLSocket socket = (SSLSocket) this.server.getServer().accept();
					socket.startHandshake();
					this.server.addClient(new ServerClient(this.server, (Socket) socket));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
