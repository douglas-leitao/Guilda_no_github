package br.com.freelancer.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Conexao {

	private Connection cnx = null;
	private boolean ocupado;

	public Conexao() throws ConnectionDBException {
	
		ParametrosDB prms = ParametrosDB.getInstance();

		try {
			Class.forName(prms.getParameter("driverJDBC"));
			
			String url = "jdbc:postgresql://"
					+ prms.getParameter("endBanco") + ":"
					+ prms.getParameter("nroPorta") + "/"
					+ prms.getParameter("nomeDatabase");

			cnx = DriverManager.getConnection(url,
					prms.getParameter("nomeUsuario"),
					prms.getParameter("senha"));
			
		} catch ( Exception e ) {
			e.printStackTrace();
			throw new ConnectionDBException(e);
		}
	}

	public enum TipoBanco {
		MYSQL, POSTGRE
	}
	
	public Connection getConnection(){
		return this.cnx;
	}

	public void reserva() {
		this.ocupado = true;
	}

	public void libera() {
		this.ocupado = false;
	}

	public boolean isReservado() {
		return ocupado;
	}

	public void close() {

		try {
			this.cnx.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void executaSql( String comando ) throws ConnectionDBException {

		try {
			Statement st = cnx.createStatement();
			st.execute(comando);
		} catch (SQLException e) {
			throw new ConnectionDBException(e);
		}
	}

	public PreparedStatement prepareStatement( String comando ) throws ConnectionDBException {

		PreparedStatement ps = null;
		try {
			ps = this.cnx.prepareStatement(comando);
		} catch (SQLException e) {
			throw new ConnectionDBException(e);
		}
		return ps;

	}

	public ResultSet executeQuery( String query ) throws ConnectionDBException {

		try {
			ResultSet rs = prepareStatement(query).executeQuery();
			return rs;
		} catch ( Exception e ) {
			throw new ConnectionDBException(e);
		}
	}

}
