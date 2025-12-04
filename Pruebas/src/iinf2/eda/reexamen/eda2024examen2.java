package iinf2.eda.reexamen;
import iinf2.eda.libs.*;

public class eda2024examen2 {
    public class Ejercicio1<E extends Comparable<E>> extends MonticuloBinario<E> {
        public boolean fusionarYVerificarHijos(MonticuloBinario<E> m2) {
            this.fusionar(m2);
            return this.verificarHijos();
        }

        public void fusionar(MonticuloBinario<E> m2) {
            MonticuloBinario<E> aux = new MonticuloBinario<>();
            while (!m2.esVacia()) {
                aux.insertar(m2.recuperarMin());
                this.insertar(m2.eliminarMin());
            }
            while (!aux.esVacia()) {
                m2.insertar(aux.eliminarMin());
            }
        }

        public boolean verificarHijos() {
            for (int i = 1; i <= talla / 2; i++) {
                if (2 * i + 1 <= talla && elArray[2 * i].compareTo(elArray[2 * i + 1]) >= 0) return false;
            }
            return true;
        }
    }

    public class Ejercicio2 extends GrafoNoDirigido {
        public Ejercicio2(int numVertices) {
            super(numVertices);
        }

        public ListaConPI<Arista> nuevoGrafoDFS() {
            visitados = new int[numVertices()];
            ListaConPI<Arista> res = new LEGListaConPI<>();
            for (int i = 0; i < numVertices(); i++) {
                if (visitados[i] == 0) nuevoGrafoDFS(i, res);
            }
            return res;
        }

        protected void nuevoGrafoDFS(int v, ListaConPI<Arista> l) {
            visitados[v] = 1;
            ListaConPI<Adyacente> a = adyacentesDe(v);
            for (a.inicio(); !a.esFin(); a.siguiente()) {
                int w = a.recuperar().getDestino();
                double p = a.recuperar().getPeso();
                if (visitados[w] == 0) {
                    visitados[w] = 1;
                    l.insertar(new Arista(v,w,p));
                    nuevoGrafoDFS(w, l);
                }
            }
        }

        public ListaConPI<Arista> nuevoGrafoBFS() {
            visitados = new int[numVertices()];
            q = new ArrayCola<>();
            ListaConPI<Arista> res = new LEGListaConPI<>();
            for (int i = 0; i < numVertices(); i++) {
                if (visitados[i] == 0) nuevoGrafoDFS(i, res);
            }
            return res;
        }

        protected void nuevoGrafoBFS(int v, ListaConPI<Arista> l) {
            q.encolar(v);
            while (!q.esVacia()) {
                int u = q.desencolar();
                visitados[u] = 1;
                ListaConPI<Adyacente> a = adyacentesDe(u);
                for (a.inicio(); !a.esFin(); a.siguiente()) {
                    int w = a.recuperar().getDestino();
                    double p = a.recuperar().getPeso();
                    if (visitados[w] == 0) {
                        q.encolar(w);
                        visitados[w] = 1;
                        l.insertar(new Arista(u,w,p));
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
            if (x == y) return true;
            return sustitucionPosible(x, y, this.raiz);
        }

        private boolean sustitucionPosible(E x, E y, NodoABB<E> actual) {
            if (actual == null) return false;

            if (x.compareTo(raiz.dato) == 0) {
                if (x.compareTo(y) > 0) return !hayMayor(actual.izq, y);
                else return !hayMenorOIgual(actual.der, y);
            } else if (x.compareTo(raiz.dato) > 0) {
                return raiz.dato.compareTo(y) >= 0 && sustitucionPosible(x,y,actual.izq);
            } else {
                return raiz.dato.compareTo(y) < 0 && sustitucionPosible(x,y,actual.der);
            }
        }
    }
}
