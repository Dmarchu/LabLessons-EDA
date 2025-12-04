package aplicaciones.hospital;


/**
 * Write a description of class ServidorColaPrioridadPlus here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ServidorColaPrioridadPlus extends ServidorColaPrioridad implements ServidorQuirofanoPlus{
    private int talla;
    
    public ServidorColaPrioridadPlus(){
        super();
        talla = 0;
    }
    
    public int numPacientes(){
        return talla;
    }
    
    public Paciente transferirPaciente(){
        Paciente res = cP.eliminarMin();
        talla--;
        return res;
    }
    
    public void distribuirPacientes(ServidorQuirofanoPlus s){
        Paciente[] res = new Paciente[talla];
        for(int i = 0; i < res.length; i++){
            res[i] = this.transferirPaciente();
        }
        for(int i = 0; i < res.length; i++){
            if(i % 2 != 0){
                this.insertarEnEspera(res[i]);
            } else {
                s.insertarEnEspera(res[i]);
            }
        }
    }
    
    public void insertarEnEspera(Paciente p){
        talla++;
        super.insertarEnEspera(p);
    }
    
    public Paciente operarPaciente(int h){
        talla--;
        return super.operarPaciente(h);
    }
}
