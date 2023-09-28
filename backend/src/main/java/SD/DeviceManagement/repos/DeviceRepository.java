package SD.DeviceManagement.repos;

import SD.DeviceManagement.entities.Device;
import SD.DeviceManagement.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DeviceRepository extends JpaRepository<Device, UUID> {

    Optional<List<Device>> findDevicesByUser(User user);

}
