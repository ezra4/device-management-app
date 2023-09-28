package SD.DeviceManagement.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserAndPassDTO {
    private String username;
    private String password;
}
