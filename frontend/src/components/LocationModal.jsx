import { useState } from "react";
import "../styles/Modal.css";

const API_URL = import.meta.env.VITE_API_URL;

export default function LocationModal({ onClose, onCreated }) {
  const [name, setName] = useState("");
  const [city, setCity] = useState("");
  const [country, setCountry] = useState("");
  const [type, setType] = useState("");
  const [capacity, setCapacity] = useState("");
  const [description, setDescription] = useState("");
  const [error, setError] = useState("");
  const [isSaving, setIsSaving] = useState(false);

  const handleSave = async () => {
    if (!name.trim() || !city.trim()) {
      setError("Bitte Pflichtfelder beachten (Name und Stadt sind erforderlich).");
      return;
    }

    setError("");
    setIsSaving(true);

    try {
      const response = await fetch(`${API_URL}/locations`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          name,
          city,
          country,
          type,
          capacity: capacity === "" ? 0 : Number(capacity),
          description,
        }),
      });

      if (!response.ok) throw new Error("Location konnte nicht erstellt werden");

      const created = await response.json();
      onCreated(created);
    } catch (err) {
      setError(err.message || "Location konnte nicht erstellt werden");
    } finally {
      setIsSaving(false);
    }
  };

  return (
    <div className="modal-overlay" onClick={onClose}>
      <div className="modal-box" onClick={(e) => e.stopPropagation()}>
        <h3>Neue Location</h3>

        <div className="modal-form">
          {/* Pflichtfelder: Stern im Placeholder macht sofort sichtbar,
              welche Felder zwingend ausgefüllt werden müssen. */}
          <input
            type="text"
            placeholder="Name *"
            value={name}
            onChange={(e) => setName(e.target.value)}
          />
          <input
            type="text"
            placeholder="Stadt *"
            value={city}
            onChange={(e) => setCity(e.target.value)}
          />
          <input
            type="text"
            placeholder="Land"
            value={country}
            onChange={(e) => setCountry(e.target.value)}
          />
          <input
            type="text"
            placeholder="Typ"
            value={type}
            onChange={(e) => setType(e.target.value)}
          />
          <input
            type="number"
            placeholder="Kapazität"
            value={capacity}
            onChange={(e) => setCapacity(e.target.value)}
          />
          <input
            type="text"
            placeholder="Beschreibung"
            value={description}
            onChange={(e) => setDescription(e.target.value)}
          />

          {/* Hinweis auf die Pflichtfelder, konsistent mit dem
              Event-Formular in PageOne.jsx */}
          <p style={{ fontSize: "12px", color: "#8c92b0", margin: "4px 0 0" }}>
            *Pflichtfelder beachten: Name &amp; Stadt.
          </p>

          {error && <div className="modal-error">{error}</div>}

          <div className="modal-buttons">
            <button className="add-button" onClick={handleSave} disabled={isSaving}>
              {isSaving ? "Speichert…" : "Erstellen"}
            </button>
            <button className="delete-button" onClick={onClose} disabled={isSaving}>
              Abbrechen
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}