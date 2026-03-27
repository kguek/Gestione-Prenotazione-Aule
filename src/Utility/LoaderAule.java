package Utility;

import Classi.Aula;
import Classi.AulaDid;
import Classi.AulaLab;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Classe {@code LoaderAule} fornisce un metodo statico per caricare le aule da
 * un file di configurazione.
 * Il file deve essere formattato con righe che contengono il numero dell'aula,
 * la capienza e il tipo (Didattica o Laboratorio) separati da punti e virgola.
 */
public class LoaderAule {
    /**
     * Legge un file di configurazione per le aule e crea una lista di oggetti
     * {@code Aula} corrispondenti.
     * *@param filePath il percorso del file di configurazione da leggere
     * 
     * @return una lista di oggetti {@link Aula} (didattica o laboratorio) creati in
     *         base al contenuto del file
     */
    public static List<Aula> loadAule(String filePath) {
        List<Aula> aule = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parti = line.split(";");
                if (parti.length != 3) {
                    System.out.println("Formato non valido per la linea: " + line);
                    continue;
                }
                int numero = Integer.parseInt(parti[0].trim());
                int capienza = Integer.parseInt(parti[1].trim());
                String tipo = parti[2].trim();
                Aula aula = null;
                if (tipo.equalsIgnoreCase("Didattica")) {
                    aula = new AulaDid(numero, capienza);

                } else if (tipo.equalsIgnoreCase("Laboratorio")) {
                    aula = new AulaLab(numero, capienza);
                }
                if (aula != null) {
                    aule.add(aula);
                }

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return aule;
    }

}
