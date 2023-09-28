import React, {useState} from "react";
import { over } from "stompjs";
import SockJS from "sockjs-client";

var stompClient = null;
const Notification = () => {
    const [message, setMessage] = useState("");

    const registerUser = () => {
        let Sock = new SockJS('http://localhost:8080/ws');
        stompClient = over(Sock);
        stompClient.connect({},onConnected,onError);
        
        const onConnected = () =>{

        }
    }
}