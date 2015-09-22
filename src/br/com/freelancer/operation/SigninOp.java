package br.com.freelancer.operation;

import org.json.JSONObject;

import br.com.freelancer.databaseConnection.Conexao;
import br.com.freelancer.databaseConnection.ConnectionDBException;
import br.com.freelancer.databaseConnection.ConnectionsPool;
import br.com.freelancer.model.UsuarioBean;
import br.com.freelancer.util.Finder;

import java.io.UnsupportedEncodingException; 
import java.security.MessageDigest; 
import java.security.NoSuchAlgorithmException;

public class SigninOp {
	public SigninOp(){	}
	UsuarioBean user;
	UsuarioBean temp;
	Conexao conexao = ConnectionsPool.getInstance().getConnection();
	Finder<UsuarioBean> find = null;
	
	public int autenticaLogin(JSONObject jObj) throws NoSuchAlgorithmException, UnsupportedEncodingException, ConnectionDBException {
		boolean login = false;
		String email = jObj.get("email").toString();
		String password = jObj.get("password-a").toString();
		
		MessageDigest algorithm = MessageDigest.getInstance("SHA-256"); 
		byte messageDigest[] = algorithm.digest(password.getBytes("UTF-8"));
		
		StringBuilder hexString = new StringBuilder(); 
		
		for (byte b : messageDigest) { 
			hexString.append(String.format("%02X", 0xFF & b)); 
		} 
		
		String passwordCript = hexString.toString();		
		
		find = new Finder<UsuarioBean>(conexao, UsuarioBean.class);
		user = new UsuarioBean();
		user.setEmail_usuario(email);
		user.setSenha_a(passwordCript);
		
		
		login = find.findLogin(user.getEmail_usuario(), user.getSenha_a(), user.isAtivo());
		
		if(login){
			return 200;
		}
		return 403;
	}
	
	public UsuarioBean carregaUsuario(){
		try {
			find = new Finder<UsuarioBean>( conexao, UsuarioBean.class );
			
			user = find.findByAK(user.getEmail_usuario());
		
		} catch (ConnectionDBException e1) {
			e1.printStackTrace();
		}
		return user;
	}

	public Integer autenticaLoginB(JSONObject jObj, String senhaB) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		
		String password = jObj.get("password-b").toString();
		
		MessageDigest algorithm = MessageDigest.getInstance("SHA-256"); 
		byte messageDigest[] = algorithm.digest(password.getBytes("UTF-8"));
		
		StringBuilder hexString = new StringBuilder(); 
		
		for (byte b : messageDigest) { 
			hexString.append(String.format("%02X", 0xFF & b)); 
		} 
		
		String passwordCript = hexString.toString();
//		System.out.println("Senha cript: "+ passwordCript);
//		System.out.println("Senha B: " + senhaB);
		if(passwordCript.equals(senhaB)){
			System.out.println("Senha B correta!");
			return 200;
		}else{
			System.out.println("Senha B INCORRETA!");
			return 403;
		}	
		
	}
	
	/*public void onlineTrue(){
		user.setOnline(true);
		
		try {
			Persistor persistor = new Persistor(conexao, user);
			persistor.update();
			System.out.println("Resultado Correto!");
		} catch (Exception e) {}
		
	}*/

	
}