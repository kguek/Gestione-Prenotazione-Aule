package Utility;

import Classi.Prenotazione;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * La classe {@code FileManager} fornisce metodi statici per salvare e caricare
 * una lista di prenotazioni su file tramite serializzazione.
 * 
 * <p>
 * Il metodo {@link #salvaPrenotazioni(List, String)} serializza l'oggetto lista
 * in un file,
 * mentre {@link #caricaPrenotazioni(String)} deserializza il file e restituisce
 * una lista di prenotazioni.
 * <p>
 * 
 * <p>
 * Il file deve necessariamente essere .ser per il funzionamento.
 * <p>
 */
public class FileManager {

    /**
     * Salva la lista di prenotazioni in un file specificato.
     *
     * @param prenotazioni la lista di {@code Prenotazione} da salvare
     * @param fileName     il percorso e nome del file in cui salvare le
     *                     prenotazioni
     */

    public static void salvaPrenotazioni(List<Prenotazione> prenotazioni, String fileName) {
        try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(fileName))) {
            os.writeObject(prenotazioni);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Carica una lista di prenotazioni da un file.
     *
     * @param filename il percorso e nome del file da cui caricare le prenotazioni
     * @return una lista di {@code Prenotazione} deserializzata dal file; se si
     *         verifica un errore
     *         viene restituita una lista vuota
     */

    @SuppressWarnings("unchecked")
    public static List<Prenotazione> caricaPrenotazioni(String filename) {
        List<Prenotazione> prenotazioni = new ArrayList<>();
        try (ObjectInputStream is = new ObjectInputStream(new FileInputStream(filename))) {
            prenotazioni = (List<Prenotazione>) is.readObject(); // mi rompe perché non riesce a verificare a
                                                                 // compile-time se il deserializzato sia un
                                                                 // List<Prenotazioni>
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return prenotazioni;
    }
}
