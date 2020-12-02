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
public class main {
    public static void main(String args[]){
        int cantNodos = 0;
        System.out.println("-----ALGORITMO DIJKSTRA-----");
        System.out.print("Cantidad de nodos: ");
        
        while(cantNodos < 3 || cantNodos > 26){ //cantidad de nodos debe ser entre 3 y 26
            try{
                Scanner sc = new Scanner(System.in);
                cantNodos = sc.nextInt();
            }
            catch(Exception e){
                System.out.println("ERROR. Valor no valido.");
            }
            if(cantNodos < 3 || cantNodos > 26)
                System.out.print("(El numero de nodos debe estar entre 3 y 26)\nIngresa un numero valido: ");
        }
        Algoritmo a1 = new Algoritmo(cantNodos);
    }
}