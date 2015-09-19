package br.com.freelancer.util;

public class ConnectionDBException extends Exception {
	
	private static final long serialVersionUID = -1839912346763737180L;

	public ConnectionDBException( String msg ) {
		super( msg );
	}
	
	public ConnectionDBException( Exception e ) {
		super( e );
	}

	public ConnectionDBException( String msg, Exception e ) {
		super( msg, e );
	}
}