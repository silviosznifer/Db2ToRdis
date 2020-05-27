package br.com.bb.db2ToRedis;

import java.sql.ResultSet;
import java.sql.SQLException;

public class QtdeCdcConsignacaoNC_ST extends SelectThread {

	public QtdeCdcConsignacaoNC_ST() {
		super("jobQtdeCdcConsignacaoNC_ST", 
				"SELECT COUNT(*) AS qtde FROM DB2CDC.CTR_CDC AS CDC WHERE CDC.DT_FRMZ_CTR_CDC >= CURRENT_DATE AND CD_LNCD = 2882;");
	}

	@Override
	public void gravaRedis(ResultSet res) throws SQLException {
		
		res.next();
		this.cr.con.set("QtdeCdcConsignacaoNC_ST", res.getString("qtde"));
	

	}

}
