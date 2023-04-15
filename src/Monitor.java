

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Monitor {
	private int MP;
	private int fallas;
	private ArrayList<Pagina> memoriaVirtual;
	private ArrayList<Pagina> marcoPagina;
	private boolean continuar = true;
	//La posición de la lista índica el número de la página virtual y el valor en la lista la posición en la memoria física
    private ArrayList<Integer> tabladePaginas = new ArrayList<Integer>();

	public Monitor(int cant) {
		marcoPagina = new ArrayList<>();
		memoriaVirtual = new ArrayList<>();
		this.MP = cant;
		fallas = 0;
	}

	public synchronized ArrayList<Pagina> getMarcoPagina(){
		return marcoPagina;
	}

	public synchronized ArrayList<Integer> getTablaPagina(){
		return tabladePaginas;
	}

	public int getFallas() {
		return fallas;
	}

	public synchronized void terminar()
	{
		continuar = false;
	}

	public synchronized boolean continuar()
	{
		return continuar;
	}

	public synchronized void llenarMemoriaVirtual(ArrayList<Integer> lst) {
		ArrayList<Integer> pagLst = new ArrayList<>();
		for (int i = 0; i < lst.size(); i++) 
		{
			if (!pagLst.contains(lst.get(i)))
			{
				pagLst.add(lst.get(i));
			}
		}
		Collections.sort(pagLst);
		for (int i = 0; i < pagLst.size(); i++) 
		{
			Pagina pag = new Pagina(pagLst.get(i));
			memoriaVirtual.add(pag);
			tabladePaginas.add(-1);
		}

	}


	public synchronized void agregarPagina(int i) {
		int yaesta = tabladePaginas.get(i);
		if (yaesta == -1) {
			fallas += 1;
			Pagina nueva = memoriaVirtual.get(i);
			if (marcoPagina.size() == MP) {
				int pos = getYoungest();
				Pagina eliminada = marcoPagina.remove(pos);
				eliminada.edad = 10000000;
				marcoPagina.add(pos, nueva);
				tabladePaginas.set(i, pos);
				tabladePaginas.set(eliminada.getId(), -1);
			}
			else
			{
				tabladePaginas.set(i, marcoPagina.size());
				marcoPagina.add(nueva);
			}
		}
		else
		{
			marcoPagina.get(yaesta).setUsada(true);
		}
	}

	public synchronized void envejecimiento(ArrayList<Pagina> memoriaVirtual) {

		for (Pagina pagina : marcoPagina) {
			if (pagina.getUsada()) {
				pagina.edad =  (int) pagina.edad/10;
				pagina.edad = pagina.edad+10000000;
				pagina.setUsada(false);
			} else {
				pagina.edad = (int) pagina.edad/10;
			}
		}

	}


	private int getYoungest() {
		Pagina ret = marcoPagina.get(0);
		int pos = 0;
		for (int i = 0; i < marcoPagina.size(); i++) {
			Pagina pagina = marcoPagina.get(i);
			if (pagina.edad < ret.edad) {
				ret = pagina;
				pos = i;
			}
		}
		return pos;
	}
}
