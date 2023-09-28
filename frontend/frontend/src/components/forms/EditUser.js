import React, { useEffect, useState } from 'react';
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import Modal from 'react-bootstrap/Modal';
import UserDataService from '../../services/UserDataService';

function EditUser({user}) {

    const [show, setShow] = useState(false);

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    const [username,setUsername] = useState();
    const [password,setPassword] = useState();
    const [role,setRole] = useState();

    useEffect(() => {
        setUsername(user?.username);
        console.log(username);
        setPassword(user?.password);
        setRole(user?.role);
    },[user]
    );

    const onChange = (e) => {
        e.preventDefault();
        if(e.target.name === "username")
            setUsername(e.target.value);
        else if(e.target.name === "password")
            setPassword(e.target.value);
        else if(e.target.name === "role")
            setRole(e.target.value);
    }

    const onSubmit = async (e) => {
        e.preventDefault();
        UserDataService.update(user.id,{
            username:username,
            password:password,
            role:role
        })
        window.location.reload(false);
        handleClose();
    }
    return (
        <>
          <Button variant="primary" onClick={handleShow}>
            Edit
          </Button>
          <Modal show={show} onHide={handleClose}>
            <Modal.Header closeButton>
              <Modal.Title>Edit User</Modal.Title>
            </Modal.Header>
            <Modal.Body>
              <Form>
                <Form.Group className="mb-3" controlId="exampleForm.ControlInput1">
                  <Form.Label>Username</Form.Label>
                  <Form.Control
                    name='username'
                    placeholder="username"
                    value={username}
                    onChange={onChange}
                    autoFocus
                  />
                </Form.Group>
                <Form.Group className="mb-3" controlId="exampleForm.ControlInput1">
                  <Form.Label>Password</Form.Label>
                  <Form.Control
                    name='password'
                    placeholder="password"
                    type='password'
                    value={password}
                    onChange={onChange}
                    autoFocus
                  />
                </Form.Group>
                <Form.Group className="mb-3" controlId="exampleForm.ControlInput1">
                  <Form.Label>Role</Form.Label>
                  <Form.Control
                    name='role'
                    placeholder="role"
                    value={role}
                    onChange={onChange}
                    autoFocus
                  />
                </Form.Group>
              </Form>
            </Modal.Body>
            <Modal.Footer>
              <Button variant="secondary" onClick={handleClose}>
                Close
              </Button>
              <Button variant="primary" onClick={onSubmit}>
                Save Changes
              </Button>
            </Modal.Footer>
          </Modal>
        </>
      );
}

export default EditUser;