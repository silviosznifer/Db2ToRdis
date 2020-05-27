package br.com.bb.db2ToRedis;

import java.sql.Time;
import java.text.SimpleDateFormat;

import br.com.bb.log.Log;

public class Gerenciador implements Runnable{

	public static void main(String[] args) throws InterruptedException {
		Gerenciador g = new Gerenciador();
		g.run();
	}
	
	public void run() {
		boolean log = true;
		int segundosDeIntervalo = 15;
		
		// Data e hora de execução do Job Gerenciador
		SelectThread controleST = new ControleST();
		
		// Número de Funcis Ativos
		SelectThread fast = new FuncisAtivosST();
				
		// CDC Automático
		SelectThread qtdeCDC = new QtdeCdcAutomatico();
		SelectThread valorCDC = new ValorCdcAutomatico();
		
		// CDC Automático
		SelectThread qtdeCDCCons = new QtdeCdcConsignacaoST();
		SelectThread valorCDCCons = new ValorCdcConsigniacaoST();
				
		// CDC Automático
		SelectThread qtdeCDCConsNC = new QtdeCdcConsignacaoNC_ST();
		SelectThread valorCDCConsNC = new ValorCdcConsigniacaoNC_ST();
		
		// CDC Automático
		SelectThread qtdeCDCSalario = new QtdeCdcSalarioST();
		SelectThread valorCDCSalario = new ValorCdcSalarioST();
		
		// Qtde de Atemdimentos por Canal
		SelectThread qtdeAtendimentoST = new QtdeAtendimentoST();
		qtdeAtendimentoST.setComLog(log);
		
		Thread t1 = null, t2 = null, t3 = null, t4 = null, t5 = null, t6 = null, t7 = null, t8 = null, t9 = null, t10 = null, t11 = null;
		
		while (true) {
			
			t1 = new Thread(fast);
			t2 = new Thread(qtdeCDC);
			t3 = new Thread(valorCDC);
			t4 = new Thread(qtdeCDCCons);
			t5 = new Thread(valorCDCCons);
			t6 = new Thread(qtdeCDCConsNC);
			t7 = new Thread(valorCDCConsNC);
			t8 = new Thread(qtdeCDCSalario);
			t9 = new Thread(valorCDCSalario);
			t10 = new Thread(qtdeAtendimentoST);
			t11 = new Thread(controleST);
			
			System.out.println(Gerenciador.getDataHoraAtual() + " - Gerenciador - Iniciando Threads.");
			
			if (!fast.isExecutando()) t1.start();
			if (!qtdeCDC.isExecutando()) t2.start();
			if (!valorCDC.isExecutando()) t3.start();
			if (!qtdeCDCCons.isExecutando()) t4.start();
			if (!valorCDCCons.isExecutando()) t5.start();
			if (!qtdeCDCConsNC.isExecutando()) t6.start();
			if (!valorCDCConsNC.isExecutando()) t7.start();
			if (!qtdeCDCSalario.isExecutando()) t8.start();
			if (!valorCDCSalario.isExecutando()) t9.start();
			if (!qtdeAtendimentoST.isExecutando()) t10.start();
			if (!controleST.isExecutando()) t11.start();
			
			System.out.println(Gerenciador.getDataHoraAtual() + " - Gerenciador - Threads iniciadas. Aguardando "+segundosDeIntervalo+" segundos.");
			Log.getInstance().incluir("Gerenciador", "Threads iniciadas. Aguardando "+segundosDeIntervalo+" segundos.");
			
			try {
				Thread.sleep(segundosDeIntervalo * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
				Log.getInstance().incluir("Gerenciador", e.getMessage());
			}
		}

	}
	
	public static String getDataHoraAtual() {
		Time dt = new Time(System.currentTimeMillis());
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		return formatter.format(dt);
	}

}
