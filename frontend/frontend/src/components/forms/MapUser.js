import React, { useEffect, useState } from 'react';
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import Modal from 'react-bootstrap/Modal';
import DeviceDataService from '../../services/DeviceDataService';
import UserDataService from '../../services/UserDataService';



function MapUser({device,users}) {

    const [show, setShow] = useState(false);

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    const [user,setUser] = useState("");
    const [persons,setPersons] = useState();

    useEffect(() => {
      setPersons(users);
    })

    const onSubmit = async (e) => {
        e.preventDefault();
        let selectedUser = persons.filter(person=>person.username===user);
        let id = selectedUser[0].id;
        if(device.user != null)
        {
          alert("Device already has a client!");
          handleClose();
        }
        else
        {
          UserDataService.addDevice2User(id,device.id);
          window.location.reload(false);
          handleClose();
        }
        
    }

    const onChange = (e) => {
        e.preventDefault();
        if(e.target.name === "user")
            setUser(e.target.value);
    }


    return(
      <>
      <Button variant="primary" onClick={handleShow}>
        Map
      </Button>
      <Modal show={show} onHide={handleClose}>
        <Modal.Header closeButton>
          <Modal.Title>Map User to a Device</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form>
            <Form.Group className="mb-3" controlId="exampleForm.ControlInput1">
              <Form.Label>Username</Form.Label>
              <Form.Control
                name='user'
                placeholder="username"
                value={user}
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
            Map
          </Button>
        </Modal.Footer>
      </Modal>
    </>
    );
}

export default MapUser;