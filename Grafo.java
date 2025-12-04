 

import librerias.estructurasDeDatos.modelos.Cola;
import librerias.estructurasDeDatos.modelos.ListaConPI;
import librerias.estructurasDeDatos.lineales.LEGListaConPI;
import librerias.estructurasDeDatos.lineales.ArrayCola;

import librerias.estructurasDeDatos.modelos.UFSet;
import librerias.estructurasDeDatos.jerarquicos.ForestUFSet;
import librerias.estructurasDeDatos.modelos.ColaPrioridad;
import librerias.estructurasDeDatos.jerarquicos.MonticuloBinario;

/** Clase abstracta Grafo: Base de la jerarquia Grafo, que define el 
 *  comportamiento de un grafo.
 *
 *  No es una interfaz porque incluye el codigo de las operaciones de un 
 *  grafo que son independientes tanto de su tipo como de su implementacion.
 *  
 *  @version Septiembre 2023
 */

public abstract class Grafo {

    protected boolean esDirigido; // Indica si un grafo es Dirigido o no
    protected int[] visitados;    // Nodos visitados en un Recorrido
    protected int ordenVisita;    // Orden de visita de nodos en un Recorrido
    protected Cola<Integer> q;    // Cola para un Recorrido BFS

    /** Crea un grafo vacio, Dirigido si dirigido es true
     * o No Dirigido en caso contrario.
     * 
     * @param dirigido Indica el tipo del grafo, Dirigido o No
     */
    public Grafo(boolean dirigido) { esDirigido = dirigido; }

    /** Comprueba si un grafo es o no Dirigido.
     *
     * @return boolean true si el grafo es Dirgido y false si es No Dirigido
     */
    public boolean esDirigido() { return esDirigido; }

    /** Devuelve el numero de vertices de un grafo.
     * 
     * @return int numero de vertices
     */
    public abstract int numVertices();

    /** Devuelve el numero de aristas de un grafo.
     * 
     * @return int numero de aristas
     */
    public abstract int numAristas();

    /** Comprueba si la arista (i,j) esta en un grafo.
     * 
     * @param i    Vertice origen
     * @param j    Vertice destino
     * @return boolean true si (i,j) esta en el grafo y false en caso contrario
     */
    public abstract boolean existeArista(int i, int j);

    /** Devuelve el peso de la arista (i,j) de un grafo, 0 si dicha arista 
     * no esta en el grafo.
     * 
     * @param i    Vertice origen
     * @param j    Vertice destino
     * @return double Peso de la arista (i,j), 0 si no existe.
     */
    public abstract double pesoArista(int i, int j);

    /** Si no esta, inserta la arista (i,j) en un grafo No Ponderado.
     * 
     * @param i    Vertice origen
     * @param j    Vertice destino
     */
    public abstract void insertarArista(int i, int j);

    /** Si no esta, inserta la arista (i, j) de peso p en un grafo Ponderado.
     * 
     * @param i    Vertice origen
     * @param j    Vertice destino
     * @param p    Peso de la arista (i, j)
     */
    public abstract void insertarArista(int i, int j, double p);

    /** Devuelve una ListaConPI que contiene los adyacentes al vertice i.
     * 
     * @param i Vertice del que se obtienen los adyacentes
     * @return ListaConPI con los vertices adyacentes a i
     */
    public abstract ListaConPI<Adyacente> adyacentesDe(int i);

    /** Devuelve un String con cada uno de los vertices de un grafo y sus 
     * adyacentes, en orden de insercion.
     * 
     * @return  String que representa a un grafo
     */               
    public String toString() {
        String res = "";  
        for (int  i = 0; i < numVertices(); i++) {
            res += "Vertice: " + i;
            ListaConPI<Adyacente> l = adyacentesDe(i);
            if (l.esVacia()) res += " sin Adyacentes "; 
            else             res += " con Adyacentes "; 
            for (l.inicio(); !l.esFin(); l.siguiente()) {
                res +=  l.recuperar() + " ";  
            }
            res += "\n";  
        }
        return res;      
    }

    /** Devuelve un array con cada uno de los vertices de un grafo y sus 
     * adyacentes, en orden BFS.
     * 
     * @return  Array de vertices visitados en el recorrido BFS
     */   
    public int[] toArrayBFS() {
        int[] res = new int[numVertices()];
        visitados = new int[numVertices()]; 
        ordenVisita = 0;  
        q = new ArrayCola<Integer>();
        for (int  i = 0; i < numVertices(); i++) {
            if (visitados[i] == 0) toArrayBFS(i, res);
        }
        return res;
    }
    // Recorrido BFS del vertice origen, que almacena en res
    // su resultado
    protected void toArrayBFS(int origen, int[] res) { 
        res[ordenVisita++] = origen;
        visitados[origen] = 1;
        q.encolar(origen);
        while (!q.esVacia()) {
            int u = q.desencolar(); 
            ListaConPI<Adyacente> l = adyacentesDe(u); 
            for (l.inicio(); !l.esFin(); l.siguiente()) {
                Adyacente a = l.recuperar(); 
                if (visitados[a.destino] == 0) {
                    res[ordenVisita++] = a.destino;
                    visitados[a.destino] = 1;
                    q.encolar(a.destino);
                }
            }  
        }
    }

    /** PRECONDICION: !this.esDirigido()
     * Devuelve un subconjunto de aristas que conectan todos los vertices
     * de un grafo No Diridigo y Conexo, o null si el grafo no es Conexo.
     *  
     * @return Arista[], array con las numV - 1 aristas que conectan  
     *                   los numV vertices del grafo, o null si el grafo 
     *                   no es Conexo
     */ 
    public Arista[] arbolRecubrimientoBFS() {
        Arista[] res = new Arista[numVertices() - 1];
        int[] resInt =  new int[numVertices()];
        visitados = new int[numVertices()];
        ordenVisita = 0;  
        q = new ArrayCola<Integer>();
        int pos = 0;
        for (int  i = 0; i < numVertices(); i++) {
            if (visitados[i] == 0){
                arbolRecubrimientoBFS(i, resInt, res, pos);
            }
        }
        if(res[res.length - 1] == null) {
            res = null;
        }
        return res;
    }

    protected void arbolRecubrimientoBFS(int origen, int[] resInt, Arista[] res, int pos){
        resInt[ordenVisita++] = origen;
        visitados[origen] = 1;
        q.encolar(origen);
        while (!q.esVacia()) {
            int u = q.desencolar(); 
            ListaConPI<Adyacente> l = adyacentesDe(u); 
            for (l.inicio(); !l.esFin(); l.siguiente()) {
                Adyacente a = l.recuperar(); 
                if (visitados[a.destino] == 0) {
                    resInt[ordenVisita++] = a.destino;
                    int adyacente = a.getDestino();
                    res[pos] = new Arista(u, adyacente, pesoArista(u, adyacente));
                    pos++;
                    visitados[a.destino] = 1;
                    q.encolar(a.destino);
                }
            }  
        }
    }

    /** PRECONDICION: !this.esDirigido()
     * Devuelve un subconjunto de aristas que, con coste minimo,  
     * conectan todos los vertices de un grafo No Dirigido y Conexo, 
     * o null si el grafo no es Conexo.
     * 
     * @return Arista[], array con las numV - 1 aristas que conectan 
     *                   los numV vertices con coste minimo, o null 
     *                   si el grafo no es Conexo
     */ 
    public Arista[] kruskal() {       
        Arista[] res = new Arista[numVertices() - 1];
        int cardenal = 0;
        ColaPrioridad<Arista> aristasFactibles = new MonticuloBinario<Arista>();
        UFSet uf = new ForestUFSet(numVertices());

        for(int i = 0; i < numVertices(); i++){
            ListaConPI<Adyacente>  adyacente = adyacentesDe(i);
            for(adyacente.inicio(); !adyacente.esFin(); adyacente.siguiente()){
                aristasFactibles.insertar(new Arista(i, adyacente.recuperar().getDestino(), adyacente.recuperar().getPeso())); 
            }
        }

        while(cardenal < numVertices() - 1 && !aristasFactibles.esVacia()){
            Arista aux = aristasFactibles.eliminarMin();
            int ufV = uf.find(aux.getOrigen());
            int ufW = uf.find(aux.getDestino());
            if(ufV != ufW){
                uf.union(ufV, ufW);
                res[cardenal++] = aux;
            }
        }

        if(cardenal == numVertices() - 1) {
            return res;
        }
        return null;
    }

    // public double getPesoTotal(Arista[] aristas) {
    // if (aristas == null) return Double.MAX_VALUE;
    // double sum = 0.0;
    // for (int i = 0; i < aristas.length; i++) {
    // sum += aristas[i].getPeso();
    // }
    // return sum;
    // }

    // public Arista[] arbolRecubrimientoBFS(int origen) {
    // Arista[] res = new Arista[numVertices() - 1];
    // visitados = new int[numVertices()];
    // ordenVisita = 0;
    // q = new ArrayCola<Integer>();
    // arbolRecubrimientoBFS(origen, res);
    // if (ordenVisita != numVertices() - 1) return null;
    // return res;
    // }

    // public Arista[] mejorArbolBFS() {
    // Arista[] mejorBFS = null;
    // double mejorPesoTotal = Double.MAX_VALUE;
    // for (int v = 0; v < numVertices(); v++) {
    // Arista[] other = arbolRecubrimientoBFS(v);
    // if (other != null) {
    // double ptotal_i = getPesoTotal(other);
    // if (ptotal_i < mejorPesoTotal) {
    // mejorBFS = other;
    // mejorPesoTotal = ptotal_i;
    // }
    // }
    // }
    // return mejorBFS;
    // } 

    // public void dirigidoAscendente() {
    // // Recorrer todos los vertices
    // for (int origen = 0; origen < numVertices(); origen++) {
    // // Recorrer todos los adyacentes de un nodo
    // ListaConPI<Adyacente> l = adyacentesDe(origen);
    // l.inicio();
    // while (!l.esFin()) {
    // if (origen > l.recuperar().getDestino()) {
    // // Eliminar [origen] -> Adj(destino, peso) de l
    // // El PI ya apunta al lugar correcto
    // l.eliminar();
    // } else { // Si el origen es menor que el destino, movemos PI
    // l.siguiente();
    // }
    // }
    // }

    // }
    // public boolean esPosibleEliminarVerticeDeKruskal(int v) {
    // Arista[] mst = this.kruskal();
    // if (mst == null) return false;
    // int cont = 0;
    // for (int i = 0; i < numVertices() - 1 && cont < 2; i++) {
    // if (mst[i].getOrigen() == v || mst[i].getDestino() == v)
    // cont++;
    // }
    // return cont == 1;
    // }

    // public boolean esSubgrafo(Grafo g) {
    // for (int v = 0; v < numVertices(); v++) {
    // ListaConPI<Adyacente> ady = adyacentesDe(v);
    // for (ady.inicio(); !ady.esFin(); ady.siguiente()) {
    // int w = ady.recuperar().getDestino();
    // if (!g.existeArista(v, w)) return false;
    // }
    // }
    // return true;
    // }
    // public int fuenteEnMST() {
    // Arista[] mst = kruskal();
    // if (mst == null) return -2;
    // int gradoSalida[] = new int[numVertices()];
    // for (Arista a : mst) {
    // if (++gradoSalida[a.origen] == numVertices() - 1) return a.origen;
    // if (++gradoSalida[a.destino] == numVertices() - 1) return a.destino;
    // }
    // return -1;
    // }
    // // También así:
    // public int fuenteEnMST() {
    // Arista[] mst = kruskal();
    // if (mst == null) return -2;
    // int[] cont = new int[numVertices()];
    // for (int i = 0; i < mst.length; i++) {
    // cont[mst[i].origen]++;
    // cont[mst[i].destino]++;
    // }
    // for (int i = 0; i < numVertices() - 1; i++) {
    // if (cont[mst[i].origen] == numVertices() - 1)
    // return mst[i].origen;
    // }
    // return -1;
    // }

    
    // public int verticeMayorGrado() {
    // Arista[] mst = kruskal();
    // int[] cont = new int[numVertices()];
    // for (int i = 0; i < mst.length; i++) {
    // cont[mst[i].origen]++;
    // cont[mst[i].destino]++;
    // }
    // int max = 0;
    // int res = 0;
    // for (int i = 0; i < numVertices(); i++) {
    // if (cont[i] > max) {
    // max = cont[i];
    // res = i;
    // }
    // }
    // return res;
    // }

    
    // usando UFSet
    // public int contarCC() {
        // UFSet cc = new ForestUFSet(numVertices());
        // int nCC = numVertices();
        // for (int u = 0; u < numVertices(); u++) {
            // ListaConPI<Adyacente> l = adyacentesDe(u);
            // for (l.inicio(); !l.esFin(); l.siguiente()) {
                // int v = l.recuperar().getDestino();
                // int claseU = cc.find(u);
                // int claseV = cc.find(v);
                // if (claseU != claseV) {
                    // cc.union(claseU, claseV);
                    // nCC--;
                // }
            // }
        // }
        // return nCC;
    // }
    
    
    
    // // usando DFS
    // public int contarCC() {
        // visitados = new int[numVertices()];
        // int nCC = 0;
        // for (int v = 0; v < numVertices(); v++) {
            // if (visitados[v] == 0) {
                // nCC++;
                // contarCC(v);
            // }
        // }
        // return nCC;
    // }

    // protected void contarCC(int v) {
        // visitados[v] = 1;
        // ListaConPI<Adyacente> l = adyacentesDe(v);
        // for (l.inicio(); !l.esFin(); l.siguiente()) {
            // int w = l.recuperar().getDestino();
            // if (visitados[w] == 0) contarCC(w);
        // }
    // } 
    public boolean esConexo() {
    int[] visitados = toArrayBFS();
    for (int i : visitados) if (visitados[i] == 0) return false;
    return true;
}
    public int numeroHojasMST() {
    Arista[] mst = kruskal();
    if (mst == null) return -1;
    int[] grado = new int[numVertices()];
    for (Arista a : mst) {
        grado[a.getOrigen()]++;
        grado[a.getDestino()]++;
    }
    int hojas = 0;
    for (int g : grado) if (g == 1) hojas++;
    return hojas;
}
public double costeMST() {
    Arista[] mst = kruskal();       // Usa el método existente
    if (mst == null) return -1.0;   // Grafo no conexo
    double suma = 0.0;
    for (Arista a : mst) suma += a.getPeso();
    return suma;
}
public boolean existeCamino(int ori, int dest) {
    boolean[] vis = new boolean[numVertices()];
    return dfs(ori, dest, vis);
}
private boolean dfs(int u, int target, boolean[] vis) {
    if (u == target) return true;
    vis[u] = true;
    for (int v : vecinos(u)) {
        if (!vis[v] && dfs(v, target, vis)) {
            return true;
        }
    }
    return false;
}
// }
// public GrafoDirigido invertir() {
    // int n = numVertices();
    // GrafoDirigido inv = new GrafoDirigido(n, true);
    // for (int u = 0; u < n; u++) {
        // for (int v : vecinosDe(u)) {
            // inv.ponArista(v, u);
        // }
    // }
    // return inv;
// }
public ListaConPI<Integer> caminoMinimo(int origen, int destino) {
    if (origen < 0 || origen >= numVertices()) return null;
    if (destino < 0 || destino >= numVertices()) return null;

    int[] pred = new int[numVertices()];
    for (int i = 0; i < pred.length; i++) pred[i] = -1;
    visitados = new int[numVertices()];
    q = new ArrayCola<Integer>();
    visitados[origen] = 1; q.encolar(origen);

    while (!q.esVacia() && visitados[destino] == 0) {
        int u = q.desencolar();
        ListaConPI<Adyacente> l = adyacentesDe(u);
        for (l.inicio(); !l.esFin(); l.siguiente()) {
            int w = l.recuperar().getDestino();
            if (visitados[w] == 0) {
                visitados[w] = 1;
                pred[w] = u;
                q.encolar(w);
            }
        }
    }
    if (visitados[destino] == 0) return null;

    ListaConPI<Integer> camino = new LEGListaConPI<Integer>();
    for (int v = destino; v != -1; v = pred[v]) {
        camino.inicio();
        camino.insertar(v); // Inserta al principio
    }
    return camino;
}
public int numVerticesAislados() {
    int aislados = 0;
    for (int v = 0; v < numVertices(); v++) {
        if (adyacentesDe(v).esVacia()) aislados++;
    }
    return aislados;
}
public double pesoPromedioMST() {
    Arista[] mst = kruskal();
    if (mst == null) return -1.0;
    double total = 0.0;
    for (Arista a : mst) total += a.getPeso();
    return total / mst.length;
}
public int verticeMenorGrado() {
    Arista[] mst = kruskal();
    if (mst == null) return -1;
    int[] grado = new int[numVertices()];
    for (Arista a : mst) {
        grado[a.getOrigen()]++;
        grado[a.getDestino()]++;
    }
    int min = Integer.MAX_VALUE, vRes = 0;
    for (int i = 0; i < numVertices(); i++) {
        if (grado[i] < min) { min = grado[i]; vRes = i; }
    }
    return vRes;
}
public boolean tieneCiclos() {
    visitados = new int[numVertices()];
    for (int v = 0; v < numVertices(); v++) {
        if (visitados[v] == 0 && dfsCiclo(v, -1)) return true;
    }
    return false;
}

private boolean dfsCiclo(int v, int padre) {
    visitados[v] = 1;
    ListaConPI<Adyacente> l = adyacentesDe(v);
    for (l.inicio(); !l.esFin(); l.siguiente()) {
        int w = l.recuperar().getDestino();
        if (visitados[w] == 0) {
            if (dfsCiclo(w, v)) return true;
        } else if (w != padre) {
            return true;
        }
    }
    return false;
}
public ListaConPI<Integer> verticesEnNivel(int origen, int nivel) {
    if (origen < 0 || origen >= numVertices()) return null;
    int[] dist = new int[numVertices()];
    for (int i = 0; i < dist.length; i++) dist[i] = -1;
    q = new ArrayCola<Integer>();
    ListaConPI<Integer> res = new LEGListaConPI<Integer>();

    dist[origen] = 0;
    q.encolar(origen);
    while (!q.esVacia()) {
        int u = q.desencolar();
        if (dist[u] == nivel) res.insertar(u);
        if (dist[u] >= nivel) continue;
        ListaConPI<Adyacente> l = adyacentesDe(u);
        for (l.inicio(); !l.esFin(); l.siguiente()) {
            int w = l.recuperar().getDestino();
            if (dist[w] == -1) {
                dist[w] = dist[u] + 1;
                q.encolar(w);
            }
        }
    }
    return res.esVacia() ? null : res;
}
public double pesoMaximoMST() {
    Arista[] mst = kruskal();
    if (mst == null) return -1.0;
    double max = 0.0;
    for (Arista a : mst) if (a.getPeso() > max) max = a.getPeso();
    return max;
}
public int distanciaMinima(int origen, int destino) {
    if (origen < 0 || origen >= numVertices()) return -1;
    if (destino < 0 || destino >= numVertices()) return -1;

    int[] dist = new int[numVertices()];
    for (int i = 0; i < dist.length; i++) dist[i] = -1;
    q = new ArrayCola<Integer>();
    dist[origen] = 0;
    q.encolar(origen);

    while (!q.esVacia() && dist[destino] == -1) {
        int u = q.desencolar();
        ListaConPI<Adyacente> l = adyacentesDe(u);
        for (l.inicio(); !l.esFin(); l.siguiente()) {
            int v = l.recuperar().getDestino();
            if (dist[v] == -1) {
                dist[v] = dist[u] + 1;
                q.encolar(v);
            }
        }
    }
    return dist[destino];
}
public int diametroMST() {
    Arista[] mst = kruskal();
    if (mst == null) return -1;

    // construir listas de adyacencia del MST
    @SuppressWarnings("unchecked")
    ListaConPI<Adyacente>[] g = new ListaConPI[numVertices()];
    for (int i = 0; i < g.length; i++) g[i] = new LEGListaConPI<>();
    for (Arista a : mst) {
        g[a.getOrigen()].insertar(new Adyacente(a.getDestino(), 1));
        g[a.getDestino()].insertar(new Adyacente(a.getOrigen(), 1));
    }

    int diametro = 0;
    for (int v = 0; v < numVertices(); v++) {
        diametro = Math.max(diametro, bfsMaxDist(g, v));
    }
    return diametro;
}

private int bfsMaxDist(ListaConPI<Adyacente>[] g, int origen) {
    int[] dist = new int[g.length];
    for (int i = 0; i < dist.length; i++) dist[i] = -1;
    ArrayCola<Integer> q = new ArrayCola<>();
    dist[origen] = 0;
    q.encolar(origen);
    int max = 0;

    while (!q.esVacia()) {
        int u = q.desencolar();
        max = Math.max(max, dist[u]);
        ListaConPI<Adyacente> l = g[u];
        for (l.inicio(); !l.esFin(); l.siguiente()) {
            int w = l.recuperar().getDestino();
            if (dist[w] == -1) {
                dist[w] = dist[u] + 1;
                q.encolar(w);
            }
        }
    }
    return max;
}
public boolean esArbol() {
    if (esDirigido() || numAristas() != numVertices() - 1) return false;
    visitados = new int[numVertices()];
    q = new ArrayCola<Integer>();
    visitados[0] = 1; q.encolar(0);
    int visitadosTotal = 1;

    while (!q.esVacia()) {
        int u = q.desencolar();
        ListaConPI<Adyacente> l = adyacentesDe(u);
        for (l.inicio(); !l.esFin(); l.siguiente()) {
            int v = l.recuperar().getDestino();
            if (visitados[v] == 0) {
                visitados[v] = 1;
                visitadosTotal++;
                q.encolar(v);
            }
        }
    }
    return visitadosTotal == numVertices();
}
public double gradoPromedio() {
    double suma = 0;
    for (int v = 0; v < numVertices(); v++) {
        ListaConPI<Adyacente> l = adyacentesDe(v);
        int g = 0;
        for (l.inicio(); !l.esFin(); l.siguiente()) g++;
        suma += g;
    }
    if (!esDirigido()) suma = suma * 2;
    return suma / numVertices();
}
public Grafo clonar() {
    GrafoDirigido copia =
        esDirigido() ? new GrafoDirigido(numVertices())
                     : new GrafoNoDirigido(numVertices());

    for (int i = 0; i < numVertices(); i++) {
        ListaConPI<Adyacente> l = adyacentesDe(i);
        for (l.inicio(); !l.esFin(); l.siguiente()) {
            Adyacente a = l.recuperar();
            if (!copia.existeArista(i, a.getDestino()))
                copia.insertarArista(i, a.getDestino(), a.getPeso());
        }
    }
    return copia;
}
public int verticeMayorPesoMST() {
    Arista[] mst = kruskal();
    if (mst == null) return -1;
    double[] peso = new double[numVertices()];
    for (Arista a : mst) {
        peso[a.getOrigen()] += a.getPeso();
        peso[a.getDestino()] += a.getPeso();
    }
    double max = -1.0; int res = -1;
    for (int i = 0; i < numVertices(); i++) {
        if (peso[i] > max) { max = peso[i]; res = i; }
    }
    return res;
}
public int gradoMinimo() {
    int min = Integer.MAX_VALUE;
    for (int v = 0; v < numVertices(); v++) {
        int g = 0;
        ListaConPI<Adyacente> l = adyacentesDe(v);
        for (l.inicio(); !l.esFin(); l.siguiente()) g++;
        if (g < min) min = g;
    }
    return min;
}
public boolean esRegular() {
    int gradoRef = 0;
    ListaConPI<Adyacente> l0 = adyacentesDe(0);
    for (l0.inicio(); !l0.esFin(); l0.siguiente()) gradoRef++;

    for (int v = 1; v < numVertices(); v++) {
        int g = 0;
        ListaConPI<Adyacente> l = adyacentesDe(v);
        for (l.inicio(); !l.esFin(); l.siguiente()) g++;
        if (g != gradoRef) return false;
    }
    return true;
}
public Grafo transpuesto() {
    GrafoDirigido t = new GrafoDirigido(numVertices());
    for (int v = 0; v < numVertices(); v++) {
        ListaConPI<Adyacente> l = adyacentesDe(v);
        for (l.inicio(); !l.esFin(); l.siguiente()) {
            Adyacente a = l.recuperar();
            t.insertarArista(a.getDestino(), v, a.getPeso());
        }
    }
    return t;
}
public int verticeMasLejano(int origen) {
    if (origen < 0 || origen >= numVertices()) return -1;
    int[] dist = new int[numVertices()];
    for (int i = 0; i < dist.length; i++) dist[i] = -1;
    q = new ArrayCola<Integer>();
    dist[origen] = 0;
    int lejano = origen;
    q.encolar(origen);

    while (!q.esVacia()) {
        int u = q.desencolar();
        ListaConPI<Adyacente> l = adyacentesDe(u);
        for (l.inicio(); !l.esFin(); l.siguiente()) {
            int w = l.recuperar().getDestino();
            if (dist[w] == -1) {
                dist[w] = dist[u] + 1;
                if (dist[w] > dist[lejano]) lejano = w;
                q.encolar(w);
            }
        }
    }
    return lejano;
}
public double pesoMayorArista() {
    double max = 0.0;
    for (int v = 0; v < numVertices(); v++) {
        ListaConPI<Adyacente> l = adyacentesDe(v);
        for (l.inicio(); !l.esFin(); l.siguiente()) {
            double p = l.recuperar().getPeso();
            if (p > max) max = p;
        }
    }
    return max;
}
public Arista[] arbolRecubrimientoDFS() {
    Arista[] res = new Arista[numVertices() - 1];
    visitados = new int[numVertices()];
    int pos = 0;
    for (int v = 0; v < numVertices(); v++) {
        if (visitados[v] == 0) pos = dfsArbol(v, res, pos);
    }
    if (pos < res.length) return null;
    return res;
}

private int dfsArbol(int v, Arista[] res, int pos) {
    visitados[v] = 1;
    ListaConPI<Adyacente> l = adyacentesDe(v);
    for (l.inicio(); !l.esFin(); l.siguiente()) {
        int w = l.recuperar().getDestino();
        if (visitados[w] == 0) {
            res[pos++] = new Arista(v, w, pesoArista(v, w));
            pos = dfsArbol(w, res, pos);
        }
    }
    return pos;
}
public boolean esCompleto() {
    int n = numVertices();
    int esperado = esDirigido() ? n * (n - 1) : n * (n - 1) / 2;
    return numAristas() == esperado;
}
public int[] grados() {
    int[] g = new int[numVertices()];
    for (int v = 0; v < numVertices(); v++) {
        int cont = 0;
        ListaConPI<Adyacente> l = adyacentesDe(v);
        for (l.inicio(); !l.esFin(); l.siguiente()) cont++;
        g[v] = cont;
    }
    return g;
}
public ListaConPI<ListaConPI<Integer>> componentesConexas() {
    ListaConPI<ListaConPI<Integer>> res = new LEGListaConPI<>();
    visitados = new int[numVertices()];
    q = new ArrayCola<Integer>();

    for (int i = 0; i < numVertices(); i++) {
        if (visitados[i] == 0) {
            ListaConPI<Integer> comp = new LEGListaConPI<>();
            visitados[i] = 1;
            q.encolar(i);
            comp.insertar(i);

            while (!q.esVacia()) {
                int u = q.desencolar();
                ListaConPI<Adyacente> l = adyacentesDe(u);
                for (l.inicio(); !l.esFin(); l.siguiente()) {
                    int w = l.recuperar().getDestino();
                    if (visitados[w] == 0) {
                        visitados[w] = 1;
                        comp.insertar(w);
                        q.encolar(w);
                    }
                }
            }
            res.insertar(comp);
        }
    }
    return res;
}
public boolean existeCicloDirigido() {
    if (!esDirigido()) return false;
    visitados = new int[numVertices()];
    int[] enRec = new int[numVertices()];
    for (int v = 0; v < numVertices(); v++) {
        if (visitados[v] == 0 && dfsCicloDir(v, enRec)) return true;
    }
    return false;
}

private boolean dfsCicloDir(int v, int[] enRec) {
    visitados[v] = 1;
    enRec[v] = 1;
    ListaConPI<Adyacente> l = adyacentesDe(v);
    for (l.inicio(); !l.esFin(); l.siguiente()) {
        int w = l.recuperar().getDestino();
        if (visitados[w] == 0) {
            if (dfsCicloDir(w, enRec)) return true;
        } else if (enRec[w] == 1) {
            return true;
        }
    }
    enRec[v] = 0;
    return false;
}
public double pesoMinimoAristaMST() {
    Arista[] mst = kruskal();
    if (mst == null) return -1.0;
    double min = Double.MAX_VALUE;
    for (Arista a : mst) if (a.getPeso() < min) min = a.getPeso();
    return min;
}
public boolean existeVerticeConGradoMST(int k) {
    Arista[] mst = kruskal();
    if (mst == null) return false;
    int[] grado = new int[numVertices()];
    for (Arista a : mst) {
        grado[a.getOrigen()]++;
        grado[a.getDestino()]++;
    }
    for (int g : grado) if (g == k) return true;
    return false;
}
public Grafo construirGrafoMST() {
    Arista[] mst = kruskal();
    if (mst == null) return null;
    GrafoNoDirigido g = new GrafoNoDirigido(numVertices());
    for (Arista a : mst) {
        g.insertarArista(a.getOrigen(), a.getDestino(), a.getPeso());
    }
    return g;
}
public int numeroAristasFueraMST() {
    Arista[] mst = kruskal();
    if (mst == null) return -1;
    return numAristas() - mst.length;
}
public boolean existeAristaMSTMayorQue(double peso) {
    Arista[] mst = kruskal();
    if (mst == null) return false;
    for (Arista a : mst) if (a.getPeso() > peso) return true;
    return false;
}
public ListaConPI<Integer> caminoEnMST(int origen, int destino) {
    Arista[] mst = kruskal();
    if (mst == null) return null;

    @SuppressWarnings("unchecked")
    ListaConPI<Adyacente>[] g = new ListaConPI[numVertices()];
    for (int i = 0; i < g.length; i++) g[i] = new LEGListaConPI<>();
    for (Arista a : mst) {
        g[a.getOrigen()].insertar(new Adyacente(a.getDestino(), a.getPeso()));
        g[a.getDestino()].insertar(new Adyacente(a.getOrigen(), a.getPeso()));
    }

    int[] pred = new int[numVertices()];
    for (int i = 0; i < pred.length; i++) pred[i] = -1;
    ArrayCola<Integer> q = new ArrayCola<>();
    pred[origen] = origen;
    q.encolar(origen);

    while (!q.esVacia() && pred[destino] == -1) {
        int u = q.desencolar();
        ListaConPI<Adyacente> l = g[u];
        for (l.inicio(); !l.esFin(); l.siguiente()) {
            int w = l.recuperar().getDestino();
            if (pred[w] == -1) {
                pred[w] = u;
                q.encolar(w);
            }
        }
    }
    if (pred[destino] == -1) return null;

    ListaConPI<Integer> camino = new LEGListaConPI<>();
    for (int v = destino; v != origen; v = pred[v]) {
        camino.inicio(); camino.insertar(v);
    }
    camino.inicio(); camino.insertar(origen);
    return camino;
}
public int hojaMenorPesoMST() {
    Arista[] mst = kruskal();
    if (mst == null) return -1;
    int[] grado = new int[numVertices()];
    double[] peso = new double[numVertices()];
    for (Arista a : mst) {
        grado[a.getOrigen()]++;  peso[a.getOrigen()] += a.getPeso();
        grado[a.getDestino()]++; peso[a.getDestino()] += a.getPeso();
    }
    int res = -1; double min = Double.MAX_VALUE;
    for (int i = 0; i < numVertices(); i++) {
        if (grado[i] == 1 && peso[i] < min) { min = peso[i]; res = i; }
    }
    return res;
}
public double pesoTotalFueraMST() {
    Arista[] mst = kruskal();
    if (mst == null) return -1.0;

    double total = 0.0;
    for (int v = 0; v < numVertices(); v++) {
        ListaConPI<Adyacente> l = adyacentesDe(v);
        for (l.inicio(); !l.esFin(); l.siguiente()) {
            int w = l.recuperar().getDestino();
            if (v < w) total += l.recuperar().getPeso(); // evita doble conteo
        }
    }
    double mstTotal = 0.0;
    for (Arista a : mst) mstTotal += a.getPeso();
    return total - mstTotal;
}
public double costeMSTSinVertice(int v) {
    GrafoNoDirigido g = new GrafoNoDirigido(numVertices() - 1);
    for (int i = 0, ni = 0; i < numVertices(); i++) {
        if (i == v) continue;
        for (int j = 0; j < numVertices(); j++) {
            if (j == v) continue;
            if (existeArista(i, j)) {
                int nj = j < v ? j : j - 1;
                g.insertarArista(ni, nj, pesoArista(i, j));
            }
        }
        ni++;
    }
    Arista[] mst = g.kruskal();
    if (mst == null) return -1.0;
    double total = 0.0;
    for (Arista a : mst) total += a.getPeso();
    return total;
}
public int alturaMST(int origen) {
    Arista[] mst = kruskal();
    if (mst == null || origen < 0 || origen >= numVertices()) return -1;

    @SuppressWarnings("unchecked")
    ListaConPI<Adyacente>[] g = new ListaConPI[numVertices()];
    for (int i = 0; i < g.length; i++) g[i] = new LEGListaConPI<>();
    for (Arista a : mst) {
        g[a.getOrigen()].insertar(new Adyacente(a.getDestino(), a.getPeso()));
        g[a.getDestino()].insertar(new Adyacente(a.getOrigen(), a.getPeso()));
    }

    int[] dist = new int[numVertices()];
    for (int i = 0; i < dist.length; i++) dist[i] = -1;
    ArrayCola<Integer> q = new ArrayCola<>();
    dist[origen] = 0;
    q.encolar(origen);
    int max = 0;

    while (!q.esVacia()) {
        int u = q.desencolar();
        max = Math.max(max, dist[u]);
        ListaConPI<Adyacente> l = g[u];
        for (l.inicio(); !l.esFin(); l.siguiente()) {
            int w = l.recuperar().getDestino();
            if (dist[w] == -1) {
                dist[w] = dist[u] + 1;
                q.encolar(w);
            }
        }
    }
    return max;
}
public double distanciaMediaVertice(int v) {
    if (v < 0 || v >= numVertices()) return -1.0;
    int[] dist = new int[numVertices()];
    for (int i = 0; i < dist.length; i++) dist[i] = -1;
    ArrayCola<Integer> q = new ArrayCola<>();
    dist[v] = 0; q.encolar(v);
    int suma = 0, visitados = 1;

    while (!q.esVacia()) {
        int u = q.desencolar();
        ListaConPI<Adyacente> l = adyacentesDe(u);
        for (l.inicio(); !l.esFin(); l.siguiente()) {
            int w = l.recuperar().getDestino();
            if (dist[w] == -1) {
                dist[w] = dist[u] + 1;
                suma += dist[w];
                visitados++;
                q.encolar(w);
            }
        }
    }
    return (visitados > 1) ? (double) suma / (visitados - 1) : 0.0;
}
public boolean esBipartito() {
    int[] color = new int[numVertices()];
    for (int i = 0; i < numVertices(); i++) {
        if (color[i] != 0) continue;
        ArrayCola<Integer> q = new ArrayCola<>();
        color[i] = 1; q.encolar(i);
        while (!q.esVacia()) {
            int u = q.desencolar();
            ListaConPI<Adyacente> l = adyacentesDe(u);
            for (l.inicio(); !l.esFin(); l.siguiente()) {
                int w = l.recuperar().getDestino();
                if (color[w] == 0) {
                    color[w] = -color[u];
                    q.encolar(w);
                } else if (color[w] == color[u]) {
                    return false;
                }
            }
        }
    }
    return true;
}
public int contarPuentes() {
    int n = numVertices();
    int[] tin = new int[n], low = new int[n];
    int tiempo = 0, puentes = 0;
    for (int i = 0; i < n; i++) tin[i] = -1;

    for (int v = 0; v < n; v++)
        if (tin[v] == -1) puentes += dfsPuentes(v, -1, tin, low, tiempo);
    return puentes;
}

private int dfsPuentes(int v, int padre, int[] tin, int[] low, int tiempo) {
    tin[v] = low[v] = ++tiempo;
    int cuenta = 0;
    ListaConPI<Adyacente> l = adyacentesDe(v);
    for (l.inicio(); !l.esFin(); l.siguiente()) {
        int w = l.recuperar().getDestino();
        if (w == padre) continue;
        if (tin[w] == -1) {
            cuenta += dfsPuentes(w, v, tin, low, tiempo);
            low[v] = Math.min(low[v], low[w]);
            if (low[w] > tin[v]) cuenta++;
        } else {
            low[v] = Math.min(low[v], tin[w]);
        }
    }
    return cuenta;
}
public Grafo subgrafoInducido(ListaConPI<Integer> vertices) {
    GrafoNoDirigido g = new GrafoNoDirigido(vertices.talla());
    int[] mapa = new int[numVertices()];
    for (int i = 0; i < mapa.length; i++) mapa[i] = -1;

    int idx = 0;
    for (vertices.inicio(); !vertices.esFin(); vertices.siguiente()) {
        int v = vertices.recuperar();
        mapa[v] = idx++;
    }

    for (vertices.inicio(); !vertices.esFin(); vertices.siguiente()) {
        int v = vertices.recuperar();
        ListaConPI<Adyacente> l = adyacentesDe(v);
        for (l.inicio(); !l.esFin(); l.siguiente()) {
            int w = l.recuperar().getDestino();
            if (mapa[w] != -1 && !g.existeArista(mapa[v], mapa[w]))
                g.insertarArista(mapa[v], mapa[w], l.recuperar().getPeso());
        }
    }
    return g;
}
public boolean aristaPerteneceMST(int i, int j) {
    Arista[] mst = kruskal();
    if (mst == null) return false;
    for (Arista a : mst) {
        if ((a.getOrigen() == i && a.getDestino() == j) ||
            (a.getOrigen() == j && a.getDestino() == i)) return true;
    }
    return false;
}
public double distanciaMediaGrafo() {
    double total = 0.0;
    for (int v = 0; v < numVertices(); v++) {
        int[] dist = new int[numVertices()];
        for (int i = 0; i < dist.length; i++) dist[i] = -1;
        ArrayCola<Integer> q = new ArrayCola<>();
        dist[v] = 0; q.encolar(v);

        while (!q.esVacia()) {
            int u = q.desencolar();
            ListaConPI<Adyacente> l = adyacentesDe(u);
            for (l.inicio(); !l.esFin(); l.siguiente()) {
                int w = l.recuperar().getDestino();
                if (dist[w] == -1) {
                    dist[w] = dist[u] + 1;
                    total += dist[w];
                    q.encolar(w);
                }
            }
        }
    }
    int n = numVertices();
    return total / (n * (n - 1));
}
public int verticeCentro() {
    int n = numVertices();
    int mejor = -1;
    int excMin = Integer.MAX_VALUE;

    for (int v = 0; v < n; v++) {
        int[] dist = new int[n];
        for (int i = 0; i < n; i++) dist[i] = -1;
        ArrayCola<Integer> q = new ArrayCola<>();
        dist[v] = 0; q.encolar(v);
        int exc = 0;

        while (!q.esVacia()) {
            int u = q.desencolar();
            ListaConPI<Adyacente> l = adyacentesDe(u);
            for (l.inicio(); !l.esFin(); l.siguiente()) {
                int w = l.recuperar().getDestino();
                if (dist[w] == -1) {
                    dist[w] = dist[u] + 1;
                    exc = Math.max(exc, dist[w]);
                    q.encolar(w);
                }
            }
        }
        for (int d : dist) if (d == -1) return -1; // No conexo
        if (exc < excMin) { excMin = exc; mejor = v; }
    }
    return mejor;
}
public double caminoMenorPeso(int origen, int destino) {
    int n = numVertices();
    double[] dist = new double[n];
    boolean[] visitado = new boolean[n];
    for (int i = 0; i < n; i++) dist[i] = Double.MAX_VALUE;
    ColaPrioridad<Arista> pq = new MonticuloBinario<>();
    dist[origen] = 0.0;
    pq.insertar(new Arista(origen, origen, 0));

    while (!pq.esVacia()) {
        Arista a = pq.eliminarMin();
        int u = a.getDestino();
        if (visitado[u]) continue;
        visitado[u] = true;
        if (u == destino) return dist[u];

        ListaConPI<Adyacente> l = adyacentesDe(u);
        for (l.inicio(); !l.esFin(); l.siguiente()) {
            int w = l.recuperar().getDestino();
            double peso = l.recuperar().getPeso();
            if (!visitado[w] && dist[u] + peso < dist[w]) {
                dist[w] = dist[u] + peso;
                pq.insertar(new Arista(u, w, dist[w]));
            }
        }
    }
    return -1.0;
}
public int numVerticesConGradoMayor(int k) {
    int cuenta = 0;
    for (int v = 0; v < numVertices(); v++) {
        int g = 0;
        ListaConPI<Adyacente> l = adyacentesDe(v);
        for (l.inicio(); !l.esFin(); l.siguiente()) g++;
        if (g > k) cuenta++;
    }
    return cuenta;
}
public int caminoMasLargoMST(int origen) {
    Arista[] mst = kruskal();
    if (mst == null || origen < 0 || origen >= numVertices()) return -1;

    @SuppressWarnings("unchecked")
    ListaConPI<Adyacente>[] g = new ListaConPI[numVertices()];
    for (int i = 0; i < g.length; i++) g[i] = new LEGListaConPI<>();
    for (Arista a : mst) {
        g[a.getOrigen()].insertar(new Adyacente(a.getDestino(), 1));
        g[a.getDestino()].insertar(new Adyacente(a.getOrigen(), 1));
    }

    int[] dist = new int[numVertices()];
    for (int i = 0; i < dist.length; i++) dist[i] = -1;
    ArrayCola<Integer> q = new ArrayCola<>();
    dist[origen] = 0; q.encolar(origen);
    int max = 0;

    while (!q.esVacia()) {
        int u = q.desencolar();
        max = Math.max(max, dist[u]);
        ListaConPI<Adyacente> l = g[u];
        for (l.inicio(); !l.esFin(); l.siguiente()) {
            int w = l.recuperar().getDestino();
            if (dist[w] == -1) {
                dist[w] = dist[u] + 1;
                q.encolar(w);
            }
        }
    }
    return max;
}
public double costeTotalGrafo() {
    double total = 0.0;
    for (int u = 0; u < numVertices(); u++) {
        ListaConPI<Adyacente> l = adyacentesDe(u);
        for (l.inicio(); !l.esFin(); l.siguiente()) {
            if (esDirigido() || u < l.recuperar().getDestino()) {
                total += l.recuperar().getPeso();
            }
        }
    }
    return total;
}
public boolean esArbolDeExpansionValido(Arista[] arbol) {
    if (arbol == null || arbol.length != numVertices() - 1) return false;
    UFSet uf = new ForestUFSet(numVertices());
    for (Arista a : arbol) {
        int x = uf.find(a.getOrigen()), y = uf.find(a.getDestino());
        if (x == y) return false;
        uf.union(x, y);
    }
    return true;
}
public int verticeMasAislado() {
    int mejor = -1, gradoMin = Integer.MAX_VALUE;
    for (int v = 0; v < numVertices(); v++) {
        int g = 0;
        ListaConPI<Adyacente> l = adyacentesDe(v);
        for (l.inicio(); !l.esFin(); l.siguiente()) g++;
        if (g < gradoMin) { gradoMin = g; mejor = v; }
    }
    return mejor;
}
public boolean existeCaminoPesoLimite(int origen, int destino, double limite) {
    double[] dist = new double[numVertices()];
    boolean[] visitado = new boolean[numVertices()];
    for (int i = 0; i < dist.length; i++) dist[i] = Double.MAX_VALUE;
    ColaPrioridad<Arista> pq = new MonticuloBinario<>();
    dist[origen] = 0.0;
    pq.insertar(new Arista(origen, origen, 0));

    while (!pq.esVacia()) {
        Arista a = pq.eliminarMin();
        int u = a.getDestino();
        if (visitado[u]) continue;
        if (dist[u] > limite) break;
        visitado[u] = true;
        if (u == destino) return dist[u] <= limite;

        ListaConPI<Adyacente> l = adyacentesDe(u);
        for (l.inicio(); !l.esFin(); l.siguiente()) {
            int w = l.recuperar().getDestino();
            double nuevo = dist[u] + l.recuperar().getPeso();
            if (!visitado[w] && nuevo < dist[w]) {
                dist[w] = nuevo;
                pq.insertar(new Arista(u, w, nuevo));
            }
        }
    }
    return false;
}
public int rutaMasLargaDesde(int origen) {
    boolean[] visitado = new boolean[numVertices()];
    return dfsLongitud(origen, visitado);
}

private int dfsLongitud(int v, boolean[] visitado) {
    visitado[v] = true;
    int mejor = 0;
    ListaConPI<Adyacente> l = adyacentesDe(v);
    for (l.inicio(); !l.esFin(); l.siguiente()) {
        int w = l.recuperar().getDestino();
        if (!visitado[w]) {
            mejor = Math.max(mejor, 1 + dfsLongitud(w, visitado));
        }
    }
    visitado[v] = false;
    return mejor;
}
public int gradoMinimoMST() {
    Arista[] mst = kruskal();
    if (mst == null) return -1;
    int[] grado = new int[numVertices()];
    for (Arista a : mst) {
        grado[a.getOrigen()]++;
        grado[a.getDestino()]++;
    }
    int min = Integer.MAX_VALUE;
    for (int g : grado) if (g < min) min = g;
    return min;
}public Arista aristaMinimaNoMST() {
    Arista[] mst = kruskal();
    if (mst == null) return null;
    boolean[][] enMST = new boolean[numVertices()][numVertices()];
    for (Arista a : mst) {
        enMST[a.getOrigen()][a.getDestino()] = true;
        enMST[a.getDestino()][a.getOrigen()] = true;
    }
    Arista mejor = null;
    for (int u = 0; u < numVertices(); u++) {
        ListaConPI<Adyacente> l = adyacentesDe(u);
        for (l.inicio(); !l.esFin(); l.siguiente()) {
            int v = l.recuperar().getDestino();
            double p = l.recuperar().getPeso();
            if (u < v && !enMST[u][v]) {
                if (mejor == null || p < mejor.getPeso()) {
                    mejor = new Arista(u, v, p);
                }
            }
        }
    }
    return mejor;
}
public boolean mstEsCamino() {
    Arista[] mst = kruskal();
    if (mst == null) return false;
    int[] grado = new int[numVertices()];
    for (Arista a : mst) {
        grado[a.getOrigen()]++;
        grado[a.getDestino()]++;
    }
    int hojas = 0;
    for (int g : grado) {
        if (g == 1) hojas++;
        else if (g != 2) return false;
    }
    return hojas == 2;
}
public boolean aristaCriticaEnMST(int u, int v) {
    Arista[] mst = kruskal();
    if (mst == null) return false;
    int pos = -1;
    double pesoUV = 0.0;
    for (int i = 0; i < mst.length; i++) {
        Arista a = mst[i];
        if ((a.getOrigen() == u && a.getDestino() == v) ||
            (a.getOrigen() == v && a.getDestino() == u)) {
            pos = i; pesoUV = a.getPeso(); break;
        }
    }
    if (pos == -1) return false; // no estaba en el MST

    // Eliminar la arista y comprobar si sigue conectado con igual coste
    Arista[] sinUV = new Arista[mst.length - 1];
    int k = 0;
    for (int i = 0; i < mst.length; i++) if (i != pos) sinUV[k++] = mst[i];

    GrafoNoDirigido g = new GrafoNoDirigido(numVertices());
    for (Arista a : sinUV)
        g.insertarArista(a.getOrigen(), a.getDestino(), a.getPeso());

    Arista[] nuevo = g.kruskal();
    if (nuevo == null) return true; // se desconecta
    double costeNuevo = 0.0;
    for (Arista a : nuevo) costeNuevo += a.getPeso();
    double costeOriginal = 0.0;
    for (Arista a : mst) costeOriginal += a.getPeso();
    return costeNuevo > costeOriginal;
}
public int cicloMasCorto() {
    int n = numVertices();
    int mejor = Integer.MAX_VALUE;
    for (int s = 0; s < n; s++) {
        int[] dist = new int[n];
        for (int i = 0; i < n; i++) dist[i] = -1;
        q = new ArrayCola<Integer>();
        dist[s] = 0; q.encolar(s);

        while (!q.esVacia()) {
            int u = q.desencolar();
            ListaConPI<Adyacente> l = adyacentesDe(u);
            for (l.inicio(); !l.esFin(); l.siguiente()) {
                int v = l.recuperar().getDestino();
                if (dist[v] == -1) {
                    dist[v] = dist[u] + 1;
                    q.encolar(v);
                } else if (dist[v] >= dist[u]) {
                    mejor = Math.min(mejor, dist[u] + dist[v] + 1);
                }
            }
        }
    }
    return (mejor == Integer.MAX_VALUE) ? -1 : mejor;
}
public int maximoGradoEntrada() {
    int[] entrada = new int[numVertices()];
    for (int u = 0; u < numVertices(); u++) {
        ListaConPI<Adyacente> l = adyacentesDe(u);
        for (l.inicio(); !l.esFin(); l.siguiente()) {
            entrada[l.recuperar().getDestino()]++;
        }
    }
    int max = 0;
    for (int g : entrada) if (g > max) max = g;
    return max;
}
public int contarAristasConPeso(double p) {
    int cont = 0;
    for (int u = 0; u < numVertices(); u++) {
        ListaConPI<Adyacente> l = adyacentesDe(u);
        for (l.inicio(); !l.esFin(); l.siguiente()) {
            if (l.recuperar().getPeso() == p) cont++;
        }
    }
    if (!esDirigido()) cont /= 2;
    return cont;
}
public double densidad() {
    int n = numVertices();
    if (n <= 1) return 0.0;
    double den = (double) numAristas() / (n * (n - 1));
    return esDirigido() ? den : 2 * den;
}
public int verticeMasLejanoMST(int origen) {
    Arista[] mst = kruskal();
    if (mst == null) return -1;

    @SuppressWarnings("unchecked")
    ListaConPI<Adyacente>[] g = new ListaConPI[numVertices()];
    for (int i = 0; i < g.length; i++) g[i] = new LEGListaConPI<>();
    for (Arista a : mst) {
        g[a.getOrigen()].insertar(new Adyacente(a.getDestino(), 1));
        g[a.getDestino()].insertar(new Adyacente(a.getOrigen(), 1));
    }

    int[] dist = new int[numVertices()];
    for (int i = 0; i < dist.length; i++) dist[i] = -1;
    ArrayCola<Integer> q = new ArrayCola<>();
    dist[origen] = 0; q.encolar(origen);
    int lejano = origen;

    while (!q.esVacia()) {
        int u = q.desencolar();
        if (dist[u] > dist[lejano]) lejano = u;
        ListaConPI<Adyacente> l = g[u];
        for (l.inicio(); !l.esFin(); l.siguiente()) {
            int w = l.recuperar().getDestino();
            if (dist[w] == -1) {
                dist[w] = dist[u] + 1;
                q.encolar(w);
            }
        }
    }
    return lejano;
}
public Arista puenteMasPesado() {
    int n = numVertices();
    int[] tin = new int[n], low = new int[n];
    for (int i = 0; i < n; i++) tin[i] = -1;
    Arista mejor = null;
    int tiempo = 0;

    for (int v = 0; v < n; v++)
        if (tin[v] == -1)
            mejor = dfsPuente(v, -1, tiempo, tin, low, mejor);
    return mejor;
}

private Arista dfsPuente(int v, int padre, int tiempo,
                         int[] tin, int[] low, Arista mejor) {
    tin[v] = low[v] = ++tiempo;
    ListaConPI<Adyacente> l = adyacentesDe(v);
    for (l.inicio(); !l.esFin(); l.siguiente()) {
        int w = l.recuperar().getDestino();
        double p = l.recuperar().getPeso();
        if (w == padre) continue;
        if (tin[w] == -1) {
            mejor = dfsPuente(w, v, tiempo, tin, low, mejor);
            low[v] = Math.min(low[v], low[w]);
            if (low[w] > tin[v]) { // Es puente
                if (mejor == null || p > mejor.getPeso())
                    mejor = new Arista(v, w, p);
            }
        } else {
            low[v] = Math.min(low[v], tin[w]);
        }
    }
    return mejor;
}
public int sucesorMST(int origen, int destino) {
    Arista[] mst = kruskal();
    if (mst == null) return -1;

    @SuppressWarnings("unchecked")
    ListaConPI<Adyacente>[] g = new ListaConPI[numVertices()];
    for (int i = 0; i < g.length; i++) g[i] = new LEGListaConPI<>();
    for (Arista a : mst) {
        g[a.getOrigen()].insertar(new Adyacente(a.getDestino(), 1));
        g[a.getDestino()].insertar(new Adyacente(a.getOrigen(), 1));
    }

    int[] pred = new int[numVertices()];
    for (int i = 0; i < pred.length; i++) pred[i] = -1;
    ArrayCola<Integer> q = new ArrayCola<>();
    pred[origen] = origen; q.encolar(origen);

    while (!q.esVacia() && pred[destino] == -1) {
        int u = q.desencolar();
        ListaConPI<Adyacente> l = g[u];
        for (l.inicio(); !l.esFin(); l.siguiente()) {
            int w = l.recuperar().getDestino();
            if (pred[w] == -1) { pred[w] = u; q.encolar(w); }
        }
    }
    if (pred[destino] == -1) return -1;
    int v = destino;
    while (pred[v] != origen) v = pred[v];
    return v;
}
public void eliminarAristasPorEncimaDe(double limite) {
    for (int u = 0; u < numVertices(); u++) {
        ListaConPI<Adyacente> l = adyacentesDe(u);
        l.inicio();
        while (!l.esFin()) {
            int v = l.recuperar().getDestino();
            double p = l.recuperar().getPeso();
            if (p > limite) {
                l.eliminar();
                if (!esDirigido()) {
                    ListaConPI<Adyacente> l2 = adyacentesDe(v);
                    for (l2.inicio(); !l2.esFin(); l2.siguiente()) {
                        if (l2.recuperar().getDestino() == u) {
                            l2.eliminar();
                            break;
                        }
                    }
                }
            } else l.siguiente();
        }
    }
}
public double[][] pesoCaminoMasCortoTodos() {
    int n = numVertices();
    double[][] dist = new double[n][n];
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            if (i == j) dist[i][j] = 0.0;
            else if (existeArista(i, j)) dist[i][j] = pesoArista(i, j);
            else dist[i][j] = Double.MAX_VALUE;
        }
    }
    for (int k = 0; k < n; k++) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (dist[i][k] != Double.MAX_VALUE &&
                    dist[k][j] != Double.MAX_VALUE &&
                    dist[i][k] + dist[k][j] < dist[i][j]) {
                    dist[i][j] = dist[i][k] + dist[k][j];
                }
            }
        }
    }
    return dist;
}

}
