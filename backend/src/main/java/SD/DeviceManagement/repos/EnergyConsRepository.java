package SD.DeviceManagement.repos;

import SD.DeviceManagement.entities.Device;
import SD.DeviceManagement.entities.EnergyConsumption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EnergyConsRepository extends JpaRepository<EnergyConsumption, UUID> {

    Optional<List<EnergyConsumption>> findByDevice(Device device);
}
