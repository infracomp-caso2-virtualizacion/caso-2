import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Scanner;

public class ManejadorMemoria {
    
	//Atributos
	private static int numFilas;
	private static int numCols;
	private static int tamInt;
	private static int tamPag;
	private static int numPag;

	private static int[][] matriz1;
	private static int[][] matriz2;
	private static int[][] matriz3;

	private static MemoriaVirtual memoriaVirtual;


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
		matriz3 = new int[numFilas][numCols];
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

	private void leerArchivo(String path) {
		
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
	
	private File modo1()
	{
		String data = "";
		data += "TP = "+ Integer.toString(tamPag);
		data += "\nNF = "+ Integer.toString(numFilas);
		data += "\nNC = "+ Integer.toString(numCols);
		data += "\nNR = "+ Integer.toString(numFilas*numCols*3);
		int pagAct = 0;
		int desplazamientoAct = 0;

		ArrayList<String> auxMatrizA = new ArrayList<String>();
		for(int i = 0; i < numFilas; i++)
		{
			for(int j = 0; j < numCols; j++)
			{
				String dataTemp = "\n[A-"+Integer.toString(i)+"-"+Integer.toString(j)+"]"+", "+Integer.toString(pagAct)+", "+Integer.toString(desplazamientoAct);
				auxMatrizA.add(dataTemp);
				pagAct += (int) ((desplazamientoAct+tamInt)/tamPag);
				desplazamientoAct = (desplazamientoAct+tamInt)%tamPag;
				if ((desplazamientoAct+tamInt) > tamPag)
				{
					pagAct ++;
					desplazamientoAct = 0;
				}
			}
		}
		ArrayList<String> auxMatrizB = new ArrayList<String>();
		for(int i = 0; i < numFilas; i++)
		{
			for(int j = 0; j < numCols; j++)
			{
				String dataTemp = "\n[B-"+Integer.toString(i)+"-"+Integer.toString(j)+"]"+", "+Integer.toString(pagAct)+", "+Integer.toString(desplazamientoAct);
				auxMatrizB.add(dataTemp);
				pagAct += (int) ((desplazamientoAct+tamInt)/tamPag);
				desplazamientoAct = (desplazamientoAct+tamInt)%tamPag;
				if ((desplazamientoAct+tamInt) > tamPag)
				{
					pagAct ++;
					desplazamientoAct = 0;
				}
			}
		}
		ArrayList<String> auxMatrizC = new ArrayList<String>();
		for(int i = 0; i < numFilas; i++)
		{
			for(int j = 0; j < numCols; j++)
			{
				String dataTemp = "\n[C-"+Integer.toString(i)+"-"+Integer.toString(j)+"]"+", "+Integer.toString(pagAct)+", "+Integer.toString(desplazamientoAct);
				auxMatrizC.add(dataTemp);
				pagAct += (int) ((desplazamientoAct+tamInt)/tamPag);
				desplazamientoAct = (desplazamientoAct+tamInt)%tamPag;
				if ((desplazamientoAct+tamInt) > tamPag)
				{
					pagAct ++;
					desplazamientoAct = 0;
				}
			}
		}

		for(int i = 0; i < (numFilas*numCols); i++)
		{
			data += auxMatrizA.get(i);
			data += auxMatrizB.get(i);
			data += auxMatrizC.get(i);
		}

		return escribirArchivo(data);
	}

	
	private void modo2(File archivo)
	{
		
	}

    public static void main(String[] args) throws Exception 
    {
        ManejadorMemoria manejadorMemoria = new ManejadorMemoria();
		manejadorMemoria.imprimirTitulo();
		// Obtiene los inputs del usuario
        Scanner sc = new Scanner ( System.in );
		System.out.println( "\nPor favor ingrese el número de filas de las matrices (debe ser positivo): ");
		numFilas = sc.nextInt ( );
		System.out.println( "\nPor favor ingrese el número de columnas de las matrices (debe ser positivo): ");
		numCols = sc.nextInt ( );
		System.out.println( "\nPor favor ingrese el tamaño de entero (Bytes): ");
		tamInt = sc.nextInt ( );
		System.out.println( "\nPor favor ingrese el tamaño de una página (Bytes): ");
		tamPag = sc.nextInt ( );
		System.out.println( "\nPor favor ingrese el número de marcos de página (debe ser positivo): ");
		numPag = sc.nextInt ( );
		manejadorMemoria.crearMatrices();
		//Se inicializa la memoria virtual
		memoriaVirtual = new MemoriaVirtual();

		File archivoSalidaModo1 = manejadorMemoria.modo1();

		manejadorMemoria.modo2(archivoSalidaModo1);



    }
    
}
