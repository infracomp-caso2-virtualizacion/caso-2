import java.util.ArrayList;

public class MemoriaVirtual 
{

    //Clase monitor donde se guardan las estructuras de datos compartidas entre Threads

    //Atributos

    //La posición de la lista índica el número de la página virtual y el valor en la lista la posición en la memoria física
    private ArrayList<Integer> tabladePaginas = new ArrayList<Integer>();
    private ArrayList<Pagina> memoriaFisica = new ArrayList<Pagina>();

    //La posición de la lista índica el número de la página virtual y el valor en la lista la posición en el Swap
    private ArrayList<Integer> tablaAuxiliar = new ArrayList<Integer>();
    private ArrayList<Pagina> espacioAuxiliarSWAP = new ArrayList<Pagina>();
    


    //Constructor
    public MemoriaVirtual() 
    {

    }

    //Métodos
    
}
