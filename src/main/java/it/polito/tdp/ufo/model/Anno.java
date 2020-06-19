package it.polito.tdp.ufo.model;

public class Anno implements Comparable<Anno>{
	
	private int anno;
	private int numeroAvvistamenti;
	/**
	 * @param anno
	 * @param numeroAvvistamenti
	 */
	public Anno(int anno, int numeroAvvistamenti) {
		super();
		this.anno = anno;
		this.numeroAvvistamenti = numeroAvvistamenti;
	}
	/**
	 * @return the anno
	 */
	public int getAnno() {
		return anno;
	}
	/**
	 * @param anno the anno to set
	 */
	public void setAnno(int anno) {
		this.anno = anno;
	}
	/**
	 * @return the numeroAvvistamenti
	 */
	public int getNumeroAvvistamenti() {
		return numeroAvvistamenti;
	}
	/**
	 * @param numeroAvvistamenti the numeroAvvistamenti to set
	 */
	public void setNumeroAvvistamenti(int numeroAvvistamenti) {
		this.numeroAvvistamenti = numeroAvvistamenti;
	}
	@Override
	public String toString() {
		return  anno + " numeroAvvistamenti=" + numeroAvvistamenti ;
	}
	@Override
	public int compareTo(Anno o) {
		// TODO Auto-generated method stub
		return this.anno-o.getAnno();
	}
	
	

}
