package SD.DeviceManagement.controllers;

import SD.DeviceManagement.dtos.DateDTO;
import SD.DeviceManagement.dtos.DeviceAndEnergyDTO;
import SD.DeviceManagement.dtos.DeviceDTO;
import SD.DeviceManagement.dtos.NrgConsDTO;
import SD.DeviceManagement.services.DeviceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping(value = "/devices")
public class DeviceController {
    private final DeviceService deviceService;

    @Autowired
    public DeviceController(DeviceService deviceService)
    {
        this.deviceService=deviceService;
    }

    @GetMapping()
    public ResponseEntity<List<DeviceDTO>> getDevices()
    {
        List<DeviceDTO> dtos = deviceService.getDevices();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PostMapping("/addDevice")
    public ResponseEntity<UUID> addDevice(@RequestBody @Valid DeviceAndEnergyDTO deviceDTO) {
        UUID deviceID = deviceService.addDevice(deviceDTO);
        return new ResponseEntity<>(deviceID,HttpStatus.OK);
    }

    @DeleteMapping("/deleteDevice/{id}")
    public ResponseEntity<UUID> deleteDevice(@PathVariable("id") UUID id)
    {
        UUID deviceID = deviceService.deleteDevice(id);
        return new ResponseEntity<>(deviceID,HttpStatus.OK);
    }

    @PutMapping("/updateDevice/{id}")
    public ResponseEntity<UUID> updateDevice(@PathVariable("id") UUID id,@RequestBody @Valid DeviceDTO deviceDTO)
    {
        UUID deviceID = deviceService.updateDevice(id,deviceDTO);
        return new ResponseEntity<>(deviceID,HttpStatus.OK);
    }

    @PostMapping(value = "/{id}/consumptions")
    public ResponseEntity<List<NrgConsDTO>> getDeviceConsumptionsFromDate(@PathVariable("id") UUID id, @RequestBody @Valid DateDTO date)
    {
        List<NrgConsDTO> dtos = deviceService.getDeviceConsumptions(id,date);
        return new ResponseEntity<>(dtos,HttpStatus.OK);
    }
}
