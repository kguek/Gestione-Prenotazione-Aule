package Classi;

/**
 * Rappresenta un'aula didattica, specializzazione della classe astratta {@code Aula}.
 * <p>
 * la tipologia, {@code TipologiaAula.LABORATORIO}.
 * </p>
 */

//Eriditerietá
public class AulaLab extends Aula {

    public AulaLab(int numAula,int numCapienza){
        super (numAula, numCapienza, TipologiaAula.LABORATORIO);
    }



    
}
