import React from 'react'
import { Form, Input,Button } from 'reactstrap'
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

function LoginPage() {
    const navigate = useNavigate();

    const login = async (e) => {
        const username = e.target[0].value;
        const password = e.target[1].value;

        let user;
        e.preventDefault();
    
            axios({
                method: "post",
                url: "http://localhost:8080/auth",
                headers: {"Content-Type": "application/json"},
                data: {
                    username:username,
                    password:password
                }
            }).then((response) => {
                sessionStorage.setItem("userID",response.data.id);
                sessionStorage.setItem("userRole",response.data.role);
                sessionStorage.setItem("username",response.data.username);
                if(response.data.role === "ROLE_CLIENT")
                    navigate("/client")
                else if(response.data.role === "ROLE_ADMIN")
                    navigate("/admin");
            })
            .catch((e) => {
                alert("User not found!");
            })
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