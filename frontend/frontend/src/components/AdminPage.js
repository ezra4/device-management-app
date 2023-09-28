import React, { useEffect,useState } from 'react'
import {Table} from 'reactstrap'
import axios from 'axios';
import UserDataService from '../services/UserDataService';
import AddUser from './forms/AddForm';
import DeleteUser from './forms/DeleteUser';
import EditUser from './forms/EditUser';
import AddDevice from './forms/AddDevice';
import DeleteDevice from './forms/DeleteDevice';
import EditDevice from './forms/EditDevice';
import { Navigate, useNavigate } from 'react-router-dom';
import MapUser from './forms/MapUser';

function AdminPage() {
    const [users,setUsers] = useState(); 
    const [devices,setDevices] = useState();

    const navigate = useNavigate();
    
    useEffect(() => {
        const userID = sessionStorage.getItem("userID");
        const userRole = sessionStorage.getItem("userRole");
        if(!userID)
        {
            alert("You are not logged in!");
            navigate("/login");
        }
        if(userRole != "ROLE_ADMIN") {
            alert("Entry not allowed!");
            navigate("/login");
    }},[]);
    useEffect(() => {
        axios({
            method: "get",
            url: "http://localhost:8080/admin",
        }).then((response) => {setUsers(response.data)})
        console.log(users);
    },[])

    useEffect(() => {
        axios({
            method: "get",
            url: "http://localhost:8080/devices"
        }).then((response) => {
            setDevices(response.data)})
    },[])

  return (
    <div>
    <Table>
        <thead>
            <tr>
                <th>Username</th>
                <th>Password</th>
                <th>Role</th>
            </tr>
        </thead>
        <tbody>
            {
                users && users.map((user,index) => {
                    return (
                    <tr key={user.id}>
                        <td>{user.username}</td>
                        <td>{user.password}</td>
                        <td>{user.role}</td>
                        <td><EditUser user={user}/></td>
                        <td><DeleteUser user={user}/></td>
                    </tr>
                    )
                })
            }
        </tbody>
    </Table>
    <AddUser/>

    <Table>
    <thead>
            <tr>
                <th>Description</th>
                <th>Address</th>
                <th>Client</th>
            </tr>
    </thead>
        <tbody>
            {
                devices && devices.map((device,index) => {
                  return (
                    <tr key={device.id}>
                        <td>{device.description}</td>
                        <td>{device.address}</td>
                        <td>{device.user?.username}</td>
                        <td><EditDevice device={device}/></td>
                        <td><DeleteDevice device={device}/></td>
                        <td><MapUser device={device} users={users}/></td>
                    </tr>
                    )
                })
            }
        </tbody>
    </Table>
    <AddDevice/>
    </div>
  )
}

export default AdminPage