import React, { useEffect, useState } from "react";
import ApiService from "../../service/ApiService";

const UsersList = () => {
  const [users, setUsers] = useState([]);

  // Define fetchUsers here, accessible everywhere
 const fetchUsers = async () => {
  try {
    const data = await ApiService.getAllUsers();
    console.log("Raw users API response:", data);
    setUsers(data);  // <-- set the raw array directly here
  } catch (error) {
    console.error("Error fetching users:", error);
    setUsers([]);
  }
};


  // Call fetchUsers once on component mount
  useEffect(() => {
    fetchUsers();
  }, []);

  const handleDeleteUser = async (userId) => {
    try {
      const result = await ApiService.deleteUser(userId);
      alert(result.message || "User deleted");
      fetchUsers(); // refresh list after delete
    } catch (error) {
      alert("Error in deleting user");
      console.error(error);
    }
  };

  return (
    <div className="admin-section">
      <h2>All Registered Users</h2>
      {users.length === 0 ? (
        <p>No users found</p>
      ) : (
       <table className="users-table">
  <thead>
    <tr>
      <th>ID</th>
      <th>Name</th>
      <th>Email</th>
      <th>Phone Number</th>
      <th>Role</th>
      <th>Action</th>
    </tr>
  </thead>
  <tbody>
    {users.map((user, index) => (
      <tr key={user.id || index}>
        <td>{user.id}</td>
        <td>{user.name || "N/A"}</td>
        <td>{user.email}</td>
        <td>{user.phoneNumber}</td>
        <td>{user.role}</td>
        <td>
          <button className="delete-btn" onClick={() => handleDeleteUser(user.id)}>
            Delete
          </button>
        </td>
      </tr>
    ))}
  </tbody>
</table>

      )}
    </div>
  );
};

export default UsersList;
