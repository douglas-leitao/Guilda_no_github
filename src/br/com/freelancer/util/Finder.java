package br.com.freelancer.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.freelancer.databaseConnection.Conexao;
import br.com.freelancer.util.ConnectionDBException;
import br.com.freelancer.databaseConnection.ConnectionsPool;

public class Finder <T extends Object> {
	private Class<T> refTabela;
	private Conexao cnx;
	private T reg;
	private boolean found;
	private boolean showCommand = true;
	private boolean human = false;
	ArrayList<T> result = new ArrayList<T>();
	
	public Finder( Conexao cnx, Class<T> refTabela ) throws ConnectionDBException {
		this.refTabela = refTabela;
		this.cnx = cnx;
		
		if( refTabela.getAnnotation( Table.class ) == null ) {
			throw new ConnectionDBException( "Nome da tabela não foi definido." );
		}
	}
	
	@SuppressWarnings("unchecked")
	public T findByPK( Object... pk ) throws ConnectionDBException {
		
		if( pk == null || pk.length == 0 ) {
			throw new ConnectionDBException( "Valores da chave primária não foram informados" );
		}
		
		Field[] campos = refTabela.getDeclaredFields();
		
		StringBuilder cmd = new StringBuilder( 200 );
		StringBuilder where = new StringBuilder( 50 );
		cmd.append( "Select " );

		int qtChaves = 0;
		
		for( Field f : campos ) {
			
			if( !f.getName().equals( "serialVersionUID" ) ) {
				cmd.append( "\n    " + f.getName() );
				cmd.append( "," );
				
				if( f.isAnnotationPresent( PrimaryKey.class ) ) {
					where.append( f.getName() );
					where.append( " = ? and " ); 
					qtChaves++;
				}
			}
		}

		if( qtChaves != pk.length ) {
			throw new ConnectionDBException( "Número de chaves difere dos parâmetros informados" );
		}
		
		where.delete( where.length() - 4, where.length() );
		cmd.delete( cmd.length() - 1, cmd.length() );
		cmd.append( " \nfrom" );
		
		Table tbl = refTabela.getAnnotation( Table.class );
		cmd.append( "\n    " + tbl.tableName() );
		
		cmd.append( " \nwhere" );
		cmd.append( "\n    " + where );
		
		try {
			
			PreparedStatement ps = cnx.prepareStatement( cmd.toString() );
			
			int i = 1;
			for( Object k: pk ) {
				ps.setObject( i++, k );
			}
			
			ResultSet rs = ps.executeQuery();
			System.out.println( cmd.toString() );
			
			if( rs.next() ) {
				
				Constructor<?> construtor = refTabela.getConstructor( (Class<?>[]) null );
				Object reg = construtor.newInstance( (Object[]) null );
				
				preencheRegistro( reg, rs );

				return (T) reg;
			} else {
				return null;
			}
			
		} catch (Exception e) {
			throw new ConnectionDBException( e );
		}
	}
	
	private void preencheRegistro( Object reg, ResultSet rs ) throws SecurityException, NoSuchMethodException {

		Field[] campos = reg.getClass().getDeclaredFields();

		int nrCampo = 1;
		
		for( Field cmp : campos ) {
			
			String nome = cmp.getName();
			
			if( !nome.equals( "serialVersionUID" ) ) {

				String setterName = "set" + Character.toUpperCase( nome.charAt( 0 ) ) + nome.substring( 1 );
				Method ms = reg.getClass().getDeclaredMethod( setterName, cmp.getType() );
				
				try {
					Object vl = rs.getObject( nrCampo++ );
					//System.out.println(setterName);
					if( cmp.getType() .getSimpleName().equals( "BigDecimal" ) ) {
						ms.invoke( reg, ((BigDecimal) vl).doubleValue() );
						
					} else {
						ms.invoke( reg, vl );
						
					}
					
				} catch (Exception e) {

					System.out.println( "Deu erro no campo: " + nome + " " + cmp.getType().getSimpleName() );
					
					e.printStackTrace();
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	public T findByAK( Object... ak ) throws ConnectionDBException {
		if( ak == null || ak.length == 0 ) {
			throw new ConnectionDBException( "Valores da chave alternativa não foram informados" );
		}
		
		Field[] campos = refTabela.getDeclaredFields();
		
		StringBuilder cmd = new StringBuilder( 200 );
		StringBuilder where = new StringBuilder( 50 );
		cmd.append( "Select " );

		int qtChaves = 0;
		
		for( Field f : campos ) {
			
			if( !f.getName().equals( "serialVersionUID" ) ) {
				cmd.append( "\n    " + f.getName() );
				cmd.append( "," );
				
				if( f.isAnnotationPresent( AlternateKey.class ) ) {
					where.append( f.getName() );
					where.append( " = ? and " ); 
					qtChaves++;
				}
			}
		}

		if( qtChaves != ak.length ) {
			throw new ConnectionDBException( "Número de chaves difere dos parâmetros informados" );
		}
		
		where.delete( where.length() - 4, where.length() );
		cmd.delete( cmd.length() - 1, cmd.length() );
		cmd.append( " \nfrom " );
		
		Table tbl = refTabela.getAnnotation( Table.class );
		cmd.append( "\n    " + tbl.tableName() );
		
		cmd.append( " \nwhere " );
		cmd.append( "\n    " + where );
		
		try {
			
			PreparedStatement ps = cnx.prepareStatement( cmd.toString() );
			
			int i = 1;
			for( Object k : ak ) {
				ps.setObject( i++, k );
			}
			
			ResultSet rs = ps.executeQuery();
			System.out.println( cmd.toString() );
			
			if( rs.next() ) {
				
				Constructor<?> construtor = refTabela.getConstructor( (Class<?>[]) null );
				Object reg = construtor.newInstance( (Object[]) null );
				
				preencheRegistro( reg, rs );

				return (T) reg;
			} else {
				return null;
			}
			
		} catch (Exception e) {
			throw new ConnectionDBException( e );
		}
	}
	
@SuppressWarnings("unchecked")
public T count( Object count, String where ) throws ConnectionDBException {
		
		if( count == null ) {
			throw new ConnectionDBException( "Valores para o Count não foram informados" );
		}
		
		Field[] campos = refTabela.getDeclaredFields();
		
		StringBuilder cmd = new StringBuilder( 200 );
		
		cmd.append( "SELECT " );
		
		for( Field f : campos ) {
			
			if(  f.isAnnotationPresent( Count.class ) ) {
				cmd.append( "\nCOUNT(" + f.getName() + ")" );
				cmd.append( "," );
			}
		}
		
		cmd.delete( cmd.length() - 1, cmd.length() );
		cmd.append( " \nFROM" );
		
		Table tbl = refTabela.getAnnotation( Table.class );
		cmd.append( "\n    " + tbl.tableName() );
		
		cmd.append( " \nWHERE" );
		cmd.append( "\n    " + where );
		
		try {
			
			PreparedStatement ps = cnx.prepareStatement( cmd.toString() );
			
			ResultSet rs = ps.executeQuery();
			System.out.println( cmd.toString() );
			
			if( rs.next() ) {
				
				Constructor<?> construtor = refTabela.getConstructor( (Class<?>[]) null );
				Object reg = construtor.newInstance( (Object[]) null );
				
				preencheRegistro( reg, rs );
				System.out.println("Testereg:" +reg);
				return (T) reg;
			} else {
				return null;
			}
			
		} catch (Exception e) {
			throw new ConnectionDBException( e );
		}
	}

	
	public List<T> find() throws ConnectionDBException {
		return find( null, null, null, null, null );
	}
	
	public List<T> find( String clausulaWhere ) throws ConnectionDBException {
		return find( clausulaWhere, null, null, null, null );
	}
	
	public List<T> find( String clausulaWhere, Object... camposSelecao ) throws ConnectionDBException {
		return find( clausulaWhere, camposSelecao, null, null, null );
	}
	
	public List<T> find( String clausulaWhere, Object[] camposSelecao, String orderBy ) throws ConnectionDBException {
		return find( clausulaWhere, camposSelecao, orderBy, null, null );
	}
	
	public List<T> find( String clausulaWhere, Object[] camposSelecao, String orderBy, Integer limit ) throws ConnectionDBException {
		return find( clausulaWhere, camposSelecao, orderBy, limit, null );
	}
	
	public List<T> find( String clausulaWhere, Object[] camposSelecao, String orderBy, Integer limit, Integer offset ) throws ConnectionDBException {
		StringBuilder cmd = new StringBuilder( "SELECT" );
		
		try {
			
			
			Field[] fields = refTabela.getDeclaredFields();

			for( Field f : fields ) {
				
				cmd.append( "\n    " );
				ColumnAlias o = f.getAnnotation( ColumnAlias.class );				
				
				if( !f.getName().equals( "serialVersionUID" ) ) {
					if( o != null ) {
						cmd.append( o.value() );
						cmd.append( "." );
					}
					ColumnName cn = f.getAnnotation( ColumnName.class );
					cmd.append( cn != null ?  cn.value() : f.getName() );
					cmd.append( "," );
				}
			}
			
			cmd.delete( cmd.length() - 1, cmd.length() );
			if( human ) cmd.append( "\n" );

			Table tn = refTabela.getAnnotation( Table.class );
			cmd.append( " \nFROM " );
			if( tn == null ) {
				cmd.append( refTabela.getSimpleName() );
			} else {
				cmd.append( "\n    " + tn.tableName() );
				
				if( tn.alias().trim().length() > 0 ) {
					cmd.append( " " );
					cmd.append( tn.alias() );
				}
			}

			if( human ) cmd.append( "\n" );
			ArrayList<JoinRule> joins = getJoins();
			
			if( joins != null ) {
				addJoins( joins, cmd );
			}

			if( clausulaWhere != null ) {
				
				clausulaWhere = clausulaWhere.trim();
				
				if( !clausulaWhere.toLowerCase().startsWith( "where " ) ) {
					cmd.append( " \nWHERE " );
				}
				
				cmd.append( "\n    " + clausulaWhere );
				if(clausulaWhere.contains("like")){
					
				}else{
					//cmd.append( " = ? " );
				}
			}
                        
                        if (orderBy != null) {
                            cmd.append(" \nORDER BY ");
                            cmd.append( "\n    " + orderBy );
                        }
			
			if( limit != null ) {
				cmd.append( " \nLIMIT " );
				cmd.append( "\n    " + limit );
			}
			
			if( offset != null ) {
				cmd.append( " \nOFFSET " );
				cmd.append( "\n    " + offset );
			}

			Conexao cnx = ConnectionsPool.getInstance().getConnection();
			
			try {
				
				PreparedStatement ps = cnx.prepareStatement( cmd.toString() );
				
				if( camposSelecao != null ) {
					for( int i = 0; i < camposSelecao.length; i++ ) {
						ps.setObject( i + 1, camposSelecao[ i ] );
					}
				}
				
				if( showCommand ) System.out.println( ps.toString() );
				ResultSet rs = ps.executeQuery();

				if( rs.next() ) {
					
					while( !rs.isAfterLast() ) {
						T reg = refTabela.newInstance();						
						preencheRegistro( reg, rs );
						result.add( reg );
						rs.next();					
					}
				}
			} finally {
				cnx.libera();
			}
			
			return result;
		} catch ( Exception e ) {
			System.out.println( cmd.toString() );
			throw new ConnectionDBException( e );
		}
		
		
	}
	
	private void addJoins( ArrayList<JoinRule> joins, StringBuilder cmd ) {

		for( JoinRule rule : joins ) {

			cmd.append(  " " );
			
			switch( rule.type() ) {
				case FULL:  cmd.append( "full" ); break;
				case INNER: cmd.append( "inner" ); break;
				case LEFT:  cmd.append( "left" ); break;
				case RIGHT: cmd.append( "right" ); break;
			}

			cmd.append( " join " );
			cmd.append( rule.tableName() );
			
			if( !rule.alias().trim().equals( "" ) ) {
				cmd.append( " " );
				cmd.append( rule.alias() );
			}
			
			cmd.append( " on " );
			cmd.append( rule.condition() );
			if( human ) cmd.append( "\n" );
		}
	}

	private ArrayList<JoinRule> getJoins() {
		
		ArrayList<JoinRule> ar = null;
		JoinRule jr = refTabela.getAnnotation( JoinRule.class );
		
		if( jr != null ) {
			ar = new ArrayList<JoinRule>();
			ar.add( jr );
		}
		
		JoinList jl = refTabela.getAnnotation( JoinList.class );
		
		if( jl != null ) {
			
			if( ar == null ) {
				ar = new ArrayList<JoinRule>();
			}
			
			for( JoinRule ruler : jl.value() ) {
				ar.add( ruler );
			}
		}
		 
		return ar;
	}

	public boolean isFound() {
		return found;
	}
	
	public T getReg() {
		return reg;
	}
	
	public void setShowCommand( boolean showCommand ) {
		this.showCommand = showCommand;
	}
	
	public boolean isShowCommand() {
		return showCommand;
	}
	
	public void setHuman( boolean human ) {
		this.human = human;
	}
	
	public boolean isHuman() {
		return human;
	}
	
	public boolean findLogin( Object... login ) throws ConnectionDBException {
		
		if( login == null || login.length == 0 ) {
			throw new ConnectionDBException( "Valores da chave primária não foram informados" );
		}
		
		Field[] campos = refTabela.getDeclaredFields();
		
		StringBuilder cmd = new StringBuilder( 200 );
		StringBuilder where = new StringBuilder( 50 );
		cmd.append( "SELECT " );

		int qtChaves = 0;
		
		for( Field f : campos ) {
			
			if( f.isAnnotationPresent( Login.class ) ) {
				cmd.append( "\n    " + f.getName() );
				cmd.append( "," );
				where.append( f.getName() );
				where.append( " = ? and " ); 
				qtChaves++;
				
			}
		}

		
		if( qtChaves != login.length ) {
			System.out.println("Chaves com a @Login = " + qtChaves);
			System.out.println("Numero de chaves informardas = " + login.length);
			throw new ConnectionDBException( "Número de chaves difere dos parâmetros informados" );
		}
		 
		where.delete( where.length() - 4, where.length() );
		cmd.delete( cmd.length() - 1, cmd.length() );
		cmd.append( " \nFROM" );
		
		View vw = refTabela.getAnnotation( View.class );
		cmd.append( "\n    " + vw.viewName() );

		cmd.append( " \nWHERE" );
		cmd.append( "\n    " + where );
		
		try {
			
			PreparedStatement ps = cnx.prepareStatement( cmd.toString() );
			
			int i = 1;
			for( Object k : login ) {
				ps.setObject( i++, k );
			}
			
			ResultSet rs = ps.executeQuery();
			System.out.println( cmd.toString() );
			
			if( rs.next() ) {
				Constructor<?> construtor = refTabela.getConstructor( (Class<?>[]) null );
				Object reg = construtor.newInstance( (Object[]) null );
				
				preencheLogin(reg, rs);
				
				System.out.println("Login true");
				return true;
			} else {
				System.out.println("Login false");
				return false;
			}
			
		} catch (Exception e) {
			throw new ConnectionDBException( e );
		}
	}
	
	private void preencheLogin( Object reg, ResultSet rs ) throws SecurityException, NoSuchMethodException {

		Field[] campos = reg.getClass().getDeclaredFields();

		int nrCampo = 1;
		
		for( Field cmp : campos ) {
			
			String nome = cmp.getName();
			
			if( cmp.isAnnotationPresent( Login.class ) ) {

				String setterName = "set" + Character.toUpperCase( nome.charAt( 0 ) ) + nome.substring( 1 );
				Method ms = reg.getClass().getDeclaredMethod( setterName, cmp.getType() );
				
				try {
					Object vl = rs.getObject( nrCampo++ );

					if( cmp.getType() .getSimpleName().equals( "BigDecimal" ) ) {
						ms.invoke( reg, ((BigDecimal) vl).doubleValue() );
						
					} else {
						ms.invoke( reg, vl );
						
					}
					
				} catch (Exception e) {

					System.out.println( "Deu erro no campo: " + nome + " " + cmp.getType().getSimpleName() );
					
					e.printStackTrace();
				}
			}
		}
	}
	
public boolean findfound( Object... login ) throws ConnectionDBException {
		
		if( login == null || login.length == 0 ) {
			throw new ConnectionDBException( "Valores da chave primária não foram informados" );
		}
		
		Field[] campos = refTabela.getDeclaredFields();
		
		StringBuilder cmd = new StringBuilder( 200 );
		StringBuilder where = new StringBuilder( 50 );
		cmd.append( "SELECT " );

		int qtChaves = 0;
		
		for( Field f : campos ) {
			
			if( f.isAnnotationPresent( PrimaryKey.class ) ) {
				cmd.append( "\n    " + f.getName() );
				cmd.append( "," );
				where.append( f.getName() );
				where.append( " = ? and " ); 
				qtChaves++;
				
			}
		}

		
		if( qtChaves != login.length ) {
			System.out.println("Chaves com a @Login = " + qtChaves);
			System.out.println("Numero de chaves informardas = " + login.length);
			throw new ConnectionDBException( "Número de chaves difere dos parâmetros informados" );
		}
		 
		where.delete( where.length() - 4, where.length() );
		cmd.delete( cmd.length() - 1, cmd.length() );
		cmd.append( " \nFROM" );
		
		Table vw = refTabela.getAnnotation( Table.class );
		cmd.append( "\n    " + vw.tableName() );

		cmd.append( " \nWHERE" );
		cmd.append( "\n    " + where );
		
		try {
			
			PreparedStatement ps = cnx.prepareStatement( cmd.toString() );
			
			int i = 1;
			for( Object k : login ) {
				ps.setObject( i++, k );
			}
			
			ResultSet rs = ps.executeQuery();
			System.out.println( cmd.toString() );
			
			if( rs.next() ) {
				Constructor<?> construtor = refTabela.getConstructor( (Class<?>[]) null );
				Object reg = construtor.newInstance( (Object[]) null );
				
				preenchefound(reg, rs);
				
				System.out.println("true");
				return true;
			} else {
				System.out.println("false");
				return false;
			}
			
		} catch (Exception e) {
			throw new ConnectionDBException( e );
		}
	}
	
	private void preenchefound( Object reg, ResultSet rs ) throws SecurityException, NoSuchMethodException {

		Field[] campos = reg.getClass().getDeclaredFields();

		int nrCampo = 1;
		
		for( Field cmp : campos ) {
			
			String nome = cmp.getName();
			
			if( cmp.isAnnotationPresent( PrimaryKey.class ) ) {

				String setterName = "set" + Character.toUpperCase( nome.charAt( 0 ) ) + nome.substring( 1 );
				Method ms = reg.getClass().getDeclaredMethod( setterName, cmp.getType() );
				
				try {
					Object vl = rs.getObject( nrCampo++ );

					if( cmp.getType() .getSimpleName().equals( "BigDecimal" ) ) {
						ms.invoke( reg, ((BigDecimal) vl).doubleValue() );
						
					} else {
						ms.invoke( reg, vl );
						
					}
					
				} catch (Exception e) {

					System.out.println( "Deu erro no campo: " + nome + " " + cmp.getType().getSimpleName() );
					
					e.printStackTrace();
				}
			}
		}
	}

}


