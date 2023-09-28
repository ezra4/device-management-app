package SD.DeviceManagement.dtos;

import SD.DeviceManagement.entities.EnergyConsumption;
import SD.DeviceManagement.entities.User;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class DeviceDTO {
    private UUID id;
    @NotNull private String description;
    @NotNull private String address;
    private User user;
}
