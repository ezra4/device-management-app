import React from 'react'
import './LoginPage.css';
import { Form, Input,Button } from 'reactstrap'
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

function LoginPage() {
    const navigate = useNavigate();
    const login = async (e) => {
        const username = e.target[0].value;
        const password = e.target[1].value;
        e.preventDefault();
        console.log(username,password);
        const response = await axios({
            method: 'POST',
            url: 'http://localhost:8080/login',
            data: {
                username: username,
                password: password
             }
        });
        if(response == "admin")
            navigate("/admin");
        else if(response == "client")
            navigate("/client");
    }
  return (
    <div>
        <p>Please sign in.</p>
        <Form id='loginForm' onSubmit={login}>
            <Input placeholder='Username'></Input>
            <Input placeholder='Password' type='password'></Input>
        </Form>
        <Button form='loginForm' type='submit'>Login</Button>
    </div>
  )
}

export default LoginPage