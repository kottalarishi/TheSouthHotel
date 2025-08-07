import React, { useState, useEffect } from "react";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import ApiService from "../../service/ApiService";

const RoomSearch = ({ handleSearchResults }) => {
  const [startDate, setStartDate] = useState(null);
  const [endDate, setEndDate] = useState(null);
  const [roomType, setRoomType] = useState('');
  const [roomTypes, setRoomTypes] = useState([]);
  const [error, setError] = useState('');

  useEffect(() => {
    const fetchRoomTypes = async () => {
      try {
        const types = await ApiService.getRoomTypes();
        console.log("Fetched room types: ", types);
        setRoomTypes(types);
      } catch (err) {
        console.log("Error fetching room types:", err.message);
      }
    };

    fetchRoomTypes();
  }, []);

  const showError = (message, timeOut = 5000) => {
    setError(message);
    setTimeout(() => {
      setError('');
    }, timeOut);
  };

  const handleInternalSearch = async () => {
    if (!startDate || !endDate || !roomType) {
      showError("Please fill all fields before searching");
      return;
    }

    try {
      const formattedStartDate = startDate.toISOString().split('T')[0];
      const formattedEndDate = endDate.toISOString().split('T')[0];

      const response = await ApiService.getAllAvailableRoomsByDateAndType(
        formattedStartDate,
        formattedEndDate,
        roomType
      );

      if (response.statusCode === 200) {
        if (!response.roomDtos || response.roomDtos.length === 0) {
          showError("No rooms available for selected date and type");
          return;
        }

        handleSearchResults(response.roomDtos);
        setError('');
      } else {
        console.log("Unexpected response:", response);
        showError("Unexpected response from server");
      }
    } catch (err) {
      showError(err?.response?.data?.message || "Error fetching rooms");
    }
  };

  return (
    <section>
      <div className="search-container">

        <div className="search-field">
          <label>Check-in Date</label>
          <DatePicker
            selected={startDate}
            onChange={(date) => setStartDate(date)}
            dateFormat="dd/MM/yyyy"
            placeholderText="Select Check-in date"
          />
        </div>

        <div className="search-field">
          <label>Check-out Date</label>
          <DatePicker
            selected={endDate}
            onChange={(date) => setEndDate(date)}
            dateFormat="dd/MM/yyyy"
            placeholderText="Select Check-out date"
          />
        </div>

        <div className="search-field">
          <label>Room Type</label>
          <select value={roomType} onChange={(e) => setRoomType(e.target.value)}>
            <option disabled value="">Select Room Type</option>
            {roomTypes.map((type) => (
              <option key={type} value={type}>{type}</option>
            ))}
          </select>
        </div>

        <button className="home-search-button" onClick={handleInternalSearch}>
          Search Rooms
        </button>

        {error && <p className="error-message">{error}</p>}

      </div>
    </section>
  );
};

export default RoomSearch;
