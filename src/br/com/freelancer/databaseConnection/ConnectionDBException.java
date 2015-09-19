package br.com.freelancer.databaseConnection;

public class ConnectionDBException extends Exception {
	private static final long serialVersionUID = 7452880470017017760L;

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