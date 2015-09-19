package br.com.freelancer.databaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Conexao {
	private String status = "Nao conectou... ";
	private boolean busy;
	private Connection connection;
	
	public Conexao() throws ConnectionDBException{
		DBParameters prms = DBParameters.getInstance();
		
		try {
			/*String driverName = parameters.getParameter("JDBCDriver");
			Class.forName(driverName);
			
			String serverName = parameters.getParameter("DBAdress");
			String mydatabase = parameters.getParameter("DBName");
			String url = "jdbc:mysql://" + serverName + "/" + mydatabase;
			String username = parameters.getParameter("Username");
			String password = parameters.getParameter("Password");*/
				Class.forName(prms.getParameter("driverJDBC"));
			
				String url = "jdbc:postgresql://"
						+ prms.getParameter("endBanco") + ":"
						+ prms.getParameter("nroPorta") + "/"
						+ prms.getParameter("nomeDatabase");
	
				connection = DriverManager.getConnection(url,
						prms.getParameter("nomeUsuario"),
						prms.getParameter("senha"));
			
			//connection = DriverManager.getConnection(url, username, password); 
			
			if (connection != null) { 
				this.status = ("STATUS---> Conectado com sucesso!");
			} else { 
				this.status = ("STATUS---> Não foi possivel realizar conexão");
			} 
		} catch (ClassNotFoundException e) {
			System.out.println("O driver expecificado nao foi encontrado."); 
		} catch (SQLException e) {
			System.out.println("Nao foi possivel conectar ao Banco de Dados."); 
		} catch (Exception e){
			throw new ConnectionDBException(e);
		}
	}
	 
	public String statusConnection() { 
		return this.status; 
	} 
	
	public void CloseConnection() throws ConnectionDBException { 
		try { 
			this.connection.close(); 
			return; 
		} catch (SQLException e) { 
			e.printStackTrace();
		}
	} 
	 
	public java.sql.Connection RestartConnection() throws ConnectionDBException { 
		this.CloseConnection();
		return this.connection;
	}
	
	public void reserva() {
		this.busy = true;
	}

	public void libera() {
		this.busy = false;
	}

	public boolean isReservado() {
		return this.busy;
	}

	public void executeSql(String command) throws ConnectionDBException {
		try {
			Statement st = connection.createStatement();
			st.execute(command);
		} catch (SQLException e) {
			throw new ConnectionDBException(e);
		}
	}

	public PreparedStatement prepareStatement(String command) throws ConnectionDBException {
		PreparedStatement ps = null;
		try {
			ps = this.connection.prepareStatement(command);
		} catch (SQLException e) {
			throw new ConnectionDBException(e);
		}
		return ps;
	}

	public ResultSet executeQuery(String query) throws ConnectionDBException {
		try {
			ResultSet rs = prepareStatement(query).executeQuery();
			return rs;
		} catch ( Exception e ) {
			throw new ConnectionDBException(e);
		}
	}	
}