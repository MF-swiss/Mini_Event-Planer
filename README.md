# 📘 Mini Event‑Planer – Backend & Frontend

Dies ist der Mini Event‑Planer, entwickelt im Rahmen des Moduls M295.
Das Projekt besteht aus einem Spring Boot Backend und einem React + Vite Frontend.
Es bietet eine vollständige REST‑API sowie eine moderne Single‑Page‑Anwendung zur Verwaltung von Events, Locations und Artists.

## 🚀 Backend (Spring Boot)

Das Backend stellt eine vollständige REST‑API bereit und basiert auf Spring Boot 3, DTO‑Mapping, Services, Repositories und JUnit‑Tests.

## ✔ Features
### Events verwalten
* Event erstellen
* Event abrufen
* Event bearbeiten
* Event löschen
* Fehlerfälle (404 / 400) sauber implementiert
* REST‑konforme Statuscodes (201, 200, 204, 404, 400)

### DTO‑Mapping
* Klare Trennung zwischen Entity und API‑Modell
* EventMapper für Entity ↔ DTO Konvertierung

### Services
* Business‑Logik ausgelagert
* Validierung und Fehlerbehandlung zentralisiert

### Repository
* Spring Data JPA
* H2‑InMemory‑DB für Tests
* PostgreSQL für Docker‑Deployment

## 🧪 Tests
Das Backend enthält Integrationstests und Unit‑Tests.


### 🔵 Integrationstests
|         Test        |              Datei             |        Zweck         |
| ------------------- | ------------------------------ | -------------------- |
| GET /events         | EventControllerIntegrationTest | Liste abrufen        |
| POST /events        | EventPostIntegrationTest       | Event erstellen      |
| Fehlerfälle         | EventErrorIntegrationTest      | 404 & 400 testen     |
| DELETE /events/{id} | EventDeleteIntegrationTest     | Löschen & Fehlerfall |

### 🟢 Unit‑Tests (C13)

|      Testtyp       |	           Datei           |           	Zweck             |
| ------------------ | --------------------------- | ------------------------------ |
| Repository       	| EventRepositoryTest	      | Persistenzlogik testen         |
| Service         	| EventServiceTest	         | Business‑Logik isoliert testen |
| Controller GET	   | EventControllerGetTest	   | DTO‑Mapping & Response testen  |
| Controller POST	   | EventControllerPostTest  	| Validierung & Mapping testen   |
| Controller DELETE	| EventControllerDeleteTest	| Löschlogik & Fehlerfall testen |

Alle Tests laufen grün und erfüllen die LB‑Vorgaben.

## 🏗 Architektur
📂 Backend‑Struktur

```
backend/
 └── src/main/java/ch/swiss/eventbackend/
      ├── controller/      → REST‑API
      ├── service/         → Business‑Logik
      ├── repository/      → Datenbankzugriff
      ├── model/           → Entities
      ├── dto/             → API‑Modelle
      └── mapper/          → DTO‑Mapping
```

## 🐳 Docker
Das Backend kann vollständig über Docker gestartet werden.


### Code
docker compose up -d --build

### Services:
* event-backend (Spring Boot)
* PostgreSQL Datenbank

## 🔌 API‑Endpoints (Auszug)
Events
| Methode | Endpoint     | Beschreibung            |
| ------- | ------------ | ----------------------- |
| GET     | /events      | Alle Events abrufen     |
| GET     | /events/{id} | Einzelnes Event abrufen |
| POST    | /events      | Neues Event erstellen   |
| PUT     | /events/{id} | Event bearbeiten        |
| DELETE  | /events/{id} | Event löschen           |


## 🎨 Frontend (React + Vite)

Das Frontend ist eine moderne Single‑Page‑Anwendung zur Verwaltung von Events, Locations und Artists.
Es konsumiert ausschliesslich die REST‑API des Backends.

### Features (Frontend)

✔ Events verwalten
* Übersicht aller Events, chronologisch sortiert
* Neues Event erstellen (Titel, Datum, Beschreibung, Location, Artist)
* Bestehendes Event bearbeiten
* Event löschen (mit Bestätigungsdialog)
* Abgelaufene Events werden optisch abgesetzt (ausgegraut)

✔ Location & Artist „on the fly" erstellen
* Dropdown-Auswahl für bestehende Locations/Artists
* Option „+ Neue Location/Artist erstellen" öffnet ein Modal, ohne das Event-Formular zu verlassen
* Neu erstellte Einträge stehen sofort im Dropdown zur Auswahl

✔ Validierung
* Pflichtfelder sind sowohl im Event-Formular als auch in den Modals sichtbar markiert (`*`)
* Clientseitige Prüfung vor dem Absenden, mit verständlicher Fehlermeldung

✔ UI/UX
* Dunkles, modernes Kartendesign
* Responsives Layout (Grid-Umbruch auf schmalen Bildschirmen)
* Konsistente Icon-Kennzeichnung (📅 Datum, 📍 Location, 🎤 Artist)

## Tech-Stack

* React (funktionale Komponenten, Hooks: `useState`, `useEffect`)
* Vite als Build-Tool und Dev-Server
* Fetch-API für die Kommunikation mit dem Backend
* Reines CSS (kein UI-Framework), thematisch an das Backend-Farbschema angelehnt

### 📂 Frontend-Struktur

```
frontend/
 └── src/
      ├── pages/
      │    └── PageOne.jsx        → Event-Übersicht, Erstellen/Bearbeiten-Formulare
      ├── components/
      │    ├── Countdown.jsx      → Post für die Startseite, zeigt verbleibende Zeit bis zum nächsten Eventbeginn
      │    ├── LocationModal.jsx  → Modal zum Anlegen einer neuen Location
      │    └── ArtistModal.jsx    → Modal zum Anlegen eines neuen Artists
      └── styles/
           ├── Post.css           → Post mit Countdown auf der Startseite
           ├── PageOne.css        → Styling der Event-Übersicht
           └── Modal.css          → Styling der Modals
```

### ⚙️ Setup

1. Ins Frontend-Verzeichnis wechseln:
   ```
   cd frontend
   ```
2. Abhängigkeiten installieren:
   ```
   npm install
   ```
3. `.env`-Datei anlegen mit der Backend-URL:
   ```
   VITE_API_URL=http://localhost:8080
   ```
4. Dev-Server starten:
   ```
   npm run dev
   ```

Das Frontend läuft anschliessend standardmässig unter `http://localhost:5173` und erwartet ein laufendes Backend unter der in `.env` hinterlegten URL.

### 🔌 Verwendete Endpoints

Das Frontend nutzt folgende Endpoints des Backends:

| Methode | Endpoint          | Verwendung im Frontend                   |
| ------- | ----------------- | ---------------------------------------- |
| GET     | /events           | Event-Liste beim Laden der Seite         |
| POST    | /events           | Neues Event über das Formular anlegen    |
| PUT     | /events/{id}      | Event bearbeiten                         |
| DELETE  | /events/{id}      | Event löschen                            |
| GET     | /locations        | Location-Dropdown befüllen               |
| POST    | /locations        | Neue Location über LocationModal anlegen |
| GET     | /artists          | Artist-Dropdown befüllen                 |
| POST    | /artists          | Neuer Artist über ArtistModal anlegen    |


## 📄 Lizenz
Dieses Projekt wurde im Rahmen der Ausbildung an der WISS Schulen für Wirtschaft Informatik Immobilien AG erstellt.

## 👤 Autor
Marco Fritsche & Luca Caputi  
Aspiring Application Developer
Schweiz