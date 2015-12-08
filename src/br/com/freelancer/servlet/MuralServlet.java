package br.com.freelancer.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import br.com.freelancer.model.TarefaBean;
import br.com.freelancer.operation.ListTarefaOp;

@WebServlet("/MuralServlet")
public class MuralServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private List<TarefaBean> listaJob;
	
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
		
		
		ListTarefaOp tipo = new ListTarefaOp();
		int nroPag = 0;

		try {
			this.listaJob = tipo.findTarefa(jObj);
			if(jObj.get("where").toString().equals("0")){
				nroPag = tipo.countTarefa();
			}else{   
				nroPag = tipo.countTarefa(jObj.get("where").toString());
			}
		} catch (Exception e) {
			this.listaJob = null;
			e.printStackTrace();
		}
		int i = 1;

		response.setContentType("application/json");      
		PrintWriter out = response.getWriter();
		out.println("{");
	
		for (TarefaBean item : listaJob){
			out.println(" \"type " + i + "\": {");
	        out.println("\"idTipoJob\": \""+ item.getId_tipo_tarefa() +"\",");
	        out.println("\"id\": \""+ item.getId_tarefa() +"\",");
	        out.println("\"data_entrega\": \""+ item.getData_entrega() +"\",");
	        out.println("\"data_cadastro\": \""+ item.getData_cadastro() +"\",");
	        out.println("\"preco\": \""+ item.getPreco() +"\",");
	        out.println("\"nroPag\": \""+ nroPag +"\",");
	        out.println("\"desc\": \""+ item.getDesc_tarefa() + "\"");
	        
	        if(listaJob.size()!=i){
	        	out.println("},");	   
	        }else{
	        	out.println("}");
	        }
	        
	        i++;
		}
	
		out.println("}");
		out.close();
	

	}
}