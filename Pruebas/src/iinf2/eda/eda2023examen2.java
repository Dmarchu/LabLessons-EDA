package iinf2.eda;

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

        private int posicionDe(int s, int i) {
            if (s < 0 || i > talla) return 0;
            if (s == elArray[i]) return i;
            int posIzq = posicionDe(s - elArray[i], 2 * i);
            if (posIzq != 0) return posIzq;
            int posDer = posicionDe(s - elArray[i], 2 * i + 1);
            if (posDer != 0) return posDer;
            return 0;
        }
    }

    //EJERCICIO2
    public class Ejercicio2<E extends Comparable<E>> extends ABB<E> {
        public Ejercicio2() {
            super();
        }

        public boolean esCompleto() {
            return esCompleto(raiz);
        }

        private boolean esCompleto(NodoABB<E> raiz) {
            if (raiz.der == null && raiz.izq == null) return true;
            if (raiz.der != null && raiz.izq != null) return esCompleto(raiz.izq) && esCompleto(raiz.der);
            else return false;
        }
    }

    //EJERCICIO3
    public class Ejercicio3 extends GrafoNoDirigido {
        public Ejercicio3(int numVertices) {
            super(numVertices);
        }

        public boolean todasCCigualNumV() {
            visitados = new int[numVertices()];
            int NumV = todasCCigualNumV(0);
            for (int i = 1; i < numVertices(); i++) {
                if (visitados[i] == 0 && NumV != todasCCigualNumV(i)) return false;
            }
            return true;
        }

        private int todasCCigualNumV(int v) {
            visitados[v] = 1;
            int ContV = 1;
            ListaConPI<Adyacente> adyacentes = this.adyacentesDe(v);
            for (adyacentes.inicio(); !adyacentes.esFin(); adyacentes.siguiente()) {
                int w = adyacentes.recuperar().getDestino();
                if (visitados[w] == 0) ContV += todasCCigualNumV(w);
            }
            return ContV;
        }
    }
}
