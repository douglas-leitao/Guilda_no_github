package br.com.freelancer.databaseConnection;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class DBParameters {
	private static DBParameters mySelf;
	
	private Properties lista;
	
	private DBParameters() throws ConnectionDBException {
		lista = new Properties();
		File arqParametros = new File( "C:/Users/Douglas/Documents/GitHub/Guilda_no_github/src/br/com/freelancer/databaseConnection/GuildaDBParameters.xml" );
		
		carregaParametros( arqParametros );
	}
	
	public void carregaParametros( File arqParametros ) throws ConnectionDBException {
		if( arqParametros.exists() ) {
			try {
				FileInputStream fis = new FileInputStream( arqParametros );

				try {
					lista = new Properties();
					lista.loadFromXML( fis );
				} finally {
					fis.close();
				}
			} catch (Exception e) {
				throw new ConnectionDBException( "Erro ao carregar arquivo de parametros: '" + arqParametros.getAbsolutePath() + "'", e );
			}
		} else {
			throw new ConnectionDBException( "Nao encontrei arquivo de parametros: '" + arqParametros.getAbsolutePath() + "'" );
		}
	}
	
	public void salvaParametros( String caminhoArquivo ) throws ConnectionDBException {
		File arq = new File( caminhoArquivo );
		salvaParametros( arq );
	}
	
	public void salvaParametros(File arqParametros) throws ConnectionDBException {
		if( arqParametros.exists() ) {
			arqParametros.delete();
		}
		
		try {
			arqParametros.createNewFile();
			System.out.println( arqParametros.getAbsolutePath() );
			
			FileOutputStream fos = new FileOutputStream( arqParametros );
			
			try {
				lista.storeToXML( fos, "Parametros do Fevas DB" );
			} finally {
				fos.close();
			}		
		} catch (IOException e) {
			throw new ConnectionDBException( "Erro criando arquivo de Parametros", e );
		}
	}

	public static DBParameters getInstance() throws ConnectionDBException {
		if( mySelf == null ) {
			mySelf = new DBParameters();
		}
		return mySelf;
	}
	
	public void setParameter( String key, String value ) {
		lista.put( key, value );
	}

	public String getParameter( String key ) {
		return lista.getProperty( key );
	}
}