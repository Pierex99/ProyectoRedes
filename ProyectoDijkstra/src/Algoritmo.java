import java.util.ArrayList;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Piero
 */
public class Algoritmo{
    int cantNodos = 0;
    ArrayList listaSimp = new ArrayList(); //para matriz de pesos
    ArrayList listaCompSimp = new ArrayList(); //para matriz de pesos
    int[][] mAdyacencias; //matriz de adyacencias
    ArrayList caminosPosibles = new ArrayList();
    String temp = "";

    public Algoritmo(int numNodos){
        cantNodos = numNodos;
        mAdyacencias = new int[numNodos][numNodos];
        int nodoOrigen = -1;
        
        //INGRESO DE PESOS
        boolean existeError;
        System.out.println("\n-----INGRESO DE PESOS----- (ingrese 0 si no hay conexion)");
        for (int i = 1; i <= cantNodos; i++) {
            for (int j = 1; j <= cantNodos; j++) {
                if (j != i) {
                    existeError = false;
                    System.out.print("Peso de la arista "+(char)(i+64)+"->"+(char)(j+64)+": ");
                    try{
                        Scanner sc = new Scanner(System.in);
                        String input = sc.nextLine();
                        mAdyacencias[i-1][j-1] = Integer.parseInt(input);
                        existeError = (mAdyacencias[i-1][j-1]<0);
                        mAdyacencias[i-1][j-1] = (mAdyacencias[i-1][j-1]==0?-1:mAdyacencias[i-1][j-1]);
                    }catch(Exception e){
                        System.out.println("ERROR.");
                        existeError = true;
                    }
                    if (existeError) {
                        j--;
                    }
                }else{
                    mAdyacencias[i-1][i-1] = -1;
                }
            }
        }
        //INGRESO DE NODO ORIGEN
        while(nodoOrigen < 0 || nodoOrigen > cantNodos-1){
            try{
                System.out.print("NODO ORIGEN (letra): ");
                Scanner sc = new Scanner(System.in);
                String input = sc.nextLine();
                nodoOrigen=((int)(input.toUpperCase()).charAt(0))-65; //aqui se ingresa un caracter y se convierte a su equivalente en numero
            }
            catch(Exception e){
                System.out.println("ERROR.");
                nodoOrigen=-1;
            }
        }
        mAdyacencias[nodoOrigen][nodoOrigen]=0; //la conexion al mismo nodo vale 0
        hallarCaminos(nodoOrigen); //se hallan los menores caminos a cada nodo a partir del origen
    }

    // Metodo para hallar los caminos desde un origen a los demas nodos
    public void hallarCaminos(int origen){
        int min;
        int aux;
        int nodo;
        int nodoCambio = 0;
        int intento;
        //INICIALIZAR LISTAS
        for (int i = 0; i < cantNodos; i++) {
            if (i != origen) {
                listaCompSimp.add(""+i); //para que sea string
            }else{
                listaSimp.add(""+i);
            }
            caminosPosibles.add("");
        }
        //PARA CADA REPETICION I DE DIJKSTRA
        for (int i = 0; i < cantNodos; i++) {
            min = -1;
            for (int j = 0; j < listaCompSimp.size(); j++) {
                nodo = Integer.parseInt((String)(listaCompSimp.get(j)));
                aux = hallarMinimo(nodo);
                if (min == -1 || (aux < min && aux != -1)) {
                    min = aux;
                    nodoCambio = j;
                }
            }
            if (min != -1) {
                listaSimp.add(""+(String)(listaCompSimp.get(nodoCambio)));
                listaCompSimp.remove(nodoCambio);
            }
        }
        //IMPRIMIR
        System.out.print("\n-----CAMINO MAS CORTO HACIA CADA NODO-----");
        for (int k = 0; k < caminosPosibles.size(); k++)
            if (k != origen) {
                temp = (String)(caminosPosibles.get(k))+(char)(k+65);
                caminosPosibles.set(k,temp);
            }
        for (int j = 0; j < caminosPosibles.size(); j++)
            if (j != origen) {
                intento = 0;
                temp = (String)(caminosPosibles.get(j));
                    while (temp.charAt(0) != (char)(origen+65) && intento < 10) {
                        aux = temp.charAt(0)-65;
                        temp = ((String)(caminosPosibles.get(aux)))+temp.substring(1,temp.length());
                        if (++intento == 10){
                            temp = "*" + temp;
                        }
                    };
                mostrarCamino(temp,j,origen);
            }
        System.out.println("\n-----Fin del algoritmo-----");
    }
    
    // Metodo para hallar el peso minimo de un camino
    public int hallarMinimo(int destino){
        int nodo = 0;
        int nodoOrigen = -1;
        int min = -1;
        int aux;
        for (int i = 0; i < listaSimp.size(); i++) {
            nodo = Integer.parseInt((String)(listaSimp.get(i)));
            if (mAdyacencias[nodo][nodo] != -1 && mAdyacencias[nodo][destino] != -1) {
                aux = mAdyacencias[nodo][nodo]+mAdyacencias[nodo][destino];
            }else{
                aux = -1;
            }
            if ((aux < min && aux != -1) || min == -1) {
                min = aux;
                nodoOrigen = nodo;
            }
        }
        if (min != -1) {
            mAdyacencias[destino][destino] = min;
            caminosPosibles.set(destino,""+(char)(nodoOrigen+65));
        }
        return min;
    }

    // Metodo para imprimir los caminos
    public void mostrarCamino(String camino, int nodoTemp, int origen){
        System.out.print("\nCamino: ");
        for (int i = 0; i < camino.length(); i++) {
            System.out.print(""+camino.charAt(i)+(i==camino.length()-1?"":"->"));
        }
        System.out.print(" || Su costo es: " + mAdyacencias[nodoTemp][nodoTemp]);
    }
}

