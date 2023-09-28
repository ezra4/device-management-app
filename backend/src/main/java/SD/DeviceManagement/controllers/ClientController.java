package SD.DeviceManagement.controllers;

import SD.DeviceManagement.dtos.DeviceAndEnergyDTO;
import SD.DeviceManagement.dtos.DeviceDTO;
import SD.DeviceManagement.services.DeviceService;
import SD.DeviceManagement.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping(value = "/client")
public class ClientController {

    private UserService userService;
    private DeviceService deviceService;
    public ClientController(UserService userService,DeviceService deviceService)
    {
        this.userService = userService;
        this.deviceService=deviceService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<DeviceDTO>> getDevicesByUser(@PathVariable("id") UUID id)
    {
        List<DeviceDTO> devices = deviceService.getDevicesByClient(id);
        return new ResponseEntity<>(devices, HttpStatus.OK);
    }
}
