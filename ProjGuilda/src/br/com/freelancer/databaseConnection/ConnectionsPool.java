package br.com.freelancer.databaseConnection;

import java.util.ArrayList;

import br.databaseConnection.ConexaoMySql;
import br.databaseConnection.ConnectionDBException;

public class ConnectionsPool {
	private static ConnectionsPool mySelf;
	private ArrayList<Conexao> lista;

	public ConnectionsPool(){
		if (lista == null) {
			lista = new ArrayList<Conexao>();
			try{
				lista.add(new Conexao());
				lista.add(new Conexao());
				lista.add(new Conexao());
				lista.add(new Conexao());
			} catch(ConnectionDBException e){
				e.printStackTrace();
			}
			
		}
	}

	public static ConnectionsPool getInstance(){
		if (mySelf == null) {
			mySelf = new ConnectionsPool();
		}

		return mySelf;
	}

	public synchronized Conexao getConnection(){
		for( Conexao cnx : lista )  {
			if(!cnx.isReservado()){				
				cnx.reserva();
				return cnx;				
			}
		}
		
		try {
			Conexao c = new Conexao();
			lista.add( c );
			c.reserva();
			return c;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void Finalize() {
		mySelf = null;
	}
}