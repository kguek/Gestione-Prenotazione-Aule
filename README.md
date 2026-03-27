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

## Come eseguire il progetto 
Il Progetto é progettato per essere compilato ed eseguito tramite riga di comando includendo le Librerie esterne necessarie.

1. **Compilazione:**
   - **Su Linux/MacOS:**
     ```bash

     javac -cp ".:lib/*" -d . $(find src -name "*.java")
    
2. **Esecuzione:**
   ```bash
   java -cp ".:lib/*" App.Main
   ````