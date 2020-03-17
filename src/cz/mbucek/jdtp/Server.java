package cz.mbucek.jdtp;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

import cz.mbucek.jdtp.connection.Handler;
import cz.mbucek.jdtp.server.ServerClient;

/*
 * 
 * @author Matěj Bucek
 * */

public class Server {

	protected ServerSocket server;
	protected ArrayList<ServerClient> clients = new ArrayList<ServerClient>();
	protected Handler handler;
	protected boolean run;
	protected int port;
	
	/*
	 * <p>Constructor of Server class</p>
	 * @param port port which will server socket use to listen for Clients
	 * */
	
	public Server(int port) throws IOException {
		this.port = port;
		this.createServer();
	}
	
	public void start() {
		this.run = true;
		Thread handler = new Thread(new Handler(this));
		handler.start();
	}
	
	public void close() {
		this.run = false;

	}
	
	private void createServer() throws IOException {
		this.server = new ServerSocket(this.port);
	}
	
	public boolean shouldRun() {
		return this.run;
	}
	
	public ServerSocket getServer() {
		return this.server;
	}
	
	public void addClient(ServerClient client) {
		this.clients.add(client);
	}
}
