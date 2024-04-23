package apuntado;

import java.util.*;
import javax.swing.*;

/**
 * @author Pedro Arango Sánchez
 * @author David Andrés Montoya Castaño
 * Universidad de Antioquia
 * Técnicas de Programación y Laboratorio [2554307] 
 * Grupo: 01 | Semestre: 2024-1
 */

public class Carta {

    /* Atributo privado para almacenar el número y pinta de la carta, así:
    1: As de Trébol, 14: As de Picas, 27: As de Corazones, 40: As de Diamantes, 
    2: Dos de Trébol, 15: Dos de Picas, 28: Dos de Corazones, 41: Dos de Diamantes,
    3: Tres de Trébol, 16: Tres de Picas, 29: Tres de Corazones, 42: Tres de Diamantes,
    4: Cuatro de Trébol, 17: Cuatro de Picas, 30: Cuatro de Corazones, 43: Cuatro de Diamantes,
    5: Cinco de Trébol, 18: Cinco de Picas, 31: Cinco de Corazones, 44: Cinco de Diamantes,
    6: Seis de Trébol, 19: Seis de Picas, 32: Seis de Corazones, 45: Seis de Diamantes,
    7: Siete de Trébol, 20: Siete de Picas, 33: Siete de Corazones, 46: Siete de Diamantes,
    8: Ocho de Trébol, 21: Ocho de Picas, 34: Ocho de Corazones, 47: Ocho de Diamantes,
    9: Nueve de Trébol, 22: Nueve de Picas, 35: Nueve de Corazones, 48: Nueve de Diamantes,
    10: Diez de Trébol, 23: Diez de Picas, 36: Diez de Corazones, 49: Diez de Diamantes,
    11: Jack de Trébol, 24: Jack de Picas, 37: Jack de Corazones, 50: Jack de Diamantes,
    12: Queen de Trébol, 25: Queen de Picas, 38: Queen de Corazones, 51: Queen de Diamantes,
    13: King de Trébol, 26: King de Picas, 39: King de Corazones, 52: King de Diamantes,*/
    private int indice;
    public static List<Integer> baraja = new ArrayList<>();
    
    //El número de la carta es generado aleatoriamente, pero tenemos que asegurar que no se repita la carta
    //a los distintos jugados. Ej: No se le puede entregar la carta 7 de Corazones a los 2 jugadores, solo hay UNA.
    public Carta(Random r) {
        while(true) {
            indice = r.nextInt(52) + 1;
            if (!baraja.contains(indice)) {
                baraja.add(indice);
                break;
            }
        }
    }

    public PintaCarta obtenerPinta() {
        /* Obtiene la pinta que corresponde a la carta,
        basado en el rango en que se ubica el índice
         */
        if (indice <= 13) {
            return PintaCarta.Trébol;
        } else if (indice <= 26) {
            return PintaCarta.Picas;
        } else if (indice <= 39) {
            return PintaCarta.Corazones;
        } else {
            return PintaCarta.Diamantes;
        }
    }

    public NombreCarta obtenerNombre() {
        //Obtiene el nombre que corresponde al número de la carta
        int numero = indice % 13;
        if (numero == 0) {
            numero = 13;
        }
        return NombreCarta.values()[numero];
    }

    public void mostrarCarta(int x, int y, JPanel pnl, boolean tapada) {
        String nombreImagen;
        
        //Obtener el nombre del archivo de la carta
        if (tapada) {
            nombreImagen = "Tapada.jpg";
        } else {
           nombreImagen = "Carta"+String.valueOf(indice)+".jpg";
        }
        
        //Cargar la imagen
        ImageIcon imagen = cargarImagen(nombreImagen);

        //Instanciar Label para mostrar la imagen
        JLabel lblCarta = new JLabel(imagen);
        //Definir posicion y dimensiones de la imagen
        lblCarta.setBounds(x, y, x + imagen.getIconWidth(), y + imagen.getIconHeight());
        //Mostrar la carta en la ventana 
        pnl.add(lblCarta);
    }

    public int obtenerIndice(){
        return this.indice;
    }
    
    public static void reiniciarBaraja(){
        baraja.clear();
    }

    public ImageIcon cargarImagen(String nombreImagen) {
        return new ImageIcon(getClass().getResource("/images/" + nombreImagen));
    }
    
}