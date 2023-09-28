package SD.DeviceManagement.dtos.builders;

import SD.DeviceManagement.dtos.DeviceAndEnergyDTO;
import SD.DeviceManagement.dtos.DeviceDTO;
import SD.DeviceManagement.dtos.NrgConsDTO;

import java.util.UUID;

public class DeviceAndEnergyBuilder {

    public static DeviceDTO toDeviceDTO(DeviceAndEnergyDTO deviceAndEnergyDTO)
    {
        return new DeviceDTO(UUID.randomUUID(),deviceAndEnergyDTO.getDescription(),deviceAndEnergyDTO.getAddress(),deviceAndEnergyDTO.getUser());
    }
}
