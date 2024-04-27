package apuntado;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Pedro Arango Sánchez
 * @author David Andrés Montoya Castaño
 * Universidad de Antioquia
 * Técnicas de Programación y Laboratorio [2554307] 
 * Grupo: 01 | Semestre: 2024-1
 */

public enum NombreCarta { 
    Ninguno(0,0,0), 
    Ace(10,1,2), 
    Dos(2,2,3), 
    Tres(3,3,4), 
    Cuatro(4,4,5), 
    Cinco(5,5,6), 
    Seis(6,6,7), 
    Siete(7,7,8), 
    Ocho(8,8,9), 
    Nueve(9,9,10), 
    Diez(10,10,11), 
    Jack(10,11,12), 
    Queen(10,12,13), 
    King(10,13,1); 

    private final int valorCarta;
    private final int valorOrden;
    private final int siguienteValor;
    
    NombreCarta(int valorCarta,int valorOrden,int siguienteValor){
        this.valorCarta = valorCarta;
        this.valorOrden = valorOrden;
        this.siguienteValor = siguienteValor;
    }
    
    public static int getPuntajePorOrden(int ordenCarta){
        List<Integer> cartas = List.of(1,11,12,13);
        if (cartas.contains(ordenCarta)) return 10;
        else return ordenCarta;
    }
    
    
    public Integer getValorCarta(){
        return valorCarta;
    }
    
    public int getValorOrden(){
        return valorOrden;
    }
    
    public int getSiguienteValor(){
        return siguienteValor;
    }

}