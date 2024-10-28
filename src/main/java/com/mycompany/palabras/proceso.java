package com.mycompany.palabras;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;

public class proceso {

    // array con los caracteres del codigo anglosajón para referenciar
    static char abecedario[] = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
            'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };

    public static void main(String[] args) {

        // Variables
        
        String nombreFichero = "";
        File archivo = null;
        RandomAccessFile raf = null;
        FileLock bloqueo = null;
        int orden;
        // Numero de palabras que se han de crear
        
        

        // Comprobamos que nombre le damos nombre al fichero
        nombreFichero = args[1];

        //Se inicializa la variable archivo con el valor del nombre del fichero
        archivo = new File(nombreFichero);

        // Comprobamos si existe el fichero y si no se crea y se inicializa con el valor 0
        // Si lo creamos con este proceso comprobamos que se cierra, si no, lanzamos un error
        if (!archivo.exists()) {
            try {
                archivo.createNewFile();
                raf = new RandomAccessFile(archivo, "rw");
                raf.writeChars("");
                System.out.println("Creado el fichero.");
            } catch (Exception e) {
                System.err.println("Error al crear el fichero");
                System.err.println(e.toString());
            } finally {
                try {
                    if (null != raf) {
                        raf.close();
                    }
                } catch (Exception e2) {
                    System.err.println("Error al cerrar el fichero");
                    System.err.println(e2.toString());
                    System.exit(1);
                }
            }
        }

        try {
            // numero de palabras que queremos que genere
            // si no es numero salta excepción
            int noPalabras = Integer.parseInt(args[0]);

            // Numero del proceso en cuestión
            if (args.length > 2 ){
                
                orden = Integer.parseInt(args[2]);
            }else{
                orden = 0;
            }

            // Para manejar los caracteres con más comodidad
            StringBuilder palabra = new StringBuilder();

            // bucle que genera el numero de palabras
            for (int i = noPalabras; i > 0; i--) {

                // Longitud de la palabra a crear
                int longitud = getLength();

                // bucle que genera los caracteres de cada palabra
                for (int j = 0; j < longitud; j++) {

                    // Los meto en el StringBuilder
                    palabra.append(getVal(getCharVal()));

                }

                try {
                    //********
                    //Sección crítica
                    //********
                    raf = new RandomAccessFile(archivo, "rwd"); 
                    //Bloquea el archivo para que solo este proceso pueda editarlo
                    bloqueo = raf.getChannel().lock();
                    //Mueve el cursor a la ultima posición del archivo en cuestión
                    raf.seek(raf.length());
                    //Escritura de cursor
                    raf.writeBytes("Proceso "+ orden +": " + palabra + "\n");
                    //Liberamos el bloqueo del canal del fichero
                    bloqueo.release();
                    bloqueo = null;
                //Captura de excepciones
                } catch (Exception e) {
                    System.err.println("Proceso " + orden + " Error al acceder al fichero");
                    System.err.println(e.toString());
                } finally {
                    //Comprueba que el archivo no se quede bloqueado
                    try {
                        if (null != raf) {
                            raf.close();
                        }
                        if (null != bloqueo) {
                            bloqueo.release();
                        }
                    } catch (Exception e2) {
                        System.err.println("Proceso " + orden + ": Error al cerrar el fichero");
                        System.err.println(e2.toString());
                        System.exit(1);  //Si hay error, finalizamos
                    }
                    //*****
                    // Fin sección crítica
                    //*****
                }

                // borro la palabra que había generado del stringbuilder
                // y se vuelve a realizar el bucle tantas veces como se especifique
                palabra.setLength(0);
            }

        } catch (NumberFormatException e) {
            System.err.println(e.toString());
        }

    }

    // Metodo que usando el metodo random genera la longitud de las palabras
    static int getLength() {
        int len;
        len = (int) (Math.random() * (15 - 5 + 1) + 5);

        return len;
    }

    // Metodo que usando el metodo random genera los caracteres de las palabras
    static int getCharVal() {
        int cha;

        cha = (int) (Math.random() * (26 - 1));

        return cha;
    }

    // Metodo que referenciando al array extrae el valor alfabetico del numero
    // aportado
    static char getVal(int val) {
        char cha;
        cha = abecedario[val];

        return cha;
    }

}
