package cz.mbucek.jdtp.server.data;

import java.io.Serializable;

public class Message implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2842210876928487745L;
	protected boolean open = false;
	protected String hash;
	protected byte[] bytes;
	protected boolean bytesAvailable = false;
	protected String string;
	protected boolean stringAvailable = false;
	protected Object object;
	protected boolean objectAvailable = false;
	protected String endpoint;
	protected Error error;
	protected boolean Error = false;
	protected boolean close = false;
	
	public Message(String message) {
		this.string = message;
		this.stringAvailable = true;
	}
	
	public Message(byte[] bytes) {
		this.bytes = bytes;
		this.bytesAvailable = true;
	}
	
	public Message(Object object) {
		this.object = object;
		this.objectAvailable = true;
	}
	
	public Message(boolean close) {
		this.close = close;
	}
	
	public Message(boolean open, String hash) {
		this.open = open;
		this.hash = hash;
	}
	
	public byte[] getBytes() {
		return this.bytes;
	}
	
	public String getString() {
		return this.string;
	}
	
	public Object getObject() {
		return this.object;
	}
	
	public boolean isClose() {
		return this.close;
	}
	
	public void setEndPoint(String endpoint) {
		this.endpoint = endpoint;
	}
	
	public String getEndPoint() {
		return this.endpoint;
	}
	
	public boolean isBytes() {
		return this.bytesAvailable;
	}
	
	public boolean isString() {
		return this.stringAvailable;
	}
	
	public boolean isObject() {
		return this.objectAvailable;
	}
	
	public boolean isError() {
		return this.Error;
	}
	
	public boolean isOpen() {
		return this.open;
	}
	
	public String getHash() {
		return this.hash;
	}
}
