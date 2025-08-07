import React, { useState } from "react";
import RoomSearch from "../common/RoomSearch";
import RoomResult from "../common/RoomResult"; // ✅ Added this import
import { useNavigate } from "react-router-dom";

const Homepage = () => {
  const [roomSearchResults, setRoomSearchResults] = useState([]);
   const navigate = useNavigate();

  const handleSearchResults = (results) => {
    setRoomSearchResults(results);
  };

  return (
    <div className="home">
      {/* Header banner */}
      <section>
        <header className="header-banner">
          <img
            src="/assets/images/hotel-253944.jpg"
            alt="The South Hotel"
            className="header-image"
          />
          <div className="overlay"></div>
          <div>
            <h1>
              Welcome to <span className="south-color">The South Hotel</span>
            </h1>
            <br />
            <h3>Step into a heaven of Comfort and care</h3>
          </div>
        </header>
      </section>

      {/* Search bar */}
      <RoomSearch handleSearchResults={handleSearchResults} />

      {/* ✅ Display search results */}
      <RoomResult roomSearchResults={roomSearchResults} />

      {/* View all rooms link */}
    

<h4>
  <button className="view-all-rooms" onClick={() => navigate("/rooms")}>
    All Rooms
  </button>
</h4>

      {/* Hotel services */}
      <h2>
        Services at <span className="south-color">The South Hotel</span>
      </h2>

      <section className="service-section">
        <div className="service-card">
          <img src="/assets/images/ac.jpg" alt="Air Conditioner" />
          <div className="service-details">
            <h3 className="service-title">Air Conditioning</h3>
            <p className="service-description">
              A cool and cozy air-conditioned room, perfect for relaxing in
              comfort
            </p>
          </div>
        </div>

        <div className="service-card">
          <img src="/assets/images/mini.jpg" alt="mini bar" />
          <div className="service-details">
            <h3 className="service-title">Mini Bar</h3>
            <p className="service-description">
              Indulge in a premium in-room mini bar, curated with fine
              beverages and gourmet snacks for your ultimate comfort
            </p>
          </div>
        </div>

        <div className="service-card">
          <img src="/assets/images/park.jpg" alt="park" />
          <div className="service-details">
            <h3 className="service-title">Parking</h3>
            <p className="service-description">
              Experience the convenience of secure, valet-assisted parking,
              ensuring your journey begins and ends in comfort
            </p>
          </div>
        </div>

        <div className="service-card">
          <img src="/assets/images/wifi.jpg" alt="wifi" />
          <div className="service-details">
            <h3 className="service-title">WiFi</h3>
            <p className="service-description">
              Stay seamlessly connected with our high-speed, hotel-wide Wi-Fi —
              designed for work, streaming, and everything in between
            </p>
          </div>
        </div>
      </section>
    </div>
  );
};

export default Homepage;
