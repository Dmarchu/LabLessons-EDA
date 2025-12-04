package iinf2.eda;
import iinf2.eda.libs.*;

public class eda2019examen2 {
    //EJERCICIO2
    public class Ejercicio2<E extends Comparable<E>> extends MonticuloBinario<E> {
        public ListaConPI<E> caminoDeHojaMinARaiz() {
            ListaConPI<E> result = new LEGListaConPI<>();
            int min = talla / 2 + 1;
            for (int i = min; i < talla; i++) {
                if (elArray[i].compareTo(elArray[min]) <= 0) min = i;
            }
            for (int i = min; i > 1; i = i / 2) {
                result.insertar(elArray[i]);
            }
            return result;
        }
    }

    //EJERCICIO3
    public class Ejercicio3 extends GrafoNoDirigido {
        public Ejercicio3(int numVertices) {
            super(numVertices);
        }

        public String toARDfs() {
            String resultado = "";
            visitados = new int[numVertices()];
            for (int i = 0; i < numVertices(); i++) {
                if (visitados[i] == 0) {
                    resultado += toARDfs(i) + "\n";
                }
            }
            return resultado;
        }

        public String toARDfs(int i) {
            String resultado = "-" + i;
            visitados[i] = 1;
            ListaConPI<Adyacente> adyacentes = adyacentesDe(i);
            for (adyacentes.inicio(); !adyacentes.esFin(); adyacentes.siguiente()) {
                int w = adyacentes.recuperar().getDestino();
                if (visitados[w] == 0) {
                    visitados[w] = 1;
                    resultado += "-" + toARDfs(w);
                }
            }
            return resultado;
        }
    }
}
