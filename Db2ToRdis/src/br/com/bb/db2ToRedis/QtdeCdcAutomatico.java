package br.com.bb.db2ToRedis;

import java.sql.ResultSet;
import java.sql.SQLException;

public class QtdeCdcAutomatico extends SelectThread {
	
	public QtdeCdcAutomatico() {
		super("jobQtdeCdcAutomatico", 
				"SELECT COUNT(*) AS qtde_cdc_automatico FROM DB2CDC.CTR_CDC AS CDC WHERE CDC.DT_FRMZ_CTR_CDC >= CURRENT_DATE AND CD_LNCD = 2997;");
	}

	@Override
	public void gravaRedis(ResultSet res) throws SQLException {
		
		res.next();
		this.cr.con.set("QuantidadeCDCAutomatico", res.getString("qtde_cdc_automatico"));
		

	}

}
