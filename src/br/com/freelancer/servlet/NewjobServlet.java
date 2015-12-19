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

import br.com.freelancer.model.UsuarioBean;
import br.com.freelancer.operation.NewjobOp;

@WebServlet("/NewjobServlet")
public class NewjobServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Integer status;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession sessao = request.getSession(true);
		UsuarioBean logado = (UsuarioBean)sessao.getAttribute("resLogado");
		
		if(logado == null){
			System.out.println("SESSAO ERRO");
			this.status = 403;
			System.out.println("Status: " + status);
			JSONObject result = new JSONObject();
			result.put("status", this.status);
			
			response.setContentType("application/json");      
			PrintWriter out = response.getWriter();
			out.print(result);
			out.flush();	
		}else{
			System.out.println("SESSAO CRIADA!");
		
		
			StringBuilder recebido = new StringBuilder( 500 );
			ServletInputStream sir = request.getInputStream();
			
			int ch;
			while( ( ch = sir.read() ) >= 0 ) {
				recebido.append( (char) ch );
			}
		
			String msgRecebida = recebido.toString();
			JSONObject jObj = new JSONObject( msgRecebida );
			
			NewjobOp cadastro = new NewjobOp();
			try {
				this.status = cadastro.cadastrarTarefa(jObj, logado.getId_usuario());
			} catch (NoSuchAlgorithmException e1) {
				e1.printStackTrace();
				this.status = 404;
			}
			System.out.println("Status: " + status);
			JSONObject result = new JSONObject();
			result.put("status", this.status);
			
			response.setContentType("application/json");      
			PrintWriter out = response.getWriter();
			out.print(result);
			out.flush();
		}
	}
}