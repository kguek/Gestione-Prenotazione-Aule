package Interfaccia;

import Classi.Aula;
import Classi.Prenotazione;
import Operazioni.GestionePrenotazioni;
import Utility.FileManager;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.time.LocalDate; //mi serve per convertire le date
import java.time.ZoneId;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Timer;

/**
 * La classe {@code MyFrame} rappresenta la finestra principale
 * dell'applicazione
 * per la gestione delle prenotazioni delle aule.
 *
 * <p>
 * Fornisce un'interfaccia grafica che consente di:
 * <ul>
 * <li>Selezionare una data e visualizzare le prenotazioni del giorno</li>
 * <li>Aggiungere, modificare e cancellare prenotazioni</li>
 * <li>Salvare e caricare le prenotazioni tramite file chooser</li>
 * </ul>
 * La tabella per visualizzare le prenotazioni è gestita dal pannello
 * {@link PanelOccupazione}, aggiunto nella parte centrale del frame.
 * <p>
 */

public class MyFrame extends JFrame {

    private PanelOccupazione panelOccupazione;
    private JSpinner spinnerData;
    private JButton btnAggiungi, btnModifica, btnCancella, btnSalva, btnCarica, btnVisualizzaData, btnStampa;
    private List<Aula> aule;

    // Gestione operazioni prenotazioni
    private GestionePrenotazioni gestPrenotazioni = new GestionePrenotazioni();

    /**
     * Costruttore: imposta il layout, carica le aule da file
     * e inizializza i vari componenti grafici (spinner, pulsanti, pannelli).
     */
    public MyFrame() {

        super("Gestione Prenotazioni Aule");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 658);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        aule = Utility.LoaderAule
                .loadAule("/home/kguek/DEV UNI/ProgettoEsame_Java/Esame/src/main/java/Utility/aule.txt");

        // Pannello superiore: selezione data e operazioni
        JPanel topPanel = new JPanel(new BorderLayout());

        // Pannello per la selezione della data
        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        datePanel.add(new JLabel("Seleziona Data:"));

        // Configuro lo spinner in modo che di base cambi il giorno
        SpinnerDateModel dateModel = new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_MONTH);
        spinnerData = new JSpinner(dateModel);
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spinnerData, "dd-MM-yyyy");
        spinnerData.setEditor(dateEditor);

        // Bottone visualizza --> aggiorna la mia tabella con la data immessa
        btnVisualizzaData = new JButton("Visualizza");
        btnVisualizzaData.addActionListener(e -> aggiornaTabellaPerData());
        datePanel.add(spinnerData);
        datePanel.add(btnVisualizzaData);

        // Pannello per i pulsanti operativi
        JPanel operationsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnAggiungi = new JButton("Aggiungi");
        btnModifica = new JButton("Modifica");
        btnCancella = new JButton("Cancella");
        btnSalva = new JButton("Salva");
        btnCarica = new JButton("Carica");
        btnStampa = new JButton("Stampa");

        // Pulsante Aggiungi: apre un dialog personalizzato per inserire tutte le info
        btnAggiungi.addActionListener(e -> {
            Prenotazione nuova = mostraDialogAggiungiPrenotazione();
            if (nuova != null) {
                boolean ok = gestPrenotazioni.addPrenotazione(nuova);
                if (ok) {
                    JOptionPane.showMessageDialog(MyFrame.this, "Prenotazione aggiunta con successo!");
                    aggiornaTabellaPerData();
                } else {
                    JOptionPane.showMessageDialog(MyFrame.this, "Errore: prenotazione non valida o conflitto orario!");
                }
            }
        });

        // Pulsante Modifica: seleziona una prenotazione del giorno corrente da
        // modificare
        btnModifica.addActionListener(e -> {
            Prenotazione daModificare = selezionaPrenotazione("Modifica Prenotazione");
            if (daModificare != null) {
                Prenotazione modificata = mostraDialogModificaPrenotazione(daModificare);
                if (modificata != null) {
                    boolean ok = gestPrenotazioni.modPrenotazione(daModificare, modificata);
                    if (ok) {
                        JOptionPane.showMessageDialog(MyFrame.this, "Prenotazione modificata con successo!");
                        aggiornaTabellaPerData();
                    } else {
                        JOptionPane.showMessageDialog(MyFrame.this, "Errore: modifica non valida o conflitto orario!");
                    }
                }
            }
        });

        // Pulsante Cancella: seleziona una prenotazione del giorno corrente e la
        // cancella
        btnCancella.addActionListener(e -> {
            Prenotazione daCancellare = selezionaPrenotazione("Cancella Prenotazione");
            if (daCancellare != null) {
                int conferma = JOptionPane.showConfirmDialog(MyFrame.this,
                        "Sei sicuro di voler cancellare questa prenotazione?", "Conferma", JOptionPane.YES_NO_OPTION);
                if (conferma == JOptionPane.YES_OPTION) {
                    boolean ok = gestPrenotazioni.cancPrenotazione(daCancellare);
                    if (ok) {
                        JOptionPane.showMessageDialog(MyFrame.this, "Prenotazione cancellata!");
                        aggiornaTabellaPerData();
                    } else {
                        JOptionPane.showMessageDialog(MyFrame.this, "Errore nella cancellazione!");
                    }
                }
            }
        });

        // Pulsante salva: utilizzo JFileChooser per salvare le prenotazioni e forzare
        // .ser
        btnSalva.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int scelta = fileChooser.showSaveDialog(this);
            if (scelta == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                // Forzo scelta estensione in '.ser'
                if (!file.getName().toLowerCase().endsWith(".ser")) {
                    file = new File(file.getAbsolutePath() + ".ser");
                }
                if (file.exists()) {
                    int sovraScrivi = JOptionPane.showConfirmDialog(this,
                            "Il file esiste giá. Vuoi sovrascriverlo?",
                            "Sovrascrivi",
                            JOptionPane.YES_NO_CANCEL_OPTION);

                    if (sovraScrivi != JOptionPane.YES_OPTION) {
                        return;
                    }
                }
                FileManager.salvaPrenotazioni(gestPrenotazioni.getPrenotazioni(), file.getAbsolutePath());
                JOptionPane.showMessageDialog(MyFrame.this, "Prenotazioni salvate!");
            }
        });

        // Pulsante carica: utilizzo JFileChooser per caricare le prenotazioni
        btnCarica.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int scelta = fileChooser.showOpenDialog(this);
            if (scelta == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                List<Prenotazione> loaded = FileManager.caricaPrenotazioni(file.getAbsolutePath());
                if (loaded != null) {
                    gestPrenotazioni.getPrenotazioni().clear();
                    gestPrenotazioni.getPrenotazioni().addAll(loaded);
                    JOptionPane.showMessageDialog(MyFrame.this, "Prenotazioni caricate!");
                    aggiornaTabellaPerData();
                }
            }
        });

        // Pulsante Stampa: richiama il metodo della tabella
        btnStampa.addActionListener(e -> panelOccupazione.stampaTabella());

        operationsPanel.add(btnAggiungi);
        operationsPanel.add(btnModifica);
        operationsPanel.add(btnCancella);
        operationsPanel.add(btnSalva);
        operationsPanel.add(btnCarica);
        operationsPanel.add(btnStampa);

        topPanel.add(datePanel, BorderLayout.WEST);
        topPanel.add(operationsPanel, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        // Pannello centrale: la tabella dell'occupazione delle aule
        panelOccupazione = new PanelOccupazione();
        add(panelOccupazione, BorderLayout.CENTER);

        avviaAutosalvataggio();
    }

    /**
     * Aggiorna la tabella delle prenotazioni, filtrandole in base alla data
     * selezionata.
     */
    private void aggiornaTabellaPerData() {
        LocalDate dataSelezionata = convertToLocalDate((Date) spinnerData.getValue());
        List<Prenotazione> prenotazioniDelGiorno = filtraPrenotazioniPerData(dataSelezionata);
        panelOccupazione.updatePrenotazioni(prenotazioniDelGiorno);
    }

    /**
     * Avvia un thread in background che salva periodicamente (ogni 2 minuti)
     * le prenotazioni correnti in un file temporaneo "autosave.ser".
     */
    private void avviaAutosalvataggio() {
        Timer autosaveTimer = new Timer();
        int intervalloMs = 2 * 60 * 1000; // 2 minuti
        autosaveTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    String tempFilePath = "autosave.ser";
                    Utility.FileManager.salvaPrenotazioni(gestPrenotazioni.getPrenotazioni(), tempFilePath);
                    System.out.println("Autosalvataggio completato in background.");
                } catch (Exception ex) {
                    System.out.println("Errore nell'autosalvataggio: " + ex.getMessage());
                }
            }
        }, intervalloMs, intervalloMs); // Ritardo iniziale, Periodo di ripetizione
    }

    /**
     * Mostra un dialog che permette di inserire i dati di una nuova prenotazione.
     *
     * <p>
     * Il metodo crea un pannello con vari controlli (JSpinner per la data e gli
     * orari di inizio/fine, JTextField per nome e motivazione, JComboBox per tipo
     * di aula e numero) e lo presenta in un JOptionPane di conferma.
     * Se l'utente preme OK, viene creata e restituita una nuova prenotazione
     * (data, oraInizio, oraFine, nome, motivazione, aula), altrimenti {@code null}.
     * </p>
     *
     * <p>
     * La data selezionata, rappresentata come un {@code Date} per lo spinner,
     * viene poi convertita in {@code LocalDate} per le operazioni interne
     * evitando così ore e minuti non richiesti. Inoltre, in base al tipo di aula
     * selezionato, si imposta il menu per il numero dell'aula.
     * </p>
     *
     * @return la nuova prenotazione se l'utente conferma, oppure {@code null} se
     *         l'utente annulla
     */

    private Prenotazione mostraDialogAggiungiPrenotazione() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));

        // 1. Creazione dei componenti grafici per l'inserimento dei dati della
        // prenotazione
        SpinnerDateModel dateModel = new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_MONTH);
        JSpinner spinnerDataDialog = new JSpinner(dateModel);
        spinnerDataDialog.setEditor(new JSpinner.DateEditor(spinnerDataDialog, "dd-MM-yyyy"));

        String[] tipi = { "Didattica", "Laboratorio" };
        JComboBox<String> comboTipo = new JComboBox<>(tipi);
        JComboBox<String> comboNumero = new JComboBox<>(new String[] { "1", "2", "3", "4", "5" });

        JSpinner spinnerOrarioInizio = new JSpinner(new SpinnerNumberModel(8, 8, 17, 1));
        JComboBox<Integer> comboDurata = new JComboBox<>();

        JTextField fieldNome = new JTextField();
        JTextField fieldMotivazione = new JTextField();

        // 2. Logica interattiva - aggiorna le durate disponibili in base all'orario di
        // inizio e al tipo di aula
        Runnable aggiornaDurate = () -> {
            int inizio = (Integer) spinnerOrarioInizio.getValue();
            boolean isDidattica = comboTipo.getSelectedItem().equals("Didattica");

            comboDurata.removeAllItems();
            int maxDurata = 18 - inizio;

            if (isDidattica) {
                for (int i = 1; i <= 8 && i <= maxDurata; i++) {
                    comboDurata.addItem(i);
                }
            } else {
                if (maxDurata >= 2)
                    comboDurata.addItem(2);
                if (maxDurata >= 4)
                    comboDurata.addItem(4);
            }
        };

        comboTipo.addActionListener(e -> aggiornaDurate.run());
        spinnerOrarioInizio.addChangeListener(e -> aggiornaDurate.run());

        // Inizializzo le durate al caricamento del dialog
        aggiornaDurate.run();

        // 3. Ordine dei campi: metto prima tipo e numero aula, poi data e orari, infine
        // nome e motivazione
        panel.add(new JLabel("Data:"));
        panel.add(spinnerDataDialog);

        panel.add(new JLabel("Tipo Aula:"));
        panel.add(comboTipo);

        panel.add(new JLabel("Numero Aula:"));
        panel.add(comboNumero);

        panel.add(new JLabel("Ora Inizio (8-17):"));
        panel.add(spinnerOrarioInizio);

        panel.add(new JLabel("Durata (Ore):"));
        panel.add(comboDurata);

        panel.add(new JLabel("Nome Prenotante:"));
        panel.add(fieldNome);

        panel.add(new JLabel("Motivazione:"));
        panel.add(fieldMotivazione);

        // 4. Mostro il dialog e, se confermato, creo la prenotazione con i dati
        // inseriti
        int result = JOptionPane.showConfirmDialog(MyFrame.this, panel, "Aggiungi Prenotazione",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            if (comboDurata.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(MyFrame.this, "Orario di inizio troppo tardi per la durata richiesta.");
                return null;
            }

            LocalDate data = convertToLocalDate((Date) spinnerDataDialog.getValue());
            int oraInizio = (Integer) spinnerOrarioInizio.getValue();
            int durataScelta = (Integer) comboDurata.getSelectedItem();
            int oraFine = oraInizio + durataScelta;

            return creaPrenotazione(data, oraInizio, oraFine, fieldNome, fieldMotivazione, comboTipo, comboNumero);
        }
        return null;
    }

    /**
     * Mostra un dialog che elenca le prenotazioni del giorno corrente,
     * consentendo di selezionare quale prenotazione modificare o cancellare.
     *
     * @param titolo il titolo della finestra di dialogo per i tipi modifica e
     *               cancella
     * @return la prenotazione selezionata, o {@code null} se annullato
     */

    private Prenotazione selezionaPrenotazione(String titolo) {
        LocalDate dataSelezionata = convertToLocalDate((Date) spinnerData.getValue());
        List<Prenotazione> prenotazioniDelGiorno = filtraPrenotazioniPerData(dataSelezionata);
        if (prenotazioniDelGiorno.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nessuna prenotazione per la data selezionata.");
            return null;
        }
        // Creo un array di stringhe che descrivono le prenotazioni (aula, orari, nome)
        // in modo tale da poterle selezionare con combo
        String[] options = prenotazioniDelGiorno.stream() // trasformo in stream la lista prenotazioni poi con map
                                                          // trasforma le prenotazioni in stringhe per poi diventare
                                                          // array
                .map(p -> "Aula " + p.getAula().getNumAula() + " dalle " + p.getOraInizio() + " alle " + p.getOraFine()
                        + " - " + p.getNomePrenotante())
                .toArray(String[]::new);
        JComboBox<String> combo = new JComboBox<>(options);
        int result = JOptionPane.showConfirmDialog(MyFrame.this, combo, titolo, JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            int index = combo.getSelectedIndex();
            return prenotazioniDelGiorno.get(index);
        }
        return null;
    }

    /**
     * Mostra un dialog che permette di modificare una prenotazione esistente,
     * pre-riempiendo i campi con i dati da modificare.
     *
     * <p>
     * Il metodo crea un pannello con diversi controlli (JSpinner per data, orari,
     * JTextField per nome e motivazione, JComboBox per tipo e numero di aula),
     * mostrando poi un JOptionPane di conferma. Se l'utente conferma (OK),
     * viene creata una nuova prenotazione con i dati aggiornati, altrimenti
     * restituisce {@code null}.
     * </p>
     * 
     * <p>
     * La data della prenotazione in {@code LocalDate} viene convertita
     * in {@code Date} per riempire lo spinner che richiede un {@code Date}.
     * Vengono impostati i valori iniziali degli orari, nome, motivazione, tipo
     * di aula e numero dell'aula in base ai campi della prenotazione da modificare.
     * </p>
     *
     * @param p la prenotazione esistente da modificare
     * @return la prenotazione aggiornata, o {@code null} se l'utente annulla
     *         l'operazione
     */

    private Prenotazione mostraDialogModificaPrenotazione(Prenotazione p) {

        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));

        // Conversione formato data (LocalData --> Date) in modo che possa inserirla
        // nello spinner del dialog modifica
        LocalDate prenDataLocal = p.getData();
        Date prenDataDate = Date.from(prenDataLocal.atStartOfDay(ZoneId.systemDefault()).toInstant());
        SpinnerDateModel dateModel = new SpinnerDateModel(prenDataDate, null, null, Calendar.DAY_OF_MONTH);
        JSpinner spinnerData = new JSpinner(dateModel);
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spinnerData, "dd-MM-yyyy");
        spinnerData.setEditor(dateEditor);

        JSpinner spinnerOrarioInizio = new JSpinner(new SpinnerNumberModel(p.getOraInizio(), 8, 17, 1));
        JSpinner spinnerOrarioFine = new JSpinner(new SpinnerNumberModel(p.getOraFine(), 9, 18, 1));

        JTextField fieldNome = new JTextField(p.getNomePrenotante());
        JTextField fieldMotivazione = new JTextField(p.getMotivazione());

        String tipoStr = (p.getAula().getTipologia() == Aula.TipologiaAula.DIDATTICA) ? "Didattica" : "Laboratorio";
        JComboBox<String> comboTipo = new JComboBox<>(new String[] { "Didattica", "Laboratorio" });
        comboTipo.setSelectedItem(tipoStr);
        JComboBox<String> comboNumero = new JComboBox<>(new String[] { "1", "2", "3", "4", "5" });
        comboNumero.setSelectedItem(String.valueOf(p.getAula().getNumAula()));

        panel.add(new JLabel("Data:"));
        panel.add(spinnerData);
        panel.add(new JLabel("Tipo Aula:"));
        panel.add(comboTipo);
        panel.add(new JLabel("Numero Aula:"));
        panel.add(comboNumero);
        panel.add(new JLabel("Ora Inizio (8-17):"));
        panel.add(spinnerOrarioInizio);
        panel.add(new JLabel("Ora Fine (9-18):"));
        panel.add(spinnerOrarioFine);
        panel.add(new JLabel("Nome Prenotante:"));
        panel.add(fieldNome);
        panel.add(new JLabel("Motivazione:"));
        panel.add(fieldMotivazione);

        int result = JOptionPane.showConfirmDialog(MyFrame.this, panel, "Modifica Prenotazione",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            LocalDate data = convertToLocalDate((Date) spinnerData.getValue());
            int oraInizio = (Integer) spinnerOrarioInizio.getValue();
            int oraFine = (Integer) spinnerOrarioFine.getValue();
            return creaPrenotazione(data, oraInizio, oraFine, fieldNome, fieldMotivazione, comboTipo, comboNumero);
        }
        return null;
    }

    /**
     * Crea un nuovo oggetto {@link Prenotazione} in base ai campi forniti nei
     * componenti grafici,
     * gestendo eventuali errori di input.
     *
     * @param data             la data selezionata
     * @param oraInizio        orario di inizio
     * @param oraFine          orario di fine
     * @param fieldNome        campo di testo per il nome del prenotante
     * @param fieldMotivazione campo di testo per la motivazione
     * @param comboTipo        combobox con il tipo di aula selezionato
     * @param comboNumero      combobox con il numero dell'aula selezionato
     * @return la prenotazione creata, o {@code null} in caso di errore
     */

    private Prenotazione creaPrenotazione(LocalDate data, int oraInizio, int oraFine,
            JTextField fieldNome, JTextField fieldMotivazione,
            JComboBox<String> comboTipo, JComboBox<String> comboNumero) {
        try {
            String nome = fieldNome.getText().trim();
            String motivazione = fieldMotivazione.getText().trim();
            String tipo = (String) comboTipo.getSelectedItem();
            int numero = Integer.parseInt((String) comboNumero.getSelectedItem());

            Aula aula = trovaAula(tipo, numero, aule);

            if (aula == null) {
                JOptionPane.showMessageDialog(this,
                        "Errore: L'aula selezionata non esiste. Controlla il caricamento del file aule.txt.");
                return null;
            }

            return new Prenotazione(data, oraInizio, oraFine, nome, motivazione, aula);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Errore nei dati inseriti: " + ex.getMessage());
            return null;
        }
    }

    /**
     * Filtra la lista di prenotazioni in base alla data specificata.
     *
     * @param data la data da filtrare
     * @return lista di prenotazioni che corrispondono alla data indicata
     */
    private List<Prenotazione> filtraPrenotazioniPerData(LocalDate data) {
        return gestPrenotazioni.getPrenotazioni().stream()
                .filter(p -> p.getData().equals(data))
                .collect(Collectors.toList());
    }

    /**
     * Converte un {@link Date} in un {@link LocalDate} usando il fuso orario di
     * sistema.
     *
     * @param date la data da convertire
     * @return conversione in LocalData
     */
    private LocalDate convertToLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * Ricerco l'aula in base ai @param tipo, @param numero, @param aule
     * 
     * @return l'oggetto {@link Aula} corrispondente se trovato, altrimenti
     *         {@code null}
     */
    private Aula trovaAula(String tipo, int numero, List<Aula> aule) {
        for (Aula a : aule) {
            if (tipo.equals("Didattica") && a.getTipologia() == Aula.TipologiaAula.DIDATTICA
                    && a.getNumAula() == numero) {
                return a;
            } else if (tipo.equals("Laboratorio") && a.getTipologia() == Aula.TipologiaAula.LABORATORIO
                    && a.getNumAula() == numero) {
                return a;
            }
        }
        return null;
    }

}