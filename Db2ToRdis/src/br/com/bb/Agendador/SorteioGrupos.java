package br.com.bb.Agendador;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class SorteioGrupos {

	public static void main(String[] args) {
		Random gerador = new Random();
		
		String[] m1 = {"Fred", "Neydson"};
		ArrayList m2 = new ArrayList();
		
		m2.add("Silvio"); m2.add("Gisele"); m2.add("Beck"); m2.add("Vitor");
		
		
		for (int i = 0; i < 2; i++) {
			System.out.println("Grupo " + (i+1)  + ": \n" + m1[i]);
			for (int j = 0; j < 2; j++) {
				int k = gerador.nextInt(m2.size());
				System.out.println(m2.get(k));
				m2.remove(k);
			}
			System.out.println();
			
		}
		
		
				
		

	}

}
