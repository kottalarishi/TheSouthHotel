import React from "react";
import { useNavigate } from "react-router-dom";
import ApiService from "../../service/ApiService";

const RoomResult = ({ roomSearchResults }) => {
  const navigate = useNavigate();
  const isAdmin = ApiService.isAdmin();

  return (
    <section className="room-results">
      {roomSearchResults && roomSearchResults.length > 0 && (
        <div className="room-list">
          {roomSearchResults.map((room) => (
            <div key={room.roomId} className="room-list-item">
              <div className="room-list-item-image">
                <img src={room.roomUrl} alt={room.roomType} />
              </div>
              <div className="room-details">
                <h3>{room.roomType}</h3>
                <p>Price: ₹{room.roomPrice} / night</p>
                <p>Description: {room.roomDescription}</p>
              </div>
              <div className="book-now-div">
                {room.roomId ? (
                  isAdmin ? (
                    <button
                      className="edit-room-button"
                      onClick={() => navigate(`/admin/edit-room/${room.roomId}`)}
                    >
                      Edit Room
                    </button>
                  ) : (
                    <button
                      className="book-now-button"
                      onClick={() => navigate(`/rooms/${room.roomId}`)}
                    >
                      View/Book Now
                    </button>
                  )
                ) : (
                  <p className="missing-id">Room ID missing!</p>
                )}
              </div>
            </div>
          ))}
        </div>
      )}
    </section>
  );
};

export default RoomResult;
