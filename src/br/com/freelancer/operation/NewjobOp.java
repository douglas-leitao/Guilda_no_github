package br.com.freelancer.operation;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;

import org.json.JSONObject;

import br.com.freelancer.databaseConnection.Conexao;
import br.com.freelancer.databaseConnection.ConnectionsPool;
import br.com.freelancer.model.TarefaBean;
import br.com.freelancer.util.Persistor;

public class NewjobOp {
	public NewjobOp() {}
	Conexao conexao = ConnectionsPool.getInstance().getConnection();
	TarefaBean job = new TarefaBean();
	Date dt_entrega;
	Date dt_cadastro;
	Integer estimativa;
	double preco;
	
	public int cadastrarTarefa(JSONObject jObj, Integer id_user) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		Date dataAtual = new Date(new java.util.Date().getTime());
		
		String desc_tarefa = jObj.get("desc-tarefa").toString();
		String requisitos = jObj.get("requisitos").toString();
		String data_entrega = jObj.getString("data-entrega").toString();
		if(data_entrega.equals( "" )){
			data_entrega = "01/01/1999";
		}
		
		try {
			dt_entrega = new Date(formato.parse(data_entrega).getTime());
			dt_cadastro = dataAtual;
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		
		
		if(jObj.get("estimativa").toString().equals("")){
			estimativa = 0;
		}else{
			estimativa = Integer.parseInt(jObj.get("estimativa").toString());
		}
		if(jObj.get("preco").toString().equals("")){
			preco = 0;
		}else{
			preco = Double.parseDouble(jObj.get("preco").toString());
		}
		Integer id_tipo_tarefa = Integer.parseInt(jObj.get("tipo-job").toString());
		
		
		job.setDesc_tarefa(desc_tarefa);
		job.setRequisitos(requisitos);
		job.setData_entrega(dt_entrega);
		job.setData_cadastro(dt_cadastro);
		job.setEstimativa_hora(estimativa);
		job.setPreco(preco);
		job.setId_tipo_tarefa(id_tipo_tarefa);
		job.setId_status_tarefa(1);
		job.setId_usuario(id_user);


		System.out.println(job.getData_cadastro());
		System.out.println(job.getData_entrega());
		
		try {
			
			Persistor persistor = new Persistor(conexao, job);
			persistor.insert();
			return 200;
		} catch (Exception e) {
			e.printStackTrace();
			return 404;
		}
	}
	
}