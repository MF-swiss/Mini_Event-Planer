import { useState, useEffect } from "react";
import "../styles/PageOne.css";
import "../styles/Modal.css";
import LocationModal from "../components/LocationModal";
import ArtistModal from "../components/ArtistModal";

const API_URL = import.meta.env.VITE_API_URL;
const NEW_OPTION_VALUE = "__new__";

const sortByDate = (arr) =>
  [...arr].sort((a, b) => new Date(a.date) - new Date(b.date));

export default function PageOne({ onNavigate }) {
  const [events, setEvents] = useState([]);
  const [locations, setLocations] = useState([]);
  const [artists, setArtists] = useState([]);

  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState(null);

  const [validationError, setValidationError] = useState("");
  const [editValidationError, setEditValidationError] = useState("");

  // New Event fields
  const [newEventTitle, setNewEventTitle] = useState("");
  const [newEventDescription, setNewEventDescription] = useState("");
  const [newEventDate, setNewEventDate] = useState("");
  const [newLocationId, setNewLocationId] = useState("");
  const [newArtistId, setNewArtistId] = useState("");

  // Edit fields
  const [editingId, setEditingId] = useState(null);
  const [editTitle, setEditTitle] = useState("");
  const [editDescription, setEditDescription] = useState("");
  const [editDate, setEditDate] = useState("");
  const [editLocationId, setEditLocationId] = useState("");
  const [editArtistId, setEditArtistId] = useState("");

  // Modal control: which modal is open ("location" | "artist" | null)
  // and which form triggered it ("new" | "edit"), so we know where to
  // put the freshly created id once the modal saves successfully.
  const [activeModal, setActiveModal] = useState(null);
  const [modalContext, setModalContext] = useState(null);

  // Load events, locations, artists
  useEffect(() => {
    async function loadAll() {
      try {
        const [eventsRes, locationsRes, artistsRes] = await Promise.all([
          fetch(`${API_URL}/events`),
          fetch(`${API_URL}/locations`),
          fetch(`${API_URL}/artists`),
        ]);

        if (!eventsRes.ok || !locationsRes.ok || !artistsRes.ok) {
          throw new Error("Daten konnten nicht geladen werden");
        }

        const eventsData = await eventsRes.json();
        const locationsData = await locationsRes.json();
        const artistsData = await artistsRes.json();

        setEvents(sortByDate(eventsData.events || eventsData));
        setLocations(locationsData.locations || locationsData);
        setArtists(artistsData.artists || artistsData);
      } catch (err) {
        setError(err.message || "Daten konnten nicht geladen werden");
      } finally {
        setIsLoading(false);
      }
    }
    loadAll();
  }, []);

  // Location + Artist sind beides Single-Selects (1 Event -> 1 Location, 1 Artist)
  const handleLocationSelect = (value, context) => {
    if (value === NEW_OPTION_VALUE) {
      setModalContext(context);
      setActiveModal("location");
      return;
    }
    if (context === "new") setNewLocationId(value);
    else setEditLocationId(value);
  };

  const handleArtistSelect = (value, context) => {
    if (value === NEW_OPTION_VALUE) {
      setModalContext(context);
      setActiveModal("artist");
      return;
    }
    if (context === "new") setNewArtistId(value);
    else setEditArtistId(value);
  };

  const closeModal = () => {
    setActiveModal(null);
    setModalContext(null);
  };

  const handleLocationCreated = (createdLocation) => {
    setLocations((prev) => [...prev, createdLocation]);
    if (modalContext === "new") setNewLocationId(String(createdLocation.id));
    else setEditLocationId(String(createdLocation.id));
    closeModal();
  };

  const handleArtistCreated = (createdArtist) => {
    setArtists((prev) => [...prev, createdArtist]);
    if (modalContext === "new") setNewArtistId(String(createdArtist.id));
    else setEditArtistId(String(createdArtist.id));
    closeModal();
  };

  // Add Event
  const addEvent = async () => {
    if (!newEventTitle.trim() || !newEventDate || !newLocationId || !newArtistId) {
      setValidationError(
        "Bitte Pflichtfelder beachten (Titel, Datum, Location und Artist sind erforderlich)"
      );
      return;
    }

    setValidationError("");

    const newEvent = {
      title: newEventTitle,
      date: newEventDate,
      description: newEventDescription,
      locationId: Number(newLocationId),
      artistId: Number(newArtistId),
    };

    try {
      const response = await fetch(`${API_URL}/events`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(newEvent),
      });

      if (!response.ok) throw new Error("Daten konnten nicht gespeichert werden");

      const addedEvent = await response.json();
      setEvents(sortByDate([...events, addedEvent]));

      resetNewEventForm();
    } catch (err) {
      setError(err.message || "Daten konnten nicht gespeichert werden");
    }
  };

  const resetNewEventForm = () => {
    setNewEventTitle("");
    setNewEventDescription("");
    setNewEventDate("");
    setNewLocationId("");
    setNewArtistId("");
  };

  // Start Edit
  const startEdit = (event) => {
    setEditingId(event.id);
    setEditValidationError("");

    setEditTitle(event.title);
    setEditDescription(event.description);
    setEditDate(event.date);
    setEditLocationId(event.locationId ? String(event.locationId) : "");
    setEditArtistId(event.artistId ? String(event.artistId) : "");
  };

  // Save Edit
  const saveEdit = async (id) => {
    if (!editTitle.trim() || !editDate || !editLocationId || !editArtistId) {
      setEditValidationError(
        "Bitte Pflichtfelder beachten (Titel, Datum, Location und Artist sind erforderlich)"
      );
      return;
    }

    setEditValidationError("");

    const editedEvent = {
      title: editTitle,
      date: editDate,
      description: editDescription,
      locationId: Number(editLocationId),
      artistId: Number(editArtistId),
    };

    try {
      const response = await fetch(`${API_URL}/events/${id}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(editedEvent),
      });

      if (!response.ok) throw new Error("Daten konnten nicht gespeichert werden");

      const updatedEvent = await response.json();

      const updatedEvents = events.map((event) =>
        event.id === id ? updatedEvent : event
      );

      setEvents(sortByDate(updatedEvents));
      resetEditForm();
    } catch (err) {
      setError(err.message || "Daten konnten nicht gespeichert werden");
    }
  };

  const resetEditForm = () => {
    setEditingId(null);
    setEditTitle("");
    setEditDescription("");
    setEditDate("");
    setEditLocationId("");
    setEditArtistId("");
  };

  // Delete Event
  const deleteEvent = async (id) => {
    if (!window.confirm("Möchtest du dieses Event wirklich löschen?")) return;

    try {
      const response = await fetch(`${API_URL}/events/${id}`, {
        method: "DELETE",
      });

      if (!response.ok) throw new Error("Daten konnten nicht gelöscht werden");

      setEvents(events.filter((event) => event.id !== id));
    } catch (err) {
      setError(err.message || "Daten konnten nicht gelöscht werden");
    }
  };

  // EventDTO liefert nur locationId/artistId, keine verschachtelten Objekte -
  // daher hier per id in den bereits geladenen Arrays nachschlagen.
  const getLocationById = (id) => locations.find((loc) => loc.id === id);
  const getArtistById = (id) => artists.find((artist) => artist.id === id);

  if (isLoading) {
    return (
      <div>
        <h1>Events</h1>
        <p>Lade Events...</p>
      </div>
    );
  }

  if (error) {
    return (
      <div>
        <h1>Events</h1>
        <p>Fehler: {error}</p>
        <button onClick={() => window.location.reload()}>Refresh</button>
      </div>
    );
  }

  return (
    <div className="page-container">
      <section className="page-header">
        <h1>Events Verwalten</h1>
        <button className="back-button" onClick={() => onNavigate("home")}>
          ← Zurück
        </button>
      </section>

      {/* ADD EVENT */}
      <section className="add-event-section">
        <h2>Neues Event hinzufügen</h2>

        <div className="form-group">
          <input
            type="text"
            placeholder="Event Titel"
            value={newEventTitle}
            onChange={(e) => setNewEventTitle(e.target.value)}
          />

          <textarea
            className="description-textarea"
            placeholder="Beschreibung"
            rows={3}
            value={newEventDescription}
            onChange={(e) => setNewEventDescription(e.target.value)}
          />

          <input
            type="date"
            value={newEventDate}
            onChange={(e) => setNewEventDate(e.target.value)}
          />

          <select
            className="location-select"
            value={newLocationId}
            onChange={(e) => handleLocationSelect(e.target.value, "new")}
          >
            <option value="">Location wählen…</option>
            {locations.map((loc) => (
              <option key={loc.id} value={loc.id}>
                {loc.name} ({loc.city})
              </option>
            ))}
            <option value={NEW_OPTION_VALUE}>+ Neue Location erstellen</option>
          </select>

          <select
            className="artist-select"
            value={newArtistId}
            onChange={(e) => handleArtistSelect(e.target.value, "new")}
          >
            <option value="">Artist wählen…</option>
            {artists.map((artist) => (
              <option key={artist.id} value={artist.id}>
                {artist.name}
              </option>
            ))}
            <option value={NEW_OPTION_VALUE}>+ Neuen Artist erstellen</option>
          </select>

          <button className="add-button" onClick={addEvent}>
            Hinzufügen
          </button>

          {validationError && (
            <div className="error-message" style={{ color: "red", marginTop: "10px", fontWeight: "bold" }}>
              {validationError}
            </div>
          )}

          <p>*Pflichtfelder beachten: Titel, Datum, Location & Artist.</p>
        </div>
      </section>

      {/* EDIT EVENT */}
      {editingId && (
        <section className="add-event-section">
          <h2>Event bearbeiten</h2>

          <div className="form-group">
            <input
              type="text"
              placeholder="Event Titel"
              value={editTitle}
              onChange={(e) => setEditTitle(e.target.value)}
            />

            <textarea
              className="description-textarea"
              placeholder="Beschreibung"
              rows={3}
              value={editDescription}
              onChange={(e) => setEditDescription(e.target.value)}
            />

            <input
              type="date"
              value={editDate}
              onChange={(e) => setEditDate(e.target.value)}
            />

            <select
              className="location-select"
              value={editLocationId}
              onChange={(e) => handleLocationSelect(e.target.value, "edit")}
            >
              <option value="">Location wählen…</option>
              {locations.map((loc) => (
                <option key={loc.id} value={loc.id}>
                  {loc.name} ({loc.city})
                </option>
              ))}
              <option value={NEW_OPTION_VALUE}>+ Neue Location erstellen</option>
            </select>

            <select
              className="artist-select"
              value={editArtistId}
              onChange={(e) => handleArtistSelect(e.target.value, "edit")}
            >
              <option value="">Artist wählen…</option>
              {artists.map((artist) => (
                <option key={artist.id} value={artist.id}>
                  {artist.name}
                </option>
              ))}
              <option value={NEW_OPTION_VALUE}>+ Neuen Artist erstellen</option>
            </select>

            <button className="add-button" onClick={() => saveEdit(editingId)}>
              Speichern
            </button>

            <button className="delete-button" onClick={() => resetEditForm()}>
              Abbrechen
            </button>

            {editValidationError && (
              <div className="error-message" style={{ color: "red", marginTop: "10px", fontWeight: "bold" }}>
                {editValidationError}
              </div>
            )}
          </div>

          <p>*Pflichtfelder beachten: Titel, Datum, Location & Artist.</p>
        </section>
      )}

      {/* EVENT LIST */}
      <section className="events-list-section">
        <h2>Bevorstehende Events ({events.length})</h2>

        {events.length === 0 ? (
          <p className="no-events">Keine Events vorhanden</p>
        ) : (
          <ul className="events-list">
            {events.map((event) => {
              const location = getLocationById(event.locationId);
              const artist = getArtistById(event.artistId);

              return (
                <li
                  key={event.id}
                  className={`event-item ${
                    new Date(event.date) < new Date(new Date().toDateString()) ? "expired" : ""
                  }`}
                >
                  <div className="event-info">
                    <strong>{event.title}</strong>
                    <p className="event-description">{event.description}</p>

                    <span className="event-date">{event.date}</span>

                    <div className="event-location">
                      <strong>Location:</strong> {location?.name}
                      <br />
                      {location?.city}, {location?.country}
                      <br />
                      {location?.type} – Kapazität: {location?.capacity}
                    </div>

                    <div className="event-artist">
                      <strong>Artist:</strong> {artist?.name}
                      <br />
                      Genre: {artist?.genre}
                      <br />
                      Herkunft: {artist?.origin}
                    </div>
                  </div>

                  <div className="event-buttons">
                    <button className="edit-button" onClick={() => startEdit(event)}>
                      Bearbeiten
                    </button>

                    <button className="delete-button" onClick={() => deleteEvent(event.id)}>
                      Löschen
                    </button>
                  </div>
                </li>
              );
            })}
          </ul>
        )}
      </section>

      {activeModal === "location" && (
        <LocationModal onClose={closeModal} onCreated={handleLocationCreated} />
      )}
      {activeModal === "artist" && (
        <ArtistModal onClose={closeModal} onCreated={handleArtistCreated} />
      )}
    </div>
  );
}
