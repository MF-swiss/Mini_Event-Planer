import { useState } from "react";
import "../styles/Modal.css";

const API_URL = import.meta.env.VITE_API_URL;

export default function ArtistModal({ onClose, onCreated }) {
  const [name, setName] = useState("");
  const [genre, setGenre] = useState("");
  const [origin, setOrigin] = useState("");
  const [experience, setExperience] = useState("");
  const [description, setDescription] = useState("");
  const [error, setError] = useState("");
  const [isSaving, setIsSaving] = useState(false);

  const handleSave = async () => {
    if (!name.trim()) {
      setError("Bitte Name angeben.");
      return;
    }

    setError("");
    setIsSaving(true);

    try {
      const response = await fetch(`${API_URL}/artists`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          name,
          genre,
          origin,
          experience,
          description,
        }),
      });

      if (!response.ok) throw new Error("Artist konnte nicht erstellt werden");

      const created = await response.json();
      onCreated(created);
    } catch (err) {
      setError(err.message || "Artist konnte nicht erstellt werden");
    } finally {
      setIsSaving(false);
    }
  };

  return (
    <div className="modal-overlay" onClick={onClose}>
      <div className="modal-box" onClick={(e) => e.stopPropagation()}>
        <h3>Neuer Artist</h3>

        <div className="modal-form">
          <input
            type="text"
            placeholder="Name"
            value={name}
            onChange={(e) => setName(e.target.value)}
          />
          <input
            type="text"
            placeholder="Genre"
            value={genre}
            onChange={(e) => setGenre(e.target.value)}
          />
          <input
            type="text"
            placeholder="Herkunft"
            value={origin}
            onChange={(e) => setOrigin(e.target.value)}
          />
          <input
            type="text"
            placeholder="Erfahrung"
            value={experience}
            onChange={(e) => setExperience(e.target.value)}
          />
          <input
            type="text"
            placeholder="Beschreibung"
            value={description}
            onChange={(e) => setDescription(e.target.value)}
          />

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
