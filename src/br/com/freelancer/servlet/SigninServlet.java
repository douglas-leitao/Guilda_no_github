package br.com.freelancer.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import br.com.freelancer.util.ConnectionDBException;
import br.com.freelancer.model.UsuarioBean;
import br.com.freelancer.operation.SigninOp;

@WebServlet("/SigninServlet")
public class SigninServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Integer status;
	UsuarioBean user;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		
		StringBuilder recebido = new StringBuilder( 500 );
		ServletInputStream sir = request.getInputStream();
		
		
		
		int ch;
		while( ( ch = sir.read() ) >= 0 ) {
			recebido.append( (char) ch );
		}

		String msgRecebida = recebido.toString();
		JSONObject jObj = new JSONObject( msgRecebida );
		
		SigninOp login = new SigninOp();
		try {
			this.status = login.autenticaLogin(jObj);
		} catch (NoSuchAlgorithmException e) {
			System.out.println("Nao foi possivel criptografar a senha!");
			this.status = 404;
			e.printStackTrace();
		} catch (ConnectionDBException e) {
			System.out.println("Nao foi possivel conectar ao banco de dados!");
			this.status = 404;
			e.printStackTrace();
		}
		
		if(status.equals(200)){
			this.user = login.carregaUsuario();
			
			HttpSession sessao = request.getSession(); 
			sessao.setAttribute("resLogado", this.user);
			
			System.out.println("SESSAO CRIADA");
			System.out.println(user.getNome_usuario()+" est� Online!");
		}
		
		JSONObject result = new JSONObject();
		result.put("status", this.status);
		
		     
		PrintWriter out = response.getWriter();
		out.print(result);
		out.flush();
	}
	
}