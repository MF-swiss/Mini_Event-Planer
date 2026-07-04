============================================================
LB‑TESTDOKUMENTATION – MINI EVENT‑PLANER BACKEND
Autor: Marco Fritsche
Modul: M295 – Web‑Backend
Projekt: Event‑Planer (Spring Boot)
============================================================

Ziel der Tests
Die Tests stellen sicher, dass die REST‑API korrekt funktioniert.
Getestet werden:

GET‑Endpoints (Daten abrufen)

POST‑Endpoints (Daten erstellen)

DELETE‑Endpoints (Daten löschen)

Fehlerfälle (404 Not Found, 400 Bad Request)

Validierung von Pflichtfeldern

Alle Tests wurden mit JUnit 5, Spring Boot Test und MockMvc durchgeführt.

Testumgebung

Spring Boot 3.2.x

JUnit Jupiter

MockMvc

H2‑InMemory‑Datenbank

Repository wird vor jedem Test geleert (deleteAll())

Integrationstests

3.1 GET /events – Liste abrufen
Datei: EventControllerIntegrationTest.java
Was wird getestet:

Der Endpoint /events ist erreichbar

Es wird 200 OK zurückgegeben

Die gespeicherten Events werden korrekt als JSON zurückgegeben

Screenshot‑Vorlage:
GET /events – Test erfolgreich (200 OK)
[SCREENSHOT HIER EINFÜGEN]

3.2 POST /events – Event erstellen
Datei: EventPostIntegrationTest.java
Was wird getestet:

Ein neues Event wird korrekt erstellt

Der Endpoint liefert 201 Created

Das zurückgegebene JSON enthält die richtigen Werte

Screenshot‑Vorlage:
POST /events – Test erfolgreich (201 Created)
[SCREENSHOT HIER EINFÜGEN]

3.3 Fehlerfälle – 404 & 400
Datei: EventErrorIntegrationTest.java

404 Not Found:

GET /events/{id} liefert 404, wenn die ID nicht existiert

400 Bad Request:

POST /events liefert 400, wenn Pflichtfelder fehlen

Screenshot‑Vorlage:
Fehlerfälle – 404 & 400 erfolgreich getestet
[SCREENSHOT HIER EINFÜGEN]

3.4 DELETE /events/{id} – Event löschen
Datei: EventDeleteIntegrationTest.java

204 No Content:

Ein existierendes Event wird gelöscht

Der Endpoint liefert 204 No Content

404 Not Found:

DELETE /events/{id} liefert 404, wenn die ID nicht existiert

Screenshot‑Vorlage:
DELETE /events/{id} – 204 & 404 erfolgreich getestet
[SCREENSHOT HIER EINFÜGEN]

Zusammenfassung der Testergebnisse

GET /events → 200 OK
POST /events → 201 Created
GET /events/{id} Fehlerfall → 404 Not Found
POST /events Fehlerfall → 400 Bad Request
DELETE /events/{id} → 204 No Content
DELETE /events/{id} Fehlerfall → 404 Not Found

Alle Tests laufen erfolgreich und erfüllen die LB‑Vorgaben.

Screenshots‑Vorlagen

Screenshot 1 – GET /events
Test: GET /events
Erwartet: 200 OK
Resultat: Erfolgreich
[SCREENSHOT]

Screenshot 2 – POST /events
Test: POST /events
Erwartet: 201 Created
Resultat: Erfolgreich
[SCREENSHOT]

Screenshot 3 – Fehlerfälle
Test: GET /events/{id}
Erwartet: 404 Not Found
Resultat: Erfolgreich
[SCREENSHOT]

Test: POST /events (ungültig)
Erwartet: 400 Bad Request
Resultat: Erfolgreich
[SCREENSHOT]

Screenshot 4 – DELETE /events/{id}
Test: DELETE /events/{id}
Erwartet: 204 No Content
Resultat: Erfolgreich
[SCREENSHOT]

Test: DELETE /events/{id} (nicht existierend)
Erwartet: 404 Not Found
Resultat: Erfolgreich
[SCREENSHOT]

============================================================
ENDE DER LB‑TESTDOKUMENTATION
============================================================