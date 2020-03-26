package cz.mbucek.jdtp.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import cz.mbucek.jdtp.Server;
import cz.mbucek.jdtp.server.data.Message;

public class ServerClient {
	protected Socket client;
	protected Server server;
	protected ObjectInputStream ois;
	protected ObjectOutputStream oos;
	protected String endpoint;
	protected boolean open = false;
	
	public ServerClient(Server server, Socket client) {
		this.server = server;
		this.client = client;
		Thread dr = new Thread(new DataReader(this.server, this));
		dr.start();
	}
	
	public void sendString(String string) throws IOException {
		Message message = new Message(string);
		this.send(message);
	}
	
	public void sendObject(Object object) throws IOException {
		Message message = new Message(object);
		this.send(message);
	}
	
	public void sendBytes(byte[] bytes) throws IOException {
		Message message = new Message(bytes);
		this.send(message);
	}
	
	protected void send(Message message) throws IOException {
		this.oos.writeObject(message);
		this.oos.flush();
	}
	
	public void open(Message message) throws IOException {
		if(!this.open) {
			this.open = true;
			this.send(message);
		}
	}
	
	public void close() throws IOException {
		Message message = new Message(true);
		this.send(message);
	}
	
	public void forceClose() throws IOException {
		this.client.close();
		this.server.removeClient(this);
	}
	
	public Socket getClient() {
		return this.client;
	}
	
	public Server getServer() {
		return this.server;
	}
	
	public String getEndPoint() {
		return this.endpoint;
	}
	
	public void setEndPoint(String endpoint) {
		this.endpoint = endpoint;
	}
	
	public boolean isOpen() {
		return this.open;
	}
	
	public void setObjectInputStream(ObjectInputStream ois) {
		this.ois = ois;
	}
	
	public void setObjectOutputStream(ObjectOutputStream oos) {
		this.oos = oos;
	}
}
