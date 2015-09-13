package br.com.freelancer.operation;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.json.JSONObject;

import br.com.freelancer.databaseConnection.Conexao;
import br.com.freelancer.databaseConnection.ConnectionsPool;
import br.com.freelancer.model.UsuarioBean;
import br.com.freelancer.util.Finder;
import br.com.freelancer.util.Persistor;

public class SignupOp {
	public SignupOp() {}
	Conexao conexao = ConnectionsPool.getInstance().getConnection();
	UsuarioBean user = new UsuarioBean();
	boolean teste = false;
	String username;
	String password;
	Finder<UsuarioBean> find = null;
	byte messageDigest[];
	String passwordCript;
	
	
	public int CadastrarUsuario(JSONObject jObj) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		//get JSON properties
		String name = jObj.get("name").toString();
		String email = jObj.get("email").toString();
		String image = jObj.get("image").toString();
		String password_a = jObj.get("password_a").toString();
		String password_b = jObj.get("password_b").toString();
		
		//validate informations
		//
		
		MessageDigest algorithm_a = MessageDigest.getInstance("SHA-256"); 
		byte messageDigest_a[] = algorithm_a.digest(password_a.getBytes("UTF-8"));
		StringBuilder hexString_a = new StringBuilder(); 
		
		for (byte b : messageDigest_a) { 
			hexString_a.append(String.format("%02X", 0xFF & b)); 
		} 
		String passwordCript_a = hexString_a.toString();
		
		MessageDigest algorithm_b = MessageDigest.getInstance("SHA-256"); 
		byte messageDigest_b[] = algorithm_b.digest(password_b.getBytes("UTF-8"));
		StringBuilder hexString_b = new StringBuilder(); 
		
		for (byte c : messageDigest_b) { 
			hexString_b.append(String.format("%02X", 0xFF & c)); 
		} 
		String passwordCript_b = hexString_b.toString();
		
	
		
		user.setNome_usuario(name);
		user.setEmail_usuario(email);
		user.setSenha_a(passwordCript_a);
		user.setSenha_b(passwordCript_b);
		
		//user.setImagem(image);


		try {
			
			Persistor persistor = new Persistor(conexao, user);
			persistor.insert();
			return 200;
		} catch (Exception e) {
			return 403;
		}
	}
	
}