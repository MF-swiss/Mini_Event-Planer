import { useState } from "react";
import "../styles/PageOne.css";

export default function PageOne({ onNavigate }) {
  const [events, setEvents] = useState([
    { id: 1, title: "Sample Event 1", date: "2026-06-15" },
    { id: 2, title: "Sample Event 2", date: "2026-07-20" },
  ]);
  const [newEventTitle, setNewEventTitle] = useState("");
  const [newEventDate, setNewEventDate] = useState("");

  const addEvent = () => {
    if (newEventTitle.trim() && newEventDate) {
      const newEvent = {
        id: Date.now(),
        title: newEventTitle,
        date: newEventDate,
      };
      setEvents([...events, newEvent]);
      setNewEventTitle("");
      setNewEventDate("");
    }
  };

  const deleteEvent = (id) => {
    setEvents(events.filter((event) => event.id !== id));
  };

  return (
    <div className="page-container">
      <section className="page-header">
        <h1>Events Verwalten</h1>
        <button className="back-button" onClick={() => onNavigate("home")}>
          ← Zurück
        </button>
      </section>

      <section className="add-event-section">
        <h2>Neues Event hinzufügen</h2>
        <div className="form-group">
          <input
            type="text"
            placeholder="Event Titel"
            value={newEventTitle}
            onChange={(e) => setNewEventTitle(e.target.value)}
          />
          <input
            type="date"
            value={newEventDate}
            onChange={(e) => setNewEventDate(e.target.value)}
          />
          <button className="add-button" onClick={addEvent}>
            Hinzufügen
          </button>
        </div>
      </section>

      <section className="events-list-section">
        <h2>Events ({events.length})</h2>
        {events.length === 0 ? (
          <p className="no-events">Keine Events vorhanden</p>
        ) : (
          <ul className="events-list">
            {events.map((event) => (
              <li key={event.id} className="event-item">
                <div className="event-info">
                  <strong>{event.title}</strong>
                  <span className="event-date">{event.date}</span>
                </div>
                <button
                  className="delete-button"
                  onClick={() => deleteEvent(event.id)}
                >
                  Löschen
                </button>
              </li>
            ))}
          </ul>
        )}
      </section>
    </div>
  );
}