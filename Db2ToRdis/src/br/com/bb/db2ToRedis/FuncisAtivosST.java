package br.com.bb.db2ToRedis;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FuncisAtivosST extends SelectThread {
	
	public FuncisAtivosST() {
		super("jobArhFuncisAtivos", "Select count(*) as qtde from DB2DWH.VS_CAD_BASC AS A WHERE A.SITUACAO_215 >= 100 AND SITUACAO_215 < 600");
	}

	@Override
	public void gravaRedis(ResultSet res) throws SQLException {
		res.next();
		this.cr.con.set("FuncisAtivos", res.getString("qtde"));
	}

}
