package SD.DeviceManagement.dtos;

import SD.DeviceManagement.entities.Role;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class UserDTO {
    private UUID id;
    @NotNull private String username;
    @NotNull private String password;
    @NotNull private Role role;
}
