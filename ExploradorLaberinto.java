package laberinto;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ExploradorLaberinto {

    public static void main(String[] args) {
        String rutaArchivo = "./entrada.txt";
        MapaLaberinto laberinto = null;

        try (BufferedReader lector = new BufferedReader(new FileReader(rutaArchivo))) {

            int filas = Integer.parseInt(lector.readLine());
            int columnas = Integer.parseInt(lector.readLine());
            laberinto = new MapaLaberinto(filas, columnas);

            String linea;
            int filaActual = 0;

            while ((linea = lector.readLine()) != null) {
                int col = 0;
                for (int i = 0; i < linea.length(); i++) {
                    char c = linea.charAt(i);
                    if (c != ' ' && c != ',' && c != '\n') {
                        laberinto.setCelda(filaActual, col, c);
                        col++;
                    }
                }
                filaActual++;
            }

        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
            return;
        }

        resolverConBacktracking(laberinto);
    }

    public static void resolverConBacktracking(MapaLaberinto laberinto) {
        PilaCamino pila = new PilaCamino();
        int[] inicio = new int[2];

        /////////////  entrada e/////////////
        for (int i = 0; i < laberinto.getFilas(); i++) {
            for (int j = 0; j < laberinto.getColumnas(); j++) {
                if (laberinto.getValor(i, j) == 'E') {
                    inicio[0] = i;
                    inicio[1] = j;
                    break;
                }
            }
        }

        pila.apilar(inicio);

        while (!pila.estaVacia()) {
            int[] posicionActual = pila.cima();
            int fila = posicionActual[0];
            int columna = posicionActual[1];

            if (laberinto.getValor(fila, columna) == 'S') {
                System.out.println("Salida encontrada en (" + fila + ", " + columna + ")   :D");
                mostrarCamino(pila, laberinto);
                return;
            }

            if (laberinto.getValor(fila, columna) != 'E') {
                laberinto.setCelda(fila, columna, 'X'); // Marca como visitado
            }

            boolean movido = false;

            
            if (esMovimientoValido(fila + 1, columna, laberinto)) {
                pila.apilar(new int[]{fila + 1, columna});
                movido = true;
            } else if (esMovimientoValido(fila - 1, columna, laberinto)) {
                pila.apilar(new int[]{fila - 1, columna});
                movido = true;
            } else if (esMovimientoValido(fila, columna + 1, laberinto)) {
                pila.apilar(new int[]{fila, columna + 1});
                movido = true;
            } else if (esMovimientoValido(fila, columna - 1, laberinto)) {
                pila.apilar(new int[]{fila, columna - 1});
                movido = true;
            }

            if (!movido) {
                pila.desapilar(); 
            }
        }

        System.out.println(“No se encontró salida en el laberinto.");
    }

    private static boolean esMovimientoValido(int fila, int columna, MapaLaberinto laberinto) {
        if (fila >= 0 && fila < laberinto.getFilas() && columna >= 0 && columna < laberinto.getColumnas()) {
            char valor = laberinto.getValor(fila, columna);
            return valor == '0' || valor == 'S';
        }
        return false;
    }

    private static void mostrarCamino(PilaCamino pila, MapaLaberinto laberinto) {
        while (!pila.estaVacia()) {
            int[] paso = pila.desapilar();
            int fila = paso[0];
            int columna = paso[1];

            if (laberinto.getValor(fila, columna) != 'E' && laberinto.getValor(fila, columna) != 'S') {
                laberinto.setCelda(fila, columna, '·');
            }
        }

        System.out.println("\nCamino recorrido:\n");
        System.out.println(laberinto);
    }
}
