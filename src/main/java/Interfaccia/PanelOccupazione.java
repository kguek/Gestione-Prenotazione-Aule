package Interfaccia;

import Classi.Aula;
import Classi.Prenotazione;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.List;
import java.awt.print.PrinterException;
import java.text.MessageFormat;

/**
 * La classe {@code PanelOccupazione} fornisce metodi per la funzione della
 * tabella (inizializzazione,refresh e update)
 * 
 * <p>
 * Il metodo {@link #initTable} iniziallizza la struttura e lo stile del JTable
 * Il metodo {@link #refreshTable()} crea la griglia.
 * Il metodo {@link #updatePrenotazioni(List)} aggiorna i dati all interno della
 * griglia.
 * </p>
 * 
 */

public class PanelOccupazione extends JPanel {

    // Nomi fissi per le aule: prime 5 per Didattica, successive 5 per Laboratorio
    public static final String[] NOMI_AULE = {
            "Did1", "Did2", "Did3", "Did4", "Did5",
            "Lab1", "Lab2", "Lab3", "Lab4", "Lab5"
    };

    private JTable table;
    private DefaultTableModel tableModel;

    public PanelOccupazione() {
        setLayout(new BorderLayout());
        initTable();
    }

    /**
     * Inizializza la JTable e la configura, impostando:
     * <ul>
     * <li>Un DefaultTableModel non editabile</li>
     * <li>Le colonne "Orario" e {@link #NOMI_AULE}</li>
     * <li>Font e dimensioni di base</li>
     * <li>Un renderer per colorare di giallo le celle occupate</li>
     * </ul>
     * Infine, richiama {@link #refreshTable()} per creare la griglia oraria di
     * base.
     */

    private void initTable() {
        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Le celle non sono editabili
            }
        };
        tableModel.addColumn("Orario");
        for (String nomeAula : NOMI_AULE) {
            tableModel.addColumn(nomeAula);
        }
        table = new JTable(tableModel);
        // table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        // table.setFillsViewportHeight(true);

        table.setRowSelectionAllowed(false);
        table.setColumnSelectionAllowed(false);
        table.setCellSelectionEnabled(false);
        table.setFocusable(false);

        // Personalizzazione tabella
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(94); // Aumenta l'altezza delle righe per renderle più grandi
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));

        // Imposta larghezze preferite per le colonne, se necessario
        table.getColumnModel().getColumn(0).setPreferredWidth(120); // Colonna Orario
        for (int i = 1; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(180);
        }

        // Renderer personalizzato per colorare le celle occupate
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (column > 0 && value != null && !value.toString().trim().isEmpty()) {
                    comp.setBackground(new Color(219, 234, 254));
                    comp.setForeground(new Color(30, 58, 138));
                } else {
                    comp.setBackground(Color.WHITE);
                    comp.setForeground(Color.BLACK);

                }
                return comp;
            }
        });

        add(new JScrollPane(table), BorderLayout.CENTER);
        refreshTable();
    }

    /**
     * Crea la struttura base della tabella, impostando una riga per ogni ora
     * (dalle 8 alle 18) e una colonna per ogni aula.
     * <p>
     * Tutte le celle, eccetto la prima colonna "Orario", vengono inizializzate a
     * stringa vuota.
     * </p>
     */

    public void refreshTable() {
        tableModel.setRowCount(0);
        for (int ora = 8; ora < 18; ora++) {
            Object[] row = new Object[NOMI_AULE.length + 1];
            row[0] = ora + " - " + (ora + 1);
            for (int i = 1; i <= NOMI_AULE.length; i++) {
                row[i] = "";
            }
            tableModel.addRow(row);
        }
    }

    /**
     * Aggiorna la tabella in base a una lista di prenotazioni.
     * <p>
     * Per ogni {@link Prenotazione}, vengono calcolati gli indici di riga
     * in base a {@code oraInizio} e {@code oraFine} e la colonna corrispondente
     * in base al tipo e al numero dell'aula.
     * La cella viene scritta con le informazioni
     * di nome del prenotante e motivazione.
     * </p>
     *
     * @param prenotazioni la lista di prenotazioni da visualizzare
     */

    public void updatePrenotazioni(List<Prenotazione> prenotazioni) {
        refreshTable();
        for (Prenotazione pren : prenotazioni) {
            int start = pren.getOraInizio();
            int end = pren.getOraFine();
            int rowStart = start - 8;
            int rowEnd = end - 8;
            // Il numero dell'aula (getNumAula) corrisponde alla colonna
            int colIndex = (pren.getAula().getTipologia() == Aula.TipologiaAula.DIDATTICA)
                    ? pren.getAula().getNumAula()
                    : pren.getAula().getNumAula() + 5;
            String info = pren.getNomePrenotante() + " - " + pren.getMotivazione();
            for (int r = rowStart; r < rowEnd; r++) {
                tableModel.setValueAt(info, r, colIndex);
            }
        }
    }

    public void stampaTabella() {
        try {
            // Intestazione e Piè di pagina per il foglio stampato
            MessageFormat header = new MessageFormat("Tabella Occupazione Aule");
            MessageFormat footer = new MessageFormat("Pagina {0}");

            // JTable.print() apre da solo la finestra di dialogo della stampante!
            boolean completato = table.print(JTable.PrintMode.FIT_WIDTH, header, footer);

            if (completato) {
                JOptionPane.showMessageDialog(this, "Stampa completata con successo!", "Stampa",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (PrinterException pe) {
            JOptionPane.showMessageDialog(this, "Errore di stampa: " + pe.getMessage(), "Errore",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

}
