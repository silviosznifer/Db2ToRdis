package br.com.bb.log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

public class Arquivo implements Runnable {

	public void run() {
		File f = this.abrirArquivo();
		this.gravarNoArquivo(f);
	}
	
	private synchronized File abrirArquivo() {
		File f = new File("/var/log/DB2ToRedis/log.txt");
		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return f;
	}
	
	private synchronized void gravarNoArquivo(File f) {
		try {
			//FileOutputStream fos = new FileOutputStream(f);
			BufferedWriter bw = new BufferedWriter(new FileWriter(f, true));
			Iterator<String> i = Log.getInstance().conteudo.iterator();
			String n;
			while (i.hasNext()) {
				n = i.next();
				bw.write(n);
				bw.newLine();
			}
			bw.flush();
			bw.close();
			Log.getInstance().conteudo.clear();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
