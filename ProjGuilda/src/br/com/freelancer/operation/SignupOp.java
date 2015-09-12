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
		String nome_usuario = jObj.get("username").toString();
		String image = jObj.get("image").toString();
		String password = jObj.get("password").toString();
		
		//validate informations
		//
		
		MessageDigest algorithm = MessageDigest.getInstance("SHA-256"); 
		byte messageDigest[] = algorithm.digest(password.getBytes("UTF-8"));
		StringBuilder hexString = new StringBuilder(); 
		
		for (byte b : messageDigest) { 
			hexString.append(String.format("%02X", 0xFF & b)); 
		} 
		String passwordCript = hexString.toString();
		
	
		
		user.setNome_usuario(nome_usuario);
		user.setNome(name);
		user.setSenha(passwordCript);
		user.setEmail(email);
		user.setImagem(image);
		user.setDistancia_Maxima(1000);
		
		try {
			
			Persistor persistor = new Persistor(conexao, user);
			persistor.insert();
			return 200;
		} catch (Exception e) {
			return 403;
		}
	}
	
}