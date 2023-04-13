import java.util.ArrayList;
import java.util.HashMap;

public class MemoriaVirtual 
{

    //Clase monitor donde se guardan las estructuras de datos compartidas entre Threads

    //Atributos

    //La posición de la lista índica el número de la página virtual y el valor en la lista la posición en la memoria física
    //Tamaño definido por MP
    //Tal vez puede ser -1 el equivalente a que no esté en la memoria real
    private ArrayList<Integer> tabladePaginas = new ArrayList<Integer>();
    //Puede ser la llave la página en memoria física y el valor la página virtual, o al revés
    private HashMap<Integer,Integer> marcosDePagina= new HashMap<Integer,Integer>();

    //Aquí deberían ir las demás variables para calcular fallos de páginas y algoritmo de envejecimiento



    //Constructor
    public MemoriaVirtual() 
    {

    }

    //Métodos
    
}
