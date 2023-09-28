import React, { useEffect, useState } from 'react';
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import Modal from 'react-bootstrap/Modal';
import DeviceDataService from '../../services/DeviceDataService';

function EditDevice({device}) {

    const [show, setShow] = useState(false);

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    const [description,setDescription] = useState();
    const [address,setAddress] = useState();
    const [time,setTime] = useState();

    useEffect(() => {
      setDescription(device?.description);
      setAddress(device?.address);
  },[device]
  );

    const onChange = (e) => {
        e.preventDefault();
        if(e.target.name === "description")
            setDescription(e.target.value);
        else if(e.target.name === "address")
            setAddress(e.target.value);
    }

    const onSubmit = async (e) => {
        e.preventDefault();
        DeviceDataService.update(device.id,{
            description:description,
            address:address,
        })
        console.log()
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
              <Modal.Title>Edit Device</Modal.Title>
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
                    value={address}
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

export default EditDevice;