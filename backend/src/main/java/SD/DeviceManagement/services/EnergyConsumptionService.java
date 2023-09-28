package SD.DeviceManagement.services;

import SD.DeviceManagement.dtos.NrgConsDTO;
import SD.DeviceManagement.dtos.builders.NrgConsBuilder;
import SD.DeviceManagement.entities.CustomMessage;
import SD.DeviceManagement.entities.Device;
import SD.DeviceManagement.entities.EnergyConsumption;
import SD.DeviceManagement.repos.DeviceRepository;
import SD.DeviceManagement.repos.EnergyConsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EnergyConsumptionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EnergyConsumptionService.class);

    private final EnergyConsRepository energyConsRepository;

    private final DeviceRepository deviceRepository;

    private final int maxConsumption = 30;
    private SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    public EnergyConsumptionService(EnergyConsRepository energyConsRepository,DeviceRepository deviceRepository,SimpMessagingTemplate simpMessagingTemplate)
    {
        this.energyConsRepository=energyConsRepository;
        this.deviceRepository=deviceRepository;
        this.simpMessagingTemplate=simpMessagingTemplate;
    }

    public List<NrgConsDTO> getAll()
    {
        List<EnergyConsumption> energyConsumptions = energyConsRepository.findAll();
        return energyConsumptions.stream().map(NrgConsBuilder::toNrgConsDTO).collect(Collectors.toList());
    }

    public void logRabbitEnergyCons(CustomMessage customMessage)
    {
        EnergyConsumption energyConsumption = new EnergyConsumption();
        Optional<Device> deviceOptional = deviceRepository.findById(customMessage.getDeviceID());
        if(deviceOptional.isPresent())
        {
            if(customMessage.getMeasurementValue()>maxConsumption)
            {
                simpMessagingTemplate.convertAndSendToUser(deviceOptional.get().getUser().getId().toString(),"/notification","Device with id "+customMessage.getDeviceID()+" has exceeded maximum consumption!");
                System.out.println("Am intrat in if, am trimis cu simpTemplate");
            }
            energyConsumption.setId(UUID.randomUUID());
            energyConsumption.setDevice(deviceOptional.get());
            energyConsumption.setNrgCons(customMessage.getMeasurementValue());
            energyConsumption.setTime(customMessage.getTimestamp());
            energyConsRepository.save(energyConsumption);
        }
        else System.out.println("Nu exist");
    }
    public List<NrgConsDTO> getByDevice(Device device)
    {
        Optional<List<EnergyConsumption>> energyConsumptionOptional = energyConsRepository.findByDevice(device);
        if(energyConsumptionOptional.isEmpty())
        {
            LOGGER.error("Energy fetch error!");
            throw new ResourceNotFoundException("Energy doesn't exist for this device!");
        }
        else{
            List<EnergyConsumption> energyConsumption = energyConsumptionOptional.get();
            return energyConsumption.stream().map(NrgConsBuilder::toNrgConsDTO).collect(Collectors.toList());
        }
    }
    public UUID updateNrgCons(UUID id, NrgConsDTO nrgConsDTO)
    {
        Optional<EnergyConsumption> energyConsumptionOptional = energyConsRepository.findById(id);
        if(energyConsumptionOptional.isEmpty())
        {
            LOGGER.error("EnergyCons update error!");
            throw new ResourceNotFoundException("Energy Consumption with {id} doesn't exist!");
        }
        else
        {
            EnergyConsumption energyConsumption = energyConsumptionOptional.get();
            energyConsumption.setTime(nrgConsDTO.getTime());
            energyConsumption.setNrgCons(nrgConsDTO.getNrgCons());
            energyConsumption.setDevice(nrgConsDTO.getDevice());
            energyConsRepository.save(energyConsumption);
            return energyConsumption.getId();
        }
    }
}
