package iinf2.eda;

import iinf2.eda.libs.LEGListaConPI;
import iinf2.eda.libs.ListaConPI;
import iinf2.eda.libs.Map;
import iinf2.eda.libs.TablaHash;

import java.util.HashMap;

public class eda2022examen1 {

    // EJERCICIO1

    public static <E> int boorrarRepes(ListaConPI<E> l) {
        int counter = 0;
        l.inicio();
        E mem = l.recuperar();
        l.siguiente();
        while (!l.esFin()) {
            if (l.recuperar().equals(mem)) { l.eliminar(); counter++; }
            else { mem = l.recuperar(); l.siguiente(); }
        }
        return counter;
    }

    // EJERCICIO 2

    public static <E extends Comparable> int numApariciones(E[] v, E e) {
        return numApariciones(v, e, 0, v.length - 1);
    }

    private static <E extends Comparable> int numApariciones(E[] v, E e, int i, int j) {
        if (i > j) return 0;
        int m = (i + j) / 2;
        int comp = v[m].compareTo(e);
        if (comp > 0) return numApariciones(v, e, i, m - 1);
        else if (comp < 0) return numApariciones(v, e, m + 1, j);
        else return 1 + numApariciones(v, e, i, m - 1) + numApariciones(v, e, m + 1, j);
    }

    // EJERCICIO 3

    public static ListaConPI<String> detectarCabecillas(ListaConPI<String> l, int umbral) {
        ListaConPI<String> res = new LEGListaConPI<>();
        Map<String, Integer> cabecillas = new TablaHash<>((int) (l.talla() * 0.1));
        for (l.inicio(); !l.esFin(); l.siguiente()) {
            String tit = l.recuperar();
            Integer last = cabecillas.recuperar(tit);
            cabecillas.insertar(tit, (last != null) ? 1 + last : 1);
            if (cabecillas.recuperar(tit) == umbral) res.insertar(tit);
        }
        return res;
    }

}