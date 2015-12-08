package br.com.freelancer.operation;

import java.util.List;

import org.json.JSONObject;

import br.com.freelancer.databaseConnection.Conexao;
import br.com.freelancer.databaseConnection.ConnectionsPool;
import br.com.freelancer.model.TarefaBean;
import br.com.freelancer.util.Finder;

public class ListTarefaOp {
	Conexao cnx = ConnectionsPool.getInstance().getConnection();
	int limit = 10;
	String where = "id_tipo_tarefa = ";
	
	public List<TarefaBean> findTarefa(JSONObject jObj) {
		String order = jObj.get("order").toString();
		
		
		where += jObj.get("where").toString();
		
		int numPagina = Integer.parseInt(jObj.get("offset").toString());		
		int offset = (numPagina * limit) - limit;
		
		Finder<TarefaBean> finder;
		try{
		
			List<TarefaBean> listaTarefa;
			
			finder = new Finder<TarefaBean>(cnx, TarefaBean.class);
			
			//listaTarefa = finder.find();
			System.out.println("where:" + where);
			if(!jObj.get("where").toString().equals("0")){
				listaTarefa = finder.find(where,null,order,limit,offset);
			}else{
				listaTarefa = finder.find(null,null,order,limit, offset);
			}
			return listaTarefa;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public int countTarefa(String newWhere) {
		
		Finder<TarefaBean> finder;
		try{
		
			List<TarefaBean> listaTarefa;
			
			finder = new Finder<TarefaBean>(cnx, TarefaBean.class);
			
			listaTarefa = finder.find(where);
			System.out.println("registros: "+listaTarefa.size());
			int nroPag = listaTarefa.size()/limit;
			if(listaTarefa.size()% limit != 0){
				nroPag++;
			}
			System.out.println("paginas: "+nroPag);
			return nroPag;
		}catch(Exception e){
			e.printStackTrace();
		}
		return 0;
		
	}
	
	public int countTarefa() {
		
		Finder<TarefaBean> finder;
		try{
		
			List<TarefaBean> listaTarefa;
			
			finder = new Finder<TarefaBean>(cnx, TarefaBean.class);
			
			listaTarefa = finder.find();
			System.out.println("registros: "+listaTarefa.size());
			int nroPag = listaTarefa.size()/limit;
			if(listaTarefa.size()% limit != 0){
				nroPag++;
			}
			System.out.println("paginas: "+nroPag);
			return nroPag;
		}catch(Exception e){
			e.printStackTrace();
		}
		return 0;
	}

}
