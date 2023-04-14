package caso2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Manejador {
	private int cant;
	private int fallas;
	private ArrayList<Pagina> memoriaVirtual;
	private ArrayList<Pagina> marcoPagina;

	public Manejador(int cant) {
		marcoPagina = new ArrayList<>();
		memoriaVirtual = new ArrayList<>();
		this.cant = cant;
		fallas = 0;
	}

	public int getFallas() {
		return fallas;
	}

	public class Pagina{
		private int identificador;
		public int edad;
		private boolean usada;


		public Pagina(int id) {
			this.identificador = id;
			this.edad = 10000000;
			this.usada = false;
		}

		public int getId() {
			return identificador;
		}

		public boolean getUsada() { return usada; }

		public void setUsada(boolean bool) {
			usada = bool;
		}



	}

	public synchronized void quitarPagina(int id) {
		for (int i = 0; i <marcoPagina.size(); i++) {
			Pagina pag = marcoPagina.get(i);
			if (pag.getId() == id) {
				marcoPagina.remove(pag);
			}
		}
	}

	public synchronized Pagina envejecimiento(ArrayList<Pagina> memoriaVirtual) {
		Pagina ret = memoriaVirtual.get(0);

		for (Pagina pagina : memoriaVirtual) {
			if (pagina.getUsada()) {
				pagina.edad = pagina.edad << 1;
				if (pagina.edad > ret.edad || !ret.getUsada()) {
					ret = pagina;
				}
			} else {
				pagina.edad = 10000000;
			}
		}
		return ret;
	}

	public synchronized void agregarPagina(int i) {
		Pagina yaesta = buscarPagina(i);
		if (yaesta == null) {

			if (marcoPagina.size() >= cant) {
				fallas += 1;


				//quitarPagina() con el id que devuelve el otro thread


			}

			Pagina nueva = memoriaVirtual.get(i);
			nueva.setUsada();
			marcoPagina.add(nueva);

		}

	}

	public void llenarMemoriaVirtual(ArrayList<String> lst) {
		Set<String> paginas = new HashSet<String>(lst);
		ArrayList<String> lstunica = new ArrayList<String>(paginas);
		for (int i = 0; i < lstunica.size(); i++) {
			Pagina pag = new Pagina(Integer.valueOf(lstunica.get(i)));
			memoriaVirtual.add(pag);
		}

	}


	public Pagina buscarPagina(int id) {
		Pagina pag = null;
		for (int i = 0; i <marcoPagina.size(); i++) {
			if (marcoPagina.get(i).getId() == id) {
				pag = marcoPagina.get(i);
			}
		}

		return pag;
	}
}
