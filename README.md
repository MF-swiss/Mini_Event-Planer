# NovaSphere Event – Interaktive Webapplikation

Diese Applikation ist ein kleiner **Event-Planer** auf Basis von **React + Vite**.
Sie bietet eine Startseite mit dem nächsten Event sowie eine Verwaltungsseite, auf der Events geladen, erstellt, bearbeitet und gelöscht werden können.

Die Daten werden über eine REST-API (lokal via `json-server`) aus `events.json` bereitgestellt.

## Features

- Anzeige des nächsten Events inkl. Countdown
- Event-Verwaltung (CRUD: erstellen, lesen, bearbeiten, löschen)
- Einfache Pflichtfeld-Validierung beim Erstellen/Bearbeiten
- Nutzung von Umgebungsvariablen über `.env` (`VITE_API_URL`)

## Voraussetzungen

- **Node.js** (empfohlen: aktuelle LTS-Version)
- **npm** (wird mit Node.js installiert)

## Installation

1. Abhängigkeiten installieren:

	`npm install`

2. `.env` Datei im Projekt-Root erstellen (falls noch nicht vorhanden):

	```env
	VITE_API_URL=http://localhost:3001
	```

## Lokale Entwicklung starten

Für die Entwicklung brauchst du **zwei laufende Prozesse**:

1. API starten (json-server auf Port 3001):

	`npm run api`

2. Frontend starten (Vite Dev Server):

	`npm run dev`

Danach ist die App standardmässig unter der im Terminal angezeigten lokalen URL erreichbar (typisch `http://localhost:5173`).

## Weitere nützliche Skripte

- Build erstellen: `npm run build`
- Build lokal previewen: `npm run preview`
- Linting ausführen: `npm run lint`
