import React from 'react'
import {useState, useEffect} from 'react';
import axios from 'axios';
import LineChart from './LineChart';
import DeviceDataService from '../services/DeviceDataService';

function ClientConsumptionsPage() {

    const [cons,setCons] = useState();
    const deviceID = sessionStorage.getItem("deviceID");
    const time = sessionStorage.getItem("time");
    const energyData = [];
    console.log(JSON.stringify(time));
    console.log(deviceID);
    useEffect(() => {
        DeviceDataService.getConsumptionsByDate(deviceID,{
            time:time
        }).then((response) => {
            response.data.forEach(element => {
                energyData.push(element.nrgCons);
            });
            console.log(energyData);
        })
      },[])
  return (
    <div>
        <LineChart energyData={energyData}/>
    </div>
  )
}

export default ClientConsumptionsPage