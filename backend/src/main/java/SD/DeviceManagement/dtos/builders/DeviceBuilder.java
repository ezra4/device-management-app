package SD.DeviceManagement.dtos.builders;

import SD.DeviceManagement.dtos.DeviceDTO;
import SD.DeviceManagement.entities.Device;

public class DeviceBuilder {
    public static Device toEntity(DeviceDTO deviceDTO)
    {
        return new Device(deviceDTO.getId(),deviceDTO.getDescription(),deviceDTO.getAddress(), deviceDTO.getUser());
    }

    public static DeviceDTO toDeviceDTO(Device device)
    {
        return new DeviceDTO(device.getId(), device.getDescription(), device.getAddress(), device.getUser());
    }
}
