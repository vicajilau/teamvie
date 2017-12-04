package engine;
import java.io.*;

/**
 * Write a description of class Proceso here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Proceso
{
    // instance variables - replace the example below with your own
    private Boolean estado;
    private String consola;
    private static final String cmd = "teamviewer daemon status";
    private String logs;

    /**
     * Constructor for objects of class Proceso
     */
    public Proceso()
    {
        logs = new String();
       lanzarComandoInicial();

    }
    
    public void lanzarComando(){
        try{
            consola = Terminal.lanzarComando(cmd);
            comprobarServicio();
        }catch(IOException err){
            consola = new String();
        }
    }
    
    public void lanzarComandoInicial(){
        try{
            consola = Terminal.lanzarComando(cmd);
            logs += consola;
            comprobarServicio();
        }catch(IOException err){
            consola = new String();
        }
    }

    /**
     * Comprueba el estado del servicio de teamviewer
     */
    public void comprobarServicio(){
        if(consola.contains("inactive")){
            estado = false;
        }else{
            estado = true;
        }
    }
    
    /**
     * Devuelve el estado del servicio
     * @return estado El estado del servicio
     */
    public Boolean getEstado(){
        lanzarComando();
        return estado;
    }
    
    /**
     * Devuelve los logs del proceso hasta ahora
     */
    public String getLogs() {
    	return logs;
    }
    
    /**
     * Cambia el estado del servicio a activo
     * @return true si lo ha cambiado con exito, false si no ha podido cambiarlo
     */
    public Boolean activarServicio(){
        lanzarComando();
        Boolean finaly = false;
        try{
            if(!estado){
            	String cmd = "bash ./config/activar-servicio";
            	logs += "\n" + Terminal.lanzarComandosPrivilegios(cmd);
                finaly = true;
            }
        }catch(IOException err){
        }
        return finaly;
    }
    
    /**
     * Cambia el estado del servicio a inactivo
     * @return true si lo ha desactivado con exito, false si no lo ha logrado
     */
    public Boolean desactivarServicio(){
        lanzarComando();
        Boolean finaly = false;
        try{
            if(estado){
            	String cmd = "bash ./config/desactivar-servicio";
            	logs += "\n" + Terminal.lanzarComandosPrivilegios(cmd);
                finaly = true;
            }
        }catch(IOException err){
        }
        return finaly;
    }
}
