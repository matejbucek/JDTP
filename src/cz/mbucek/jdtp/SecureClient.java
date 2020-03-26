package cz.mbucek.jdtp;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import cz.mbucek.jdtp.client.ClientDataReader;
import cz.mbucek.jdtp.connection.Context;

public class SecureClient extends Client{
	protected Context context;
	protected String[] cipherSuites = { "TLS_AES_256_GCM_SHA384" };
	protected String[] protocols = { "TLSv1.3" };
	public SecureClient(String address, int ip, String serverEndPoint) {
		super(address, ip, serverEndPoint);
	}

	public SecureClient(String address, int ip, String serverEndPoint, Context context) {
		super(address, ip, serverEndPoint);

		this.context = context;

		if(this.context.cipherSuites != null) {
			this.cipherSuites = this.context.cipherSuites;
		}

		if(this.context.protocols != null) {
			this.protocols = this.context.protocols;
		}
	}

	public void connect() throws IOException {
		if(this.context != null) {
			if(this.context.trustStorePath != null || this.context.trustStorePass != null) {
				try {
					SSLSocketFactory sslsocketfactory = this.context.getSocketFactory();
					this.run = true;
					this.client = sslsocketfactory.createSocket(this.address, this.port);
					SSLSocket sslclient = (SSLSocket) this.client;
					sslclient.setEnabledCipherSuites(this.cipherSuites);
					sslclient.setEnabledProtocols(this.protocols);
					sslclient.startHandshake();
					this.dr = new Thread(new ClientDataReader(this)); 
					this.dr.start();
				} catch (KeyManagementException | UnrecoverableKeyException | NoSuchAlgorithmException | KeyStoreException
						| CertificateException | IOException e) {
					e.printStackTrace();
				}

			}else {
				SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
				this.run = true;
				this.client = sslsocketfactory.createSocket(this.address, this.port);
				SSLSocket sslclient = (SSLSocket) this.client;
				sslclient.setEnabledCipherSuites(this.cipherSuites);
				sslclient.setEnabledProtocols(this.protocols);
				sslclient.startHandshake();
				this.dr = new Thread(new ClientDataReader(this)); 
				this.dr.start();
			}
		}else {
			SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
			this.run = true;
			this.client = sslsocketfactory.createSocket(this.address, this.port);
			SSLSocket sslclient = (SSLSocket) this.client;
			sslclient.setEnabledCipherSuites(this.cipherSuites);
			sslclient.setEnabledProtocols(this.protocols);
			sslclient.startHandshake();
			this.dr = new Thread(new ClientDataReader(this)); 
			this.dr.start();
		}
	}

}
