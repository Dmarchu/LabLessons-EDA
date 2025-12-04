package iinf2.eda.libs;

public interface Pila<E> {
    void apilar(E e);
    E desapilar();
    E tope();
    boolean esVacia();
}

