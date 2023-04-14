package caso2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class MemoriaReal {

	private static int cantMarcos;
	private ArrayList<String> lstPaginas = new ArrayList<>();

	private String getUltimaPagina() {
		ArrayList<String> lstPaginas = this.lstPaginas;
		
	}

	public void obtenerUltimaMemor

	public void cargarArchivo() throws IOException {
		File archivo = new File ("./test_cases/test_1.txt");
		FileReader fr;
		
		fr = new FileReader (archivo);
		BufferedReader br = new BufferedReader(fr);
		String linea = br.readLine();
		String[] parts = linea.split("=");
		cantMarcos =Integer.valueOf(parts[1]);
		linea = br.readLine();
		lstPaginas = new ArrayList<>();
		while (linea != null) {
			parts = linea.split(",");
			lstPaginas.add(parts[1]);
			linea = br.readLine();
		}
		Manejador mj = new Manejador(cantMarcos);
		CargadeReferencias hilo = new CargadeReferencias(lstPaginas,mj);
		hilo.start();

	
		
		
	}
	
	
	
	public static void main(String[] args) throws IOException {
		MemoriaReal m = new MemoriaReal();
		m.cargarArchivo();
		Manejador mj = new Manejador(cantMarcos);
	}
}
