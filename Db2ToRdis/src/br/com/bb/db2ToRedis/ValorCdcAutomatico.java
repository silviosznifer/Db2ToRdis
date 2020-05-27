package br.com.bb.db2ToRedis;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ValorCdcAutomatico extends SelectThread {
	
	public ValorCdcAutomatico() {
		super("jobValorCdcAutomatico", 
				"SELECT SUm(sdo.VL_SDO_DVDR) as Valor FROM DB2CDC.CTR_CDC AS CDC LEFT OUTER JOIN DB2CDC.VRC_CTR_OPR_CDC AS sdo ON (cdc.NR_CTR_OPR = sdo.NR_CTR_OPR) WHERE CDC.DT_FRMZ_CTR_CDC >= CURRENT_DATE AND CD_LNCD = 2997;");
	}

	@Override
	public void gravaRedis(ResultSet res) throws SQLException {
		
		res.next();
		this.cr.con.set("ValorCDCAutomatico", res.getString("Valor"));
		

	}

}
