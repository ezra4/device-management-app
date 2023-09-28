package SD.DeviceManagement.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Credentials {
    @NotNull private String username;
    @NotNull private String password;
}
