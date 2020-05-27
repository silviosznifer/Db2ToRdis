package br.com.bb.Agendador;

import java.sql.Time;

public class AgendamentoJob {
	private Job nome;
	private int periodicidade = 1; // 1-diário, 2-eventual
	private Time dataHoraDeExecucao;
	public Job getNome() {
		return nome;
	}
	public void setNome(Job nome) {
		this.nome = nome;
	}
	public int getPeriodicidade() {
		return periodicidade;
	}
	public void setPeriodicidade(int periodicidade) {
		this.periodicidade = periodicidade;
	}
	public Time getDataHoraDeExecucao() {
		return dataHoraDeExecucao;
	}
	public void setDataHoraDeExecucao(Time horaDeExecucao) {
		this.dataHoraDeExecucao = horaDeExecucao;
	}
	
	
	

}
