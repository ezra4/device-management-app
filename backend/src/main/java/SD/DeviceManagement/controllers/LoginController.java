package SD.DeviceManagement.controllers;

import SD.DeviceManagement.dtos.Credentials;
import SD.DeviceManagement.dtos.UserDTO;
import SD.DeviceManagement.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(value = "/auth")
public class LoginController {

    private UserService userService;

    @Autowired
    public LoginController(UserService userService)
    {
        this.userService=userService;
    }
    @PostMapping()
    public ResponseEntity<UserDTO> login(@RequestBody @Valid Credentials credentials)
    {
        UserDTO userDTO = userService.getUserByUsernameAndPassword(credentials.getUsername(), credentials.getPassword());
        return new ResponseEntity<>(userDTO,HttpStatus.OK);
    }
}
