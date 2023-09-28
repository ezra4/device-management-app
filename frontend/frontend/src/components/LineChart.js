import React from "react";
import {Line,Chart} from 'react-chartjs-2'
import {
    Chart as ChartJS,
    CategoryScale,
    LinearScale,
    PointElement,
    LineElement,
    Title,
    Tooltip,
    Legend,
  } from 'chart.js'
ChartJS.register(
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend
)

function LineChart(energyData) {
    console.log(energyData);
    const data = {
        labels: ['0','1','2','3','4','5','6','7','8','9','10','11','12','13','14','15','16','17','18','19','20','21','22','23'],
        datasets:[
            {
                 label: 'Energy consumed (hourly)',
                 data: energyData.energyData
            }
        ]
    }
    
    return(
        <Line data={data}/>
    )
}

export default LineChart;