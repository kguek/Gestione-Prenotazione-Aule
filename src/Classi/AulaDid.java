package Classi;

/**
 * Rappresenta un'aula didattica, specializzazione della classe astratta {@code Aula}.
 * <p>
 * la tipologia, {@code TipologiaAula.DIDATTICA}.
 * </p>
 */

//Eriditerietá
public class AulaDid extends Aula {

    public AulaDid(int numAula,int numCapienza){
        super (numAula, numCapienza, TipologiaAula.DIDATTICA); 
    }



    
}
