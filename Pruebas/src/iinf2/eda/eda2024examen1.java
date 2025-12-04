package iinf2.eda;

import iinf2.eda.libs.LEGListaConPI;
import iinf2.eda.libs.ListaConPI;
import iinf2.eda.libs.Map;
import iinf2.eda.libs.TablaHash;

public class eda2024examen1 {

    // EJERCICIO1

    public static boolean verificarParentesis(String s) {
        LEGListaConPI<Character> c = new LEGListaConPI<>();
        for (int i = 0; i < s.length(); i++) {
            c.insertar(s.charAt(i));
        }
        return verificarParentesis(c);
    }

    public static boolean verificarParentesis(LEGListaConPI<Character> list) {
        int open = 0;
        for (list.inicio(); !list.esFin(); list.siguiente()) {
            Character current = list.recuperar();
            open += (current.equals('(')) ? 1 : (current.equals(')')) ? -1 : 0;
            if (open < 0) return false;
        }
        return open == 0;
    }

    // EJERCICIO 2

    public static int cartuchosTinta(int[] v1, int[] v2) {
        if (v2[0] == 0) return 0;
        int diff = v2[0] - v1[0];
        if (v2[v2.length - 1] - v1[v1.length - 1] == diff) return -1;
        return cartuchosTinta(v1, v2, 0, v2.length, diff);
    }

    private static int cartuchosTinta(int[] v1, int[] v2, int i, int j, int diff) {
        int m = (i + j) / 2;
        if (v2[m] == 0) return m;
        else if (v2[m] - v1[m] == diff) return cartuchosTinta(v1, v2, m + 1, j, diff);
        else return cartuchosTinta(v1, v2, i, m - 1, diff);
    }

    // EJERCICIO 3

    public static class Producto {
        private String nombre;

        public Producto(String nombre) {
            this.nombre = nombre;
        }

        public String toString() {return nombre;}
    }

    public static Map<Producto, Double> actualizar(Map<Producto, Double> mapCatalogo, Map<Producto, Double> nuevosPrecios) {
        int tallaEstimada = (int) Math.round(mapCatalogo.talla() * 0.3);
        Map<Producto, Double> mapLiquidacion = new TablaHash<>(tallaEstimada);
        ListaConPI<Producto> claves = mapCatalogo.claves();
        for (claves.inicio(); !claves.esFin(); claves.siguiente()) {
            Producto producto = claves.recuperar();
            Double nuevoPrecio = nuevosPrecios.recuperar(producto);
            if (nuevoPrecio != null) {
                Double antiguoPrecio = mapCatalogo.recuperar(producto);
                mapCatalogo.insertar(producto, nuevoPrecio);
                if (nuevoPrecio <= antiguoPrecio * 0.3) {
                    mapLiquidacion.insertar(producto, nuevoPrecio);
                    mapCatalogo.eliminar(producto);
                }
            }
        }
        return mapLiquidacion;
    }
}