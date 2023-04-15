public class Pagina{
    private int identificador;
    public int edad;
    private boolean usadaRec;


    public Pagina(int id) {
        this.identificador = id;
        this.edad = 10000000;
        this.usadaRec = false;
    }

    public int getId() {
        return identificador;
    }

    public boolean getUsada() { return usadaRec; }

    public void setUsada(boolean bool) {
        usadaRec = bool;
    }


}