
package com.mycompany.colaborar;

import java.io.File;
import java.io.RandomAccessFile;


public class lanzador {

    public static void main(String[] args) {
        //Variables
        Process nuevoProceso;
        File archivo = null;
        RandomAccessFile raf = null;
        

        //Creamos 10 procesos que accederán al mismo fichero creando X palabras cada uno
        try {
            for (int i = 1; i <= 10; i++) {
                //Lanza un proceso p empezando por el valor 1 y acabando por el 10
                //Luego saca por consola el numero del procesocreado
                nuevoProceso = Runtime.getRuntime().exec("java -jar "
                        + "proceso_jmrr.jar " + i*10 + " fichero.txt " + (i-1));
                System.out.println("Creado el proceso " + i);
                
             }
        //recoge excepciones
        } catch (SecurityException ex) {
            System.err.println("Ha ocurrido un error de Seguridad."
                    + "No se ha podido crear el proceso por falta de permisos.");
        } catch (Exception ex) {
            System.err.println("Ha ocurrido un error, descripción: "
                    + ex.toString());
        }
    }
}