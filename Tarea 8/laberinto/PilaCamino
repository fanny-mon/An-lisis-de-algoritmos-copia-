package laberinto;

import java.util.LinkedList;

public class PilaCamino {

    private LinkedList<int[]> pila;

    public PilaCamino() {
        pila = new LinkedList<>();
    }

    public boolean estaVacia() {
        return pila.isEmpty();
    }

    public void apilar(int[] coordenada) {
        pila.addFirst(coordenada);
    }

    public int[] desapilar() {
        if (pila.isEmpty()) return null;
        return pila.removeFirst();
    }

    public int[] cima() {
        if (pila.isEmpty()) return null;
        return pila.peekFirst();
    }

    public int tamanio() {
        return pila.size();
    }

    @Override
    public String toString() {
        return "Camino actual: " + pila.toString();
    }
}

