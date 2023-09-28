import React, { useEffect, useState } from 'react';
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import Modal from 'react-bootstrap/Modal';
import DeviceDataService from '../../services/DeviceDataService';

function AddDevice() {

    const [show, setShow] = useState(false);

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    const [description,setDescription] = useState("");
    const [address,setAddress] = useState("");
    const [time,setTime] = useState("");

    const onChange = (e) => {
        e.preventDefault();
        if(e.target.name === "description")
            setDescription(e.target.value);
        else if(e.target.name === "address")
            setAddress(e.target.value);
        else if(e.target.name === "time")
            setTime(e.target.value);
    }

    const onSubmit = async (e) => {
        e.preventDefault();
        DeviceDataService.create({
            description:description,
            address:address,
            time:time,
        })
        window.location.reload(false);
        handleClose();
    }
    return (
        <>
          <Button variant="primary" onClick={handleShow}>
            Add
          </Button>
          <Modal show={show} onHide={handleClose}>
            <Modal.Header closeButton>
              <Modal.Title>Add Device</Modal.Title>
            </Modal.Header>
            <Modal.Body>
              <Form>
                <Form.Group className="mb-3" controlId="exampleForm.ControlInput1">
                  <Form.Label>Description</Form.Label>
                  <Form.Control
                    name='description'
                    placeholder="description"
                    value={description}
                    onChange={onChange}
                    autoFocus
                  />
                </Form.Group>
                <Form.Group className="mb-3" controlId="exampleForm.ControlInput1">
                  <Form.Label>Address</Form.Label>
                  <Form.Control
                    name='address'
                    placeholder="address"
                    type='address'
                    value={address}
                    onChange={onChange}
                    autoFocus
                  />
                </Form.Group>
                <Form.Group className="mb-3" controlId="exampleForm.ControlInput1">
                  <Form.Label>Time</Form.Label>
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
              <Button variant="primary" onClick={onSubmit}>
                Add Device
              </Button>
            </Modal.Footer>
          </Modal>
        </>
      );
}

export default AddDevice;