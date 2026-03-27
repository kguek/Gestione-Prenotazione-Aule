package Classi;

import java.io.Serializable;

/**
 * Classe astratta che definisce le caratteristiche base di un'aula.
 */

public abstract class Aula implements Serializable {

     /**
     * Elenca le due tipologie di aula: LABORATORIO e DIDATTICA.
     */

    public enum TipologiaAula {
        LABORATORIO,
        DIDATTICA
    }
    //incapsulamento
    private int numAula;
    private int numCapienza;
    private TipologiaAula tipologia;

    /**
     * Costruttore base per  {@code Aula}.
     *
     * @param numAula     il numero dell'aula
     * @param numCapienza la capienza massima di persone
     * @param tipologia   la tipologia 
     */

    public Aula (int numAula, int numCapienza, TipologiaAula tipologia){

        this.tipologia=tipologia;
        this.numAula=numAula;
        this.numCapienza=numCapienza;
    }

    public int getNumAula(){
        return numAula;
    }

    public int getNumCapienza(){
        return numCapienza;
    }

    public TipologiaAula getTipologia(){
        return tipologia;
    }
    
}
