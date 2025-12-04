package iinf2.eda;

import iinf2.eda.libs.LEGListaConPI;
import iinf2.eda.libs.ListaConPI;
import iinf2.eda.libs.Map;
import iinf2.eda.libs.TablaHash;

import java.util.HashMap;

public class eda2021examen1 {

    // EJERCICIO 2

    public static int falloConsecutivo(int[] v) {
        int mpos = v.length - 1;
        if (v[mpos] == v[0] + mpos) return v[mpos] + 1;
        return falloConsecutivo(v, 0, v.length - 1);
    }

    private static int falloConsecutivo(int[] v, int i, int j) {
        if (i > j || v[i] != v[0] + i) return v[0] + i;
        int m = (i + j) / 2;
        if (v[0] == v[m] - m) return falloConsecutivo(v, m + 1, j);
        else return falloConsecutivo(v, i, m - 1);
    }

    // EJERCICIO 3

    public static <C,V> Map<V, ListaConPI<C>> invertirClaves(Map<C, V> m1) {
        Map<V, ListaConPI<C>> m2 = new TablaHash<>(m1.talla());
        ListaConPI<C> claves = m1.claves();
        for (claves.inicio(); !claves.esFin(); claves.siguiente()) {
            C clave = claves.recuperar();
            V valor = m1.recuperar(clave);
            ListaConPI<C> nclaves = m2.recuperar(valor);
            if (nclaves == null) nclaves = new LEGListaConPI<>();
            nclaves.insertar(clave);
            m2.insertar(valor, nclaves);
        }
        return m2;
    }

}