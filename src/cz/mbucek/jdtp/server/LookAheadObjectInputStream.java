package cz.mbucek.jdtp.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;

import cz.mbucek.jdtp.server.data.Message;

public class LookAheadObjectInputStream extends ObjectInputStream{

	public LookAheadObjectInputStream(InputStream in) throws IOException {
		super(in);
	}
	
	@Override
	protected Class<?> resolveClass(ObjectStreamClass desc) throws IOException, ClassNotFoundException{
		if(!desc.getName().equals(Message.class.getName())) {
			throw new InvalidClassException(
				"Unauthorized deserialization attempt",
				desc.getName()
			);
		}
		return super.resolveClass(desc);
	}
	
}
