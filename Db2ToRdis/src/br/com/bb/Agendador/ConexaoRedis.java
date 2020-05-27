package br.com.bb.Agendador;

import br.com.bb.log.Log;
import redis.clients.jedis.Jedis;

public class ConexaoRedis extends Conexao {
	private String servidor = "localhost";
	private String senha = "";
	private int porta = 6379;
	
	public ConexaoRedis(String servidor, String senha, int porta) {
		this.servidor = servidor;
		this.senha = senha;
		this.porta = porta;
		this.conectar();
	}
	
	public ConexaoRedis() {
		
	}
	
	public String getServidor() {
		return servidor;
	}

	public void setServidor(String servidor) {
		this.servidor = servidor;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Jedis con = null;

	public boolean conectar() {
		try {
			this.con = new Jedis(servidor, this.porta);
			if (!this.senha.isEmpty())
				this.con.auth(this.senha);
			return true;
		} catch (Exception e) {
			Log.getInstance().incluir("Conexao Redis", "Falha - "+e.getMessage());
			e.printStackTrace();
			return false;
		}
		
	}
	
	public boolean encerrar() {
		this.con.close();
		return true;
	}

}
