

import java.util.ArrayList;

public class CargadeReferencias extends Thread{

	private ArrayList<Integer> paginasLista;
	private Monitor mj;
	private int cant;
	private String tipo;
	private boolean continuar;
	
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
				System.out.println("carg");
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
				System.out.println("envej");
				mj.envejecimiento(mj.getMarcoPagina());
				try {
					sleep(1);
					
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println("Ocurrieron " + mj.getFallas() + " fallas");
		}
			
		
	}
}

