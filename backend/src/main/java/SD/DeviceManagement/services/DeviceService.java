package SD.DeviceManagement.services;

import SD.DeviceManagement.dtos.*;
import SD.DeviceManagement.dtos.builders.DeviceAndEnergyBuilder;
import SD.DeviceManagement.dtos.builders.DeviceBuilder;
import SD.DeviceManagement.dtos.builders.NrgConsBuilder;
import SD.DeviceManagement.dtos.builders.UserBuilder;
import SD.DeviceManagement.entities.Device;
import SD.DeviceManagement.entities.EnergyConsumption;
import SD.DeviceManagement.entities.Role;
import SD.DeviceManagement.entities.User;
import SD.DeviceManagement.repos.DeviceRepository;
import SD.DeviceManagement.repos.EnergyConsRepository;
import SD.DeviceManagement.repos.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DeviceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceService.class);

    private DeviceRepository deviceRepository;

    private UserRepository userRepository;
    private EnergyConsRepository energyConsRepository;
    private EnergyConsumptionService energyConsumptionService;

    @Autowired
    public DeviceService(
            DeviceRepository deviceRepository,
            UserRepository userRepository,
            EnergyConsRepository energyConsRepository,
            EnergyConsumptionService energyConsumptionService
    ) {
        this.deviceRepository = deviceRepository;
        this.userRepository = userRepository;
        this.energyConsRepository = energyConsRepository;
        this.energyConsumptionService = energyConsumptionService;
        configRabbit();
    }

    public void configRabbit() {
        User user = new User();
        user.setRole(Role.ROLE_CLIENT);
        user.setUsername("lab2");
        user.setPassword("lab2");
        UUID idUser = UUID.randomUUID();
        user.setId(idUser);


        //UUID prestabilit pentru assignment2
        //acelasi UUID si in config file din producer
        UUID idDevice = UUID.fromString("15b54180-3af8-4c42-b851-8906a664928f");
        Device device = new Device();
        device.setDescription("Rabbit test");
        device.setAddress("Rabbit test");
        device.setUser(user);
        device.setId(idDevice);

        deviceRepository.save(device);
    }
        public List<DeviceDTO> getDevices() {
        List<Device> devices = deviceRepository.findAll();
        return devices.stream().map(DeviceBuilder::toDeviceDTO).collect(Collectors.toList());
    }

    public DeviceDTO getDeviceById(UUID id) {
        Optional<Device> deviceOptional = deviceRepository.findById(id);
        if (deviceOptional.isEmpty()) {
            LOGGER.error("Device fetch error!");
            throw new ResourceNotFoundException("Device with {id} doesn't exist!");
        } else {
            Device device = deviceOptional.get();
            return DeviceBuilder.toDeviceDTO(device);
        }
    }

    public List<DeviceDTO> getDevicesByClient(UUID id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            LOGGER.error("User with id {} was not found in the database!", id);
            throw new ResourceNotFoundException(User.class.getSimpleName() + " with id: " + id);
        } else {
            User user = userOptional.get();
            Optional<List<Device>> devicesOptional = deviceRepository.findDevicesByUser(user);
            if (devicesOptional.isEmpty()) {
                LOGGER.error("Devices fetch error!");
                throw new ResourceNotFoundException("Devices not found for user!");
            } else {
                List<Device> devices = devicesOptional.get();
                return devices.stream().map(DeviceBuilder::toDeviceDTO).collect(Collectors.toList());
            }
        }

    }

    public UUID addDevice(DeviceAndEnergyDTO deviceAndEnergyDTO) {
        DeviceDTO deviceDTO = DeviceAndEnergyBuilder.toDeviceDTO(deviceAndEnergyDTO);
        Date date = deviceAndEnergyDTO.getTime();
        Device device = DeviceBuilder.toEntity(deviceDTO);
        deviceRepository.save(device);

        for (int i = 0; i < 24; i++) {
            Random rnd = new Random();
            int nrgCons = 1 + rnd.nextInt(20);
            date.setHours(i);
            EnergyConsumption energyConsumption = new EnergyConsumption(UUID.randomUUID(), date, nrgCons, device);
            energyConsRepository.save(energyConsumption);
        }
        return device.getId();
    }

    public UUID deleteDevice(UUID id) {
        Optional<Device> deviceOptional = deviceRepository.findById(id);
        if (deviceOptional.isEmpty()) {
            LOGGER.error("Device delete error!");
            throw new ResourceNotFoundException("Device with {id} doesn't exist!");
        } else {
            Device device = deviceOptional.get();
            if (device.getUser() != null) {
                device.setUser(null);
                deviceRepository.save(device);
            }
            Optional<List<EnergyConsumption>> energyConsumptionOptional = energyConsRepository.findByDevice(device);
            if (energyConsumptionOptional.isPresent()) {
                List<EnergyConsumption> energyConsumptions = energyConsumptionOptional.get();
                for (EnergyConsumption en : energyConsumptions
                ) {
                    en.setDevice(null);
                    energyConsRepository.save(en);
                    energyConsRepository.delete(en);
                }
            }
            deviceRepository.delete(device);
            return device.getId();
        }
    }

    public UUID updateDevice(UUID id, DeviceDTO deviceDTO) {
        Optional<Device> deviceOptional = deviceRepository.findById(id);

        if (deviceOptional.isEmpty()) {
            LOGGER.error("Device update error!");
            throw new ResourceNotFoundException("Device with {id} doesn't exist!");
        } else {
            Device device = deviceOptional.get();
            device.setAddress(deviceDTO.getAddress());
            device.setDescription(deviceDTO.getDescription());
            deviceRepository.save(device);
            return device.getId();
        }
    }

    public void addDeviceToUser(DeviceDTO deviceDTO, UserDTO userDTO) {
        User user = UserBuilder.toEntity(userDTO);
        Device device = DeviceBuilder.toEntity(deviceDTO);
        device.setUser(user);
        deviceRepository.save(device);
    }

    public List<NrgConsDTO> getDeviceConsumptions(UUID id, DateDTO date) {
        Optional<Device> deviceOptional = deviceRepository.findById(id);
        if (deviceOptional.isEmpty()) {
            LOGGER.error("Device fetch error!");
            throw new ResourceNotFoundException("Device with id {} doesn't exist!");
        } else {
            Device device = deviceOptional.get();
            Optional<List<EnergyConsumption>> energyConsumptionsOptional = energyConsRepository.findByDevice(device);
            if (energyConsumptionsOptional.isPresent()) {
                List<EnergyConsumption> energyConsumptions = energyConsumptionsOptional.get();
                List<NrgConsDTO> nrgConsDTOS = new ArrayList<>();
                for (EnergyConsumption en: energyConsumptions
                     ) {
                    if(en.getTime().getDay() == (date.getTime().getDay()))
                        nrgConsDTOS.add(NrgConsBuilder.toNrgConsDTO(en));
                }
                return nrgConsDTOS;
            }
        }
        return null;
    }
}
