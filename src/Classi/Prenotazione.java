package Classi;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * La classe {@code Prenotazione} rappresenta una prenotazione per un'aula in un
 * determinato giorno e orario.
 * Ogni prenotazione contiene informazioni sulla data, l'orario di inizio e
 * fine, il nome del prenotante,
 * la motivazione della prenotazione e l'aula prenotata.
 * <p>
 * La classe implementa l'interfaccia {@code Serializable} per consentire la
 * serializzazione
 * delle prenotazioni su file, permettendo di salvare e caricare le prenotazioni
 * in modo persistente.
 * </p>
 * <p>
 * Le prenotazioni sono utilizzate principalmente all'interno della classe
 * {@code GestionePrenotazioni}.
 * </p>
 * <p>
 * La classe fornisce un costruttore per creare una nuova prenotazione e getter
 * per accedere alle informazioni della prenotazione.
 * </p>
 * <p>
 * *@param data la data della prenotazione
 * *@param oraInizio l'orario di inizio della prenotazione (in formato 24 ore,
 * ad esempio 14 per le 14:00)
 * *@param oraFine l'orario di fine della prenotazione (in formato 24 ore, ad
 * esempio 16 per le 16:00)
 * *@param nomePrenotante il nome della persona che ha effettuato la
 * prenotazione
 * *@param motivazione la motivazione della prenotazione
 * *@param aula l'aula prenotata {@link Aula} che si intende prenotare
 * </p>
 * <p>
 * La classe è progettata per essere semplice e focalizzata sulla
 * rappresentazione dei dati di una prenotazione.
 * </p>
 */

public class Prenotazione implements Serializable {
    // incapsulamento
    private LocalDate data;
    private int oraInizio;
    private int oraFine;
    String nomePrenotante;
    String motivazione;

    // Poliformismo utilizzo aula senza riferirmi principalmente ad AulaDid e
    // AulaLab
    Aula aula;

    public Prenotazione(LocalDate data, int oraInizio, int oraFine, String nomePrenotante, String motivazione,
            Aula aula) {
        this.data = data;
        this.oraInizio = oraInizio;
        this.oraFine = oraFine;
        this.nomePrenotante = nomePrenotante;
        this.motivazione = motivazione;
        this.aula = aula;
    }

    public LocalDate getData() {
        return data;
    }

    public int getOraInizio() {
        return oraInizio;
    }

    public int getOraFine() {
        return oraFine;
    }

    public String getNomePrenotante() {
        return nomePrenotante;
    }

    public String getMotivazione() {
        return motivazione;
    }

    public Aula getAula() {
        return aula;
    }

}
