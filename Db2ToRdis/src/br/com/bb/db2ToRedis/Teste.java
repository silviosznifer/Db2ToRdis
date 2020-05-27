package br.com.bb.db2ToRedis;

import java.time.LocalDateTime;
import java.time.temporal.ChronoField;

public class Teste {

	public static void main(String[] args) {
		//ConexaoRedis redis = new ConexaoRedis("dxl1miv00010.dispositivos.bb.com.br", "phhdtdqlxcjh", 6382);
		
		// ConexaoDB2 db2 = new ConexaoDB2();
		
		//redis.con.set("teste1_chave", "teste1_valor");
		
		//String sql = "Select count(*) as qtde from DB2DWH.VS_CAD_BASC AS A WHERE A.SITUACAO_215 >= 100 AND SITUACAO_215 < 600";
		//ResultSet res = null;
		
//		try {
//			res = db2.stm.executeQuery(sql);
//		} catch (SQLException e) {
//			
//			e.printStackTrace();
//		}
//		
//		try {
//			res.next();
//			System.out.println(res.getString("qtde"));
//		} catch (SQLException e) {
//			
//			e.printStackTrace();
//		}
		
		
		//System.out.println(redis.con.get("teste1_chave"));
		
		//redis.encerrar();
//		db2.encerrar();
		
		LocalDateTime agora = LocalDateTime.now();
		System.out.println(agora.get(ChronoField.HOUR_OF_DAY));
		System.out.println(agora.get(ChronoField.MINUTE_OF_HOUR));

	}

}
