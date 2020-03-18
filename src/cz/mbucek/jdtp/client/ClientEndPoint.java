package cz.mbucek.jdtp.client;

import cz.mbucek.jdtp.Client;
import cz.mbucek.jdtp.server.data.Message;
import cz.mbucek.jdtp.server.data.Error;

public interface ClientEndPoint {
	public void onOpen(Client client);
	public void onMessage(Client client, Message message);
	public void onError(Client client, Error error);
	public void onClose(Client client);
}
