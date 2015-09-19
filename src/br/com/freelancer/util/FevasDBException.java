package br.com.freelancer.util;

public class FevasDBException extends Exception {
	
	private static final long serialVersionUID = -1839912346763737180L;

	public FevasDBException( String msg ) {
		super( msg );
	}
	
	public FevasDBException( Exception e ) {
		super( e );
	}

	public FevasDBException( String msg, Exception e ) {
		super( msg, e );
	}
}