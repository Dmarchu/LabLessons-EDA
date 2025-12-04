package iinf2.eda;

import iinf2.eda.libs.*;

public class eda2024examen2 {
    //EJERCICIO 1
    public class Ejercicio1<E extends Comparable<E>> extends MonticuloBinario<E> {
        public Ejercicio1() {
            super();
        }

        public void fusionar(MonticuloBinario<E> m2) {
            MonticuloBinario<E> aux = new MonticuloBinario<>();
            while (m2.esVacia()) {
                this.insertar(m2.recuperarMin());
                aux.insertar(m2.eliminarMin());
            }
            while (aux.esVacia()) m2.insertar(aux.eliminarMin());
        }

        public boolean verificarHijos() {
            for (int i = 0; i < this.talla / 2; i++) {
                if (2 * i + 1 < this.talla && this.elArray[2 * i].compareTo(this.elArray[2 * i + 1]) >= 0) return false;
            }
            return true;
        }
    }

    //EJERCICIO 2
    public class Ejercicio2 extends GrafoNoDirigido {
        public Ejercicio2(int numVertices) {
            super(numVertices);
        }

        public ListaConPI<Arista> nuevoGrafoDFS() {
            ListaConPI<Arista> aristas = new LEGListaConPI<>();
            this.visitados = new int[numVertices()];
            for (int v = 0; v < numVertices(); v++) {
                if (this.visitados[v] == 0) nuevoGrafoDFS(v, aristas);
            }
            return aristas;
        }

        private void nuevoGrafoDFS(int v, ListaConPI<Arista> aristas) {
            this.visitados[v] = 1;
            ListaConPI<Adyacente> adyacentes = this.adyacentesDe(v);
            for (adyacentes.inicio(); !adyacentes.esFin(); adyacentes.siguiente()) {
                int w = adyacentes.recuperar().getDestino();
                if (visitados[w] == 0) {
                    double p = adyacentes.recuperar().getPeso();
                    aristas.insertar(new Arista(v, w, p));
                    visitados[w] = 1;
                    nuevoGrafoDFS(w, aristas);
                }
            }
        }

        public ListaConPI<Arista> nuevoGrafoBFS() {
            visitados = new int[numVertices()];
            q = new ArrayCola<>();
            ListaConPI<Arista> aristas = new LEGListaConPI<>();
            for (int v = 0; v < numVertices(); v++) {
                if (this.visitados[v] == 0) {nuevoGrafoBFS(v, aristas);}
            }
            return aristas;
        }

        private void nuevoGrafoBFS(int v, ListaConPI<Arista> aristas) {
            visitados[v] = 1;
            q.encolar(v);
            while (!q.esVacia()) {
                int u = q.desencolar();
                ListaConPI<Adyacente> adyacentes = this.adyacentesDe(u);
                for (adyacentes.inicio(); !adyacentes.esFin(); adyacentes.siguiente()) {
                    int w = adyacentes.recuperar().getDestino();
                    double p = adyacentes.recuperar().getPeso();
                    if (visitados[w] == 0) {
                        visitados[w] = 1;
                        aristas.insertar(new Arista(v, w, p));
                        q.encolar(w);
                    }
                }
            }
        }
    }

    //EJERCICIO 4
    public class Ejercicio4<E extends Comparable<E>> extends ABB<E> {
        public Ejercicio4() {
            super();
        }

        protected boolean hayMayor(NodoABB<E> actual, E e) {
            if (actual == null) return true;
            else if (actual.dato.compareTo(e) > 0) return hayMayor(actual.izq, e) && hayMayor(actual.der, e);
            else return false;
        }

        protected boolean hayMenorOIgual(NodoABB<E> actual, E e) {
            if (actual == null) return true;
            else if (actual.dato.compareTo(e) <= 0) return hayMenorOIgual(actual.izq, e) && hayMenorOIgual(actual.der, e);
            else return false;
        }

        public boolean sustitucionPosible(E x, E y) {
            return sustitucionPosible(x, y, this.raiz);
        }

        private boolean sustitucionPosible(E x, E y, NodoABB<E> actual) {
            if (x == y) return true;
            if (actual.dato.compareTo(x) == 0) {
                if (x.compareTo(y) > 0) return !hayMayor(actual.izq, y);
                else return !hayMenorOIgual(actual.der, y);
            } else if (actual.dato.compareTo(x) > 0) {
                return actual.dato.compareTo(y) >= 0 && sustitucionPosible(x, y, actual.izq);
            } else {
                return actual.dato.compareTo(y) < 0  && sustitucionPosible(x, y, actual.der);
            }
        }
    }
}
