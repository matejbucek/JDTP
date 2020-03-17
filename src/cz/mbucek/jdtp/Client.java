package cz.mbucek.jdtp;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;

public class Client {
	
	protected Socket client;
	protected SocketAddress ipAddress;
	
	public Client(SocketAddress ipAddress) {
		this.ipAddress = ipAddress;
		this.client = new Socket();
	}
	
	public void connect() throws IOException {
		this.client.connect(this.ipAddress);
	}
	
	public void close() throws IOException {
		this.client.close();
	}
}
