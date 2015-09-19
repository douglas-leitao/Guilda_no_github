package br.com.freelancer.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class ParametrosDB {

	private static ParametrosDB mySelf;
	
	private Properties lista;
	
	
	private ParametrosDB() throws ConnectionDBException {

		
		File arqParametros = new File( "prmsDB.xml" );
		
		carregaParametros( arqParametros );
		//salvaParametros( arqParametros );
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
				throw new ConnectionDBException( "Erro ao carregar arquivo de parâmetros: '" + arqParametros.getAbsolutePath() + "'", e );
			}
			
		} else {
			throw new ConnectionDBException( "Não encontrei arquivo de parâmetros: '" + arqParametros.getAbsolutePath() + "'" );
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

	public static ParametrosDB getInstance() throws ConnectionDBException {

		if( mySelf == null ) {
			mySelf = new ParametrosDB();
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













