package SD.DeviceManagement.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CustomMessage implements Serializable {

    @Serial
    private static final long serialVersionUID = -6470090944414208496L;

    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss") private Date timestamp;
    private UUID deviceID;
    private int measurementValue;

}
