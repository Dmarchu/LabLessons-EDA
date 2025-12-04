package iinf2.eda.reexamen;

import iinf2.eda.libs.*;

public class eda2023examen2 {
    //EJERCICIO 1
    public class Ejercicio1 extends MonticuloBinario<Integer> {
        public Ejercicio1() {
            super();
        }

        public int posicionDe(int s) {
            return posicionDe(s, 1);
        }

        protected int posicionDe(int s, int pos) {
            if (s - elArray[pos] < 0 || pos > talla) return 0;
            if (s - elArray[pos] == 0) return pos;

            if (posicionDe(s, 2 * pos) != 0) return 2 * pos;
            if (posicionDe(s, 2 * pos + 1) != 0) return 2 * pos + 1;
            return 0;
        }
    }

    //EJERCICIO2
    public class Ejercicio2<E extends Comparable<E>> extends ABB<E> {
        public Ejercicio2() {
            super();
        }

        public boolean esCompleto() {
            return esCompleto(this.raiz);
        }

        protected boolean esCompleto(NodoABB<E> raiz) {
            if (raiz.izq == null && raiz.der == null) return true;
            if (raiz.izq != null && raiz.der != null) return esCompleto(raiz.izq) && esCompleto(raiz.der);
            return false;
        }
    }

    //EJERCICIO3
    public class Ejercicio3 extends GrafoNoDirigido {
        public Ejercicio3(int numVertices) {
            super(numVertices);
        }

        public boolean todasIguales() {
            visitados = new int[numVertices()];
            int last = todasIguales(0);
            for (int i = 1; i < numVertices(); i++) {
                if (visitados[i] == 0) {
                    if (last != todasIguales(i)) return false;
                }
            }
            return true;
        }

        protected int todasIguales(int v) {
            visitados[v] = 1;
            ListaConPI<Adyacente> l = adyacentesDe(v);
            int counter = 1;
            for (l.inicio(); !l.esFin(); l.siguiente()) {
                int w = l.recuperar().getDestino();
                if (visitados[w] == 0) {
                    visitados[w] = 1;
                    counter += todasIguales(w);
                }
            }
            return counter;
        }
    }
}
