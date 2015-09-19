package br.com.freelancer.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import br.com.freelancer.databaseConnection.Conexao;

public class Persistor {
	
	private Object refTabela;

	private Conexao cnx;
	
	public Persistor( Conexao cnx, Object refTabela ) throws ConnectionDBException {
		this.refTabela = refTabela;
		this.cnx = cnx;
		
		if( refTabela.getClass().getAnnotation( Table.class ) == null ) {
			throw new ConnectionDBException( "Nome da tabela não foi definido." );
		}
	}
	
	public void insert() throws ConnectionDBException {
		try {
			String cmd = criaComandoInsert();
			PreparedStatement ps = cnx.prepareStatement( cmd );
			Field[] atributos = refTabela.getClass().getDeclaredFields();
			
			int i = 1;
			for( Field fld : atributos ) {
				if( !fld.getName().equals( "serialVersionUID" ) && !fld.isAnnotationPresent( AutoIncrement.class ) && !fld.isAnnotationPresent( Default.class ) ) {
					
					String nomeGetter;
					
					if(fld.isAnnotationPresent(Boolean.class)){
					nomeGetter = "is" + 
							Character.toUpperCase(fld.getName().charAt( 0 )) +
							fld.getName().substring( 1 );
					}else{
					nomeGetter = "get" + 
							Character.toUpperCase(fld.getName().charAt( 0 )) +
							fld.getName().substring( 1 );
					}
										
					Method mg = refTabela.getClass().getDeclaredMethod( nomeGetter, (Class<?>[])null );
					Object obj = mg.invoke( refTabela, (Object[]) null );
					ps.setObject( i++, obj );
				}
			}
		
			System.out.println( ps.toString() );
			
			//EXECUTA O COMANDO SALVAR
			ps.execute();
			System.out.println( "Gravação com Sucesso!" );
		} catch (Exception e) {
			throw new ConnectionDBException(e);
		}
	}
	
	private String criaComandoInsert() {
		Field[] atributos = refTabela.getClass().getDeclaredFields();
		StringBuilder cmd = new StringBuilder( 120 );
		cmd.append( "INSERT INTO " );
		
		Table tbl = refTabela.getClass().getAnnotation( Table.class );
		cmd.append( tbl.tableName() );
		cmd.append( " ( " );
		
		int qtFlds = 0;
		
		for( Field fld : atributos ) {
			if( !fld.getName().equals( "serialVersionUID" ) && !fld.isAnnotationPresent( AutoIncrement.class ) && !fld.isAnnotationPresent( Default.class ) ) {
				cmd.append( fld.getName() );
				cmd.append( ", " );
								
				qtFlds ++;
			}
		}		
		cmd.replace( cmd.length() - 2, cmd.length(), " ) \nVALUES ( " );
		
		for( int i = 0; i < qtFlds; i++ ) {
			cmd.append( "?," ); 
		}
		
		cmd.replace( cmd.length() - 1, cmd.length(), " );" );
		
		
		System.out.println(cmd.toString());
		
		
		return cmd.toString();
	}
	
	public void update() throws ConnectionDBException {
		try {
			String cmd = criaComandoUpdate();
			PreparedStatement ps = cnx.prepareStatement( cmd.toString() );
			Field[] atributos = refTabela.getClass().getDeclaredFields();
			ArrayList<Object> chaves = new ArrayList<Object>();
		
			int i = 1;
			
			for(Field fld : atributos){
				String nomeGetter;
				
				if(fld.isAnnotationPresent(Boolean.class)){
				nomeGetter = "is" + 
						Character.toUpperCase(fld.getName().charAt( 0 )) +
						fld.getName().substring( 1 );
				} else {
				nomeGetter = "get" + 
						Character.toUpperCase(fld.getName().charAt( 0 )) +
						fld.getName().substring( 1 );
				}
				
				if( !fld.getName().equals( "serialVersionUID" ) ) {
					Method mg = refTabela.getClass().getDeclaredMethod(nomeGetter, (Class<?>[])null);
					Object obj = mg.invoke(refTabela, (Object[])null);
					
					if( fld.isAnnotationPresent(PrimaryKey.class)) {	
						chaves.add( obj );
					}
					ps.setObject(i++, obj);
				}				
			}
			
			for(Object vl : chaves){				
				if( vl != null ) {
					ps.setObject( i++, vl );
				}
			}
			
			System.out.println( ps );
			
			//EXECUTA O COMANDO EDITAR
			ps.execute();
			System.out.println( "Alteração com Sucesso!" );
		} catch (Exception e) {
			throw new ConnectionDBException( e );
		}
	}
	
	private String criaComandoUpdate( ){
		Field[] atributos = refTabela.getClass().getDeclaredFields();

		StringBuilder cmd = new StringBuilder( 120 );
		StringBuilder where = new StringBuilder( 50 );
		StringBuilder txt = new StringBuilder( 20 );
		
		cmd.append( "UPDATE " );
		
		Table tbl = refTabela.getClass().getAnnotation( Table.class );
		cmd.append( tbl.tableName() );
		cmd.append( " SET " );

		for(Field fld: atributos){
			if( !fld.getName().equals( "serialVersionUID" ) ) {
				cmd.append("\n    " );
				cmd.append(fld.getName());
				cmd.append(" = ?, " );
				
				if( fld.isAnnotationPresent(PrimaryKey.class)) {
					where.append( fld.getName() );
					where.append( " = ? ");
					where.append( "\n    and ");
				}					
			}			
		}
		txt.append( " \nWHERE\n    " );
		
		cmd.replace(cmd.length() - 2, cmd.length(), "");		
		where.delete( where.length() - 4, where.length() );
		cmd.append( txt );
		cmd.append( where );
		
		//System.out.println( cmd );
		return cmd.toString();
	}
			
	public void delete() throws ConnectionDBException {
		try {
			String cmd = criaComandoDelete();
			PreparedStatement ps = cnx.prepareStatement( cmd.toString() );
			Field[] atributos = refTabela.getClass().getDeclaredFields();
			
			int i = 1;
			
			for(Field fld: atributos){
				String nomeGetter = "get" + 
						Character.toUpperCase(fld.getName().charAt( 0 )) +
						fld.getName().substring( 1 );
				
				if( fld.isAnnotationPresent(PrimaryKey.class)) {
					Method mg = refTabela.getClass().getDeclaredMethod(nomeGetter, (Class<?>[])null);
					Object obj = mg.invoke(refTabela, (Object[])null);
					
					ps.setObject(i++, obj);
				}
			}
			System.out.println( ps );
			
			//EXECUTA O COMANDO DELETAR
			ps.execute();
			System.out.println( "Exclusão com Sucesso!" );
		} catch (Exception e) {
			throw new ConnectionDBException( e );
		}
	}

	private String criaComandoDelete() {
		Field[] atributos = refTabela.getClass().getDeclaredFields();
		StringBuilder cmd = new StringBuilder( 150 );
		StringBuilder where = new StringBuilder( 50 );

		cmd.append( "DELETE \nFROM\n    " );		
		Table tbl = refTabela.getClass().getAnnotation( Table.class );
		cmd.append( tbl.tableName() );
		cmd.append( "\nWHERE \n    " );
		
		for( Field campo : atributos ) {			
			if( campo.isAnnotationPresent(PrimaryKey.class)) {				
				where.append( campo.getName() );
				where.append( " = ? \n    and " );
			}
		}
		cmd.append( where );
		cmd.delete( cmd.length() - 4, cmd.length() );

		return cmd.toString();
	}	
}