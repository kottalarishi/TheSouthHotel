import React, { useEffect, useState } from "react";
import ApiService from "../../service/ApiService";

const RoomsManagement = () => {
  const [rooms, setRooms] = useState([]);
  const [error, setError] = useState("");
  const [newRoom, setNewRoom] = useState({
    roomType: "",
    roomPrice: "",
    roomDescription: "",
  });
  const [photo, setPhoto] = useState(null);

  // Fetch rooms
  const fetchRooms = async () => {
    try {
      const res = await ApiService.getAllRooms();
      console.log("Rooms response:", res);
      const roomList = Array.isArray(res.roomDtos) ? res.roomDtos : [];
      setRooms(roomList);
    } catch (err) {
      console.error("Fetch rooms failed", err);
      setError("Failed to fetch rooms");
    }
  };

  useEffect(() => {
    fetchRooms();
  }, []);

  const handleAddRoom = async () => {
    const { roomType, roomPrice, roomDescription } = newRoom;

    if (!roomType || !roomPrice || !roomDescription || !photo) {
      alert("Fill all fields and select a photo");
      return;
    }

    const formData = new FormData();
    formData.append("roomType", roomType);
    formData.append("roomPrice", roomPrice);
    formData.append("roomDescription", roomDescription);
    formData.append("photo", photo);

    try {
      await ApiService.addRoom(formData);
      alert("Room added successfully");
      setNewRoom({ roomType: "", roomPrice: "", roomDescription: "" });
      setPhoto(null);
      document.getElementById("photo").value = null;
      fetchRooms();
    } catch (err) {
      console.error("Error adding room", err);
      alert("Failed to add room");
    }
  };

  const handleDelete = async (roomId) => {
    try {
      await ApiService.deleteRoomById(roomId);
      fetchRooms();
    } catch (err) {
      alert("Failed to delete room");
    }
  };

  return (
    <div className="rooms-management" style={{ padding: "20px" }}>
      <h2>Room Management</h2>
      {error && <p style={{ color: "red" }}>{error}</p>}

      <h3>Add New Room</h3>
      <div className="add-room-form">
        <input
          type="text"
          placeholder="Room Type"
          value={newRoom.roomType}
          onChange={(e) => setNewRoom({ ...newRoom, roomType: e.target.value })}
        />
        <input
          type="number"
          placeholder="Price"
          value={newRoom.roomPrice}
          onChange={(e) => setNewRoom({ ...newRoom, roomPrice: e.target.value })}
        />
        <input
          type="text"
          placeholder="Description"
          value={newRoom.roomDescription}
          onChange={(e) => setNewRoom({ ...newRoom, roomDescription: e.target.value })}
        />
        <input
          type="file"
          id="photo"
          accept="image/*"
          onChange={(e) => setPhoto(e.target.files[0])}
        />
        <button onClick={handleAddRoom}>Add Room</button>
      </div>

      <h3>All Rooms</h3>
      {rooms.length === 0 ? (
        <p>No rooms found.</p>
      ) : (
        <div className="room-grid">
          {rooms.map((room) => (
            <div key={room.id} className="room-card">
              <img src={room.roomUrl} alt={room.roomType} className="room-image" />
              <div className="room-details">
                <h4>{room.roomType}</h4>
                <p>₹{room.roomPrice}</p>
                <p>{room.roomDescription}</p>
                <button onClick={() => handleDelete(room.id)}>Delete</button>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default RoomsManagement;
