/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package palabras;

import java.io.*;

/**
 *
 * @author Gabriel
 */
public class Palabras {

    public static String fichero = "mediasandia.txt";
    public static Texto texto;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        Programainicio();

    }

    public static void CargadorPrograma() {
        System.out.println("El programa se esta cargado...");
        RepeticionPalabras();
        System.out.println("Listo\n");

    }

    public static void Programainicio() throws IOException {

        CargadorPrograma();

        String s = LeerFichero(fichero);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        MenuPrincipal();
        System.out.print("------- Introduce una opcion del menu principal: ");

        char c = (char) br.read();
        char intro = (char) br.read();

        while (1 > 0) {
            while (intro != '\n') {
                c = 'z';
                intro = (char) br.read();
            }
            switch (c) {
                case 'a':
                    int npalabras = texto.getNpalabrastotales();
                    System.out.println("En el fichero mediasandia.txt hay: " + npalabras + " palabras.\n");
                    break;
                case 'b':
                    System.out.println(texto.VerRepeticiones());
                    break;
                case 'c':
                    texto.PalabraMasRepetida();
                    break;
                case 'd':
                    BuscarPalabraTexto();
                    break;
                case 'e':
                    ContarNLineas();
                    break;
                case 'm':
                    MenuPrincipal();
                    break;
                case 's':
                    System.out.println("El programa a finalizado");
                    System.exit(0);
                default:
                    System.out.println("* opción no valida\n\n ");
                    break;
            }

            System.out.print("------- Introduce una opcion del menu principal: ");
            c = (char) br.read();
            intro = (char) br.read();
        }

    }

    public static String LeerFichero(String fichero) {
        String s = "", aux = "";
        try {
            FileReader rf = new FileReader(fichero);
            BufferedReader br = new BufferedReader(rf);
            aux = br.readLine();
            while (aux != null) {
                s += aux + "\n";
                aux = br.readLine();
            }
        } catch (IOException e) {
            System.err.println("Algo a salido mal");
        }
        return s;
    }

    public static void MenuPrincipal() {
        String s = "";
        s += "                   *** Menu Principal ***\n";
        s += "\n-Opcion a: Contar palabras.\n";
        s += "\n-Opcion b: Ver repeticiones de palabras.\n";
        s += "\n-Opcion c: Palabra mas repetida.\n";
        s += "\n-Opcion d: Buscar una palabra .\n";
        s += "\n-Opcion e: Contar lineas del fichero .\n";
        s += "\n-Opcion m: Volver a imprimir el menu\n";
        s += "\n-Opcion s: Salir.\n";

        System.out.println(s);

    }

    public static boolean EsSeparador(char c) {
        return c == ' ' || c == '.' || c == ',' || c == '\n';

    }

    //En este metodo veremos cuantas veces aparece cada palabra
    public static void RepeticionPalabras() {
        char letras[] = new char[0];
        char auxiliar[];
        texto = new Texto();
        Palabra palabra;
        Palabra palabras[] = new Palabra[0];
        Palabra palabrasaux[];
        boolean separador = true;
        try {
            FileReader rf = new FileReader(fichero);
            BufferedReader br = new BufferedReader(rf);
            char aux = (char) br.read();
            while (aux != (char) -1) {
                if (separador && !EsSeparador(aux)) {
                    letras = new char[1];
                    letras[0] = aux;
                    separador = false;
                } else if (!separador && !EsSeparador(aux)) {
                    letras = AmpliarArray(letras, aux);
                    separador = false;
                } else if (!separador && EsSeparador(aux)) {
                    separador = true;
                    palabra = new Palabra(letras);
                    texto = texto.InsertarPalabra(palabra);
                } else {
                    separador = true;
                }

                aux = (char) br.read();
            }
        } catch (IOException e) {
            System.err.println("Algo a salido mal");
        }
        if (texto.getRepeticiones().length == texto.getNpalabras()) {
            System.err.println("Hay un error en metodo repeticion palabras");
        }
    }

    public static char[] AmpliarArray(char[] letras, char c) {
        char[] auxiliar = new char[letras.length + 1];
        for (int i = 0; i < letras.length; i++) {
            auxiliar[i] = letras[i];
        }
        auxiliar[auxiliar.length - 1] = c;
        return auxiliar;
    }

    public static void BuscarPalabraTexto() {

        System.out.print("Escribre la palabra que deseas buscar: ");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        char letras[] = new char[0];
        try {
            char c = (char) br.read();
            while ((c != (char) -1) && (!EsSeparador(c))) {

                letras = AmpliarArray(letras, c);
                c = (char) br.read();
            }
            if (EsSeparador(c) && c == '\n') {
                //todo bien
                Buscar(letras);
            } else if (EsSeparador(c) && c != '\n') {
                System.out.println("La opcion no es valida");
                c = (char) br.read();
            }
        } catch (IOException e) {
            System.err.println("Leyendo  de teclado");
        }

    }

    public static void Buscar(char[] letras) {

        try {
            FileReader rf = new FileReader(fichero);
            BufferedReader br = new BufferedReader(rf);
            boolean separador = true;
            boolean coiciden = true;
            int nlinea = 0;
            int nletra = 0;
            int n = 0;
            int inicio = 0;
            char aux = (char) br.read();
            while (aux != (char) -1) {
                if (separador && !EsSeparador(aux)) {
                    //Empieza nueva palabra
                    coiciden = true;
                    inicio = nletra;
                    n = 0;
                    if (aux != letras[n]) {
                        coiciden = false;
                    }
                    n++;
                    separador = false;

                } else if (!separador && !EsSeparador(aux)) {
                    //Leo una letra mas de la palabra
                    if (n < letras.length && aux != letras[n]) {
                        coiciden = false;
                    }
                    n++;
                    separador = false;

                } else if (!separador && EsSeparador(aux) && coiciden) {
                    //Acaba una palarbra en la letra anterior
                    if (n == letras.length) {
                        //encontre la palabra
                        System.out.println("He encontrado la palabra en la linea " + nlinea + " posicion " + inicio + ".\n");
                    }
                    separador = true;

                } else {
                    //Estoy leyendo separadores consecutivos

                    separador = true;
                }
                if (aux == '\n') {
                    nlinea++;
                    nletra = 0;

                } else {
                    nletra++;
                }
                aux = (char) br.read();
            }
        } catch (IOException e) {
            System.err.println("Algo a salido mal");
        }

    }

    public static void ContarNLineas() {

        try {
            FileReader rf = new FileReader(fichero);
           
            int nlineas = 1;
            char c = (char) rf.read();
            while (c != (char) -1) {
                if (c == '\n') {
                    nlineas++;
                }
                c = (char) rf.read();
            }
            System.out.println("En el texto hay " + nlineas + " lineas.\n");

        } catch (IOException e) {
            System.err.println("Error leyendo  de teclado");
        }
    }
}
