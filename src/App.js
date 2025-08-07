import './App.css';
import React from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import './App.css';


import Navbar from './component/common/Navbar';
import FooterComponent from './component/common/Footer';
import Homepage from "./component/home/Homepage";
import AllRoomsPage from './component/booking_rooms/AllRooms';
import FindBookingPage from './component/booking_rooms/FindBookingPage';
import RoomDetailsPage from './component/booking_rooms/RoomDetailsPage';
import RegisterPage from "./component/auth/RegisterPage";
import LoginPage from './component/auth/LoginPage';
import ProfilePage from './component/profile/ProfilePage';
import FindMyBookingPage from './component/booking_rooms/FindBookingPage';
import EditProfilePage from './component/profile/EditProfilePage';
import AdminPage from './component/admin/AdminPage';
import UsersList from './component/admin/UserList';
import BookingsList from './component/admin/BookingList';
import RoomsManagement from './component/admin/RoomsManagement';

import { ProtectedRoute,AdminRoute } from './service/guard';


function App() {
  return (
    <BrowserRouter>
      <div className="App">
        <Navbar />
        <div className="content">
          <Routes>
            <Route path="/" element={<Homepage />} />
            <Route path="/home" element={<Homepage />} />
            <Route path="/rooms" element={<AllRoomsPage />} />
            <Route path="/find-booking" element={<FindBookingPage />} />
            <Route path="/rooms/:roomId" element={<RoomDetailsPage />} />
            <Route path="/register" element={<RegisterPage />} />
            <Route path="/login" element={<LoginPage />} />
            <Route path="/profile"
  element={<ProtectedRoute element={<ProfilePage />} />}/>  
           
  <Route path="/admin" element={<AdminRoute element={<AdminPage />} />} />
 
          <Route path="/find-booking" element={<FindMyBookingPage />} />
            <Route path="/edit-profile" element={<EditProfilePage />} />

            <Route path="/admin/users" element={<AdminRoute element={<UsersList />} />} />
<Route path="/admin/bookings" element={<AdminRoute element={<BookingsList />} />} />
<Route path="/admin/rooms" element={<AdminRoute element={<RoomsManagement/>} />} />

          </Routes>
        </div>
        <FooterComponent />
      </div>
    </BrowserRouter>
  );
}

export default App;
