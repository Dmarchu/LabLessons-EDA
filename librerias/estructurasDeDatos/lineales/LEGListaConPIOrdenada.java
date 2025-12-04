package librerias.estructurasDeDatos.lineales;
import librerias.estructurasDeDatos.modelos.ListaConPI;

/**
 * Write a description of class LEGListaConPiOrdenada here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class LEGListaConPIOrdenada<E extends Comparable> 
extends LEGListaConPI<E> implements ListaConPI<E> {
    /**
     * Constructor for objects of class LEGListaConPiOrdenada
     */
    public LEGListaConPIOrdenada() {
        super();
    }

    public void insertar(E e){
        inicio();
        while(!esFin() && e.compareTo(recuperar())>0){
            siguiente();
        }
        super.insertar(e);
    }
}
