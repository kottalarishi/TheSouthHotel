import React from "react";
import { useNavigate } from "react-router-dom";
const AdminPage = () => {
  const navigate = useNavigate();

  return (
    <div className="admin-page-container">
      <div className="admin-dashboard-card">
        <h2>Admin Dashboard</h2>

        <div className="admin-button-group">
          <button onClick={() => navigate("/admin/users")}>All Users</button>
          <button onClick={() => navigate("/admin/bookings")}>Manage Bookings</button>
          <button onClick={() => navigate("/admin/rooms")}>Manage Rooms</button>
        </div>
      </div>
    </div>
  );
};

export default AdminPage;
