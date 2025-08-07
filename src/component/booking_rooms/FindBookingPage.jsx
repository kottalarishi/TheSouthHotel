import React, { useState } from 'react';
import ApiService from '../../service/ApiService';

const FindMyBookingPage = () => {
  const [confirmationCode, setConfirmationCode] = useState('');
  const [booking, setBooking] = useState(null);
  const [errorMessage, setErrorMessage] = useState('');

  const handleSearch = async () => {
  setErrorMessage('');
  setBooking(null);

  if (!confirmationCode.trim()) {
    setErrorMessage('Please enter a confirmation code.');
    return;
  }

  try {
    const response = await ApiService.getBookingsByConfirmationCode(confirmationCode.trim());

    if (response && response.bookings) {
      setBooking(response.bookings);
    } else {
      setErrorMessage('Booking not found.');
    }
  } catch (error) {
    console.error('Error fetching booking:', error);
    setErrorMessage(
      error.response?.data?.message || 'Failed to fetch booking. Please try again.'
    );
  }
};

  return (
    <div className="find-booking-container">
      <h2>Find My Booking</h2>

      <div className="search-box">
        <input
          type="text"
          value={confirmationCode}
          onChange={(e) => setConfirmationCode(e.target.value)}
          placeholder="Enter your confirmation code"
        />
        <button onClick={handleSearch}>Search</button>
      </div>

      {errorMessage && <p className="error-message">{errorMessage}</p>}

      {booking && (
        <div className="booking-details">
          <h3>Booking Details</h3>
          <p><strong>Confirmation Code:</strong> {booking.bookingConfirmationCode}</p>
          <p><strong>Check-in:</strong> {booking.checkInDate}</p>
          <p><strong>Check-out:</strong> {booking.checkOutDate}</p>
          <p><strong>No. of Adults:</strong> {booking.noOfAdults}</p>
          <p><strong>No. of Children:</strong> {booking.noOfChildren}</p>

          {booking.room && (
            <div className="room-details">
              <h4>Room Info</h4>
              <p><strong>Type:</strong> {booking.room.roomType}</p>
              <p><strong>Description:</strong> {booking.room.roomDescription}</p>
              <img
                src={booking.room.roomUrl}
                alt={booking.room.roomType}
                className="room-image"
              />
              <p><strong>Price:</strong> ₹{booking.room.roomPrice}</p>
            </div>
          )}
        </div>
      )}
    </div>
  );
};

export default FindMyBookingPage;
