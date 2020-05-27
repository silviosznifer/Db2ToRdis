package br.com.bb.db2ToRedis;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Random;

import br.com.bb.Agendador.ConexaoDB2;
import br.com.bb.Agendador.ConexaoRedis;
import br.com.bb.log.Log;

public abstract class SelectThread implements Runnable {
	private boolean comLog = false;
	private String nome = "";
	public String sql = "";
	private boolean executando = false;
	public ConexaoRedis cr;
	public ConexaoDB2 cdb2;
	
	public SelectThread() {
		Random rd = new Random();
		this.nome = "job" + rd.nextInt();
	}
	
	public SelectThread(String nome, String sql) {
		this.nome = nome;
		this.sql = sql;
	}
	

	public void run() {
		this.executando = true;
		
		if (this.comLog) {
			System.out.println(this.getDataHoraAtual() + " - "+this.nome+" - Conectando aos BDs.");
			Log.getInstance().incluir(this.nome, "Conectando aos BDs");
		}
		// Conecta nos BDs
		this.conectaBDs();
		
		// Grava controle de inicio de execução no Redis
		this.gravaLogRedis("iniciando");
		
		if (this.comLog) {
			System.out.println(this.getDataHoraAtual() + " - "+this.nome+" - Executando DB2.");
			Log.getInstance().incluir(this.nome, "Executando DB2");
		}
		// Executa SQL no Db2
		
		try {
			if (!this.getSql().isEmpty()) {
				ResultSet res = this.executaSQL();
				
				if (res != null) { 
				
					if (this.comLog) {
						System.out.println(this.getDataHoraAtual() + " - "+this.nome+" - Gravando no Redis.");
						Log.getInstance().incluir(this.nome, "Gravando no Redis");
					}
					// Grava resultado no Redis
					this.gravaRedis(res);
					
					this.executando = false;
					// Grava controle de fim de execução no Redis
					this.gravaLogRedis("finalizado ok");
					if (this.comLog) {
						System.out.println(this.getDataHoraAtual() + " - "+this.nome+" - Job Finalizado.");
						Log.getInstance().incluir(this.nome, "Job Finalizado");
					}
				}
			} else {
				// SQL vazio - grava só o log de execução
				this.gravaRedis(null);
				this.executando = false;
				this.gravaLogRedis("SQL vazio - finalizado ok");
			}
		} catch (Exception e) {
			System.out.println(this.getDataHoraAtual() + " - "+this.nome+" - "+e.getMessage());
			e.printStackTrace();
			this.executando = false;
			Log.getInstance().incluir(this.nome, "Erro: "+e.getMessage());
		}
		
		// Fecha as conexões
		this.encerrar();
		
	}
	
	public void conectaBDs() {
		this.cr = new ConexaoRedis("dxl1miv00010.dispositivos.bb.com.br", "phhdtdqlxcjh", 6382);
		this.cdb2 = new ConexaoDB2();
	}
	
	public void encerrar() {
		this.cdb2.encerrar();
		this.cr.encerrar();
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public String getSql() {
		return sql;
	}


	public void setSql(String sql) {
		this.sql = sql;
	}


	public boolean isExecutando() {
		return executando;
	}


	public void setExecutando(boolean executando) {
		this.executando = executando;
	}


	public ConexaoRedis getCr() {
		return cr;
	}


	public void setCr(ConexaoRedis cr) {
		this.cr = cr;
	}


	public ConexaoDB2 getCdb2() {
		return cdb2;
	}


	public void setCdb2(ConexaoDB2 cdb2) {
		this.cdb2 = cdb2;
	}
	
	
	public String getDataHoraAtual() {
		Time dt = new Time(System.currentTimeMillis());
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		return formatter.format(dt);
	}
	
	public ResultSet executaSQL() throws SQLException {
		ResultSet res = null;
		
		res = this.cdb2.stm.executeQuery(this.sql);
		return res;
	}
	
	public void gravaLogRedis(String msg) {
		this.cr.con.set(this.nome, this.getDataHoraAtual() + " - executando=" + this.executando + " - msg=" + msg);
	}
	
	
	
	public boolean isComLog() {
		return comLog;
	}

	public void setComLog(boolean comLog) {
		this.comLog = comLog;
	}

	public abstract void gravaRedis(ResultSet res) throws Exception;

}
