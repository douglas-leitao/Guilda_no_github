package br.com.freelancer.util;

import java.util.ArrayList;


import br.com.freelancer.util.ConnectionDBException;

public class PoolDeConexoes {

	private static PoolDeConexoes mySelf;
	private static ArrayList<Conexao> lista;

	PoolDeConexoes() {
		if (lista == null) {
			lista = new ArrayList<Conexao>();
			try {
				lista.add(new Conexao());
				lista.add(new Conexao());
				lista.add(new Conexao());
				lista.add(new Conexao());
				lista.add(new Conexao());
			} catch (ConnectionDBException e) {
				
				e.printStackTrace();
			}
		}
	}

	public static PoolDeConexoes getInstance() {

		if (mySelf == null) {
			mySelf = new PoolDeConexoes();
		}
		
		return mySelf;
	}

	public static synchronized Conexao getConexao() {
		getInstance();
		
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
		} catch (ConnectionDBException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public void finaliza() {
		
		mySelf = null;
	}
}
