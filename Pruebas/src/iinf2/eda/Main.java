package iinf2.eda;

import iinf2.eda.libs.LEGListaConPI;
import iinf2.eda.libs.ListaConPI;
import iinf2.eda.libs.Map;
import iinf2.eda.libs.TablaHash;

import java.util.Random;

public class Main {
    public static Random rand = new Random();

    public static void main(String[] args) {
        /* todo - Exámen 1 */
        primerparcial2024();

        /* todo - Exámen 2 */
        primerparcial2023();

        /* todo - Exámen 3 */
        primerparcial2022();

        /* todo - Exámen 4 */
        primerparcial2021();
    }

    public static void primerparcial2024() {
        System.out.println("EXÁMEN 2024 1ER PARCIAL");

        /* Coste T(n) ∈ Ω(1), T(n) ∈ Θ(n)
            Ω(1) porque el mejor caso acaba en la primera iteración al encontrar un ')'
            Θ(n) porque el peor caso recorre el bucle entero
         */
        System.out.println("verificarParentesis: " +
                eda2024examen1.verificarParentesis("((1 + 3) / 5) + (3 * 2)")); // Punto Interés

        /* Coste T(n) ∈ Ω(1), T(n) ∈ Θ(log n)
            Ω(1) porque el mejor caso acaba en la lanzadera
            Θ(log n) porque el peor caso hace todas las divisiones del DyV
         */
        System.out.println("cartuchosTinta: " +
                eda2024examen1.cartuchosTinta(new int[]{10,50,60}, new int[]{35,0,10})); // DyV

        /* Coste T(n) ∈ Ω(n), T(n) ∈ Θ(n)
            Ω(n) = Θ(n) porque deberá recorrer el Map al completo siempre
         */
        Map<eda2024examen1.Producto, Double> m1 = new TablaHash<>(10);
        Map<eda2024examen1.Producto, Double> m2 = new TablaHash<>(10);
        for (int i = 0; i < 10; i++) {
            eda2024examen1.Producto p = new eda2024examen1.Producto("Producto " + i);
            m1.insertar(p, rand.nextDouble((99.9 - 1.0) + 1) + 1.0);
            if (i % 3 == 0) m2.insertar(p, m1.recuperar(p) * rand.nextDouble((0.3 - 0.15) + 1) + 0.15);
            else if (rand.nextInt(2) == 1) m2.insertar(p, rand.nextDouble((99.9 - 1.0) + 1) + 1.0);
        }
        Map<eda2024examen1.Producto, Double> m3 = eda2024examen1.actualizar(m1, m2);
        System.out.println("actualizar (" + m3.talla() + "): "); // Maps, Tablas Hash
        if (m3.esVacio()) {
            System.out.println("No se han encontrado actualizaciones.");
        } else {
            ListaConPI<eda2024examen1.Producto> l = m3.claves();
            for (l.inicio(); !l.esFin(); l.siguiente()) {
                System.out.println("-> " + l.recuperar().toString() + ": " + String.format("%.2f", m3.recuperar(l.recuperar())) + "€");
            }
        }

        System.out.println();
    }

    public static void primerparcial2023() {
        /* todo - Exámen 1 */
        System.out.println("EXÁMEN 2023 1ER PARCIAL");

        /* Coste T(n) ∈ Ω(), T() ∈ Θ()
            Ω() porque el mejor caso
            Θ() porque el peor caso
         */
        LEGListaConPI<Integer> lp1 = new LEGListaConPI<>();
        LEGListaConPI<Integer> lp2 = new LEGListaConPI<>();
        for (int i = 1; i <= 10; i++) {
            if (i % 2 == 0) lp1.insertar(i);
            else lp2.insertar(i);
        }
        lp1.inicio();
        lp2.inicio();
        System.out.println("mergeLists: " +
                eda2023examen1.mergeLists(lp1, lp2)); // ListaPI

        /* Coste T(n) ∈ Ω(1?), T(n) ∈ Θ(log n)
            Ω(1?) porque el mejor caso
            Θ(log n) porque el peor caso
         */
        int[] v = new int[]{1,2,3,5,6,7,8,10,11,13};
        System.out.println("encontrarAnomalia: " +
                eda2023examen1.encontrarAnomalia(v)); // DyV

        /* Coste T(n) ∈ Ω(), T() ∈ Θ()
            Ω() porque el mejor caso
            Θ() porque el peor caso
         */
        ListaConPI<eda2023examen1.Fichaje> lpf = new LEGListaConPI<>();
        String[] IDs = new String[]{"Máximo Angulo", "Zacarías Satrústegui", "Germán Palomares", "Sr. Saldaña"};
        for (int i = 0; i < 10; i++) {
            int sel = rand.nextInt(3);
            lpf.insertar(new eda2023examen1.Fichaje(IDs[sel], 1, 8 + rand.nextInt((3 - 1) + 1) + 1));
        }
        System.out.println("horasExtra: " +
                eda2023examen1.horasExtra(lpf, 0, 3)); //

        System.out.println();
    }

    private static void primerparcial2022() {
        /* todo - Exámen 2022 */
        System.out.println("EXÁMEN 2022 1 PARCIAL");

        /* Coste T(n) ∈ Ω(n), T(n) ∈ Θ(n)
            Ω(n) porque el mejor caso
            Θ(n) porque el peor caso
         */
        LEGListaConPI<Integer> l = new LEGListaConPI<>();
        int num = rand.nextInt(10) + 1;
        for (int i = 1; i <= 7; i++) {
            if (rand.nextInt(3) == 1) num++;
            l.insertar(num);
        }
        l.inicio();
        System.out.print("borrarRepes: [");
        int tall = 0;
        for (l.inicio(); !l.esFin(); l.siguiente()) {
            System.out.print((l.talla() > tall++ + 1) ? l.recuperar() + ", " :  l.recuperar() + "] -> [");
        }
        l.inicio();
        eda2022examen1.boorrarRepes(l);
        tall = 0;
        for (l.inicio(); !l.esFin(); l.siguiente()) {
            System.out.print((l.talla() > tall++ + 1) ? l.recuperar() + ", " : l.recuperar() + "]\n");
        } // ListaPi

        /* Coste T(n) ∈ Ω(1), T(n) ∈ Θ(log n)
            Ω(1) porque el mejor caso
            Θ(n) porque el peor caso
         */
        num = 1;
        Integer[] arr = new Integer[15];
        int ran;
        System.out.print("numApariciones: [");
        for (int i = 0; i < 15; i++) {
            if (rand.nextInt(3) == 1) num++;
            arr[i] = num;
            System.out.print((i < 14) ? num + ", " : num + "] of " + (ran = rand.nextInt(num)) + " -> " +
                    eda2022examen1.numApariciones(arr, ran) + "\n");
        } // DyV

        /* Coste T(n) ∈ Ω(), T() ∈ Θ()
            Ω() porque el mejor caso
            Θ() porque el peor caso
         */
        ListaConPI<String> list = new LEGListaConPI<>();
        String[] IDs = new String[]{"Máximo Angulo", "Zacarías Satrústegui", "Germán Palomares", "Sr. Saldaña"};
        for (int i = 0; i < 30; i++) {
            list.insertar(IDs[rand.nextInt(4)]);
        }
        System.out.println("detectarCabecillas: ");
        ListaConPI<String> cabecillas = eda2022examen1.detectarCabecillas(list, 10);
        if (cabecillas.esVacia()) {
            System.out.println("No se han encontrado cabecillas");
        } else {
            for (cabecillas.inicio(); !cabecillas.esFin(); cabecillas.siguiente()) {
                System.out.println("-> " + cabecillas.recuperar());
            }
        }

        System.out.println();
    }

    private static void primerparcial2021() {
        /* todo - Exámen 2021 */
        System.out.println("EXÁMEN 2021 1 PARCIAL");

        /* Coste T(n) ∈ Ω(n), T(n) ∈ Θ(log n)
            Ω(n) porque el mejor caso
            Θ(log n) porque el peor caso
         */
        int[] v = new int[]{2,3,4,5,6,7,8,9,10,11};
        System.out.println("falloConsecutivo: " +
                eda2021examen1.falloConsecutivo(v)); // DyV

        /* Coste T(n) ∈ Ω(1), T(n) ∈ Θ(log n)
            Ω(1) porque el mejor caso
            Θ(n) porque el peor caso
         */


        /* Coste T(n) ∈ Ω(), T() ∈ Θ()
            Ω() porque el mejor caso
            Θ() porque el peor caso
         */


        System.out.println();
    }

    private static void parcialVacio() {
        /* todo - Exámen X */
        System.out.println("EXÁMEN XXXX X PARCIAL");

        /* Coste T(n) ∈ Ω(), T() ∈ Θ()
            Ω() porque el mejor caso
            Θ() porque el peor caso
         */
        System.out.println("nombremetodo: " +
                ""); //

        /* Coste T(n) ∈ Ω(), T() ∈ Θ()
            Ω() porque el mejor caso
            Θ() porque el peor caso
         */
        System.out.println("nombremetodo: " +
                ""); //

        /* Coste T(n) ∈ Ω(), T() ∈ Θ()
            Ω() porque el mejor caso
            Θ() porque el peor caso
         */
        System.out.println("nombremetodo: " +
                ""); //

        System.out.println();
    }
}
