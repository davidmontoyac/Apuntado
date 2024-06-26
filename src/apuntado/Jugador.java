package apuntado;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import javax.swing.JPanel;

/**
 * @author Pedro Arango Sánchez
 * @author David Andrés Montoya Castaño
 * Universidad de Antioquia
 * Técnicas de Programación y Laboratorio [2554307] 
 * Grupo: 01 | Semestre: 2024-1
 */

public class Jugador {

    private Random r;
    private Carta[] cartas;
    private Map<String, List<Integer>> frecuenciaCartas;
    private Integer puntosJugador;
    private List<Integer> indicesCartasEscalera;
    private List<Integer> indicesCartasGrupo;

    public Jugador() {
        // Iniciar el generador de numeros aleatorios
        r = new Random();
        puntosJugador=0;
    }

    public void repartir() {
        //Instanciar las 10 cartas
        puntosJugador=0;
        cartas = new Carta[10];
        for (int i = 0; i < 10; i++) {
            cartas[i] = new Carta(r);
        }
    }

    public void mostrar(JPanel pnl, boolean tapada) {
        //limpiar el panel
        pnl.removeAll();
        //mostrar cada carta
        for (int i = 0; i < 10; i++) {
            cartas[i].mostrarCarta(300 - i * 25, 10, pnl, tapada);
        }
        //Redibujar el panel 
        pnl.repaint();
    }

    public String obtenerFiguras() {
        indicesCartasGrupo = new ArrayList<>();
        frecuenciaCartas = new HashMap<>();
        contarConcurrencias(); //Contamos las concurrencias de las cartas.

        String mensaje = "";
        for (Map.Entry<String, List<Integer>> carta : frecuenciaCartas.entrySet()) {
            Integer frecuencia = carta.getValue().get(0); //El indice 0 siempre será la que contenga la frecuencia.
            if (frecuencia >= 3) {
                String nombreCarta = carta.getKey();
                mensaje += Figura.values()[frecuencia].name() + " de " + nombreCarta + "\n";
                indicesCartasGrupo.addAll(carta.getValue().subList(1, carta.getValue().size()));
            }
        }
        mensaje += obtenerEscaleras();         //Posible escalera
        
        
        if (mensaje.equals("")) {
            mensaje = "Mejor suerte para la próxima, esta vez no te salieron figuras.";
        } else {
            mensaje = "El jugador tiene las siguientes figuras:\n" + mensaje;
        }

        return mensaje;
    }
    

    public Map<String, List<Integer>> contarConcurrencias() {
        frecuenciaCartas=new HashMap<>();
        for (Carta carta : cartas) {
            String nombreCarta = carta.obtenerNombre().name();
            List<Integer> listaCarta = frecuenciaCartas.getOrDefault(nombreCarta,new ArrayList<>(Arrays.asList(0)));
            Integer frecuencia=listaCarta.isEmpty()?1:(listaCarta.get(0)+1);
            Integer indice=carta.obtenerIndice();
            listaCarta.set(0,frecuencia);
            listaCarta.add(indice);
            frecuenciaCartas.put(nombreCarta, listaCarta);
        }
        return frecuenciaCartas;
    }

    public String obtenerEscaleras() {
        indicesCartasEscalera=new ArrayList<>();
        Map<PintaCarta, List<NombreCarta>> agrupamientosPorPinta = agruparCartasPorPinta();
        String message = "";
        for (Map.Entry<PintaCarta, List<NombreCarta>> entry : agrupamientosPorPinta.entrySet()) {
            PintaCarta pintaCarta = entry.getKey();
            Integer indicePinta=pintaCarta.getSumarIndice();
            List<NombreCarta> agrupadosXPinta = entry.getValue();
            if (agrupadosXPinta.size() >= 3) {
                agrupadosXPinta.sort((vl1, vl2) -> vl1.getValorOrden()- vl2.getValorOrden());
                agrupadosXPinta.addAll(agrupadosXPinta);
                int sumTemp=1;
                List<Integer> escaleraTest=new ArrayList<>();
                List<Integer> finalEscalera=new ArrayList<>(); //Almacenará escaleras por pinta. Pinta puede tener >1 escaleras.
                for (int i = 0; i < agrupadosXPinta.size()-1; i++) {
                    Integer index1 = agrupadosXPinta.get(i).getValorOrden();
                    Integer index2 = agrupadosXPinta.get(i + 1).getValorOrden();
                    if(index2-index1!=1 && index2-index1!=-12){
                        sumTemp=1;
                        escaleraTest.clear();
                    }else {
                        sumTemp++;
                        if(!escaleraTest.contains(index1)) escaleraTest.add(index1);
                        if(!escaleraTest.contains(index2)) escaleraTest.add(index2);
                    }
                    if(sumTemp>=3) {
                        if(!finalEscalera.containsAll(escaleraTest)) {
                            finalEscalera.addAll(escaleraTest);
                        }
                        if(!message.contains("Escalera de " + pintaCarta.name()+"\n")){
                            message += "Escalera de " + pintaCarta.name()+"\n";
                        }
                    }
                }
                indicesCartasEscalera.addAll(finalEscalera.stream().map((card)->card+indicePinta).toList());
            }
        }
        obtenerPuntosJugador();
        return message;
    }

    public Map<PintaCarta, List<NombreCarta>> agruparCartasPorPinta() {
        Map<PintaCarta, List<NombreCarta>> agrupamientosPorPinta = new HashMap<>();
        for (Carta carta : cartas) {
            PintaCarta pintaCarta = carta.obtenerPinta();
            NombreCarta nombreCarta = carta.obtenerNombre();
            List<NombreCarta> nombresCartas = agrupamientosPorPinta.getOrDefault(pintaCarta, new ArrayList<>());
            nombresCartas.add(nombreCarta);
            agrupamientosPorPinta.put(pintaCarta, nombresCartas);
        }
        return agrupamientosPorPinta;
    }
    
    public Integer obtenerPuntosJugador(){
        puntosJugador = 0;
        Set<Integer> indicesUsados = new HashSet<>();
        indicesUsados.addAll(indicesCartasGrupo);
        indicesUsados.addAll(indicesCartasEscalera);

        for (int i = 0; i < cartas.length; i++) 
            if(!indicesUsados.contains(cartas[i].obtenerIndice())) 
                puntosJugador+=cartas[i].obtenerNombre().getValorCarta();
        
        //System.out.println("Índices usados: ");
        //indicesUsados.forEach((indice)->System.out.print(indice+"-"));
        return puntosJugador;
        
    }

}