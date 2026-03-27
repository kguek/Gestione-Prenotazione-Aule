package Operazioni;

import Classi.Aula;
import Classi.Prenotazione;
import java.util.ArrayList;
import java.util.List;

/**
 * La classe {@code GestionePrenotazioni} gestisce una lista di prenotazioni per
 * le aule.
 * <p>
 * Questa classe consente di:
 * <ul>
 * <li>Aggiungere una nuova prenotazione, verificando la sua validità
 * (conflitti orari, durata e orari accettabili).</li>
 * <li>Modificare una prenotazione esistente, sostituendola con una nuova,
 * ripristinando quella originale se la nuova non è valida</li>
 * <li>Cancellare una prenotazione.</li>
 * </ul>
 * La validità di una prenotazione viene controllata tramite il metodo
 * {@code isPrenotazioneValida},
 * che verifica che gli orari siano compatibili con le regole del sistema
 * e che non vi siano conflitti.
 *
 * <p>
 * Le prenotazioni sono memorizzate internamente in una
 * {@code List<Prenotazione>}.
 * </p>
 */

public class GestionePrenotazioni {

    private List<Prenotazione> prenotazioni;

    public GestionePrenotazioni() {
        prenotazioni = new ArrayList<>();
    }

    public List<Prenotazione> getPrenotazioni() {
        return prenotazioni;
    }

    /**
     * Aggiunge una nuova prenotazione alla lista, se è valida secondo i vincoli
     * definiti. Se la prenotazione non è valida, non viene aggiunta e viene
     * restituito {@code false}.
     * 
     * @param nuova la nuova prenotazione da aggiungere
     * @return {@code true} se la prenotazione è valida e viene aggiunta,
     *         {@code false} in caso contrario
     */

    public boolean addPrenotazione(Prenotazione nuova) {

        if (!isPrenotazioneValida(nuova)) {
            return false;
        }

        prenotazioni.add(nuova);
        return true;
    }

    /**
     * Modifca la prenotazione interna alla lista con una nuova prenotazione
     * chiama prima {@code isPrenotazioneValida} per verificare che la nuova
     * prenotazione sia valida.
     * Se la nuova prenotazione non è valida, la vecchia prenotazione viene
     * ripristinata e viene restituito {@code false}.
     * 
     * @param vecchia la prenotazione da sostituire
     * @param nuova   la nuova prenotazione
     * @return {@code true} se la prenotazione è valida e viene modificata,
     *         {@code false} in caso contrario
     */

    public boolean modPrenotazione(Prenotazione vecchia, Prenotazione nuova) {
        prenotazioni.remove(vecchia);
        if (!isPrenotazioneValida(nuova)) {
            prenotazioni.add(vecchia);
            return false;
        }
        prenotazioni.add(nuova);
        return true;
    }

    /**
     * Cancella una prenotazione esistente dalla lista. Se la prenotazione non
     * esiste, restituisce {@code false}.
     * 
     * @param prenotazione la prenotazione da cancellare
     * @return {@code true} se la prenotazione esiste e viene cancellata,
     *         {@code false} in caso contrario
     */

    public boolean cancPrenotazione(Prenotazione prenotazione) {
        return prenotazioni.remove(prenotazione);
    }

    /**
     * Verifica se una nuova prenotazione rispetta i vincoli orari e di tipologia.
     * <p>
     * Questo metodo controlla:
     * <ul>
     * <li>Che l'orario di inizio sia >= 8 e quello di fine <= 18</li>
     * <li>Che {@code oraFine} sia maggiore di {@code oraInizio}</li>
     * <li>Che la durata sia compatibile con il tipo di aula (Did 8h max e Lab
     * 2|4)</li>
     * <li>Che non esistano conflitti con altre prenotazioni della stessa aula
     * e data, verificando la sovrapposizione degli intervalli orari</li>
     * </ul>
     * Se uno di questi vincoli non è soddisfatto, la prenotazione viene considerata
     * false.
     * </p>
     *
     * @param nuova la nuova prenotazione da validare
     * @return {@code true} se la prenotazione rispetta tutti i vincoli richiesti,
     *         {@code false} in caso contrario
     */

    // Incapsulamento
    private boolean isPrenotazioneValida(Prenotazione nuova) {

        if (nuova.getOraInizio() < 8 || nuova.getOraFine() > 18 || nuova.getOraFine() <= nuova.getOraInizio()) {
            return false;
        }

        int durata = nuova.getOraFine() - nuova.getOraInizio();
        Aula.TipologiaAula tipo = nuova.getAula().getTipologia();

        // Controllo orari in base all aula

        if (tipo == Aula.TipologiaAula.DIDATTICA) {
            if (durata < 1 || durata > 8) {
                return false;
            }
        } else if (tipo == Aula.TipologiaAula.LABORATORIO) {
            if (durata != 2 && durata != 4) {
                return false;
            }
        }

        // Controllo conflitti prenotazioni per stessa aula e data

        for (Prenotazione p : prenotazioni) {
            if (p.getAula().getNumAula() == nuova.getAula().getNumAula() &&
                    p.getAula().getTipologia() == nuova.getAula().getTipologia() &&
                    p.getData().equals(nuova.getData()) &&
                    orarioConflitto(nuova.getOraInizio(), nuova.getOraFine(), p.getOraInizio(), p.getOraFine())) {
                return false;
            }
        }

        return true;
    }

    // Controllo intervallo delle ore
    private boolean orarioConflitto(int startOrario1, int endOrario1, int startOrario2, int endOrario2) {
        return (startOrario1 < endOrario2 && startOrario2 < endOrario1);
    }

}
