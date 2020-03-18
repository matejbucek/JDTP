package cz.mbucek.jdtp;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

import cz.mbucek.jdtp.connection.Handler;
import cz.mbucek.jdtp.server.EndPoint;
import cz.mbucek.jdtp.server.ServerClient;
import cz.mbucek.jdtp.server.ServerEndPoint;

/*
 * 
 * @author Matěj Bucek
 * */

public class Server {

	protected ServerSocket server;
	protected ArrayList<ServerClient> clients = new ArrayList<ServerClient>();
	protected ArrayList<ServerEndPoint> endpoints = new ArrayList<ServerEndPoint>();
	protected Handler handler;
	protected boolean run;
	protected int port;
	
	/*
	 * <p>Constructor of Server class</p>
	 * @param port port which will server socket use to listen for Clients
	 * */
	
	public Server(int port){
		this.port = port;
	}
	
	public void start() throws IOException {
		this.createServer();
		this.run = true;
		Thread handler = new Thread(new Handler(this));
		handler.start();
	}
	
	public void close() throws IOException {
		this.run = false;
		this.server.close();
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
	
	public ServerEndPoint getEndPoint(String endpoint) {
		for (int i = 0; i < this.endpoints.size(); i++) {
			if(this.endpoints.get(i).getClass().getAnnotation(EndPoint.class).src().equalsIgnoreCase(endpoint)) {
				return this.endpoints.get(i);
			}
		}
		return null;
	}
	
	public void addEndPoint(ServerEndPoint endpoint) {
		this.endpoints.add(endpoint);
	}
	
	public void setEndPoints(ArrayList<ServerEndPoint> endpoints) {
		this.endpoints = endpoints;
	}
}
