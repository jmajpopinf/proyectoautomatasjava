
package Clases;

public class ConversionAutomata {
    
    public ConversionAutomata(){
    }
    
    String So;
    String [] WAFN, WAFD;
    String [] alfabeto;
    String [] estados;

    public void setSo(String So) {
        this.So = So;
    }

    public void setW_AFN(String[] WAFN) {
        this.WAFN = WAFN;
    }

    public void setW_AFD(String[] WAFD) {
        this.WAFD = WAFD;
    }

    public void setAlfabeto(String[] alfabeto) {
        this.alfabeto = alfabeto;
    }

    public void setEstados(String[] estados) {
        this.estados = estados;
    }
    
    
    public String [][] AFD(){            
        String [][] MTAutomataAFD = new String [2][alfabeto.length+2];
        
        MTAutomataAFD = TitulosMatriz(0, 0, 1, alfabeto.length, MTAutomataAFD, alfabeto);        
        MTAutomataAFD[0][0] = "Q";
        MTAutomataAFD[0][alfabeto.length+1] = "Composicion";
        MTAutomataAFD[1][0] = EstadosA_Z(0);
        MTAutomataAFD[1][alfabeto.length+1] = Cerradura(So);
        MTAutomataAFD = afnAafd(MTAutomataAFD, 1, 1);            
        return MTAutomataAFD;
    }
    
    
    public String [][] TitulosMatriz(int Finicial, int fila, 
            int ColumInicial, int columna, 
            String matriz[][], String cadena[]){
        
        int a=0;
        
        for(int b=Finicial; b<=fila; b++){
            for(int c=ColumInicial; c<=columna; c++){
                matriz[b][c] = cadena[a];
                a++;
            }
        }
        return matriz;
    }
     
    public String EstadosA_Z(int pos){
        String cadenaEstados="";
        String EAZ="";
        
        for(char a='A' ;a<='Z'; a++){
            cadenaEstados += a;
        }                
            EAZ = String.valueOf(cadenaEstados.charAt(pos));        
        return EAZ;
    }
    
    public String Cerradura(String So){
        String s1="", s2="", s3="";
        String[] cadena;
        
        cadena = So.split(","); //convertimos la c en un vector
        
        for(int a=0; a<cadena.length; a++){        
            for(int b=0; b<WAFN.length; b++){
                if((WAFN[b].equals(cadena[a]))&&(WAFN[b+1].equals("e"))){   //compara los estados con los estados de la funtrans con el valor e lamda                       
                    s1 += WAFN[b+2] + "," + Cerradura(WAFN[b+2]) + ",";      //= (1,e,(1)),(1,a,(2))            
                }else if((WAFN[b].equals(cadena[a]))&&(!(WAFN[b+1].equals("e")))){
                    s1 += "";
                }
                if(cadena[a].equals(" ")){
                    s1 = "0";
                }
                b+=2;      
            }  
        }        
        for(int j=0; j<cadena.length-1; j++){
            s3 = s3 + cadena[j] + ",";
        }
        s3 += cadena[cadena.length-1];      
        s1 += s3;      
        s2 = ordenarC(s1); //ordena la composicion del afd
        return s2;
    } 
    
    
    public String ordenarC(String cadena){
        String s1="", s2="";
        String [] arreglo = cadena.split(",");           
        String temporal;
        int a, b;
        
        for(a=0; a<arreglo.length; a++){
            for(b=a+1; b<arreglo.length; b++){
                if(V_ascii(arreglo[a])>V_ascii(arreglo[b])){
                    temporal = arreglo[a];
                    arreglo[a] = arreglo[b];
                    arreglo[b] = temporal;                   
                }
            }
        } 
        
        for(a=0; a<arreglo.length-1; a++){
            s1 += arreglo[a] + ",";
        }
        
        s1 += arreglo[arreglo.length-1];  
        s2 = Repetidos(s1);        
        return s2;
    }   
    
    public String Repetidos(String cadena){
        String str="", str2="";
        String actual, anterior;
        int i=1;
        
        String [] arreglo = cadena.split(",");      
        str += arreglo[0] + ",";
        while(i < arreglo.length){
            anterior = arreglo[i-1];
            actual = arreglo[i];            
            if(actual.equals(anterior)){            
            }else{
                str += arreglo[i] + ",";
            }
            i++;
        }
        String [] array2 = str.split(",");         
        for(int j=0; j<array2.length-1; j++){
            str2 += array2[j] + ",";
        }
        str2 += array2[array2.length-1];
        return str2;
    }
    
    
    public Integer V_ascii(String cadena){
        int resul = 0;
        
        for(int i=0; i<cadena.length(); i++){
            resul += (int)cadena.charAt(i);
        }
        return resul;
    }
   
    
    public String [][] afnAafd(String[][] MTAutomataAFD, int fila, int cnt){   
        String U, Transicion, xAlfabeto;
        int auxiliar=0;  
        
        if(fila < (cnt+1)){
            Transicion = MTAutomataAFD[fila][0]; //posicion fila 1 columna 0 = estado
            for(int a=0; a<alfabeto.length; a++){     
                xAlfabeto = alfabeto[a]; 
                U = Cerradura(Mueve(Transicion, xAlfabeto, MTAutomataAFD));            
                for(int b=1; b<MTAutomataAFD.length; b++){ //recorremos toda la fila de la matriz
                    if(U.equals(MTAutomataAFD[b][alfabeto.length+1])){ //si la recorrio auxiliar =1 para avanzar a la sig.
                        auxiliar = 1;                   
                    }
                }
                if(auxiliar == 0){ 
                    cnt+=1;
                    String [][] copia = new String [cnt+1][alfabeto.length+2];
                    copia = copiarMatriz(copia, MTAutomataAFD);                    
                    copia[cnt][0] = EstadosA_Z(cnt-1); 
                    copia[cnt][alfabeto.length+1] = U; 
                    MTAutomataAFD = copia;
                }else{
                    auxiliar = 0;                   
                }           
                for(int k=1; k<MTAutomataAFD.length; k++){
                    if(U.equals(MTAutomataAFD[k][alfabeto.length+1])){                  
                        for(int l=1; l<alfabeto.length+1; l++){                 
                            if(xAlfabeto.equals(MTAutomataAFD[0][l])){                         
                                MTAutomataAFD[fila][l] = MTAutomataAFD[k][0];                           
                            }
                        }                                 
                    }
                }
            }         
        MTAutomataAFD = afnAafd(MTAutomataAFD, fila+1, cnt);            
        }        
        return MTAutomataAFD;            
    }
    
    public String [][] copiarMatriz(String matriz [][], String[][] MTAutomataAFD){            
        int i, j;
        
        for(i=0; i<matriz.length; i++){
            for(j=0; j<alfabeto.length+2; j++){
                matriz[i][j] = " ";
            }
        }
         for(i=0; i<MTAutomataAFD.length; i++){
            for(j=0; j<alfabeto.length+2; j++){
                matriz[i][j] = MTAutomataAFD[i][j];
            }
        }
        return matriz;
    }
    
    public String Mueve(String transicion, String xAlfabeto, String [][] matriz){
        String s1="", str1, s2="", c [], cT="";
        
        for(int i=0; i<matriz.length; i++){      //for para el largo de la matriz      
            if(transicion.equals(matriz[i][0])){ //compara el estado que recibimos con la poscion [0][0]
               cT = matriz[i][alfabeto.length+1]; //ct= a la primera fila dela matriz afn
            }            
        }
        c = cT.split(",");
        for(int a=0; a<c.length; a++){
            for(int b=0; b<WAFN.length; b++){            
                if(WAFN[b].equals(c[a])){   //compara la funcion de trancicion afn a la primera fila matriz
                    if(WAFN[b+1].equals(xAlfabeto)){ //compara el alfabeto de la funcTran es igual al alfabeto de la matriz
                        s2 += WAFN[b+2] + ",";   //avanzamos a la siguiente expresion transicion de la funcion            
                    }else if(!(WAFN[b+1].equals(xAlfabeto))){ //si no son iguales
                        s1 = "";            //toma valor vacio           
                    }
                }
                b+=2;   //si estamos en el primer estado se realiza la comparacion y avanza a la siguiente funcion          
            }
            s1 += s2; 
        }
        if(s1.equals("")){
           s1 = " ";
        }        
        str1 = ordenarC(s1);  //ordena los estados obtenidos  
        return str1;
    }
    
    
    public String [][] AFN(String[][] Matriz){
        Matriz = TitulosMatriz(1, estados.length, 0, 0, Matriz, estados);
        Matriz = TitulosMatriz(0, 0, 1, alfabeto.length, Matriz, alfabeto);
        Matriz[0][0] = "Q";
        Matriz[0][alfabeto.length+1] = "e";
        Matriz = M_AFN(Matriz);
        return Matriz;
    }
    
    public String [][] M_AFN(String[][] Matriz){ 
        String cadena = "", estado = "", F_alfa = "";
        int x=0, y=0, z=0;   
        
        for(int a=1; a<=estados.length; a++){
            for(int b=1; b<=(alfabeto.length+1); b++){
                Matriz[a][b] = " - ";
                if(x<WAFN.length){ 
                    //iguala la posicion en matriz con la funcion de transicion
                    if((Matriz[a][0].equals(WAFN[x]))&&(Matriz[0][b].equals(WAFN[x+1]))){
                        y = a; //en el primer ciclo = 1
                        z = b; //en el primer ciclo = 1
                       
                        estado = WAFN[x]; //obtenemos estado 1
                        F_alfa = WAFN[x+1];  //obtenemos alfabeto a                      
                        cadena = WAFN[x+2];  //en el primer cilo c = 1
                        
                        Matriz[a][b] = cadena;   //imprime en la poscion [1][1] = 1
                        
                    }else if((WAFN[x].equals(estado))&&(WAFN[x+1].equals(F_alfa))){ //si ya se comprovo la funcion 
                        cadena = cadena + "," + WAFN[x+2]; //avanzamos a la siguiente funcion de transicion 
                        a=y;//posicion =1
                        b=z; //posicion = 3
                        
                        Matriz[a][b] = cadena; //imprime ne la posicion [1][3] = 2
                        
                    }else if((!(Matriz[a][0].equals(WAFN[x])))||(!(Matriz[0][b].equals(WAFN[x+1])))){
                        Matriz[a][b] = " - ";
                        x-=3;
                    }                    
                    x+=3;
                }
            }             
        }      
        return Matriz;
    } 
    
    
        //obtener los estados de aceptacion como vector
    public String Aceptacion(String[][] MTAutomataAFD, String cadena []){
        String str="", str1="", str2, str3;
        for(int a=0; a<cadena.length; a++){
            for(int i=1; i<MTAutomataAFD.length; i++){
                if(MTAutomataAFD[i][alfabeto.length+1].contains(cadena[a])){
                    str += MTAutomataAFD[i][0];
                }
            }
        }       
        for(int b=0; b<str.length()-1; b++){
            str1 += str.charAt(b) + ",";
        }
        str1 += str.charAt(str.length()-1);                
        str2 = ordenarC(str1);               
        str3 = str2.replaceAll(",", "\n");
        return str3; 
    }
    
        //obtener los estados de aceptacion en lin
    public String AceptacionAFD(String[][] MTAutomataAFD, String cadena []){
        String str="", str1="", str2, str3;
        for(int a=0; a<cadena.length; a++){
            for(int i=1; i<MTAutomataAFD.length; i++){
                if(MTAutomataAFD[i][alfabeto.length+1].contains(cadena[a])){
                    str += MTAutomataAFD[i][0];
                }
            }
        }       
        for(int b=0; b<str.length()-1; b++){
            str1 += str.charAt(b) + ",";
        }
        str1 += str.charAt(str.length()-1);                
        str2 = ordenarC(str1);               
        str3 = str2.replaceAll(",", ",");
        return str3; 
    }
    
    //obtenemos la funcion de transicion afd por medio de la matriz afd
    public String FunTransicionAFD(String[][] MTAutomataAFD, String [] estados){
        String str="", str1="";
        for(int g=0; g<estados.length; g++){
            for(int h=0; h<alfabeto.length; h++){
                for(int i=1; i<MTAutomataAFD.length; i++){
                    for(int j=1; j<=alfabeto.length; j++){
                        if((estados[g].equals(MTAutomataAFD[i][0]))&&(alfabeto[h].equals(MTAutomataAFD[0][j]))){
                            str += MTAutomataAFD[i][0] + "," + MTAutomataAFD[0][j] + "," + MTAutomataAFD[i][j] + ",";
                        }            
                    }
                }
            }
        }
        for(int l=0; l<str.length()-1; l++){
            str1 += str.charAt(l) ;         
        }
        return str1;        
    }
    
    
    public String evalCadena(String cadena, String[][] Matriz, String aceptacion){ 
        String[] arreglo = cadena.split("");        
        String estado="", resultado="";
        
        //verifica si la cad es nula
        if(arreglo[0].equals("")){
            estado = "A";
        }else{
            estado = obtenerEstado(cadena, "A", 0); //obtenemos los estado que corresponden x la cad            
        }
        //si el estado de la cad estan entre los estados de aceptacin del afd
        if(aceptacion.contains(estado)){                 
           resultado = "CadenaAceptada";
        }else{
            resultado = "CadenaNoAceptada";
        }        
        return resultado;
    }
    
    public String obtenerEstado(String cadena, String So, int pos){
        String[] arreglo = cadena.split("");
        //obtenemos los estados por medio de la funcion de transicion
        String estado="";        
        for(int i=0; i<WAFD.length; i++){            
            if(arreglo[pos].equals(WAFD[i+1])){
                if(So.equals(WAFD[i])){                    
                    estado = WAFD[i+2];
                }
            }                        
            i+=2;
        }       
        if(arreglo.length > (pos+1)){                
            estado = obtenerEstado(cadena, estado, pos+1);
        }
        return estado;        
    }
    
    
    //metodos para obtener el txt del automata afd
    
     public String EstadosAFD(String[][] MTAutomataAFD){
        String str="";
        for(int i=1; i<MTAutomataAFD.length; i++){
            str += MTAutomataAFD[i][0] + "\n";
        }
        return str; 
    }

}
