package iinf2.eda;
import iinf2.eda.libs.*;

public class eda2022examen2 {
    //EJERCICIO1
    public class Ejercicio1<E extends Comparable<E>> extends ABB<E> {
        public boolean esPostOrden(ListaConPI<E> l) {
            if (raiz == null) return false;
            l.inicio();
            esPostOrden(raiz, l);
            return l.esFin();
        }

        private void esPostOrden(NodoABB<E> raiz, ListaConPI<E> l) {
            if (raiz.izq != null && !l.esFin() && raiz.dato.compareTo(l.recuperar()) > 0) esPostOrden(raiz.izq, l);
            if (raiz.der != null && !l.esFin() && raiz.dato.compareTo(l.recuperar()) < 0) esPostOrden(raiz.der, l);
            if (raiz.dato.compareTo(l.recuperar()) == 0) l.siguiente();
        }
    }

    //EJERCICIO2
    public class Ejercicio2<E extends Comparable<E>> extends ABB<E> {
        public int sinHermanos() {
            if (talla() < 2) return 0;
            return sinHermanos(raiz);
        }

        public int sinHermanos(NodoABB<E> raiz) {
            if (raiz.izq != null && raiz.der == null) return 1 + sinHermanos(raiz.izq);
            if (raiz.izq == null && raiz.der != null) return 1 + sinHermanos(raiz.der);
            if (raiz.izq != null && raiz.der != null) return sinHermanos(raiz.izq) + sinHermanos(raiz.der);
            return 0;
        }
    }

    //EJERCICIO3
    public class Ejercicio3 extends GrafoNoDirigido {
        public Ejercicio3(int numVertices) {
            super(numVertices);
        }

        public int contarRamificaciones() {
            int counter = 0;
            for (int i = 0; i < numVertices(); i++) {
                if (adyacentesDe(i).talla() > 1) counter++;
            }
            return counter;
        }
    }

    //EJERCICIO4
    public class Ejercicio4 extends GrafoDirigido {
        public Ejercicio4(int numVertices) {
            super(numVertices);
        }

        public boolean buscar(int origen, int destino) {
            return buscar(origen, destino, true) || buscar(origen, destino, false);
        }

        public boolean buscar(int origen, int destino, boolean esPar) {
            if (origen == destino) return true;
            ListaConPI<Adyacente> adyacentes = this.elArray[origen];
            for (adyacentes.inicio(); !adyacentes.esFin(); adyacentes.siguiente()) {
                int w = adyacentes.recuperar().getDestino();
                int p = (int) adyacentes.recuperar().getPeso();
                if ((p % 2 == 0) == !esPar && buscar(w, destino, !esPar)) return true;
            }
            return false;
        }
    }

}
