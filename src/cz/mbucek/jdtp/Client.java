package cz.mbucek.jdtp;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;

import cz.mbucek.jdtp.client.ClientDataReader;
import cz.mbucek.jdtp.client.ClientEndPoint;
import cz.mbucek.jdtp.server.data.Message;
import cz.mbucek.jdtp.utils.Utils;

public class Client {
	
	protected Socket client;
	protected String address;
	protected String serverEndPoint;
	protected ClientEndPoint endpoint;
	protected boolean open = false;
	protected boolean openSent = false;
	protected int port;
	protected boolean run = false;
	protected Thread dr;
	protected ObjectInputStream ois;
	protected ObjectOutputStream oos;
	protected String hash;
	
	public Client(String address, int port, String serverEndPoint) {
		this.address = address;
		this.port = port;
		this.serverEndPoint = serverEndPoint;
		try {
			this.hash = Utils.encodeMess(Utils.getRandomMess(50));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	public void connect() throws IOException {
		this.run = true;
		this.client = new Socket(this.address, this.port);
		this.dr = new Thread(new ClientDataReader(this)); 
		this.dr.start();
	}
	
	public void close() throws IOException {
		this.run = false;
		this.send(new Message(true));
		this.client.close();
	}
	
	public boolean isRun() {
		return this.run;
	}
	
	public Socket getClient() {
		return this.client;
	}
	
	public void setEndPoint(ClientEndPoint endpoint) {
		this.endpoint = endpoint;
	}
	
	public ClientEndPoint getEndPoint() {
		return this.endpoint;
	}
	
	public boolean isOpen() {
		return this.open;
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
	
	public void open() throws IOException {
		if(!this.open) {
			Message message = new Message(true, this.hash);
			message.setEndPoint(this.serverEndPoint);
			this.openSent = true;
			this.send(message);
		}
	}
	
	public void setOpen() {
		if(!this.open) {
			this.open = true;
		}
	}
	
	public String getHash() {
		return this.hash;
	}
	
	public boolean getOpenSent() {
		return this.openSent;
	}
	
	public void setObjectInputStream(ObjectInputStream ois) {
		this.ois = ois;
	}
	
	public void setObjectOutputStream(ObjectOutputStream oos) {
		this.oos = oos;
	}
}
