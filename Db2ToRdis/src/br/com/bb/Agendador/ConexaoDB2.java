package br.com.bb.Agendador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import br.com.bb.log.Log;

public class ConexaoDB2 extends Conexao {
	private String usuario = "DB2MTT01";
	// senha lá embaixo
	
	public Connection con = null;
	public Statement stm = null;
	
	public ConexaoDB2() {
		this.conectar();
	}
	
	public boolean conectar() {
		boolean retorno = false;
		try
	    {
	        Class.forName("com.ibm.db2.jcc.DB2Driver");
	        con = DriverManager.getConnection("jdbc:db2://gwdb2.bb.com.br:50100/bdb2p04", usuario, senha);
	        stm = con.createStatement();
	        
	    }
	    catch(Exception e)
	    {
	    	Log.getInstance().incluir("Conexao DB2", "Falha - "+e.getMessage());
	        e.printStackTrace();
	    }
		return retorno;
	}
	
	public boolean encerrar() {
		try {
			this.con.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private String senha = "28002939";

}
