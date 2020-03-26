package cz.mbucek.jdtp;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;

import cz.mbucek.jdtp.connection.Context;
import cz.mbucek.jdtp.connection.Handler;

public class SecureServer extends Server{
	protected SSLServerSocket sslServer;
	protected Context context = null;
	protected String[] cipherSuites = { "TLS_AES_256_GCM_SHA384" };
	protected String[] protocols = { "TLSv1.3" };
	public SecureServer(int port) {
		super(port);
		this.secure = true;
	}

	public SecureServer(int port, Context context) {
		super(port);
		this.secure = true;
		this.context = context;

		if(this.context.cipherSuites != null) {
			this.cipherSuites = this.context.cipherSuites;
		}

		if(this.context.protocols != null) {
			this.protocols = this.context.protocols;
		}
	}

	public void start() throws IOException {
		this.createServer();
		this.run = true;
		Thread handler = new Thread(new Handler(this));
		handler.start();
	}

	public void createServer() throws IOException{
		if(this.context != null) {
			if(this.context.keyStorePath != null || this.context.keyStorePass != null) {
				try {
					SSLServerSocketFactory ssf = this.context.getServerSocketFactory();
					this.server = ssf.createServerSocket(this.port);
					this.sslServer = (SSLServerSocket) this.server;
					this.sslServer.setNeedClientAuth(true);
					this.sslServer.setEnabledCipherSuites(this.cipherSuites);
					this.sslServer.setEnabledProtocols(this.protocols);
				} catch (KeyManagementException | UnrecoverableKeyException | NoSuchAlgorithmException | KeyStoreException
						| CertificateException | IOException e) {
					e.printStackTrace();
				}
			}else {
				SSLServerSocketFactory ssf = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
				this.server = ssf.createServerSocket(this.port);
				this.sslServer = (SSLServerSocket) this.server;
				this.sslServer.setNeedClientAuth(true);
				this.sslServer.setEnabledCipherSuites(this.cipherSuites);
				this.sslServer.setEnabledProtocols(this.protocols);
			}
		}else{
			SSLServerSocketFactory ssf = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
			System.out.println("Secure");
			this.server = ssf.createServerSocket(this.port);
			this.sslServer = (SSLServerSocket) this.server;
			this.sslServer.setNeedClientAuth(true);
			this.sslServer.setEnabledCipherSuites(this.cipherSuites);
			this.sslServer.setEnabledProtocols(this.protocols);
		}
	}

	public SSLServerSocket getSSLServer() {
		return this.sslServer;
	}

}
