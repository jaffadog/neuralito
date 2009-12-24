package edu.unicen.surfforecaster.gwt.server.util;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Vector;

public class FileDataIO {
	
	private Vector<Vector<String>> linesValues = null;
	
	public FileDataIO(){
		this.linesValues = new Vector<Vector<String>>();
	}
	
	public Vector<Vector<String>> readFile(InputStream is) {
	      BufferedReader br = null;

	      try {
	    	 br = new BufferedReader(new InputStreamReader( is ));

	         // Read file
	         String line = "";
	         while((line = br.readLine())!=null){
	         	this.linesValues.add(this.parseLine(line));
	         	line = "";
	         }
	      }
	      catch(Exception e){
	         e.printStackTrace();
	         return null;
	      }finally{
	    	  try {
				br.close();
				return this.linesValues;
			} catch (IOException e) {
				e.printStackTrace();
			}  
	      }
	      return null;
	   }
	
	private Vector<String> parseLine(String line){
		String value = "";
		Vector<String> lineValues = new Vector<String>();
		for (int i = 0; i < line.length(); i++){
			if (line.charAt(i) != ' ' && line.charAt(i) != '\n')
				value += line.charAt(i);
			else{
				if (value.trim() != "" && value.trim() != "\n")
					lineValues.add(value);
				value = "";
			}
		}
		if (value.trim() != "" && value.trim() != "\n")
			lineValues.add(value);
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
           if (null != file){
				try {
					file.close();
					return true;
				} catch (IOException e) {
					e.printStackTrace();
					return false;
				}
           }
        }
        return false;
    }

}
