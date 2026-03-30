package Operazioni;

import Classi.AulaDid;
import Classi.AulaLab;
import Classi.Prenotazione;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

public class GestionePrenotazioniTest {
        @Test
        public void testPrenotazione() {
                GestionePrenotazioni gestione = new GestionePrenotazioni();
                AulaDid aulaTest = new AulaDid(101, 50);
                Prenotazione p1 = new Prenotazione(LocalDate.now().plusDays(1), 10, 12, "Mario Rossi",
                                "Lezione di Matematica",
                                aulaTest);
                gestione.addPrenotazione(p1);
                assertEquals(1, gestione.getPrenotazioni().size(),
                                "La prenotazione dovrebbe essere stata aggiunta correttamente.");
        }

        @Test
        public void testPrenotazioneOrarioInvalido() {
                GestionePrenotazioni gestione = new GestionePrenotazioni();
                AulaDid aulaTest = new AulaDid(101, 50);
                Prenotazione pError = new Prenotazione(LocalDate.now().plusDays(1), 12, 10, "Luigi Bianchi", "Errore",
                                aulaTest);
                gestione.addPrenotazione(pError);
                assertEquals(0, gestione.getPrenotazioni().size(),
                                "La prenotazione con orario di fine precedente all'orario di inizio non dovrebbe essere aggiunta.");
        }

        @Test
        public void testPrenotazioneConflitto() {
                GestionePrenotazioni gestione = new GestionePrenotazioni();
                AulaDid aulaTest = new AulaDid(101, 50);
                Prenotazione p1 = new Prenotazione(LocalDate.now().plusDays(1), 10, 12, "Mario Rossi",
                                "Lezione di Matematica",
                                aulaTest);
                Prenotazione p2 = new Prenotazione(LocalDate.now().plusDays(1), 11, 13, "Luigi Bianchi",
                                "Lezione di Fisica",
                                aulaTest);
                gestione.addPrenotazione(p1);
                gestione.addPrenotazione(p2);
                assertEquals(1, gestione.getPrenotazioni().size(),
                                "Solamente la prima prenotazione dovrebbe essere stata aggiunta.");
        }

        @Test
        public void testPrenotazioneDurataInvalida() {
                GestionePrenotazioni gestione = new GestionePrenotazioni();
                AulaLab aulaTest = new AulaLab(101, 50);
                Prenotazione pError = new Prenotazione(LocalDate.now().plusDays(1), 10, 13, "Luigi Bianchi", "Errore",
                                aulaTest);
                gestione.addPrenotazione(pError);
                assertEquals(0, gestione.getPrenotazioni().size(),
                                "La prenotazione con durata di 3 ore non dovrebbe essere aggiunta.");
        }

        @Test
        public void testPrenotazioneDurataOrarioSup() {
                GestionePrenotazioni gestione = new GestionePrenotazioni();
                AulaLab aulaTest = new AulaLab(101, 50);
                Prenotazione pError = new Prenotazione(LocalDate.now().plusDays(1), 10, 17, "Luigi Bianchi", "Errore",
                                aulaTest);
                gestione.addPrenotazione(pError);
                assertEquals(0, gestione.getPrenotazioni().size(),
                                "La prenotazione con durata superore alle 4 ore non dovrebbe essere aggiunta.");
        }

        @Test
        public void testPrenotazioneStessoOrarioAulaDiversa() {
                GestionePrenotazioni gestione = new GestionePrenotazioni();
                AulaDid aula1 = new AulaDid(101, 50);
                AulaLab aula2 = new AulaLab(102, 30);
                Prenotazione p1 = new Prenotazione(LocalDate.now().plusDays(1), 10, 12, "Mario Rossi",
                                "Lezione di Matematica",
                                aula1);
                Prenotazione p2 = new Prenotazione(LocalDate.now().plusDays(1), 10, 12, "Luigi Bianchi",
                                "Lezione di Fisica",
                                aula2);
                gestione.addPrenotazione(p1);
                gestione.addPrenotazione(p2);
                assertEquals(2, gestione.getPrenotazioni().size(),
                                "Entrambe le prenotazioni dovrebbero essere aggiunte poiché sono in aule diverse.");
        }

        @Test
        public void testRimuoviPrenotazione() {
                GestionePrenotazioni gestione = new GestionePrenotazioni();
                AulaDid aulaTest = new AulaDid(101, 50);
                Prenotazione p1 = new Prenotazione(LocalDate.now().plusDays(1), 10, 12, "Mario Rossi",
                                "Lezione di Matematica",
                                aulaTest);
                gestione.addPrenotazione(p1);
                assertTrue(gestione.getPrenotazioni().contains(p1),
                                "La prenotazione dovrebbe essere presente prima della rimozione.");
                gestione.getPrenotazioni().remove(p1);
                assertFalse(gestione.getPrenotazioni().contains(p1),
                                "La prenotazione non dovrebbe essere presente dopo la rimozione.");
        }

        @Test
        public void testModificaPrenotazione() {
                GestionePrenotazioni gestione = new GestionePrenotazioni();
                AulaDid aulaTest = new AulaDid(101, 50);
                Prenotazione p1 = new Prenotazione(LocalDate.now().plusDays(1), 10, 12, "Mario Rossi",
                                "Lezione di Matematica",
                                aulaTest);
                gestione.addPrenotazione(p1);
                assertEquals(1, gestione.getPrenotazioni().size(), "La prenotazione dovrebbe essere presente.");
                Prenotazione p2 = new Prenotazione(LocalDate.now().plusDays(1), 11, 13, "Mario Rossi",
                                "Lezione di Matematica",
                                aulaTest);
                gestione.modPrenotazione(p1, p2);
                Prenotazione salvata = gestione.getPrenotazioni().get(0);
                assertEquals(11, salvata.getOraInizio(), "L'orario di inizio dovrebbe essere stato modificato.");
                assertEquals(13, salvata.getOraFine(), "L'orario di fine dovrebbe essere stato modificato.");
                assertTrue(gestione.getPrenotazioni().contains(p2),
                                "La prenotazione dovrebbe essere stata modificata.");
                assertFalse(gestione.getPrenotazioni().contains(p1),
                                "La prenotazione vecchia non dovrebbe essere presente.");
        }
}