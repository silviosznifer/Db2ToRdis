package br.com.bb.db2ToRedis;

import java.sql.ResultSet;

public class ControleST extends SelectThread {
	private String versao = "1.0.2";
	
	public ControleST() {
		super("jobControle", "");
	}

	@Override
	public void gravaRedis(ResultSet res) throws Exception {
		this.cr.con.set("versaoJobIntraday", versao);
	}

}
