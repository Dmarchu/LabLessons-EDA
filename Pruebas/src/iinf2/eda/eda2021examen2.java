package iinf2.eda;
import iinf2.eda.libs.*;

public class eda2021examen2 {
    //EJERCICIO1
    public class Ejercicio1<E extends Comparable<E>> extends ABB<E> {
        public boolean contiene(ListaConPI<E> lpi) {
            lpi.inicio();
            contiene(raiz, lpi);
            return lpi.esFin();
        }

        private void contiene(NodoABB<E> raiz, ListaConPI<E> lpi) {
            if (raiz != null && !lpi.esFin()) {
                contiene(raiz.izq, lpi);
                if (!lpi.esFin()) {
                    int cmp = lpi.recuperar().compareTo(raiz.dato);
                    if (cmp == 0) lpi.siguiente();
                    if (cmp >= 0) contiene(raiz.der, lpi);
                }
            }
        }
    }

    //EJERCICIO2
    public static <E extends Comparable<E>> ListaConPI<E> repeatedInQueue(ColaPrioridad<E> q, E e) {
        Pila<E> s = new ArrayPila<>();
        ListaConPI<E> l = new LEGListaConPI<>();
        E compare = e;
        while (!q.esVacia()) {
            E element = q.eliminarMin();
            if (compare.compareTo(element) < 0) {
                l.insertar(element);
                compare = element;
            }
            s.apilar(element);
        }
        while (!s.esVacia()) {
            q.insertar(s.desapilar());
        }
        return l;
    }

    //EJERCICIO3
    public class Ejercicio3 extends GrafoDirigido {
        public Ejercicio3(int numVertices) {
            super(numVertices);
        }

        public String laberinto() {
            String sol = "";
            int[] res = new int[numVertices()];
            visitados = new int[numVertices()]; ordenVisita = 0;
            if (laberinto(0, res)) {
                for (int i = ordenVisita - 1; i >= 0; i--) {
                    sol += res[i] + " ";
                }
            } else { sol = "No hay solución"; }
            return sol;
        }

        protected boolean laberinto(int i, int[] res) {
            visitados[i] = 1;
            if (i == numVertices() - 1) {
                res[ordenVisita++] = i;
                return true;
            }
            ListaConPI<Adyacente> adyacentes = adyacentesDe(i);
            for (adyacentes.inicio(); !adyacentes.esFin(); adyacentes.siguiente()) {
                int w = adyacentes.recuperar().getDestino();
                if (visitados[w] == 0 && laberinto(w, res)) {
                    res[ordenVisita++] = w;
                    return true;
                }
            }
            return false;
        }
    }
}
