import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import ApiService from "../../service/ApiService";
const EditProfilePage = () => {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    id: "",
    name: "",
    email: "",
    phoneNumber: "",
    password: "",
  });

  const [error, setError] = useState("");
  const [message, setMessage] = useState("");

  useEffect(() => {
    const fetchData = async () => {
      try {
        const res = await ApiService.getLoggedInUser();
        if (res && res.user) {
          setFormData({
            id: res.user.id || "",
            name: res.user.name || "",
            email: res.user.email || "",
            phoneNumber: res.user.phoneNumber || "",
            password: "",
          });
          setError("");
        } else {
          setError("User info not found");
        }
      } catch (err) {
        console.error("Fetch error:", err);
        setError("Error fetching user info");
      }
    };
    fetchData();
  }, []);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

 const handleSubmit = async (e) => {
  e.preventDefault();
  setError("");
  setMessage("");

  try {
    const updateData = {
      name: formData.name,
      email: formData.email,
      phoneNumber: formData.phoneNumber,
    };
    if (formData.password) {
      updateData.password = formData.password;
    }

    await ApiService.updateUserProfile(formData.id, updateData);

    setMessage("Profile updated successfully!");
    setError(""); // Clear any previous error
    setTimeout(() => navigate("/profile"), 1500);

  } catch (err) {
    console.error("Update error:", err);
    setError("Something went wrong!");
  }
};

return (
  <div className="center-wrapper">
    <div className="edit-profile-container">
      <h2>Edit Profile</h2>

      {error && <p className="error-text">{error}</p>}
      {message && <p className="success-text">{message}</p>}

      <form onSubmit={handleSubmit} className="edit-profile-form">
        <label>Name:</label>
        <input
          type="text"
          name="name"
          value={formData.name}
          onChange={handleChange}
          required
        />

        <label>Email:</label>
        <input
          type="email"
          name="email"
          value={formData.email}
          onChange={handleChange}
          required
        />

        <label>Phone Number:</label>
        <input
          type="text"
          name="phoneNumber"
          value={formData.phoneNumber}
          onChange={handleChange}
          required
        />

        <label>New Password (optional):</label>
        <input
          type="password"
          name="password"
          value={formData.password}
          onChange={handleChange}
        />

        <button type="submit">Update Profile</button>
      </form>
    </div>
  </div>
);
}

export default EditProfilePage;
