package iinf2.eda;

import iinf2.eda.libs.ListaConPI;
import iinf2.eda.libs.Map;
import iinf2.eda.libs.TablaHash;

public class eda2023examen1 {

    // EJERCICIO1

    public static <E extends Comparable<E>> ListaConPI<E> mergeLists(ListaConPI<E> l1, ListaConPI<E> l2) {
        l1.inicio();
        l2.inicio();
        while (!l1.esFin() && !l2.esFin()) {
            E analyzing = l1.recuperar();
            E inserting = l2.recuperar();
            if (analyzing.compareTo(inserting) < 0) l1.siguiente();
            else {
                l1.insertar(inserting);
                l2.eliminar();
            }
        }
        while (!l2.esFin()) {
            l1.insertar(l2.recuperar());
            l2.eliminar();
        }
        return l1;
    }

    // EJERCICIO 2

    public static int encontrarAnomalia(int[] v) {
        return encontrarAnomalia(v, 0, v.length - 1);
    }

    private static int encontrarAnomalia(int[] v, int i, int j) {
        int m = (i + j) / 2;
        if (m + 1 < v.length && v[m] + 1 != v[m + 1]) return m + 1;
        else if (v[m] == v[i] + (m - i)) return encontrarAnomalia(v, m + 1, j);
        else return encontrarAnomalia(v, i, m - 1);
    }

    // EJERCICIO 4

    public static class Fichaje {
        private String ID;
        private Integer day, hours;

        public Fichaje(String ID, Integer day, Integer hours) {
            this.ID = ID;
            this.day = day;
            this.hours = hours;
        }

        public String getID() {return ID;}
        public Integer getDia() {return day;}
        public Integer getHoras() {return hours;}
    }

    public static String horasExtra(ListaConPI<Fichaje> list, int dia_i, int dia_f) {
        Map<String, Integer> extras = new TablaHash<>(list.talla());
        int maxHours = 0;
        String laCabra = null;
        for (list.inicio(); !list.esFin(); list.siguiente()) {
            Fichaje f = list.recuperar();
            Integer dia = f.getDia();
            Integer horas = f.getHoras();
            if (dia > dia_i && dia < dia_f && horas > 8) {
                String ID = f.getID();
                Integer tosum = extras.recuperar(ID);
                extras.insertar(ID, (tosum != null) ? tosum + (horas - 8) : horas - 8);
                if (extras.recuperar(ID) > maxHours) {
                    maxHours = extras.recuperar(ID);
                    laCabra = ID;
                }
            }
        }
        return laCabra + " (" + extras.recuperar(laCabra) + "h)";
    }
}