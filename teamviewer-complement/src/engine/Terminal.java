package engine;
import java.io.*;

/**
 * Write a description of class Daemon here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Terminal
{
	/**
	 * Permite lanzar el comando recibido por parámetro a la consola del sistema
	 * @param cmd El comando a lanzar
	 * @return El valor resultante de la consola de comandos
	 * @throws IOException Excepción de entrada y salida
	 */
    public static String lanzarComando2(String cmd)throws IOException{
        Process proceso = Runtime.getRuntime().exec (cmd);
        InputStream inputstream = proceso.getInputStream();
        BufferedInputStream bufferedinputstream = new BufferedInputStream(inputstream);
        
         int numRead;
         final int bufferSize = 1024;
         byte[] buffer = new byte[bufferSize];
         ByteArrayOutputStream outString = new ByteArrayOutputStream();
        
         try{
             while ((numRead = bufferedinputstream.read(buffer)) != -1) {
            outString.write(buffer, 0, numRead);
        }
    } finally {
        bufferedinputstream.close();
    }
    return new String(outString.toByteArray());
    }
    
    public static String lanzarComando(String comando)throws IOException{
    	String[] cmd = {"/bin/bash","-c",comando};
    	Process pb = Runtime.getRuntime().exec(cmd);

        String line;
        String texto = new String("Estado inicial:\n");
        BufferedReader input = new BufferedReader(new InputStreamReader(pb.getInputStream()));
        while ((line = input.readLine()) != null) {
            texto += line + "\n";
        }
        input.close();
        return texto;
    }
    
    /**
     * Lanza comandos con privilegios de administrador y luego los devuelve por el texto
     * @param cmd El comando a introducir
     * @return El texto devuelto por la consola de comandos
     * @throws IOException
     */
    public static String lanzarComandosPrivilegios(String cmd) throws IOException{
        Process pb = Runtime.getRuntime().exec(cmd);

        String line;
        String texto = new String();
        BufferedReader input = new BufferedReader(new InputStreamReader(pb.getInputStream()));
        while ((line = input.readLine()) != null) {
            texto += line + "\n";
        }
        input.close();
        return texto;
    }
}
