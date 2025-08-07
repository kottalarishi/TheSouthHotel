import React, { useEffect, useState } from 'react';
import ApiService from '../../service/ApiService';

const ProfilePage = () => {
  const [user, setUser] = useState(null);
  const [bookings, setBookings] = useState([]);
  const [error, setError] = useState('');

  useEffect(() => {
    const fetchUserAndBookings = async () => {
      try {
        // 1. Get logged-in user
        const userResponse = await ApiService.getLoggedInUser();
        const userData = userResponse.user;
        setUser(userData);

        // 2. Get bookings using user ID
        const bookingsResponse = await ApiService.getUserBookings(userData.id);

        // 3. Fix: Extract bookingsDtos from inside user
        if (bookingsResponse.user && Array.isArray(bookingsResponse.user.bookingsDtos)) {
          setBookings(bookingsResponse.user.bookingsDtos);
        } else {
          setBookings([]);
        }
      } catch (err) {
        console.error('Error:', err);
        setError('Failed to fetch profile or bookings.');
      }
    };

    fetchUserAndBookings();
  }, []);

  return (

      
    <div className="profile-container" style={{ padding: '20px' }}>
      <h2>Profile</h2>

        <button onClick={() => window.location.href = '/edit-profile'} className="edit-profile-btn">
  Edit Profile
</button>


      {user && (
        <div className="user-info" style={{ marginBottom: '20px' }}>
          <p><strong>Welcome,</strong> {user.name}</p>
          <p><strong>Email:</strong> {user.email}</p>
          <p><strong>Phone Number:</strong> {user.phoneNumber}</p>
          <p><strong>Role:</strong> {user.role}</p>
        </div>
      )}

      <h3>My Booking History</h3>

      {bookings.length > 0 ? (
        bookings.map((booking, index) => (
          <div key={index} className="booking-card" style={{ marginBottom: '20px', border: '1px solid #ccc', padding: '15px', borderRadius: '10px' }}>
            <p><strong>Confirmation Code:</strong> {booking.bookingConfirmationCode}</p>
            <p><strong>Check-in:</strong> {booking.checkInDate}</p>
            <p><strong>Check-out:</strong> {booking.checkOutDate}</p>
            <p><strong>Adults:</strong> {booking.noOfAdults}</p>
            <p><strong>Children:</strong> {booking.noOfChildren}</p>

            {booking.roomDto && (
              <div>
                <p><strong>Room Type:</strong> {booking.roomDto.roomType}</p>
                <img
                  src={booking.roomDto.roomUrl}
                  alt={booking.roomDto.roomType}
                  style={{ width: '200px', borderRadius: '10px' }}
                />
              </div>
            )}
          </div>
        ))
      ) : (
        <p>No bookings found.</p>
      )}

      {error && <p className="error" style={{ color: 'red' }}>{error}</p>}
    </div>
  );
};

export default ProfilePage;
