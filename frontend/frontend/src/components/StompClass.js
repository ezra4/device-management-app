import React, { useState } from 'react';
import SockJsClient from 'react-stomp';

const SOCKET_URL = 'http://localhost:8080/ws-notification';

const userID = sessionStorage.getItem("userID");
const StompClass = () => {
  const [message, setMessage] = useState('Your server message is here.');

  let onConnected = () => {
    console.log("Connected!!")
  }

  let onMessageReceived = (msg) => {
    setMessage(msg.message);
    alert(msg);
  }

  return (
    <div>
      <SockJsClient
        url={SOCKET_URL}
        topics={['/client/'+userID+'/notification']}
        onConnect={onConnected}
        onDisconnect={console.log("Disconnected!")}
        onMessage={msg => onMessageReceived(msg)}
        debug={false}
      />
      <div>{message}</div>
    </div>
  );
}

export default StompClass;