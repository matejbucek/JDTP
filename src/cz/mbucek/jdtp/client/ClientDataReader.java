package cz.mbucek.jdtp.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.NoSuchAlgorithmException;

import cz.mbucek.jdtp.Client;
import cz.mbucek.jdtp.server.data.Message;
import cz.mbucek.jdtp.utils.Utils;

public class ClientDataReader extends Thread{
	protected Client client;
	protected ClientCaller caller;
	
	public ClientDataReader(Client client) {
		this.client = client;
		this.caller = new ClientCaller(this.client);
	}
	
	public void run() {
			ObjectInputStream oin = null;
			ObjectOutputStream oos = null;
			try {
				oos = new ObjectOutputStream(this.client.getClient().getOutputStream());
				this.client.setObjectOutputStream(oos);
				this.client.open();
			} catch (IOException e2) {
				e2.printStackTrace();
			}
			try {
				oin = new ObjectInputStream(this.client.getClient().getInputStream());
				this.client.setObjectInputStream(oin);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			
			
		
			try {
		while(this.client.isRun()) {
			while(!this.client.getClient().isClosed()) {
				Message message = (Message) oin.readObject();
				
				if(message.isClose()) {
					this.caller.callClose();
				}else if(message.isOpen()) {
					if(message.getHash().equalsIgnoreCase(Utils.isCorrectMess(this.client.getHash()))) {
						this.client.setOpen();
						this.caller.callOpen();
					}else {
						this.client.close();
						this.caller.callClose();
					}
				}else if((message.isBytes() || message.isString() || message.isObject()) && this.client.isOpen()) {
					this.caller.callMessage(message);
				}
				
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
