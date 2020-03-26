package cz.mbucek.jdtp.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import cz.mbucek.jdtp.Server;
import cz.mbucek.jdtp.server.data.Message;

public class DataReader extends Thread{
	private Server server;
	private ServerClient client;
	private Caller caller;

	public DataReader(Server server, ServerClient client) {
		this.server = server;
		this.client = client;

		this.caller = new Caller(this.server, this.client);
	}

	public void run() {
		ObjectInputStream oin = null;
		ObjectOutputStream oos = null;
		
		try {
			oin = new ObjectInputStream(this.client.getClient().getInputStream());
			oos = new ObjectOutputStream(this.client.getClient().getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		this.client.setObjectInputStream(oin);
		this.client.setObjectOutputStream(oos);


		try {
			while(!this.client.getClient().isClosed()) {
				Message message = (Message) oin.readObject();
				if(message.isClose()) {
					this.caller.callClose();
					this.client.getClient().close();
					this.server.removeClient(this.client);
				}else if(message.isOpen()){
					Message response = new Message(true, Base64.getEncoder().encodeToString(MessageDigest.getInstance("SHA-1").digest((message.getHash() + "2V83AFA5-VO24-47DA-9DCA-NU6L05FT0D31").getBytes("UTF-8"))));
					this.client.open(response);
					this.client.setEndPoint(message.getEndPoint());
					this.caller.callOpen();
				}else if((message.isBytes() || message.isString() || message.isObject()) && this.client.isOpen()) {
					this.caller.callMessage(message);
				}else {
					//Add error
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
}
