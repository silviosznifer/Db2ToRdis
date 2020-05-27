package br.com.bb.log;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class Log {
	
	private static Log instancia;
	protected ArrayList<String> conteudo;
	
	private Log() {
		
	}
	
	public static synchronized Log getInstance() {
		if (instancia == null) {
			instancia = new Log();
			instancia.conteudo = new ArrayList<String>();
		}
		return instancia;
	}
	
	public synchronized void incluir(String id, String texto) {
		Time dt = new Time(System.currentTimeMillis());
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String dataHora = formatter.format(dt);
		
		instancia.conteudo.add(dataHora + " - "+id+" - "+texto);
		
		if (instancia.conteudo.size() > 5) {
			Thread t = new Thread(new Arquivo());
			t.start();
		}
		
	}

}
