package br.com.freelancer.model;



import br.com.freelancer.util.AlternateKey;
import br.com.freelancer.util.AutoIncrement;
import br.com.freelancer.util.Boolean;
import br.com.freelancer.util.Default;
import br.com.freelancer.util.PrimaryKey;
import br.com.freelancer.util.Table;
import br.com.freelancer.util.Login;
import br.com.freelancer.util.View;


@Table(tableName="USUARIO")
@View(viewName="VWLOGIN")
public class UsuarioBean {
	@AutoIncrement
	@PrimaryKey
	private Integer id_usuario;
	@Login
	@AlternateKey
	private String nome_usuario;
	@AlternateKey
	private String email_usuario;
	@Login
	private String senha_a;
	@Login
	private String senha_b;
	@Login
	@Default
	@Boolean
	private boolean ativo = true;
	
	public Integer getId_usuario() {
		return id_usuario;
	}
	public void setId_usuario(Integer id_usuario) {
		this.id_usuario = id_usuario;
	}
	public String getNome_usuario() {
		return nome_usuario;
	}
	public void setNome_usuario(String nome_usuario) {
		this.nome_usuario = nome_usuario;
	}
	public String getEmail_usuario() {
		return email_usuario;
	}
	public void setEmail_usuario(String email_usuario) {
		this.email_usuario = email_usuario;
	}
	public String getSenha_a() {
		return senha_a;
	}
	public void setSenha_a(String senha_a) {
		this.senha_a = senha_a;
	}
	public String getSenha_b() {
		return senha_b;
	}
	public void setSenha_b(String senha_b) {
		this.senha_b = senha_b;
	}
	
	public boolean isAtivo() {
		return ativo;
	}
	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
		
	
}