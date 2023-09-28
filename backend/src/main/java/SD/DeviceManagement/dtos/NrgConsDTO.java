package SD.DeviceManagement.dtos;

import SD.DeviceManagement.entities.Device;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
public class NrgConsDTO {

    private UUID id;
    @NotNull @JsonFormat(pattern = "yyyy-MM-dd") private Date time;
    @NotNull private int nrgCons;
    private Device device;
}
