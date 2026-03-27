package Utility;

import Classi.Aula;
import Classi.AulaDid;
import Classi.AulaLab;
import Classi.Prenotazione;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FileManagerTest {

    @Test
    public void testSalvaECaricaPrenotazioni(@TempDir Path tempDir) {
        File filetest = tempDir.resolve("prenotazioni_test.dat").toFile();
        String filepath = filetest.getAbsolutePath();
        List<Prenotazione> prenotazioniDaSalvare = new ArrayList<>();
        AulaDid aulaDid = new AulaDid(101, 50);
        prenotazioniDaSalvare
                .add(new Prenotazione(LocalDate.now(), 10, 12, "Mario Rossi", "Lezione di Matematica", aulaDid));
        AulaLab aulaLab = new AulaLab(202, 30);
        prenotazioniDaSalvare
                .add(new Prenotazione(LocalDate.now(), 14, 16, "Luigi Bianchi", "Laboratorio di Informatica", aulaLab));
        FileManager.salvaPrenotazioni(prenotazioniDaSalvare, filepath);
        List<Prenotazione> prenotazioniCaricate = FileManager.caricaPrenotazioni(filepath);
        assertTrue(filetest.exists(), "Il file dovrebbe essere stato creato.");
        assertEquals(2, prenotazioniCaricate.size(), "Dovrebbero essere state caricate due prenotazioni.");
        assertEquals("Mario Rossi", prenotazioniCaricate.get(0).getNomePrenotante(),
                "Il nome del richiedente della prima prenotazione dovrebbe essere 'Mario Rossi'.");
        assertEquals("Luigi Bianchi", prenotazioniCaricate.get(1).getNomePrenotante(),
                "Il nome del richiedente della seconda prenotazione dovrebbe essere 'Luigi Bianchi'.");

    }

}
