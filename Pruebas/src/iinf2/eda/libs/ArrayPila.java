package iinf2.eda.libs;

public class ArrayPila<E> implements Pila<E> {

    // Nodo interno
    private static class Nodo<E> {
        E dato;
        Nodo<E> siguiente;

        Nodo(E dato, Nodo<E> siguiente) {
            this.dato = dato;
            this.siguiente = siguiente;
        }
    }

    // Referencia al tope de la pila
    private Nodo<E> tope;

    public ArrayPila() {
        tope = null;
    }

    @Override
    public void apilar(E e) {
        tope = new Nodo<>(e, tope);
    }

    @Override
    public E desapilar() {
        if (esVacia()) throw new IllegalStateException("Pila vacía");
        E dato = tope.dato;
        tope = tope.siguiente;
        return dato;
    }

    @Override
    public E tope() {
        if (esVacia()) throw new IllegalStateException("Pila vacía");
        return tope.dato;
    }

    @Override
    public boolean esVacia() {
        return tope == null;
    }
}
