package ui;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Random;

public class Battleship{

    private static Scanner input;
    public static int[] posicion_barcos_jugador = new int[10];
    public static int[] posicion_barcos_maquina;
    public static int[] posicion_maquina_consola= new int[10];


    public static void main (String[]args){
        input = new Scanner (System.in);
        menu();



    }



    public static void menu(){
        int option = -1;
        do{

            System.out.println("\n--------------BIENVENIDO A BATTLEFIELD--------------");
            System.out.println();
            System.out.println("1. Jugar");
            System.out.println("0. Salir");
            System.out.print("Eleccion: ");
            option = input.nextInt();
            System.out.println("----------------------------------------------------");
            


        switch(option){

            case 1:
                do {
                    
                reiniciarTableros();
                
                ubicacion_barcos();
                
                ubicacion_barcos_maquina();
                
                turno_a_turno();

                System.out.println("\n¿Quieres jugar otra vez? (1: Sí, 0: No): ");
                option = input.nextInt();
                }while (option == 1 );
                System.out.println("\nGracias por jugar!");
                break; 


            case 0:

                 break;

            default:
                
            System.out.println("Opción no válida. Inténtalo de nuevo.");
                
                break;

            }
        }while(option!=0);
    }
    
    
    public static void ubicacion_barcos(){

        posicion_barcos_jugador= new int[10];
        boolean[] tablero = new boolean [10];

        System.out.println("\nUbica tus barcos en posiciones del 0 al 9 (sin repetir y respetando el tamaño de cada barco): ");
        System.out.println("El barco 1 es la lancha, el barco 2 el barco medico y el barco 3 corresponde al barco de municion.");

        int[] tamaños = {1, 2, 3};

        for (int i = 0; i<tamaños.length; i++){
            int tamaño = tamaños[i];
            int[] posiciones = new int[tamaño]; 
            boolean valido;

            do{
                valido = true;
                System.out.print("\nIngresa la posición inicial para el barco de " + tamaño + " casillas: ");
                int inicio = input.nextInt();

                if (inicio < 0 || inicio + tamaño - 1 > 9) {
                System.out.println("Posición inválida. Debe estar dentro del rango permitido.");
                valido = false;
                continue;
                }
                for (int j = 0; j < tamaño; j++) {
                    
                    if (tablero[inicio + j]) {
                    
                    System.out.println("Una de las casillas ya está ocupada. Elige otra ubicación.");
                    valido = false;
                    break;
                }
                posiciones[j] = inicio + j;
            }
            }while(!valido);

            for (int k = 0; k < posiciones.length; k++) {
                int pos = posiciones[k];
                tablero[pos] = true;
                posicion_barcos_jugador[pos] = 1;
            }

        }
        System.out.println("Tus barcos han sido colocados en: "+ Arrays.toString(posicion_barcos_jugador));
    }

    public static void ubicacion_barcos_maquina(){

        posicion_barcos_maquina = new int[10];

        boolean[] tablero_maquina = new boolean [10];

        Random random = new Random();

        int[] tamaños = {1,2,3};

        for (int i = 0; i < tamaños.length; i++) {
            
            int tamaño = tamaños[i];
            
            int [] posiciones = new int[tamaño];

            boolean valido = true;

            do{
                valido = true;

                int inicio = random.nextInt(10-tamaño+1);

                if (inicio + tamaño - 1 > 9){
                    valido = false; 
                    continue;

                }

                for (int j = 0; j < tamaño; j++) {
                    
                    if (tablero_maquina[inicio+j]){
                        valido = false;
                        break; 

                    }
                    posiciones[j] = inicio+j;
                }


            }while(!valido);
            
            for (int k = 0; k < posiciones.length; k++) {
                
                int pos = posiciones[k];
                tablero_maquina[pos]= true;
                posicion_barcos_maquina[pos] = 1;

            }
        }

    }

    public static void turno_a_turno() {
        
        System.out.println("\nHORA DE JUGAR! ");
        
        while (true) {
            
            Random random = new Random();
            int ataque;
            do {
                System.out.print("\nIngresa la posición para atacar (0-9): ");
                ataque = input.nextInt();
            } while (ataque < 0 || ataque > 9 || posicion_barcos_maquina[ataque] == 2);

            if (posicion_barcos_maquina[ataque] == 1) {
                System.out.println("¡Impacto en el barco de la máquina!");
                posicion_barcos_maquina[ataque] = 2;
                posicion_maquina_consola[ataque] = 2;
            } else {
                System.out.println("Fallaste el ataque.");
                posicion_barcos_maquina[ataque] = 3;
                posicion_maquina_consola[ataque] = 3;
            }

            System.out.println("Línea de mar rival:");
            System.out.println(Arrays.toString(posicion_maquina_consola));

            
            if (Arrays.stream(posicion_barcos_maquina).noneMatch(x -> x == 1)) {
                System.out.println("¡Has ganado la partida!");
                break;
            }

            
            int ataque_maquina;
            do {
                ataque_maquina = random.nextInt(10);
            } while (posicion_barcos_jugador[ataque_maquina] == 2 || posicion_barcos_jugador[ataque_maquina] == 3);

            if (posicion_barcos_jugador[ataque_maquina] == 1) {
                System.out.println("\n¡La máquina ha impactado tu barco en la posición " + ataque_maquina + "!");
                posicion_barcos_jugador[ataque_maquina] = 2;
            } else {
                System.out.println("\nLa máquina falló su ataque.");
                posicion_barcos_jugador[ataque_maquina] = 3;
            }

            System.out.println("Línea de mar del jugador:");
            System.out.println(Arrays.toString(posicion_barcos_jugador));

            
            if (Arrays.stream(posicion_barcos_jugador).noneMatch(x -> x == 1)) {
                System.out.println("¡La máquina ha ganado la partida!");
                break;
            }
        }
    }
    
    public static void reiniciarTableros(){

        posicion_barcos_jugador = new int[10];
        posicion_barcos_maquina = new int[10];

    }
    

}