import react from "react";
import { NavLink,useNavigate } from "react-router-dom";
import Apiservice from "../../service/ApiService";

function Navbar(){
const isAuthenticated=Apiservice.isAuthenticated();
const isAdmin=Apiservice.isAdmin();
const isUser=Apiservice.isUser();
const navigate=useNavigate();

    const handleLogout=()=>{
        const isLogout=window.confirm("Are you sure you reallt wnt to logout");
        if(isLogout){
            Apiservice.logout();
            navigate('/home')
        }
    }


    return(
            <nav className="navbar-brand">
                <div>
                        <NavLink to="/home">The south hotel</NavLink>
                </div>

                <ul className="navbar-ul">

                    <li><NavLink to="/home" className={({ isActive }) => isActive ? "active-link" : ""}>Home</NavLink></li>
                    <li><NavLink to="/rooms" className={({ isActive }) => isActive ? "active-link" : ""}>Rooms</NavLink></li>
                    <li><NavLink to="/find-booking" className={({ isActive }) => isActive ? "active-link" : ""}>Find My Bookings</NavLink></li>
{isUser && (
  <li>
    <NavLink 
      to="/profile" 
      className={({ isActive }) => isActive ? "active-link" : ""}
    >
      Profile
    </NavLink>
  </li>
)}
                    {isAdmin &&<li><NavLink to="/admin" className={({ isActive }) => isActive ? "active-link" : ""}>Admin</NavLink></li>}
                    {!isAuthenticated &&<li><NavLink to="/login" className={({ isActive }) => isActive ? "active-link" : ""}>login</NavLink></li>}
                    {!isAuthenticated &&<li><NavLink to="/register" className={({ isActive }) => isActive ? "active-link" : ""}>register</NavLink></li>}
                    {isAuthenticated &&<li onClick={handleLogout}>Logout</li>}

                    
                    

    

                </ul>
            </nav>
    )

}
export default Navbar;