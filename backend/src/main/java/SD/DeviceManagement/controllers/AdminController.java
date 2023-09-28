package SD.DeviceManagement.controllers;

import SD.DeviceManagement.dtos.DeviceDTO;
import SD.DeviceManagement.dtos.UserDTO;
import SD.DeviceManagement.services.DeviceService;
import SD.DeviceManagement.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/admin")
public class AdminController {
    private UserService userService;
    private DeviceService deviceService;

    @Autowired
    public AdminController(UserService userService,DeviceService deviceService)
    {
        this.userService=userService;
        this.deviceService=deviceService;
    }

    @GetMapping()
    public ResponseEntity<List<UserDTO>> getUsers()
    {
        List<UserDTO> dtos = userService.getUsers();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PostMapping(path = "/addUser")
    public ResponseEntity<UUID> addUser(@RequestBody @Valid UserDTO userDTO)
    {
        if(userService.getUserByUsername(userDTO.getUsername())!=null)
            return new ResponseEntity<>(userDTO.getId(),HttpStatus.IM_USED);

        UUID userID = userService.addUser(userDTO);
        return new ResponseEntity<>(userID,HttpStatus.OK);
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<UUID> deleteUser(@PathVariable("id") UUID id)
    {
        UUID userID = userService.deleteUser(id);
        return new ResponseEntity<>(userID,HttpStatus.OK);
    }

    @DeleteMapping(value = "/deleteAllUsers")
    public ResponseEntity<String> deleteAll()
    {
        userService.deleteAll();
        return new ResponseEntity<>("All users have been deleted!",HttpStatus.OK);
    }

    @PutMapping("/updateUser/{id}")
    public ResponseEntity<UUID> updateUser(@PathVariable("id") UUID id, @RequestBody @Valid UserDTO userDTO)
    {
        UUID userID = userService.updateUser(id,userDTO);
        return new ResponseEntity<>(userID,HttpStatus.OK);
    }

    @PutMapping("/addDevice2User/user-{id1}&device-{id2}")
    public ResponseEntity<DeviceDTO> addDeviceToUser(@PathVariable("id1") UUID userID, @PathVariable("id2") UUID deviceID)
    {
        UserDTO userDTO = userService.getUserById(userID);
        DeviceDTO deviceDTO = deviceService.getDeviceById(deviceID);
        deviceService.addDeviceToUser(deviceDTO,userDTO);
        return new ResponseEntity<>(deviceDTO,HttpStatus.OK);
    }
}
