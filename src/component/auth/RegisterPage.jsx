// src/component/auth/RegisterPage.jsx
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import ApiService from "../../service/ApiService";


const RegisterPage = () => {
  const navigate = useNavigate();

  // Rename fullName → name to match backend
  const [registration, setRegistration] = useState({
    name: "",
    email: "",
    password: "",
    phoneNumber: "",
    role: "USER"
  });

  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");

  const handleChange = (e) => {
    setRegistration({
      ...registration,
      [e.target.name]: e.target.value,
    });
  };

  const handleRegister = async (e) => {
    e.preventDefault();
    try {
      // Send { name, email, password, phoneNumber, role }
      await ApiService.registerUser(registration);
      setSuccess("Registration successful! Redirecting to login…");
      setError("");
      setTimeout(() => navigate("/login"), 2000);
    } catch (err) {
      setError(err.response?.data?.message || "Registration failed.");
      setSuccess("");
    }
  };

  return (
    <div className="auth-form-container">
      <form onSubmit={handleRegister} className="auth-form">
        <h2>Create an Account</h2>

        {error && <div className="error-msg">{error}</div>}
        {success && <div className="success-msg">{success}</div>}

        <input
          type="text"
          name="name"               // <-- changed here
          placeholder="Full Name"
          value={registration.name}
          onChange={handleChange}
          required
        />

        <input
          type="email"
          name="email"
          placeholder="Email"
          value={registration.email}
          onChange={handleChange}
          required
        />

        <input
          type="password"
          name="password"
          placeholder="Password"
          value={registration.password}
          onChange={handleChange}
          required
        />

        <input
          type="text"
          name="phoneNumber"
          placeholder="Phone Number"
          value={registration.phoneNumber}
          onChange={handleChange}
        />

        <button type="submit">Register</button>

        <p className="auth-switch">
          Already have an account?{" "}
          <span onClick={() => navigate("/login")} className="auth-link">
            Login
          </span>
        </p>
      </form>
    </div>
  );
};

export default RegisterPage;
