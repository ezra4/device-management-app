import React, { useEffect,useState } from 'react'
import {Table} from 'reactstrap'
import axios from 'axios';
import UserDataService from '../services/UserDataService';
import LineChart from './LineChart';
import DeviceConsumptions from './forms/DeviceConsumptions';
import StompClass from './StompClass';
import { Navigate, useNavigate } from 'react-router-dom';

function ClientPage() {

  const [devices,setDevices] = useState();
  const [consumptions,setConsumptions] = useState();

  const navigate = useNavigate();
  const userID = sessionStorage.getItem("userID");
  const userRole = sessionStorage.getItem("userRole");

  useEffect(() => {
    const userID = sessionStorage.getItem("userID");
    const userRole = sessionStorage.getItem("userRole");
    if(!userID)
    {
        alert("You are not logged in!");
        navigate("/login");
    }
  },[]);
  var rows = [];
  var cols = [];

  const [dateNrg,setDateNrg] = useState();
  const [dateTime,setDateTime] = useState();
  console.log(userID);
  console.log(userRole);
  //const id = "b6f7ba71-fbd9-4637-9395-26f306b901c3";
  useEffect(() => {
    axios({
        method: "get",
        url: `http://localhost:8080/client/${userID}`
    }).then((response) => {
      setDevices(response.data);
    })
  },[])

  return (
    <div>
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
                          <td>{device.user.username}</td>
                          <DeviceConsumptions device={device}/>
                      </tr>
                      )
                  })
              }
          </tbody>
      </Table>
      <div>
        <StompClass/>
      </div>
    </div>
  )
}
export default ClientPage;