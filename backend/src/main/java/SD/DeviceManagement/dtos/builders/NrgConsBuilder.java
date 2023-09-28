package SD.DeviceManagement.dtos.builders;

import SD.DeviceManagement.dtos.NrgConsDTO;
import SD.DeviceManagement.entities.EnergyConsumption;

public class NrgConsBuilder {

    public static EnergyConsumption toEntity(NrgConsDTO nrgConsDTO)
    {
        return new EnergyConsumption(nrgConsDTO.getId(),nrgConsDTO.getTime(),nrgConsDTO.getNrgCons(),nrgConsDTO.getDevice());
    }

    public static NrgConsDTO toNrgConsDTO(EnergyConsumption energyConsumption)
    {
        return new NrgConsDTO(energyConsumption.getId(),energyConsumption.getTime(), energyConsumption.getNrgCons(),energyConsumption.getDevice());
    }
}
