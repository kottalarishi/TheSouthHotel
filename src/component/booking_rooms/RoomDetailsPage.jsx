// src/component/room/RoomDetailsPage.jsx

import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';

import ApiService from '../../service/ApiService';

const RoomDetailsPage = () => {
  const navigate = useNavigate();
  const { roomId } = useParams();

  const [roomDetails, setRoomDetails] = useState(null);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState(null);

  const [checkInDate, setCheckInDate] = useState(null);
  const [checkOutDate, setCheckOutDate] = useState(null);
  const [numAdults, setNumAdults] = useState(1);
  const [numChildren, setNumChildren] = useState(0);

  const [totalPrice, setTotalPrice] = useState(0);
  const [totalGuests, setTotalGuests] = useState(1);
  const [confirmed, setConfirmed] = useState(false);

  const [showDatePicker, setShowDatePicker] = useState(false);
  const [userId, setUserId] = useState('');
  const [confirmationCode, setConfirmationCode] = useState('');
  const [showMessage, setShowMessage] = useState(false);
  const [errorMessage, setErrorMessage] = useState('');

  useEffect(() => {
    const storedUserId = localStorage.getItem("userId");
    if (storedUserId) {
      setUserId(storedUserId);
    }

    const fetchRoomDetails = async () => {
      try {
        const data = await ApiService.getRoomById(roomId);
        setRoomDetails(data.room);
      } catch (err) {
        console.error("Room fetch failed:", err);
        setError(err.response?.data?.message || 'Failed to load room details.');
      } finally {
        setIsLoading(false);
      }
    };

    fetchRoomDetails();
  }, [roomId]);

  const handleConfirmBooking = () => {
    if (!ApiService.isAuthenticated()) {
      alert('You must login first to book a room.');
      navigate('/login');
      return;
    }

    if (!checkInDate || !checkOutDate) {
      setErrorMessage('Please select check-in and check-out dates.');
      return;
    }

    const oneDay = 24 * 60 * 60 * 1000;
    const nights = Math.round((checkOutDate - checkInDate) / oneDay);

    if (nights < 1) {
      setErrorMessage("Check-out date must be at least one day after check-in.");
      return;
    }

    const guests = numAdults + numChildren;
    setTotalGuests(guests);

    const price = nights * roomDetails.roomPrice;
    setTotalPrice(price);
    setConfirmed(true);
    setErrorMessage('');
  };

  const acceptBooking = async () => {
  if (!ApiService.isAuthenticated()) {
    alert('You must login first to book a room.');
    navigate('/login');
    return;
  }

  if (!checkInDate || !checkOutDate) {
    alert("Please select check-in and check-out dates.");
    return;
  }

  const adults = parseInt(numAdults);
  const children = parseInt(numChildren);

  if (isNaN(adults) || adults < 1) {
    alert("Please enter at least one adult.");
    return;
  }

const booking = {
  checkInDate: checkInDate.toISOString(),
  checkOutDate: checkOutDate.toISOString(),
  noOfAdults: adults, // ✅ this matches the backend field name
  noOfChildren: isNaN(children) ? 0 : children,
};
  
  console.log("Booking Payload being sent:", booking); 
  try {
    const response = await ApiService.bookRoom(roomId, userId, booking);

    if (response.statusCode === 200) {
      setConfirmationCode(response.bookingConfirmationCode);
      setShowMessage(true);
      setTimeout(() => {
        setShowMessage(false);
        navigate('/rooms');
      }, 5000);
    } else {
      setErrorMessage(response.message || 'Booking failed.');
    }
  } catch (err) {
    console.error("Booking error:", err);
    setErrorMessage(err.response?.data?.message || 'Booking failed.');
  }
};


  if (isLoading) return <p>Loading room details...</p>;
  if (error) return <p className="error">{error}</p>;
  if (!roomDetails) return <p>No room found.</p>;

  return (
    <div className="room-details-booking">
      {showMessage && (
        <p className="booking-success-message">
          Booking successful! Confirmation code: {confirmationCode}.
        </p>
      )}
      {errorMessage && <p className="error-message">{errorMessage}</p>}

      <h2>{roomDetails.roomType}</h2>
      <img src={roomDetails.roomUrl} alt={roomDetails.roomType} className="room-details-image" />
      <p>Price: ₹{roomDetails.roomPrice} / night</p>
      <p>{roomDetails.roomDescription}</p>

      {roomDetails.bookingsDtos?.length > 0 && (
        <div>
          <h4>Existing Bookings:</h4>
          <ul>
            {roomDetails.bookingsDtos.map((booking, idx) => (
              <li key={booking.id || idx}>
                #{idx + 1}: {booking.checkInDate} to {booking.checkOutDate}
              </li>
            ))}
          </ul>
        </div>
      )}

      <div className="booking-controls">
        {!showDatePicker ? (
          <button onClick={() => setShowDatePicker(true)} className="book-now-button">
            Book Now
          </button>
        ) : (
          <button onClick={() => setShowDatePicker(false)} className="go-back-button">
            Cancel
          </button>
        )}
      </div>

      {showDatePicker && (
        <div className="booking-form">
          <DatePicker
            selected={checkInDate}
            onChange={(date) => setCheckInDate(date)}
            placeholderText="Check-in Date"
            className="date-picker"
          />
          <DatePicker
            selected={checkOutDate}
            onChange={(date) => setCheckOutDate(date)}
            placeholderText="Check-out Date"
            className="date-picker"
          />

          <div className="guest-inputs">
            <label>
              Adults:
              <input
                type="number"
                min={1}
                value={numAdults}
                onChange={(e) => {
                  const value = parseInt(e.target.value);
                  setNumAdults(isNaN(value) ? 1 : value);
                }}
              />
            </label>
            <label>
              Children:
              <input
                type="number"
                min={0}
                value={numChildren}
                onChange={(e) => {
                  const value = parseInt(e.target.value);
                  setNumChildren(isNaN(value) ? 0 : value);
                }}
              />
            </label>
          </div>

          <button onClick={handleConfirmBooking} className="confirm-booking">
            Confirm Booking
          </button>

          {totalPrice > 0 && (
            <div>
              <p>Total Guests: {totalGuests}</p>
              <p>Total Price: ₹{totalPrice}</p>
              <button onClick={acceptBooking} className="accept-booking" disabled={!confirmed}>
                Accept Booking
              </button>
            </div>
          )}
        </div>
      )}
    </div>
  );
};

export default RoomDetailsPage;
