package cz.mbucek.jdtp;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

import cz.mbucek.jdtp.connection.Handler;
import cz.mbucek.jdtp.server.EndPoint;
import cz.mbucek.jdtp.server.ServerClient;
import cz.mbucek.jdtp.server.ServerEndPoint;

/*	<h1>Server class</h1>
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
	
	/*
	 * <p>Starts server</p>
	 * */
	
	public void start() throws IOException {
		this.createServer();
		this.run = true;
		Thread handler = new Thread(new Handler(this));
		handler.start();
	}
	
	/*
	 * <p>Close server</p>
	 * */
	
	public void close() throws IOException {
		this.run = false;
		this.server.close();
	}
	
	/*
	 * <p>Creates ServerSocket</p>
	 * */
	
	private void createServer() throws IOException {
		this.server = new ServerSocket(this.port);
	}
	
	/*
	 * <p>Should server run</p>
	 * 
	 * @return boolean which declares if server should run or not
	 * */
	
	public boolean shouldRun() {
		return this.run;
	}
	
	/*
	 * <p>Gives server socket</p>
	 * 
	 * @return ServerSocket from Server class
	 * */
	
	public ServerSocket getServer() {
		return this.server;
	}
	
	/*
	 * <p>Adds ServerClient to clientlist. Used by handler</p>
	 * 
	 * @param ServerClient
	 * */
	
	public void addClient(ServerClient client) {
		this.clients.add(client);
	}
	
	/*
	 * <p>Gives back ServerEndPoint class</p>
	 * 
	 * @param String endpoint name
	 * 
	 * @return ServerEndPoint of given name
	 * */
	
	public ServerEndPoint getEndPoint(String endpoint) {
		for (int i = 0; i < this.endpoints.size(); i++) {
			if(this.endpoints.get(i).getClass().getAnnotation(EndPoint.class).src().equalsIgnoreCase(endpoint)) {
				return this.endpoints.get(i);
			}
		}
		return null;
	}
	
	/*
	 * <p>Adds endpoint to server</p>
	 * 
	 * @param ServerEndPoint you want to add
	 * */
	
	public void addEndPoint(ServerEndPoint endpoint) {
		this.endpoints.add(endpoint);
	}
	
	/*
	 * <p>Set Servers endpoints list to your list</p>
	 * 
	 * @param ArrayList<ServerEndPoint>
	 * */
	
	public void setEndPoints(ArrayList<ServerEndPoint> endpoints) {
		this.endpoints = endpoints;
	}
}
