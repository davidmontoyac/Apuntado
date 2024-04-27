package apuntado;

/**
 * @author Pedro Arango Sánchez
 * @author David Andrés Montoya Castaño
 * Universidad de Antioquia
 * Técnicas de Programación y Laboratorio [2554307] 
 * Grupo: 01 | Semestre: 2024-1
 */

public enum PintaCarta {
    Ninguno(0), 
    Trébol(0), 
    Picas(13), 
    Corazones(26), 
    Diamantes(39);
    
    private final int sumarIndice;
    
    private PintaCarta(int sumarIndice){
        this.sumarIndice = sumarIndice;
    }
    
    public int getSumarIndice(){
        return this.sumarIndice;
    }
}