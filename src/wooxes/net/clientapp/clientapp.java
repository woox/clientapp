package wooxes.net.clientapp;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class clientapp {

	private static boolean log = false;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if( args.length>1){
			System.out.println("Per mostrar ajuda -h");
			return;
		}
		
		if( args.length == 1){
			if( args[0].equals("-h") ){
				System.out.println("Ajuda:");
				System.out.println("-h: Mostra l'ajuda");
				System.out.println("-log: Mostra informació de l'execució");
				return;
			}
			else if ( args[0].equals("-log")){
				log = true;
			}
		}


		// S'inicia l'execució, llegint el fitxer de propietats
    	Properties properties = new Properties();
    	try{
    		properties.load(new FileInputStream("clientapp.properties"));
    	}
    	catch(Exception e){
    		System.out.println("Error al obrir el fitxer de configuració");
    		return;
    	}

		String servidor = properties.getProperty("servidor");
		int id = Integer.parseInt(properties.getProperty("id"));
		String clau = properties.getProperty("clau");
		int temps = Integer.parseInt(properties.getProperty("temps", "10"));
		
		while(true){
			try{
				actualitzar(servidor , id, clau);
				Thread.currentThread();
				Thread.sleep(temps * 60 * 1000);

				
			}
			catch(MalformedURLException e){
				System.out.println(e.toString());
				return;
			} catch (InterruptedException e) {
				System.out.println(e.toString());
				return;
			}
		}
	}
	
	private static void actualitzar(String servidor, int id, String clau) throws MalformedURLException{
		String url = servidor + "/client?id=" + id  +"&clau="+clau;
		if( log ){
			String date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss - ").format(new Date());
			System.out.println(date + "Conectant a...");
			System.out.println(date + servidor);
		}

        URL miURL = new URL(url);
        
        try{
	        if( log ){
		        BufferedReader in = new BufferedReader(new InputStreamReader(miURL.openStream()));
		        String line;
		        while ((line = in.readLine()) != null) {
		        	System.out.println(line);
		        }
		        in.close();
	
	        }
	        else{
	        	// Si no es vol llegir el contingut, no és necessari tot el codi de sobre
	        	miURL.getContent();
	        }
        }
        catch(Exception e){
        	// Error al llegir la URL
        	if( log){
        		System.out.println(e.toString());
        	}
        }
	}
}
