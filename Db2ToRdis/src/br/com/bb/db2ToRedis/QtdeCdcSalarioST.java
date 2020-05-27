package br.com.bb.db2ToRedis;

import java.sql.ResultSet;
import java.sql.SQLException;

public class QtdeCdcSalarioST extends SelectThread {

	public QtdeCdcSalarioST() {
		super("jobQtdeCdcSalarioST", 
				"SELECT COUNT(*) AS qtde FROM DB2CDC.CTR_CDC AS CDC WHERE CDC.DT_FRMZ_CTR_CDC >= CURRENT_DATE AND CD_LNCD = 2991;");
	}

	@Override
	public void gravaRedis(ResultSet res) throws SQLException {
		
		res.next();
		this.cr.con.set("QtdeCdcSalarioST", res.getString("qtde"));

	}

}
