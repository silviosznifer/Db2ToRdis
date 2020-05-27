package br.com.bb.Agendador;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Agendador {

	public static void main(String[] args) throws InterruptedException {
		
		// Cadastro de Jobs e a hora de execução
		// Obter do Redis os agendados e gravar os em execução/abendados
		Time dt = new Time(System.currentTimeMillis());
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		String strDate = formatter.format(dt);
		
		ConexaoRedis redis = new ConexaoRedis();
		redis.con.select(15);
				
		List<String> listaJobsAgendados = redis.con.lrange("jobsAgendados",0,-1);
		System.out.println(listaJobsAgendados); 
		// usar Json para criara AgendamentoJob
		
		
		List<Job> listaJobsEmExecução = new ArrayList<Job>();
		List<Job> listaJobsAbendados = new ArrayList<Job>();
		

		// loop de verificação de hora e execução de sequência
		while (true) {
			dt.setTime(System.currentTimeMillis());
			strDate = formatter.format(dt);
			redis.con.set("ultimaExecucao", strDate);
			System.out.println(strDate);
			
			// Corre a lista de jobs para verificar se algum job deve ser iniciado
			// Verifica se esse job ainda está em execução na lista
			// Executa os Jobs que devem ser executados
			// Envia para Lista de Jobs abendados aqueles que deram erro
			// Manter controles de execução no Redis
			Thread.sleep(1000);
		}
		
		
		
	}

}
