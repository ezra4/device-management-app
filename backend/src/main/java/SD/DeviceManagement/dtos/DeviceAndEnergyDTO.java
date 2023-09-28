package SD.DeviceManagement.dtos;

import SD.DeviceManagement.entities.User;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
public class DeviceAndEnergyDTO {

    private UUID id;
    @NotNull private String description;
    @NotNull private String address;
    @NotNull private Date time;
    private User user;
}
