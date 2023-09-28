import './App.css';
import "bootstrap/dist/css/bootstrap.min.css";
import React, {Component} from "react";
import { BrowserRouter, Link, Route,Routes } from 'react-router-dom';
import AdminPage from './components/AdminPage';
import LoginPage from './components/LoginPage';
import ClientPage from './components/ClientPage';
import ClientConsumptionsPage from './components/ClientConsumptionsPage';
import ChatRoom from './components/Chatroom';

class App extends Component{
  render(){
    return(
    <div>
      <nav className="navbar navbar-expand navbar-dark bg-dark">
        <a href="/" className="navbar-brand">
          Device Management
        </a>
        <div className='navbar-nav mr-auto'>
          <li className='nav-item'>
            <Link to={"/login"} className="nav-link">
              Login page
            </Link>
          </li>
          <li>
            <Link to={"/login"} onClick={() => {sessionStorage.clear()}} className="nav-link">
              Logout
            </Link>
          </li>
          <li className='nav-item'>
            <Link to={"/admin"} className="nav-link">
              Admin page
            </Link>
          </li>
          <li className='nav-item'>
            <Link to={"/client"} className="nav-link">
              Client page
            </Link>
          </li>
          <li className='nav-item'>
            <Link to={"/devices"} className="nav-link">
              Devices
            </Link>
          </li>
          <li>
            <Link to={"/chatroom"} className="nav-link">
              Chatroom
            </Link>
          </li>
        </div>
      </nav>

      <div className='container mt-3'>
        <Routes>
          <Route path='/admin' element={<AdminPage/>}/>
          <Route path='/login' element={<LoginPage/>}/>
          <Route path='/client' element={<ClientPage/>}/>
          <Route path='/client/consumptions' element={<ClientConsumptionsPage/>}/>
          <Route path='/chatroom' element={<ChatRoom/>}/>
        </Routes>
      </div>
    </div>
  )}
}

export default App;
