# 📘 Mini Event‑Planer – Backend (Spring Boot)

Dies ist das Backend des Mini Event‑Planers, entwickelt im Rahmen des Moduls M295.
Das Projekt stellt eine vollständige REST‑API zur Verwaltung von Events, Locations und Artists bereit.

Das Backend basiert auf Spring Boot 3, nutzt DTO‑Mapping, Services, Repositories und ist vollständig mit Integrationstests abgesichert.

## 🚀 Features (Backend)

✔ Events verwalten
* Event erstellen
* Event abrufen
* Event löschen
* Fehlerfälle (404 / 400) sauber implementiert
* REST‑konforme Statuscodes (201, 200, 204, 404, 400)

✔ DTO‑Mapping
* Klare Trennung zwischen Entity und API‑Modell
* EventMapper für saubere Konvertierung

✔ Services
* Business‑Logik ausgelagert
* Validierung und Fehlerbehandlung im Service

✔ Repository
* Spring Data JPA
* H2‑InMemory‑DB für Tests
* PostgreSQL für Docker‑Deployment

✔ Integrationstests
* GET /events
* POST /events
* DELETE /events/{id}

Fehlerfälle (404, 400)

## 🧪 Tests
Das Backend enthält vollständige Integrationstests mit MockMvc:

|         Test        |              Datei             |        Zweck         |
| ------------------- | ------------------------------ | -------------------- |
| GET /events         | EventControllerIntegrationTest | Liste abrufen        |
| POST /events        | EventPostIntegrationTest       | Event erstellen      |
| Fehlerfälle         | EventErrorIntegrationTest      | 404 & 400 testen     |
| DELETE /events/{id} | EventDeleteIntegrationTest     | Löschen & Fehlerfall |


Alle Tests laufen grün und erfüllen die LB‑Vorgaben.

## 🏗 Architektur
📂 Backend‑Struktur
Code
backend/
 └── src/main/java/ch/swiss/eventbackend/
      ├── controller/      → REST‑API
      ├── service/         → Business‑Logik
      ├── repository/      → Datenbankzugriff
      ├── model/           → Entities
      ├── dto/             → API‑Modelle
      └── mapper/          → DTO‑Mapping

## 🐳 Docker
Das Backend kann vollständig über Docker gestartet werden.

Beispiel:
Code
docker compose up -d --build

Services:
* event-backend (Spring Boot)
* PostgreSQL Datenbank

## 🔌 API‑Endpoints (Auszug)
Events
| Methode | Endpoint     | Beschreibung            |
| ------- | ------------ | ----------------------- |
| GET     | /events      | Alle Events abrufen     |
| GET     | /events/{id} | Einzelnes Event abrufen |
| POST    | /events      | Neues Event erstellen   |
| DELETE  | /events/{id} | Event löschen           |


## 🎨 Frontend
Das Frontend wird später ergänzt.
*****************************
*****************************
*****************************
*****************************
*****************************

(Platzhalter für zukünftige Beschreibung)



## 📄 Lizenz
Dieses Projekt wurde im Rahmen der Ausbildung an der WISS Schulen für Wirtschaft Informatik Immobilien AG erstellt.

## 👤 Autor
Marco Fritsche & Luca Caputi
Aspiring Application Developer
Schweiz