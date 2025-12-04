package librerias.estructurasDeDatos.grafos;

/** Clase Arista: representa una arista de un grafo.
 *  
 *  @version noviembre 2021
 */
 
public class Arista implements Comparable<Arista>{
    
    // UNA Arista TIENE UN vertice origen y UN vertice destino:
    /*COMPLETAR*/
    protected int v;
    protected int w;
    
    // UNA Arista TIENE UN peso:
    /*COMPLETAR*/
    protected double p;
    
    /** Crea una arista (v, w) con peso p.
      *
      * @param v  Vertice origen
      * @param w  Vertice destino
      * @param p  Peso
     */
    public Arista(int v, int w, double p) {
        this.v = v;
        this.w = w;
        this.p = p;
    }

    /** Devuelve el vertice origen de una arista.
      *
      * @return int vertice origen
     */
    public int getOrigen() {    
        return v;
    }
    
    /** Devuelve el vertice destino de una arista.
      *
      * @return int vertice destino
     */
    public int getDestino() {  
        return w;
    }
    
    /** Devuelve el peso de una arista.
      *
      * @return double Peso de la arista
     */
    public double getPeso() {
        return p;  
    }
    
    /** Devuelve un String que representa una arista
      * en formato (origen, destino, peso)
      *
      * @return  String que representa la arista
     */
    public String toString() {
        return "(" + this.v + ", " + this.w + ", " + this.p + ")";   
    }
    
    public int compareTo(Arista a){
        double pesoA = this.getPeso();
        double pesoB = a.getPeso();
        if(pesoA == pesoB){
            return 0;
        } else if (pesoA > pesoB){
            return 1;
        } else {
            return -1;
        }
    }
}