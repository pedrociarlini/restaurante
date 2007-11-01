package algoritmo;

import java.awt.Point;

public class SensoresEquipe2 {

	private int visaoIdentificacao[];//identificacao
	private int ambienteOlfatoEquipe[];
	private int ambienteOlfatoOponente[];
	
	private int energiaEquipe[];//visao Equipe
	private int energiaOponente[];//visao Energia
	
	private int direcaoEquipe[];//visao Equipe
	private int direcaoOponente[];//visao orientacao
	
	private Point posicao;
	private int nivelEnergia;
	
	public int[] getAmbienteOlfatoEquipe() {
		return ambienteOlfatoEquipe;
	}
	public void setAmbienteOlfatoEquipe(int[] ambienteOlfatoEquipe) {
		this.ambienteOlfatoEquipe = ambienteOlfatoEquipe;
	}
	public int[] getAmbienteOlfatoOponente() {
		return ambienteOlfatoOponente;
	}
	public void setAmbienteOlfatoOponente(int[] ambienteOlfatoOponente) {
		this.ambienteOlfatoOponente = ambienteOlfatoOponente;
	}
	public int[] getDirecaoEquipe() {
		return direcaoEquipe;
	}
	public void setDirecaoEquipe(int[] direcaoEquipe) {
		this.direcaoEquipe = direcaoEquipe;
	}
	public int[] getDirecaoOponente() {
		return direcaoOponente;
	}
	public void setDirecaoOponente(int[] direcaoOponente) {
		this.direcaoOponente = direcaoOponente;
	}
	public int[] getEnergiaEquipe() {
		return energiaEquipe;
	}
	public void setEnergiaEquipe(int[] energiaEquipe) {
		this.energiaEquipe = energiaEquipe;
	}
	public int[] getEnergiaOponente() {
		return energiaOponente;
	}
	public void setEnergiaOponente(int[] energiaOponente) {
		this.energiaOponente = energiaOponente;
	}
	public int getNivelEnergia() {
		return nivelEnergia;
	}
	public void setNivelEnergia(int nivelEnergia) {
		this.nivelEnergia = nivelEnergia;
	}
	public Point getPosicao() {
		return posicao;
	}
	public void setPosicao(Point posicao) {
		this.posicao = posicao;
	}
	public int[] getVisaoIdentificacao() {
		return visaoIdentificacao;
	}
	public void setVisaoIdentificacao(int[] visaoIdentificacao) {
		this.visaoIdentificacao = visaoIdentificacao;
	}

}
