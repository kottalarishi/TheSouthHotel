// src/component/auth/LoginPage.jsx
import React, { useState } from "react";
import ApiService from "../../service/ApiService";
import { useNavigate } from "react-router-dom";
import { jwtDecode } from 'jwt-decode';


const LoginPage = () => {
  const [loginRequest, setLoginRequest] = useState({ email: "", password: "" });
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const handleChange = (e) => {
    setLoginRequest({ ...loginRequest, [e.target.name]: e.target.value });
  };

 const handleSubmit = async (e) => {
  e.preventDefault();
  try {
    const response = await ApiService.loginUser(loginRequest);

    console.log("LOGIN RESPONSE:", response); // shows token, role, etc.

    const { token, role } = response;

    if (!token) {
      setError("Login failed! Token missing.");
      return;
    }

    // ✅ Store token and role
    localStorage.setItem("token", token);
    localStorage.setItem("role", role);
    localStorage.setItem("email", loginRequest.email); // optional

    // ✅ Decode JWT and store userId
    const decodedToken = jwtDecode(token);
    const userId = decodedToken.userId;
    if (userId) {
      localStorage.setItem("userId", userId);
    } else {
      console.warn("userId not found in token!");
    }

    // ✅ Navigate based on role
    if (role === "ADMIN") {
      navigate("/admin");
    } else {
      navigate("/home");
    }
  } catch (err) {
    if (err.response) {
      console.log("LOGIN ERROR RESPONSE:", err.response.data);
      setError(err.response.data.message || "Invalid username or password");
    } else {
      setError("Network or server error");
    }
  }
};


  return (
    <div className="login-page">
      <form onSubmit={handleSubmit}>
        <h2>Login</h2>
        {error && <p className="error">{error}</p>}

        <input
          type="email"
          name="email"
          placeholder="Email"
          value={loginRequest.email}
          onChange={handleChange}
          required
        />

        <input
          type="password"
          name="password"
          placeholder="Password"
          value={loginRequest.password}
          onChange={handleChange}
          required
        />

        <button type="submit">Login</button>
      </form>
    </div>
  );
};

export default LoginPage;
