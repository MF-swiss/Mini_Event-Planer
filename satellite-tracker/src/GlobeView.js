import { useEffect, useRef } from "react";
import Globe from "globe.gl";
import * as satellite from "satellite.js";

export default function GlobeView() {
  const globeRef = useRef();

  useEffect(() => {
    const globe = Globe()(globeRef.current)
      .globeImageUrl("//unpkg.com/three-globe/example/img/earth-blue-marble.jpg")
      .backgroundImageUrl("//unpkg.com/three-globe/example/img/night-sky.png");

    fetch("https://api.spacexdata.com/v4/starlink")
      .then(res => res.json())
      .then(data => {
        const orbits = [];

        data.slice(0, 20).forEach(s => {
          if (!s.spaceTrack?.TLE_LINE1) return;

          const tle1 = s.spaceTrack.TLE_LINE1;
          const tle2 = s.spaceTrack.TLE_LINE2;

          const satrec = satellite.twoline2satrec(tle1, tle2);

          const orbitPoints = [];

          for (let i = 0; i < 90; i++) {
            const time = new Date(Date.now() + i * 60 * 1000);
            const position = satellite.propagate(satrec, time).position;

            if (!position) continue;

            const gmst = satellite.gstime(time);
            const geo = satellite.eciToGeodetic(position, gmst);

            orbitPoints.push({
              lat: satellite.degreesLat(geo.latitude),
              lng: satellite.degreesLong(geo.longitude),
              alt: geo.height / 2000
            });
          }

          orbits.push(orbitPoints);
        });

        globe
          .pathsData(orbits)
          .pathPoints("points")
          .pathPointLat(d => d.lat)
          .pathPointLng(d => d.lng)
          .pathPointAltitude(d => d.alt)
          .pathColor(() => "rgba(255,255,255,0.5)");

        globe.onPointClick(sat => {
          alert(`
        Name: ${sat.name}
        Lat: ${sat.lat}
        Lng: ${sat.lng}
        Altitude: ${sat.alt}
        `);
        });
      });
  }, []);

  return (
    <div ref={globeRef} style={{ width: "100vw", height: "100vh" }} />
  );
}