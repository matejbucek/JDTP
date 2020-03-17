package cz.mbucek.jdtp.server;

import cz.mbucek.jdtp.server.data.Message;
import cz.mbucek.jdtp.server.data.Error;

@EndPoint(src = "/")
public interface ServerEndPoint {
	public void onOpen(ServerClient client);
	public void onMessage(ServerClient client, Message message);
	public void onError(ServerClient client, Error error);
	public void onClose(ServerClient client);
}