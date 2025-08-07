import React, { useEffect, useState } from "react";
import ApiService from "../../service/ApiService";
import RoomResult from "../common/RoomResult";
import Pagination from "../common/Pagination";
import RoomSearch from "../common/RoomSearch";

const AllRoomsPage = () => {
  const [allRooms, setAllRooms] = useState([]); // master list
  const [rooms, setRooms] = useState([]);       // search results
  const [filteredRooms, setFilteredRooms] = useState([]); // filter results

  const [roomTypes, setRoomTypes] = useState([]);
  const [selectedRoomType, setSelectedRoomType] = useState('');

  const [currentPage, setCurrentPage] = useState(1);
  const [roomsPerPage] = useState(5);

  // Fetch rooms and room types initially
  useEffect(() => {
    const fetchRooms = async () => {
      try {
        const response = await ApiService.getAllRooms();
        const all = response.roomDtos;
        setAllRooms(all);
        setRooms(all);
        setFilteredRooms(all);
      } catch (error) {
        console.log('Error fetching rooms', error.message);
      }
    };

    const fetchRoomTypes = async () => {
      try {
        const types = await ApiService.getRoomTypes();
        setRoomTypes(types);
      } catch (error) {
        console.error('Error fetching room types:', error.message);
      }
    };

    fetchRooms();
    fetchRoomTypes();
  }, []);

  // Handle filtering by room type
  const handleRoomTypeChange = (e) => {
    const selectedType = e.target.value;
    setSelectedRoomType(selectedType);
    filterRooms(selectedType, rooms);
  };

  // Generic filtering function
  const filterRooms = (type, baseRooms = rooms) => {
    if (type === '') {
      setFilteredRooms(baseRooms);
    } else {
      const filtered = baseRooms.filter((room) => room.roomType === type);
      setFilteredRooms(filtered);
    }
    setCurrentPage(1);
  };

  // Handle results from RoomSearch
  const handleSearchResults = (results) => {
    setRooms(results);
    filterRooms(selectedRoomType, results); // Apply current filter on search
  };

  // Pagination
  const indexOfLastRoom = currentPage * roomsPerPage;
  const indexOfFirstRoom = indexOfLastRoom - roomsPerPage;
  const currentRooms = filteredRooms.slice(indexOfFirstRoom, indexOfLastRoom);

  const paginate = (pageNumber) => setCurrentPage(pageNumber);

  return (
    <div className="all-rooms">
      <h2>All Rooms</h2>

      <div className="all-room-filter-div">
        <label>Filter by Room Type:</label>
        <select value={selectedRoomType} onChange={handleRoomTypeChange}>
          <option value="">All</option>
          {roomTypes.map((type) => (
            <option key={type} value={type}>{type}</option>
          ))}
        </select>
      </div>

      <RoomSearch handleSearchResults={handleSearchResults} />
      <RoomResult roomSearchResults={currentRooms} />

      <Pagination
        roomsPerPage={roomsPerPage}
        totalRooms={filteredRooms.length}
        currentPage={currentPage}
        paginate={paginate}
      />
    </div>
  );
};

export default AllRoomsPage;
