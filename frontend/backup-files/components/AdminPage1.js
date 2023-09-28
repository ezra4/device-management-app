import React, { useEffect,useState } from 'react'
import {Table} from 'reactstrap'
import axios from 'axios';

function AdminPage() {
    const [users,setUsers] = useState(); 
    const [devices,setDevices] = useState();
    useEffect(() => {
        axios({
            method: "get",
            url: "http://127.0.0.1:8080/admin",
        }).then((response) => {setUsers(response.data)})
    },[])

    useEffect(() => {
        axios({
            method: "get",
            url: "http://127.0.0.1:8080/admin/devices"
        }).then((response) => {setUsers(response.data)})
    },[])
  return (
    <div>
    <Table>
        <thead>
            <tr>
                <th>Username</th>
                <th>Password</th>
                <th>Role</th>
                <th>Devices</th>
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
                        <td>{user.devices}</td>
                    </tr>
                    )
                })
            }
        </tbody>
    </Table>


    <Table>
    <thead>
            <tr>
                <th>Description</th>
                <th>Address</th>
                <th>Consumption</th>
                <th>User</th>
            </tr>
        </thead>
        <tbody>
            {
                devices && devices.map((device,index) => {
                    return (
                    <tr key={device.id}>
                        <td>{device.username}</td>
                        <td>{device.password}</td>
                        <td>{device.role}</td>
                        <td>{device.devices}</td>
                    </tr>
                    )
                })
            }
        </tbody>
    </Table>
    </div>
  )
}

export default AdminPage