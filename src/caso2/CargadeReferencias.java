package caso2;

import java.util.ArrayList;

public class CargadeReferencias extends Thread{

	private ArrayList<String> paginasLista;
	private Manejador mj;
	private int cant;
	private String tipo;
	private boolean continuar;
	
	public String getTipo() {
		return tipo;
	}
	
	public CargadeReferencias(ArrayList<String>pags, Manejador mj, String tipo) {
		paginasLista = pags;
		this.mj = mj;
		this.tipo = tipo;
	}
	
	public void run() {
		mj.llenarMemoriaVirtual(paginasLista);
		
		
			continuar = true;
			if (this.getTipo().equals("cargador")){
				for (int i = 0; i < paginasLista.size(); i++) {
						
						System.out.println("carg");
						mj.agregarPagina(Integer.valueOf(paginasLista.get(i)));
						try {
							sleep(2);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					
				}
			continuar= false;
		
		} else if (this.getTipo().equals("envejecimiento")) {
			for (int i = 0; i < paginasLista.size()*2; i ++) {
				System.out.println("envej");
				mj.envejecimiento(mj.getMarcoPagina());
				try {
					sleep(1);
					
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
			
		System.out.println("Ocurrieron " + mj.getFallas() + " fallas");
		
	}
}

