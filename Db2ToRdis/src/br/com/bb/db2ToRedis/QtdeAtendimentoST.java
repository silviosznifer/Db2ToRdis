package br.com.bb.db2ToRedis;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;

public class QtdeAtendimentoST extends SelectThread {

	public QtdeAtendimentoST() {
		super("jobQtdeAtendimento", 
				"SELECT\n" + 
				"\n" + 
				"       DATE(CONCAT(YEAR(MIV.TS_CRIC_MSG), CONCAT('-', CONCAT(MONTH(MIV.TS_CRIC_MSG), CONCAT('-', DAY(MIV.TS_CRIC_MSG)))))) AS \"DATA\",\n" + 
				"\n" + 
				"       MIV.CD_ITCE_CNL_ATDT AS ITCE_CNL,\n" + 
				"\n" + 
				"       CANAIS.NM_ITCE_CNL_ATDT,\n" + 
				"\n" + 
				"       COUNT(*) AS QTDE_ATDT\n" + 
				"\n" + 
				"FROM DB2MIV.TX_MSG_EXPS AS MIV\n" + 
				"\n" + 
				"LEFT JOIN DB2I185B.CANAIS AS CANAIS ON CANAIS.CD_ITCE_CNL_ATDT = MIV.CD_ITCE_CNL_ATDT\n" + 
				"\n" + 
				"WHERE DATE(MIV.TS_CRIC_MSG) = CURRENT DATE\n" + 
				"\n" + 
				"--WHERE DATE(MIV.TS_CRIC_MSG) = '#DATA_PROC#'\n" + 
				"\n" + 
				"AND CANAIS.CD_ITCE_CNL_ATDT IS NOT NULL\n" + 
				"\n" + 
				"GROUP BY\n" + 
				"\n" + 
				"       DATE(CONCAT(YEAR(MIV.TS_CRIC_MSG), CONCAT('-', CONCAT(MONTH(MIV.TS_CRIC_MSG), CONCAT('-', DAY(MIV.TS_CRIC_MSG)))))),\n" + 
				"\n" + 
				"       MIV.CD_ITCE_CNL_ATDT,\n" + 
				"\n" + 
				"       CANAIS.NM_ITCE_CNL_ATDT");
	}

	@Override
	public void gravaRedis(ResultSet res) throws Exception {
			
		LocalDateTime agora = LocalDateTime.now();
		int hora = agora.get(ChronoField.HOUR_OF_DAY);
		int min = agora.get(ChronoField.MINUTE_OF_HOUR);
		String chave;
		
		while (res.next()) {
			chave = res.getString("ITCE_CNL")+"_"+res.getString("NM_ITCE_CNL_ATDT").trim();
			
			// Grava registro acumulado on-line 
			this.cr.con.set(chave, res.getString("QTDE_ATDT"));
							
			// Meia-noite apaga o registro de hora em hora
			if (hora == 0 && min == 0) {
				this.cr.con.del(chave+"_hora");
			}
			
			// Quando hora cheia, inclui registro daquela na hora na lista
			if (min == 0) {
				this.cr.con.zadd(chave+"_hora", hora, res.getString("QTDE_ATDT"));
			}
		}
		
		if (min == 0) {
			Thread.sleep(60000); // Espera 1 minuto para não incluir a informação de hora em hora multiplas vezes
		}

	}

}
