package br.com.bb.executor;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import br.com.bb.Agendador.ConexaoRedis;
import br.com.bb.db2ToRedis.Gerenciador;
import br.com.bb.log.Log;

public class Executor {

	public static void main(String[] args) {
		
		if (args.length > 0 && "-agora".equals(args[0])) {
			Thread t = new Thread(new Gerenciador());
			t.start();
			System.out.println("Executando agora");
			Log.getInstance().incluir("Executor", "Executando agora");
		} else {
		
			ConexaoRedis cr = new ConexaoRedis("dxl1miv00010.dispositivos.bb.com.br", "phhdtdqlxcjh", 6382);		
			
			String jobControle = cr.con.get("jobControle");
			
			System.out.println(jobControle);
			
			if (jobControle == null) {
				Thread t = new Thread(new Gerenciador());
				t.start();
				System.out.println("Executando");
				Log.getInstance().incluir("Executor", "Executando por controle null");
			} else {
				LocalDateTime dataHoraAtual = LocalDateTime.now();
				
				DateTimeFormatter f = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
				LocalDateTime dataJob = LocalDateTime.parse(jobControle.substring(0, 19), f);
				Duration diferenca = Duration.between(dataJob, dataHoraAtual);
		
				System.out.println(diferenca.getSeconds());
				
				if (diferenca.getSeconds() > 300) {
					Thread t = new Thread(new Gerenciador());
					t.start();
					System.out.println("Executando");
					Log.getInstance().incluir("Executor", "Executando por decurso de prazo");
				} else {
					System.out.println("Não executando. Para executar imediatamente, utilize o parametro '-agora'.");
					Log.getInstance().incluir("Executor", "Não executando. Para executar imediatamente, utilize o parametro '-agora'.");
				}
			}
			
			System.out.println("Finalizar");
	
			cr.encerrar();
		}
	}

}
