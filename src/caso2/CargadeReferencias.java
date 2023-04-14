package caso2;

import java.util.ArrayList;

public class CargadeReferencias extends Thread{

	private ArrayList<String> paginasLista;
	private Manejador mj;
	private int cant;
	
	public CargadeReferencias(ArrayList<String>pags, Manejador mj) {
		paginasLista = pags;
		this.mj = mj;
	}
	
	public void run() {
		mj.llenarMemoriaVirtual(paginasLista);
		for (int i = 0; i < paginasLista.size(); i++) {
			synchronized(mj) {
			
				mj.agregarPagina(Integer.valueOf(paginasLista.get(i)));
				try {
					sleep(2);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println(mj.getFallas());
	}
}
