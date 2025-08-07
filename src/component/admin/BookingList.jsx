import React, { useEffect, useState } from "react";
import ApiService from "../../service/ApiService";

const AdminBookingsPage = () => {
  const [bookings, setBookings] = useState([]);

  useEffect(() => {
    fetchBookings();
  }, []);

  const fetchBookings = async () => {
    try {
      const response = await ApiService.getAllBookings(); // { statusCode, message, data: [...] }
      setBookings(response.data || []);
    } catch (error) {
      console.error("Error fetching bookings:", error);
    }
  };

  const handleDelete = async (bookingId) => {
    if (!window.confirm("Are you sure you want to delete this booking?")) return;
    try {
      await ApiService.deleteBooking(bookingId);
      setBookings((prev) => prev.filter((b) => b.id !== bookingId));
    } catch (error) {
      console.error("Error deleting booking:", error);
    }
  };

  const hasUser = bookings.some((b) => b.userDto);
  const hasRoom = bookings.some((b) => b.roomDto);

  return (
    <div className="p-6">
      <h2 className="text-2xl font-bold text-center mb-6">All Bookings</h2>

      <div className="overflow-x-auto shadow-lg rounded-lg">
        <table className="min-w-full bg-white border border-gray-200 text-sm text-left">
          <thead className="bg-gray-100 text-gray-700 uppercase tracking-wide">
            <tr>
              <th className="px-4 py-3 border-b">Booking ID</th>
              {hasUser && <th className="px-4 py-3 border-b">User ID</th>}
              {hasUser && <th className="px-4 py-3 border-b">User Email</th>}
              {hasRoom && <th className="px-4 py-3 border-b">Room ID</th>}
              {hasRoom && <th className="px-4 py-3 border-b">Room Type</th>}
              <th className="px-4 py-3 border-b">Check-in</th>
              <th className="px-4 py-3 border-b">Check-out</th>
              <th className="px-4 py-3 border-b">Guests</th>
              <th className="px-4 py-3 border-b">Actions</th>
            </tr>
          </thead>
          <tbody>
            {bookings.length === 0 ? (
              <tr>
                <td colSpan="9" className="text-center py-4 text-gray-500">
                  No bookings found
                </td>
              </tr>
            ) : (
              bookings.map((booking) => (
                <tr key={booking.id} className="hover:bg-gray-50">
                  <td className="px-4 py-2 border-b">{booking.id}</td>
                  {hasUser && <td className="px-4 py-2 border-b">{booking.userDto?.id}</td>}
                  {hasUser && <td className="px-4 py-2 border-b">{booking.userDto?.email}</td>}
                  {hasRoom && <td className="px-4 py-2 border-b">{booking.roomDto?.id}</td>}
                  {hasRoom && <td className="px-4 py-2 border-b">{booking.roomDto?.roomType}</td>}
                  <td className="px-4 py-2 border-b">{booking.checkInDate}</td>
                  <td className="px-4 py-2 border-b">{booking.checkOutDate}</td>
                  <td className="px-4 py-2 border-b">{booking.totalNoOfGuest}</td>
                  <td className="px-4 py-2 border-b">
                    <button
                      onClick={() => handleDelete(booking.id)}
                      className="bg-red-500 text-white px-3 py-1 rounded hover:bg-red-600 transition"
                    >
                      Delete
                    </button>
                  </td>
                </tr>
              ))
            )}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default AdminBookingsPage;
