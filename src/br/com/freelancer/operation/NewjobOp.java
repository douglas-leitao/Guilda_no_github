package br.com.freelancer.operation;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.json.JSONObject;

import br.com.freelancer.databaseConnection.Conexao;
import br.com.freelancer.databaseConnection.ConnectionsPool;
import br.com.freelancer.model.TarefaBean;
import br.com.freelancer.util.Finder;
import br.com.freelancer.util.Persistor;
import sun.util.calendar.LocalGregorianCalendar.Date;

public class NewjobOp {
	public NewjobOp() {}
	Conexao conexao = ConnectionsPool.getInstance().getConnection();
	TarefaBean job = new TarefaBean();
		
	
	public int CadastrarTarefa(JSONObject jObj) throws NoSuchAlgorithmException, UnsupportedEncodingException{
	
		String desc_tarefa = jObj.get("desc-tarefa").toString();
		String requisitos = jObj.get("requisitos").toString();
		String teste = jObj.getString("data-entrega").toString();
		System.out.println(teste);
		//Date data_entrega = Date.parse(jObj.get("image").toString());
		Integer extimativa = Integer.parseInt(jObj.get("extimativa").toString());
		double preco = Double.parseDouble(jObj.get("preco").toString());
		
		
		job.setDesc_tarefa(desc_tarefa);
		job.setRequisitos(requisitos);
		//job.setData_entrega(data_entrega);
		job.setExtimativa_horas(extimativa);
		job.setPreco(preco);
		


		try {
			
			Persistor persistor = new Persistor(conexao, job);
			persistor.insert();
			return 200;
		} catch (Exception e) {
			return 403;
		}
	}
	
}