import { useEffect, useState } from "react";

export default function TestStarlink() {
  const [data, setData] = useState([]);

  useEffect(() => {
    fetch("https://api.spacexdata.com/v4/starlink")
      .then(res => res.json())
      .then(json => {
        console.log("Starlink API Response:", json);
        setData(json.slice(0, 5)); // nur 5 anzeigen
      })
      .catch(err => console.error("API Fehler:", err));
  }, []);

  return (
    <div>
      <h2>Starlink API Test</h2>
      <pre>{JSON.stringify(data, null, 2)}</pre>
    </div>
  );
}
