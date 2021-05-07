package Clases;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


/**
 *
 * @author linux
 */
public class CargarArchivo {
    
    public String leerTxt(String Dir) throws IOException{
        String cad = "";
        
        try {
            FileReader TxtArchivo = new FileReader(Dir);
            BufferedReader DatTemp = new BufferedReader(TxtArchivo);
            String linea;
            
            while((linea = DatTemp.readLine()) != null){
                cad += linea + "\n";
            }
            
            DatTemp.close();
            TxtArchivo.close();
            
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Error al abrir el archivo", "Error",2);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al leer el archivo", "Error",2);
        }
        
        return cad;
    }
    
    public boolean CaracteresEspeciales(char car){
        if((car == '(')||
                (car == ')')||
                (car == '{')||
                (car == '}')){
            return true;
        }else{
            return false;
        }
    }  
    
    //obtener caracater por caracter de cada lin del txt
    public String caracter(String cadena){
        char c;
        String s1="";
        String s2="";
        
        for(int a=2; a<cadena.length(); a++){
            c=cadena.charAt(a);
            if(!CaracteresEspeciales(c)){
                s1 += c;
            }
        } 
        
        String[] arreglo = s1.split(",");
        
        for(int b=0; b<arreglo.length; b++){
            s2 += arreglo[b] + "\n";
        }
        return s2;
    }
    
    //obtener lineas del txt
    public String linea(int n, String Dir){
        String lin = "";
        int contador = 0;
        
        try {
            FileReader TxtArchivo = new FileReader(Dir);
            BufferedReader DatTemp = new BufferedReader(TxtArchivo);
            lin = DatTemp.readLine();
            while((lin != null) && (contador < n)){
                lin = DatTemp.readLine();
                contador++;
            }
            
            DatTemp.close();
            TxtArchivo.close();
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CargarArchivo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CargarArchivo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lin;
    }
    
}
