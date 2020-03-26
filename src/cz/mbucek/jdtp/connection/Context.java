package cz.mbucek.jdtp.connection;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

public class Context {
	public String keyStorePath = null;
	public String keyStorePass = null;
	public String trustStorePath = null;
	public String trustStorePass = null;
	public String[] cipherSuites = null;
	public String[] protocols = null;
	public Context() {

	}

	public Context(String[] cipherSuites, String[] protocols) {
		this.cipherSuites =cipherSuites;
		this.protocols = protocols;
	}

	public Context(String keyStorePath, String keyStorePass, String trustStorePath, String trustStorePass) {
		this.keyStorePath = keyStorePath;
		this.keyStorePass = keyStorePass;
		this.trustStorePath = trustStorePath;
		this.trustStorePass = trustStorePass;
	}

	public void setKeyStorePath(String keyStorePath) {
		this.keyStorePath = keyStorePath;
	}

	public void setKeyStorePass(String keyStorePass) {
		this.keyStorePass = keyStorePass;
	}
	
	public void setTrustStorePath(String trustStorePath) {
		this.trustStorePath = trustStorePath;
	}
	
	public void setTrustStorePass(String trustStorePass) {
		this.trustStorePass = trustStorePass;
	}
	
	public void setCipherSuites(String[] cipherSuites) {
		this.cipherSuites = cipherSuites;
	}
	
	public void setProtocols(String[] protocols) {
		this.protocols = protocols;
	}
	
	public SSLServerSocketFactory getServerSocketFactory() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, CertificateException, FileNotFoundException, IOException, UnrecoverableKeyException {
		KeyStore ks = KeyStore.getInstance("JKS");
		ks.load(new FileInputStream(this.keyStorePath), this.keyStorePass.toCharArray());

		KeyManagerFactory kmf = KeyManagerFactory.getInstance("PKIX");
		kmf.init(ks, this.keyStorePass.toCharArray());

		TrustManagerFactory tmf = TrustManagerFactory.getInstance("PKIX"); 
		tmf.init(ks);

		SSLContext sc = SSLContext.getInstance("TLS"); 
		TrustManager[] trustManagers = tmf.getTrustManagers(); 
		sc.init(kmf.getKeyManagers(), trustManagers, null); 

		SSLServerSocketFactory ssf = sc.getServerSocketFactory(); 
		return ssf;
	}
	
	public SSLSocketFactory getSocketFactory() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, CertificateException, FileNotFoundException, IOException, UnrecoverableKeyException {
		KeyStore ks = KeyStore.getInstance("JKS");
		ks.load(new FileInputStream(this.trustStorePath), this.trustStorePass.toCharArray());

		KeyManagerFactory kmf = KeyManagerFactory.getInstance("PKIX");
		kmf.init(ks, this.trustStorePass.toCharArray());

		TrustManagerFactory tmf = TrustManagerFactory.getInstance("PKIX"); 
		tmf.init(ks);

		SSLContext sc = SSLContext.getInstance("TLS"); 
		TrustManager[] trustManagers = tmf.getTrustManagers(); 
		sc.init(kmf.getKeyManagers(), trustManagers, null); 

		SSLSocketFactory ssf = sc.getSocketFactory(); 
		return ssf;
	}
}
