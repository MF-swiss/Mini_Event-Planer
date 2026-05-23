# Storyboard zeichnen

## Schritt 2.1: Die Bildschirme der WISS-Quiz-App
┌──────────────┐ ┌──────────────┐ ┌──────────────┐
│              │ │              │ │              │
│  Startseite  │ │     Quiz     │ │   Ergebnis   │
│              │ │              │ │              │
└──────────────┘ └──────────────┘ └──────────────┘

## Schritt 2.2: Den Inhalt jedes Bildschirms skizzieren
  ┌────────────────────────────┐
  │ WISS-Quiz    | Punkte: 3   │ ← Header mit Score
  ├────────────────────────────┤
  │                            │
  │    Welcher Begriff kommt   │ ← Frage
  │  im ICAO-Alphabet zuerst?  │
  │                            │
  │                            |
  |           Alfa             |
  |           Bravo            | ← Buttons
  |           Charlie          |
  │                            │
  │           Richtig!         │ ← Feedback
  │                            │
  │                            │
  |                            |
  |        NächsteFrage        | ← Navigation
  │                            │
  └────────────────────────────┘

## Schritt 2.3: Navigation einzeichnen



Start−Button

Startseite ─────────────────► Quiz
     │
     │

Alle Fragen beantwortet

     ▼
  Ergebnis
     │
     │

Nochmalspielen

     └────────────► Startseite


 ## Schritt 2.4: Reflexion

 ### Wenn du fertig bist, schau dir dein Storyboard an und frage dich:

* Welche Komponenten wirst du wahrscheinlich brauchen? (Tipp: Wahrscheinlich mindestens Navigation, GameSession, StartPage, ResultPage)
* Welche Daten müssen zwischen den Bildschirmen weitergegeben werden? (z.B. Punktestand vom Quiz zur Ergebnis-Seite)
* Was fehlt noch? Welche Bildschirme könntest du zusätzlich brauchen? (z.B. ein Impressum, eine Regeln-Seite, eine Admin-Seite zum Fragen erstellen?)

