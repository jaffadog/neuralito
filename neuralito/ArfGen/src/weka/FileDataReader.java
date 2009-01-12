package weka;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Time;
import java.util.Date;
import java.util.Vector;

public class FileDataReader {
	
	private Vector<Vector<String>> linesValues = null;
	
	public FileDataReader(){
		this.linesValues = new Vector<Vector<String>>();
	}
	
	public Vector<Vector<String>> readFile(String fileName) {
	      File file = null;
	      FileReader fr = null;
	      BufferedReader br = null;

	      try {
	         // Apertura del fichero y creacion de BufferedReader para poder
	         // hacer una lectura comoda (disponer del metodo readLine()).
	         file = new File(fileName);
	    	 fr = new FileReader(file);
	         br = new BufferedReader(fr);

	         // Lectura del fichero
	         String line = "";
	         while((line = br.readLine())!=null){
	        	//System.out.println(line);
	         	this.linesValues.add(this.parseLine(line));
	         	line = "";
	         }
	      }
	      catch(Exception e){
	         e.printStackTrace();
	         return null;
	      }finally{
	         // En el finally cerramos el fichero, para asegurarnos
	         // que se cierra tanto si todo va bien como si salta 
	         // una excepcion.
	         try{                    
	            if( null != fr ){   
	               fr.close();
	               return this.linesValues;
	            }                  
	         }catch (Exception e2){ 
	            e2.printStackTrace();
	            return null;
	         }
	      }
	      return null;
	   }
	
	private Vector<String> parseLine(String line){
		String value = "";
		Vector<String> lineValues = new Vector();
		for (int i = 0; i < line.length(); i++){
			if (line.charAt(i) != ' ' && line.charAt(i) != '\n')
				value += line.charAt(i);
			else{
				if (value.trim() != "" && value.trim() != "\n")
					lineValues.add(value);
				value = "";
			}
		}
		return lineValues;
	}
	
	public boolean writeFile(String fileName, String text){
        FileWriter file = null;
        PrintWriter pw = null;
        try
        {
        	file = new FileWriter(fileName);
            pw = new PrintWriter(file);
            pw.println(text == null ? "" : text);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           // Nuevamente aprovechamos el finally para 
           // asegurarnos que se cierra el fichero.
           if (null != file){
				try {
					file.close();
					return true;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return false;
				}
           }
        }
        return false;
    }

}
