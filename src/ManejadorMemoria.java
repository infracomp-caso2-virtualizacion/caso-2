import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class ManejadorMemoria {
    
	//Atributos
	private static int numFilas;
	private static int numCols;
	private static int tamInt;
	private static int tamPag;
	private static int numPag;

	private static int[][] matriz1;
	private static int[][] matriz2;

	
	private ArrayList<Integer> lstPaginas = new ArrayList<>();


    //Métodos
    public void imprimirTitulo ( ) {
        System.out.println ( "==================================================" );
        System.out.println ( "ISIS 2203 - Infraestructura Computacional - 202310" );
        System.out.println ( "------------ Caso 2 - Memoria Virtual ------------" );
        System.out.println ( "---- s.forerog2 - j.torres16 - codigo2 -----" );
        System.out.println ( "==================================================" );
    }


    public void crearMatrices()
	{
		matriz1 = new int[numFilas][numCols];
		matriz2 = new int[numFilas][numCols];
		int maxInt = (int) (Math.pow(2,(tamInt*8))/2);

		for(int i = 0; i < numFilas; i++)
		{
			for(int j = 0; j < numCols; j++)
			{
				matriz1[i][j] = ThreadLocalRandom.current().nextInt(0, maxInt - 1);
				matriz2[i][j] = ThreadLocalRandom.current().nextInt(0, maxInt - 1);
			}
		}
		
		System.out.println("Matrices");
		System.out.println("==========================");
		imprimirMatrices();
	}

	
	private void imprimirMatrices() 
	{
		System.out.println("Matriz 1");
		for(int i = 0; i < numFilas; i++)
		{
			for(int j = 0; j < numCols; j++)
			{
				System.out.println(matriz1[i][j] + "\t");
			}
			System.out.println();
		}
		System.out.println("Matriz 2");
		for(int i = 0; i < numFilas; i++)
		{
			for(int j = 0; j < numCols; j++)
			{
				System.out.println(matriz2[i][j] + "\t");
			}
			System.out.println();
		}
		
		
	}

	

	private File escribirArchivo(String data)
	{
		File file = new File("modo1salida.txt");
		FileWriter fr = null;
		try {
			fr = new FileWriter(file);
			fr.write(data);
		} 
		catch (IOException e) {
			System.out.println("Ocurrió un érror al crear el archivo del modo 1.");
			e.printStackTrace();
		}
		finally{
			try {
				fr.close();
			} 
			catch (IOException e) {
				System.out.println("Ocurrió un érror al crear el archivo del modo 1.");
				e.printStackTrace();
			}
		}
		return file;
	}
	
	private void modo1()
	{
		String data = "";
		data += "TP="+ Integer.toString(tamPag);
		data += "\nNF="+ Integer.toString(numFilas);
		data += "\nNC="+ Integer.toString(numCols);
		data += "\nNR="+ Integer.toString(numFilas*numCols*3);
		data += "\nMP="+ Integer.toString(numPag);
		int pagActA = 0;
		int desplazamientoActA = 0;
		int tamMatriz = tamInt*numFilas*numCols;
		int pagActB = (int) tamMatriz/tamPag;
		int desplazamientoActB = tamMatriz%tamPag;
		int pagActC = (int) 2*tamMatriz/tamPag;
		int desplazamientoActC = (2*tamMatriz)%tamPag;

		for(int i = 0; i < numFilas; i++)
		{
			for(int j = 0; j < numCols; j++)
			{
				data += "\n[A-"+Integer.toString(i)+"-"+Integer.toString(j)+"]"+","+Integer.toString(pagActA)+","+Integer.toString(desplazamientoActA);
				data += "\n[B-"+Integer.toString(i)+"-"+Integer.toString(j)+"]"+","+Integer.toString(pagActB)+","+Integer.toString(desplazamientoActB);
				data += "\n[C-"+Integer.toString(i)+"-"+Integer.toString(j)+"]"+","+Integer.toString(pagActC)+","+Integer.toString(desplazamientoActC);
				pagActA += (int) ((desplazamientoActA+tamInt)/tamPag);
				desplazamientoActA = (desplazamientoActA+tamInt)%tamPag;
				pagActB += (int) ((desplazamientoActB+tamInt)/tamPag);
				desplazamientoActB = (desplazamientoActB+tamInt)%tamPag;
				pagActC += (int) ((desplazamientoActC+tamInt)/tamPag);
				desplazamientoActC = (desplazamientoActC+tamInt)%tamPag;
			}
		}
		escribirArchivo(data);
	}

	
	private void modo2(String archivoModo1)
	{
		try (BufferedReader br = new BufferedReader(new FileReader(archivoModo1))) {
			String linea;
			for (int i = 0; i < 5; i++) {
				linea = br.readLine();
				String[] campos = linea.split("=");
				if (campos.length == 2) {
					String nombre = campos[0];
					String valor = campos[1];
					if (nombre.equals("NF")) {numFilas = Integer.valueOf(valor);}
					if (nombre.equals("NC")) {numCols = Integer.valueOf(valor);}
					if (nombre.equals("TE")) {tamInt = Integer.valueOf(valor);}
					if (nombre.equals("TP")) {tamPag = Integer.valueOf(valor);}
					if (nombre.equals("MP")) {numPag = Integer.valueOf(valor);}
				} else {
					System.err.println("Error: la línea no tiene el formato correcto: " + linea);
				}
				
			}
			linea = br.readLine();
			lstPaginas = new ArrayList<>();
			while (linea != null) {
				String[] parts = linea.split(",");
				lstPaginas.add(Integer.valueOf(parts[1]));
				linea = br.readLine();
			}
		} 
		catch (IOException e) {
			System.err.println("Error al leer el archivo: " + e.getMessage());
		}	
		
		Monitor mj = new Monitor(numPag);
		mj.llenarMemoriaVirtual(lstPaginas);
		CargadeReferencias hilo1 = new CargadeReferencias(lstPaginas, mj, "cargador");
		CargadeReferencias hilo2 = new CargadeReferencias(lstPaginas, mj, "envejecimiento");
		hilo1.start();
		hilo2.start();

	
	}
	
    public static void main(String[] args) throws Exception 
    {
        ManejadorMemoria manejadorMemoria = new ManejadorMemoria();
		manejadorMemoria.imprimirTitulo();
		Scanner sc = new Scanner ( System.in );
		String archivoModo1 = "";
		System.out.println( "\nPor favor ingrese 1 para ejecutar modos 1 y 2, o 2 para solo el modo 2: ");
		String opcion = sc.nextLine();
		if (opcion.equals("1"))
		{
			System.out.println( "\nPor favor ingrese la ruta del archivo (ej: \"datos.txt\"): ");
			String archivo = sc.nextLine();
			try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
				String linea;
				while ((linea = br.readLine()) != null) {
					String[] campos = linea.split("=");
					if (campos.length == 2) {
						String nombre = campos[0];
						String valor = campos[1];
						if (nombre.equals("NF")) {numFilas = Integer.valueOf(valor);}
						if (nombre.equals("NC")) {numCols = Integer.valueOf(valor);}
						if (nombre.equals("TE")) {tamInt = Integer.valueOf(valor);}
						if (nombre.equals("TP")) {tamPag = Integer.valueOf(valor);}
						if (nombre.equals("MP")) {numPag = Integer.valueOf(valor);}
					} else {
						System.err.println("Error: la línea no tiene el formato correcto: " + linea);
					}
				}
			} catch (IOException e) {
				System.err.println("Error al leer el archivo: " + e.getMessage());
			}

			manejadorMemoria.crearMatrices();
			//Se inicializa la memoria virtual
			manejadorMemoria.modo1();
			archivoModo1 = "modo1salida.txt";
		}
		else if (opcion.equals("2"))
		{
			System.out.println( "\nPor favor ingrese la ruta del archivo (ej: \"datos.txt\"): ");
			archivoModo1 = sc.nextLine();
		}
		manejadorMemoria.modo2(archivoModo1);

    }
    
}
