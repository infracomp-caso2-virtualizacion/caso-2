

import java.util.ArrayList;

public class CargadeReferencias extends Thread{

	private ArrayList<Integer> paginasLista;
	private Monitor mj;
	private int cant;
	private String tipo;
	private boolean continuar;
	public int numFallas;
	
	public int getCantFallas() {
		return numFallas;
	}
	
	public void getCantFallasStr() {
		System.out.println("Ocurrieron " + numFallas + " fallas");
	}
	public void setCantFallas(int fallas) {
		this.numFallas = fallas;
	}
	public String getTipo() {
		return tipo;
	}
	
	public CargadeReferencias(ArrayList<Integer> pags, Monitor mj, String tipo) {
		paginasLista = pags;
		this.mj = mj;
		this.tipo = tipo;
	}
	
	public void run() {
		if (this.getTipo().equals("cargador")){
			for (int i = 0; i < paginasLista.size(); i++) {
				mj.agregarPagina(paginasLista.get(i));
				try {
					sleep(2);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			}
			mj.terminar();
	
		} 
		else if (this.getTipo().equals("envejecimiento")) {
			while(mj.continuar()) {
				mj.envejecimiento(mj.getMarcoPagina());
				try {
					sleep(1);
					
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}
		setCantFallas(mj.getFallas());
		
	}
}

