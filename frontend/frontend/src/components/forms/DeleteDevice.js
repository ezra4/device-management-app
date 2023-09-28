import React, { useEffect, useState } from 'react';
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import Modal from 'react-bootstrap/Modal';
import DeviceDataService from '../../services/DeviceDataService';

function DeleteDevice({device}) {

    const [show, setShow] = useState(false);

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    const onSubmit = async (e) => {
        e.preventDefault();
        DeviceDataService.delete(device.id);
        window.location.reload(false);
        handleClose();
    }

    return (
        <>
          <Button variant="primary" onClick={handleShow}>
            Delete
          </Button>
          <Modal show={show} onHide={handleClose}>
            <Modal.Header closeButton>
              <Modal.Title>Delete Device</Modal.Title>
            </Modal.Header>
            
            <Modal.Footer>
              <Button variant="secondary" onClick={handleClose}>
                Close
              </Button>
              <Button variant="primary" onClick={onSubmit} on>
                Delete
              </Button>
            </Modal.Footer>
          </Modal>
        </>
      );
}

export default DeleteDevice;