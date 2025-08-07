import React, { useEffect, useState } from "react";
import { Navigate, useLocation } from "react-router-dom";
import ApiService from "./ApiService";
export const ProtectedRoute = ({ element: Component }) => {
  const location = useLocation();
  const [loading, setLoading] = useState(true);
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  useEffect(() => {
    const checkAuth = () => {
      const auth = ApiService.isAuthenticated();
      setIsLoggedIn(auth);
      setLoading(false);
    };

    checkAuth();
  }, []);

  if (loading) {
    return (
      <div style={{ padding: "2rem", textAlign: "center" }}>
        <h2>Checking permissions...</h2>
      </div>
    );
  }

  return isLoggedIn ? (
    Component
  ) : (
    <Navigate to="/login" replace state={{ from: location }} />
  );
};

export const AdminRoute = ({ element: Component }) => {
  const location = useLocation();
  const [loading, setLoading] = useState(true);
  const [isAdmin, setIsAdmin] = useState(false);

  useEffect(() => {
    const checkAdmin = () => {
      const admin = ApiService.isAdmin();
      setIsAdmin(admin);
      setLoading(false);
    };

    checkAdmin();
  }, []);

  if (loading) {
    return (
      <div style={{ padding: "2rem", textAlign: "center" }}>
        <h2>Checking admin access...</h2>
      </div>
    );
  }

  return isAdmin ? (
    Component
  ) : (
    <Navigate to="/unauthorized" replace state={{ from: location }} />
  );
};
