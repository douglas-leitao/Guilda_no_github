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

import org.json.JSONObject;
import br.com.freelancer.operation.SignupOp;

@WebServlet("/SignupServlet")
public class SignupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Integer Status;
	
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
		
		SignupOp cadastro = new SignupOp();
		try {
			this.Status = cadastro.CadastrarUsuario(jObj);
		} catch (NoSuchAlgorithmException e1) {
			this.Status = 404;
		}

		JSONObject result = new JSONObject();
		result.put("status", this.Status);
		
		response.setContentType("application/json");      
		PrintWriter out = response.getWriter();
		out.print(result);
		out.flush();
	}
}