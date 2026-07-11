# 📋 07B \- LB-Vorbereitung \- Bewertung & Nachweis

---

Dieses Dokument ist deine **Landkarte zur LB-Note**. Es sagt dir für jeden Bewertungspunkt ganz konkret: *was muss in deinem Projekt sichtbar sein*, und *in welchem Block haben wir das gemacht*. Wenn du diese Liste abarbeitest, hast du eine vollständige, vorgaben-konforme LB. 

---

## 🎯 Das eine Prinzip: das WIE ist vorgegeben, das WAS ist frei

* **Frei wählbar ist deine Domäne.** Manga-Bibliothek, Webshop, Messaging-App, Rezept-Sammlung, egal. Bau, was dich interessiert.

* **Vorgegeben sind die Muster.** DTOs, die 3-Schicht-Architektur, Bean Validation, der GlobalExceptionHandler, mindestens eine Entity-Beziehung und die drei Test-Typen sind **Pflicht**, egal welche Domäne du wählst.

💡 **Das WISS-Quiz ist deine Referenz.** Jedes Muster, das hier verlangt wird, hast du im Quiz-Backend (und im Webshop-Beispiel aus Block 07A) schon gebaut. Du darfst diese Strukturen **1:1 übernehmen** und nur die Domäne austauschen. Das ist ausdrücklich erlaubt und der schnellste Weg zu einer guten Note. Wer stattdessen ein völlig anderes Konstrukt abliefert, verliert die Punkte für genau die Muster, die hier verlangt werden.

---

## 

## 🧭 So liest du die Punkteliste

Jede Position wird wie gewohnt bewertet — **fehlt / teilweise / vollständig** — zählt aber je nach Wichtigkeit unterschiedlich viel. Es gibt drei Gewichtsklassen:

| Gewicht | vollständig | teilweise | fehlt |
| :---- | :---- | :---- | :---- |
| 🟥 **Kritisch** | 6 | 3 | 0 |
| 🟧 **Wichtig** | 4 | 2 | 0 |
| 🟦 **Basis** | 2 | 1 | 0 |

Die Note berechnet sich linear (`erreichte Punkte × 5 / Maximalpunkte + 1`):

Note \= (erreichte Punkte × 5 / 100\) \+ 1

Maximal erreichbar sind **100 Punkte** (A: 16, B: 28, C: 56). In der Spalte **Gewicht** siehst du je Position, wie viel sie maximal zählt. Die Spalte **“So erfüllst du es vollständig”** sagt, was dafür sichtbar sein muss.

## A \- Allgemein (16 Punkte)

| \# | Position | Gewicht | So erfüllst du es vollständig | Block |
| ----- | ----- | ----- | ----- | ----- |
| A1 | Komplexität angemessen (mind. wie WISS-Quiz) | 🟥 6 | Dein Datenmodell hat **mindestens eine echte Beziehung** (`@OneToMany`/`@ManyToOne`) zwischen zwei Entities, und du bietest **vollständiges CRUD** über mindestens eine Haupt-Entity. Vorlage: `Order`/`OrderItem`. | 07A |
| A2 | Nur behandelter Stack | 🟦 2 | Du nutzt **ausschliesslich die im Kurs behandelten Techniken** (Spring Boot, JPA, DTO/Mapper, Bean Validation, JUnit/Mockito). **Kein Lombok**, keine unbesprochenen Libraries oder Muster. | alle |
| A3 | Abgabe vollständig & lauffähig | 🟧 4 | ZIP/Repo enthält Backend \+ Doku-PDF, lässt sich ohne grossen Aufwand importieren und **starten**. | 01A/B |
| A4 | Positiver Gesamteindruck | 🟦 2 | Projekt und Doku wirken sorgfältig: konsistente Benennung, aufgeräumte Struktur, keine Leichen im Code. | — |
| A5 | GitHub-Repo & Commits | 🟦 2 | Code liegt in einem **GitHub-Repository** mit **mehreren Commits** (committe nach jedem Block); Abgabe als Repo. Kein einzelner Last-Minute-Commit. | 07/alle |

---

## B \- Projektdokumentation (28 Punkte)

| \# | Position | Gewicht  | So erfüllst du es vollständig  | Block  |
| :---- | :---- | :---- | :---- | :---- |
| B1 | Format | 🟦 2 | Doku als **PDF** (oder GitHub-Markdown).  | —  |
| B2 | Projektidee | 🟦 2 | Halbe A4-Seite Elevator-Pitch: welcher Prozess wird abgebildet, für wen.  | LB-Vorb.  |
| B3 | Anforderungs-katalog | 🟧 4 | Mindestens 3 User Stories mit Akzeptanzkriterien.  | LB-Vorb.  |
| B4 | Klassendia-gramm | 🟧 4 | Modell-Klassen mit Attributen **und Beziehungen** \- deine `@OneToMany` ist im Diagramm sichtbar. (draw.io empfohlen)  | 07A  |
| B5 | Ablauf je User Story | 🟧 4 | Für jede zentrale User Story der **Ablauf als Folge von API-Calls**, z. B. *Bestellung anlegen → `POST /api/orders` → `GET /api/orders/{id}`*.  | 04A  |
| B6 | Datentypen je Endpoint | 🟧 4 | Pro Haupt-Endpoint ein **Beispiel-Request und \-Response als JSON**, zeigt den Aufbau deiner Datentypen.  | 04A  |
| B7 | Testplan | 🟦 2 | **5 sinnvolle manuelle Testfälle** (Insomnia) mit erwartetem Ergebnis.  | 04B/06A  |
| B8 | Test Durchfüh-rung | 🟦 2 | Ergebnisse der 5 Testfälle dokumentiert (Soll/Ist).  | 06A  |
| B9 | Installations-anleitung | 🟦 2 | Schritt für Schritt: DB/Docker starten, Projekt bauen, App starten — eine Lehrperson kommt **ohne Rückfragen** zum Laufen.  | 01A/B  |
| B10 | Hilfestellungen & Quellen | 🟦 2 | Alle Hilfen (Mitlernende, Internet, **KI**) **deklariert**, massvoll eingesetzt.  | —  |

💡 **B5 und B6 ersetzen das frühere “Storyboard” und “Screen Mockups”.** Deine LB ist ein reines Backend ohne Oberfläche, Bildschirm-Mockups ergeben hier keinen Sinn. Stattdessen dokumentierst du den **Ablauf deiner API-Aufrufe** und die **Form deiner Daten (JSON)**. Das ist die ehrliche Backend-Variante derselben Idee: zeigen, *wie* deine Anwendung benutzt wird und *welche* Daten fliessen.

---

## C \- Implementierung REST-API (56 Punkte)

| \# | Position | Gewicht | So erfüllst du es vollständig | Block |
| :---- | :---- | :---- | :---- | :---- |
| C1 | Import ohne Anpassungen | 🟦 2 | Projekt lässt sich direkt in IntelliJ importieren, Maven löst alle Dependencies. | 01A |
| C2 | Baut ohne Kompilierfehler | 🟧 4 | Build (IDE oder `mvn clean install`) läuft fehlerfrei durch. | 01A |
| C3 | 3-Schichten-Architektur | 🟥 6 | **Controller → Service → Repository** sauber getrennt. **Keine Geschäftslogik und keine DB-Zugriffe im Controller.** | 02A/03 |
| C4 | DTO statt Entity | 🟥 6 | Nach aussen gehen **DTOs** (über einen Mapper), **nie das Entity direkt**, auch bei Beziehungen (keine Endlos-Rekursion). | 04A/07A |
| C5 | Endpoint: Laden | 🟧 4 | **GET** liefert Daten als DTO (Liste **und** Einzel per id). | 03B/04A |
| C6 | Endpoint: Erfassen | 🟧 4 | **POST** nimmt ein **Form-DTO mit `@Valid`** entgegen und legt einen Datensatz an. | 04A/06A |
| C7 | Endpoint: Editieren | 🟧 4 | **PUT** (id aus URL) aktualisiert einen bestehenden Datensatz. | 04B |
| C8 | Endpoint: Löschen | 🟧 4 | **DELETE** entfernt einen Datensatz. | 04B |
| C9 | Validierung | 🟧 4 | **Bean Validation** am Form-DTO (`@NotBlank`, `@Positive`, …). Ungültige Daten führen zu **wohlgeformten Fehlermeldungen**, nicht zu einem Absturz. | 06A |
| C10 | Exceptions sauber gehandelt | 🟧 4 | Eigene Exception \+ **`@RestControllerAdvice` GlobalExceptionHandler** \+ **ErrorResponse-DTO**. Die App stürzt nie ab, kein stilles try/catch, das Fehler schluckt. | 06A |
| C11 | Persistenz in der DB | 🟥 6 | Daten liegen in **PostgreSQL (Docker)** über JPA-`@Entity`. **Nicht** H2, nicht in-memory, nicht in einer JSON-Datei. | 01B/05A |
| C12 | JavaDoc | 🟦 2 | Sinnvolle JavaDoc-Kommentare an allen Klassen, **ohne** getter/setter. | 06B |
| C13 | Unit-Tests (5, gelernte Typen) | 🟧 4 | **5 sinnvolle Tests** mit den gelernten Typen: Repository (`@DataJpaTest`), Service (reines Mockito), Controller (`@WebMvcTest` \+ `@MockitoBean`). Keine trivialen `assertEquals(1, 1)`. | 06B |
| C14 | Tests ausführbar | 🟦 2 | Alle Tests laufen grün durch. | 06B |

## ---

## ✅ Deine Abgabe-Checkliste

Hak das ab, bevor du abgibst. Wenn alles steht, hast du die Vorgaben erfüllt.

**Datenmodell & Architektur**

- [ ] Mindestens eine `@OneToMany`/`@ManyToOne`\-Beziehung im Modell (im Klassendiagramm sichtbar)  
- [ ] Controller → Service → Repository sauber getrennt, keine Logik im Controller  
- [ ] DTOs nach aussen (über Mapper), nie das Entity direkt

**Endpoints (vollständiges CRUD)**

- [ ] GET (Liste \+ Einzel), POST (Form-DTO \+ `@Valid`), PUT, DELETE

**Robustheit**

- [ ] Bean Validation am Form-DTO  
- [ ] GlobalExceptionHandler \+ ErrorResponse, App stürzt nie ab  
- [ ] Daten persistent in PostgreSQL (Docker)

**Tests & Code-Doku**

- [ ] 5 Tests (Repository-, Service-, Controller-Typ), alle grün  
- [ ] JavaDoc an allen Klassen (ohne getter/setter)

**Dokumentation (PDF)**

- [ ] Projektidee, 3 User Stories mit Akzeptanzkriterien  
- [ ] Klassendiagramm **mit Beziehung**  
- [ ] API-Ablauf je User Story \+ Beispiel-JSON je Endpoint  
- [ ] Testplan (5 Fälle) \+ dokumentierte Durchführung  
- [ ] Installationsanleitung  
- [ ] Quellen & KI-Nutzung deklariert

**Stack**

- [ ] Nur der im Kurs behandelte Stack, kein Lombok, keine unbesprochenen Libraries

**Git & Abgabe**

- [ ] Code liegt in einem GitHub-Repository, nach jedem Block committet und gepusht  
- [ ] Abgabe als Repository oder als ZIP nach Vorgabe

---

## **⚠️ Wo am meisten Punkte verloren gehen**

Ehrlich, damit du es vermeidest. Das sind die typischen Stellen, an denen Projekte Punkte liegen lassen, oft genau dann, wenn jemand **nicht** dem WISS-Quiz-Muster folgt. Die mit 🟥 markierten zählen am meisten (6 Punkte):

* 🟥 **Alles im Controller** statt Controller → Service → Repository (kostet C3).  
* 🟥 **Das Entity direkt zurückgeben** statt eines DTO (kostet C4, führt bei Beziehungen ausserdem zur Endlos-Rekursion).  
* 🟥 **Keine echte Beziehung** im Modell, nur eine flache Entity (kostet A1, und B4).  
* 🟥 **Keine richtige Datenbank** (JSON-Datei oder in-memory statt PostgreSQL) (kostet C11).  
* 🟧 **Exceptions still schlucken** mit try/catch statt einem GlobalExceptionHandler (kostet C10).  
* 🟧 **Triviale Tests**, die nichts prüfen, oder gar keine der gelernten Test-Typen (kostet C13, ausserdem C14).  
* 🟦 **Fremde/unbesprochene Libraries oder Muster**, die wir im Kurs nicht behandelt haben (kostet A2).

---

## 🤖 KI & fremde Hilfe

In diesem Modul sind **KI-Assistenten nicht Teil des gelehrten Vorgehens**. Du darfst Hilfe nutzen (Mitlernende, Internet, Doku) — aber:

* **Jede Hilfe muss deklariert werden** (Kapitel “Hilfestellungen”). Das gilt auch für KI. Undeklarierte fremde Lösungen gelten als **Plagiat (Note 1\)**.

* Es zählt nur, was **den im Kurs behandelten Techniken** entspricht (Position A2). Lösungen mit unbesprochenen Bibliotheken oder Mustern bringen keine Punkte, auch wenn sie “funktionieren”.

* Faustregel: Wenn du es nicht selbst erklären könntest, gehört es nicht ungeprüft in deine Abgabe.

---

## 🔁 Git: committen & sichern

Lege deinen Code von Anfang an in einem **GitHub-Repository** ab und **committe \+ pushe nach jedem Block**. Das hat zwei handfeste Gründe:

* **Versicherung.** In den LB-Vorgaben der WISS steht: *“Wem kurz vor der Abgabe der Laptop verstirbt … und kein aktuelles GIT-Repository vorweist, muss nicht auf Gnade hoffen.”* Ein gepushtes Repo ist dein Backup gegen Datenverlust.

* **Nachweis echter Arbeit.** Eine über die Blöcke verteilte Commit-Historie zeigt, dass dein Projekt schrittweise gewachsen ist. Ein einziger Riesen-Commit in der Nacht vor der Abgabe wirkt dagegen wenig glaubwürdig.

💡 **Praxis:** Am Ende jedes Blocks ein Commit mit einer kurzen, sprechenden Message (z. B. `feat: CRUD-Endpoints für Order`), dann `push`. So hast du nie mehr als einen Block Arbeit zu verlieren.

