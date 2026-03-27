package App;

import Interfaccia.MyFrame;
import javax.swing.UIManager;
import java.awt.Font;

import com.formdev.flatlaf.FlatLightLaf;

/**
 * La classe {@code Main} è il punto di ingresso dell'applicazione. Si occupa di
 * configurare l'aspetto grafico e avviare la finestra principale.
 *
 * <p>
 * In particolare, questa classe:
 * <ul>
 * <li>Imposta il tema moderno FlatLaf prima di creare la finestra</li>
 * <li>Configura un font moderno e pulito per tutta l'applicazione</li>
 * <li>Avvia la finestra principale {@link MyFrame}</li>
 * </ul>
 * <p>
 */
public class Main {
    public static void main(String[] args) {

        // 1. Installiamo il tema moderno PRIMA di creare la finestra
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());

            // 2. Impostiamo un Font moderno, pulito e senza grazie per TUTTA l'app
            UIManager.put("defaultFont", new Font("Segoe UI", Font.PLAIN, 14));

        } catch (Exception ex) {
            System.err.println("Impossibile avviare il tema moderno");
        }

        // 3. Avviamo la finestra
        new MyFrame().setVisible(true);
    }
}