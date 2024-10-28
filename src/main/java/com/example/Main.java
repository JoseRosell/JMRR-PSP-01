package com.example;

import java.util.ArrayList;
import java.util.Collections;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        // Variables
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(isr);
        ArrayList<Integer> input = new ArrayList<Integer>();
        String entrada = null;

        // paso los elementos a un
        // array input para manejarlos facilmentne
        try {
            while ((entrada = in.readLine()) != null && !entrada.equals("")) {
                // Convierte la entrada en un entero y lo agrega a la lista
                input.add(Integer.parseInt(entrada));
            } 

        // el metodo sort ordena los parametros del array
        Collections.sort(input);

        // muestro uno por uno los elementos del array
        for (int i : input) {
            System.out.println(i);
        }
    } catch (IOException ex) {
        System.err.println(ex.getMessage());
    } catch (NumberFormatException ex) {
        System.err.println("Error: Entrada no válida, asegúrate de que lo que pasas sean números.");
    }
    }
}
