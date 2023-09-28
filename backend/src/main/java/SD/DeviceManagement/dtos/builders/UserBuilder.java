package SD.DeviceManagement.dtos.builders;

import SD.DeviceManagement.dtos.UserDTO;
import SD.DeviceManagement.entities.User;

import java.util.UUID;

public class UserBuilder {

    public static User toEntity(UserDTO userDTO)
    {
        return new User(userDTO.getId(),userDTO.getUsername(),userDTO.getPassword(),userDTO.getRole());
    }

    public static User toEntityWithoutID(UserDTO userDTO)
    {
        return new User(UUID.randomUUID(),userDTO.getUsername(),userDTO.getPassword(),userDTO.getRole());
    }
    public static UserDTO toUserDTO(User user)
    {
        return new UserDTO(user.getId(),user.getUsername(),user.getPassword(),user.getRole());
    }
}
