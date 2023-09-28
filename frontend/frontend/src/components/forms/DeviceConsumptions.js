import React, { useEffect, useState } from 'react';
import Form from 'react-bootstrap/Form';
import Modal from 'react-bootstrap/Modal';
import Button from 'react-bootstrap/Button';
import { useNavigate } from 'react-router-dom';

import UserDataService from '../../services/UserDataService';

function DeviceConsumptions({device}) {
    const navigate = useNavigate();
    const [show, setShow] = useState(false);

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    const [time,setTime] = useState();
    const onSubmit = async (e) => {
        e.preventDefault();
        console.log(device.id);
        sessionStorage.setItem("deviceID",device.id);
        sessionStorage.setItem("time",time);
        navigate("/client/consumptions");
    }

    const onChange = (e) => {
        e.preventDefault();
        if(e.target.name === "time")
            setTime(e.target.value);
    }
    return (
        <>
          <Button variant="primary" onClick={handleShow}>
            Consumptions
          </Button>
          <Modal show={show} onHide={handleClose}>
            <Modal.Header closeButton>
              <Modal.Title>Date</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <Form>
                    <Form.Group className="mb-3" controlId="exampleForm.ControlInput1">
                    <Form.Label>Date</Form.Label>
                    <Form.Control
                        name='time'
                        placeholder="yyyy-MM-dd"
                        value={time}
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
              <Button variant="primary" onClick={onSubmit} on>
                Show Chart
              </Button>
            </Modal.Footer>
          </Modal>
        </>
      );
}

export default DeviceConsumptions;