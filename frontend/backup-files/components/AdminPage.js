import React, { Component, useEffect,useState } from 'react'
import {Table} from 'reactstrap'
import UserDataService from "../services/UserDataService";

class AdminPage extends Component {
   
    

  return (
    <div>
    <Table>
        <thead>
            <tr>
                <th>Id</th>
                <th>Username</th>
                <th>Password</th>
                <th>Role</th>
            </tr>
        </thead>
        <tbody>
            {
                users && users.map((index,user) => {
                  console.log("Userul este:" + user.username);
                   return <tr key={user.id}>
                        <td>{user.id}</td>
                        <td>{user.username}</td>
                        <td>{user.password}</td>
                        <td>{user.role}</td>
                    </tr>
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