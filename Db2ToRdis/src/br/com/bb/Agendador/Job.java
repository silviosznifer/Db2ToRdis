package br.com.bb.Agendador;

public abstract class Job {
	public Job proximoJob = null;
	
	protected boolean preProcessamento() {
		return true;
	};
	
	protected abstract boolean executar();
	
	public int sequencia() {
		int retorno = -1;
		if (this.preProcessamento()) {
			boolean resultadoExecucao = this.executar();
			boolean resultadoPosProc = this.posProcessamento(resultadoExecucao);
			if (resultadoExecucao && resultadoPosProc) retorno =  0; // execução total com sucesso
			else if (resultadoExecucao && !resultadoPosProc) retorno = 1; // execução com sucesso e falha no pós processamento
			else retorno = 2; // execução com falha e e pós processamento com sucesso
		}
		if (proximoJob != null && retorno == 0) return proximoJob.sequencia();
		else return retorno;
		
	}
	protected boolean posProcessamento(boolean resultadoExecucao) {
		return true;
	}
	
}
