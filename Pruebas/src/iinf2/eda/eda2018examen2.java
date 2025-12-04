package iinf2.eda;

import iinf2.eda.libs.*;

public class eda2018examen2 {
    //EJERCICIO1
    public class Ejercicio1<E extends Comparable<E>> extends MonticuloBinario<E> {
        public E eliminarMax() {
            int max = this.talla / 2 + 1;
            for (int i = max + 1; i <= talla; i++) {
                if (elArray[i].compareTo(elArray[max]) > 0) max = i;
            }
            E maxVal = elArray[max];
            elArray[max] = elArray[talla--];
            E aux = elArray[max];
            while (max > 1 && aux.compareTo(elArray[max / 2]) < 0) {
                elArray[max] = elArray[max / 2];
                max = max / 2;
            }
            elArray[max] = aux;
            return maxVal;
        }
    }

    //EJERCICIO 2
    public class Ejercicio2 extends GrafoDirigido {
        public Ejercicio2(int numVertices) {
            super(numVertices);
        }

        public int aristasHA() {
            int counter = 0;
            visitados = new int[numVertices()];
            for (int i = 0; i < numVertices(); i++) {
                if (visitados[i] == 0) counter += aristasHA(i);
            }
            return counter;
        }

        protected int aristasHA(int v) {
            visitados[v] = 1;
            ListaConPI<Adyacente> l = adyacentesDe(v);
            int counter = 0;
            for (l.inicio(); !l.esFin(); l.siguiente()) {
                int w = l.recuperar().getDestino();
                if (visitados[w] == 0) {
                    counter += aristasHA(w);
                } else if (visitados[w] == 1) {
                    counter++;
                }
            }
            visitados[v] = 2;
            return counter;
        }
    }
}
