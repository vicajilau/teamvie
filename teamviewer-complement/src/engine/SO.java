package engine;

public class SO {
	
	private static String OS = System.getProperty("os.name").toLowerCase();
	
	 public static boolean isWindows() {
	        return (OS.indexOf("win") >= 0);
	    }
	 
	    public static boolean isMac() {
	        return (OS.indexOf("mac") >= 0);
	    }
	 
	    public static boolean isUnix() {
	        return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 );
	    }
	 
	    public static boolean isSolaris() {
	        return (OS.indexOf("sunos") >= 0);
	    }
	    
	    /**
	     * Comprueba el sistema operativo del sistema que lo ejecuta y devuelve el so
	     * @return 0 Windows, 1 Mac, 2 Unix, 3 Solaris, 4 Desconocido
	     */
	    public static int getSoSystem() {
	    	if(isWindows()) {
	    		return 0;
	    	}else if(isMac()){
	    		return 1;
	    	}else if(isUnix()) {
	    		return 2;
	    	}else if(isSolaris()) {
	    		return 3;
	    	}else {
	    		return 4;
	    	}
	    }
}
