# Gestione Prenotazione Aule (Java Swing)

Un'applicazione desktop sviluppata per un esame universitario in Java per la gestione e il monitoraggio
delle prenotazioni di aule universitarie (Didattiche e Laboratori).
Il progetto é stato realizzato applicando i principi della Programmazione Orientata ad oggetti (OOP)
e un'interfaccia grafica moderna.

## Funzionalitá principali
- **Gestione Aule:** Supporto per diverse tipologie di aule (Didattica, Laboratorio) lette dinamicamente da file.
- **Prenotazioni Intelligenti:** Controllo automatico di conflitti di orario e rispetto dei vincoli specifici per tipologia di aula.
- **Interfaccia Grafica Moderna:** Realizzata con Java Swing e potenziata dal Look and Feel **FlatLaf** per un design pulito e professionale.
- **Persistenza Dati:** Salvataggio automatico (Autosave in background) e manuale su file system.
- **Esportazione:** Possibilità di stampare la tabella delle occupazioni.

## Test Automatici (Junit 5)
Il progetto include una suite di test unitari automatizzati per garantire l'affidabilitá del codice:
- **Validazione Logica:** Controllo preventivo delle sovrapposizioni di orario e dei vincoli specifici di ogni tipologia di aula.
- **Operazioni CRUD.** Verifica del corretto funzionamento di aggiunta,modifica e cancellazione delle prenotazioni.
- **Salvataggio Dati.** Test del salvataggio e caricamento su file system system utilizzando l'ambiente isolato (`@TempDir`) di JUnit, per garantire che i test non sporchino i file reali dell'utente.

Per lanciare l'intera suite di test, usa il comando:
```bash
mvn test
```
Per lanciare un singolo file di test, usa il comando:
```bash
mvn test -Dtest="NOMEFILE_TEST"
```

## Come eseguire il progetto 
Questo progetto utilizza **Maven** per la gestione delle dipendenze e la compilazione.

Assicurarsi di avere [Mavedn](https://maven.apache.org/) e JDK 17 (o superiore) installati sul tuo sistema.

1. **Compilazione:**
   - **Su Linux/MacOS:**
     ```bash

     mvn clenan compile
    
2. **Esecuzione:**
   ```bash
   mvn exec:java -Dexec.mainClass="App.Main"
   ````