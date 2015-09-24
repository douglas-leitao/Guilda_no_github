package br.com.freelancer.model;



import br.com.freelancer.util.AlternateKey;
import br.com.freelancer.util.AutoIncrement;
import br.com.freelancer.util.Boolean;
import br.com.freelancer.util.Default;
import br.com.freelancer.util.PrimaryKey;
import br.com.freelancer.util.Table;
import br.com.freelancer.util.Login;
import br.com.freelancer.util.View;
import sun.util.calendar.LocalGregorianCalendar.Date;


@Table(tableName="TAREFA")
@View(viewName="VWTAREFA")
public class TarefaBean {
	@AutoIncrement
	@PrimaryKey
	private Integer id_tarefa;
	
	private Integer id_tipo_tarefa;
	
	private Integer id_status_tarefa;
	
	private Integer id_usuario;
	
	private String desc_tarefa;
	
	private String requisitos;

	private Double preco;
	
	private Date data_cadastro;
	
	private Date data_entrega;
	
	private Integer extimativa_horas;

	public Integer getId_tarefa() {
		return id_tarefa;
	}

	public void setId_tarefa(Integer id_tarefa) {
		this.id_tarefa = id_tarefa;
	}

	public Integer getId_tipo_tarefa() {
		return id_tipo_tarefa;
	}

	public void setId_tipo_tarefa(Integer id_tipo_tarefa) {
		this.id_tipo_tarefa = id_tipo_tarefa;
	}

	public Integer getId_status_tarefa() {
		return id_status_tarefa;
	}

	public void setId_status_tarefa(Integer id_status_tarefa) {
		this.id_status_tarefa = id_status_tarefa;
	}

	public Integer getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(Integer id_usuario) {
		this.id_usuario = id_usuario;
	}

	public String getDesc_tarefa() {
		return desc_tarefa;
	}

	public void setDesc_tarefa(String desc_tarefa) {
		this.desc_tarefa = desc_tarefa;
	}

	public String getRequisitos() {
		return requisitos;
	}

	public void setRequisitos(String requisitos) {
		this.requisitos = requisitos;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public Date getData_cadastro() {
		return data_cadastro;
	}

	public void setData_cadastro(Date data_cadastro) {
		this.data_cadastro = data_cadastro;
	}

	public Date getData_entrega() {
		return data_entrega;
	}

	public void setData_entrega(Date data_entrega) {
		this.data_entrega = data_entrega;
	}

	public Integer getExtimativa_horas() {
		return extimativa_horas;
	}

	public void setExtimativa_horas(Integer extimativa_horas) {
		this.extimativa_horas = extimativa_horas;
	}
	
	
}