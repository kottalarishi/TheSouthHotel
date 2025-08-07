import axios from "axios";


export default class ApiService {
    static Base_Url = "http://localhost:8080";

    static getHeader() {
        const token = localStorage.getItem("token");
        return {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json",
        };
    }

    // --------- AUTH ---------

    static async registerUser(registration) {
        const response = await axios.post(`${this.Base_Url}/auth/register`, registration);
        return response.data;
    }

    static async loginUser(loginDetails) {
        const response = await axios.post(`${this.Base_Url}/auth/login`, loginDetails);
        return response.data;
    }

    static logout() {
        localStorage.removeItem("token");
        localStorage.removeItem("role");
    }

    static isAuthenticated() {
        return !!localStorage.getItem("token");
    }

    static isAdmin() {
        return localStorage.getItem("role") === "ADMIN";
    }

    static isUser() {
        return localStorage.getItem("role") === "USER";
    }

    // --------- USER ---------

  static async getAllUsers() {
  const response = await axios.get(`${this.Base_Url}/users/all`, {
    headers: this.getHeader(),
  });

  console.log("GET /users/all response:", response.data);

  // Use 'userDtos' instead of 'users'
  return Array.isArray(response.data.userDtos) ? response.data.userDtos : [];
}

 static async getLoggedInUser() {
  const response = await axios.get(`${this.Base_Url}/users/getLoggedInprofile`, {
    headers: this.getHeader(),
  });
  return response.data;
}



static async getUserBookings(userId) {
  const response = await axios.get(`${this.Base_Url}/users/getUserBookings/${userId}`, {
    headers: this.getHeader(),
  });
  return response.data; // Return full response including .user.bookingsDtos
}

   static updateUserProfile(userId, userData) {
  return axios.put(`${this.Base_Url}/users/update/${userId}`, userData, {
    headers: {
      Authorization: `Bearer ${localStorage.getItem('token')}`,
    },
  });
}




    static async deleteUser(userId) {
        const response = await axios.delete(`${this.Base_Url}/users/deleteById/${userId}`, {
            headers: this.getHeader(),
        });
        return response.data;
    }

    // --------- ROOMS ---------

    static async addRoom(formData) {
    const response = await axios.post(`${this.Base_Url}/rooms/add`, formData, {
        headers: {
            ...this.getHeader(),
            "Content-Type": "multipart/form-data",
        },
    });
    return response.data;
}

static async getRoomById(roomId) {
  const token = localStorage.getItem('token');
  return await axios.get(`${this.Base_Url}/rooms/roomById/${roomId}`, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  }).then(res => res.data);
}





    static async getAllAvailableRooms() {
        const response = await axios.get(`${this.Base_Url}/rooms/allAvailableRooms`);
        return response.data;
    }

    static async getAllAvailableRoomsByDateAndType(checkInDate, checkOutDate, roomType) {
        const response = await axios.get(
            `${this.Base_Url}/rooms/availableRoomsBYDateAndType?checkInDate=${checkInDate}&checkOutDate=${checkOutDate}&roomType=${roomType}`,
            {
                headers: this.getHeader()
            }
        );
        return response.data;
    }

    static async getRoomTypes() {
        const response = await axios.get(`${this.Base_Url}/rooms/types`);
        return response.data;
    }

    static async getAllRooms() {
        const response = await axios.get(`${this.Base_Url}/rooms/all`);
        return response.data;
    }
static async getAllRooms() {
    const token = localStorage.getItem("token");
    const response = await axios.get(`${this.Base_Url}/rooms/all`, {
        headers: {
            Authorization: `Bearer ${token}`
        }
    });
    return response.data;
}

    static async deleteRoomById(roomId) {
        const response = await axios.delete(`${this.Base_Url}/rooms/delete/${roomId}`, {
            headers: this.getHeader(),
        });
        return response.data;
    }

   static async updateRoom(roomId, formData) {
  const response = await axios.put(`${this.Base_Url}/rooms/update/${roomId}`, formData, {
    headers: {
      ...this.getHeader(),
      "Content-Type": "multipart/form-data",
    },
  });
  return response.data;
}

    // --------- BOOKINGS ---------

    static async bookRoom(roomId, userId, bookingData) {
        const response = await axios.post(
            `${this.Base_Url}/bookings/bookRoom/${roomId}/${userId}`,
            bookingData,
            {
                headers: this.getHeader()
            }
        );
        return response.data;
    }
  static async getAllBookings() {
  const response = await axios.get(`${this.Base_Url}/bookings/all`, {
    headers: this.getHeader()
  });
  return response.data; // ✅ This ensures you'll receive the object with bookingsDtos
}





    static async getBookingsByConfirmationCode(confirmationCode) {
        const response = await axios.get(
            `${this.Base_Url}/bookings/getByconfirmationCode/${confirmationCode}`,
            {
                headers: this.getHeader()
            }
        );
        return response.data;
    } 

  static async cancelBooking(bookingId) {
    const response = await axios.delete(
        `${this.Base_Url}/bookings/delete/${bookingId}`,
        {
            headers: this.getHeader(), // this should return Authorization header
        }
    );
    return response.data;
}

  

}
