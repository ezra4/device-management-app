package SD.DeviceManagement.services;

import SD.DeviceManagement.dtos.UserDTO;
import SD.DeviceManagement.dtos.builders.UserBuilder;
import SD.DeviceManagement.entities.Device;
import SD.DeviceManagement.entities.Role;
import SD.DeviceManagement.entities.User;
import SD.DeviceManagement.repos.UserRepository;
import SD.DeviceManagement.repos.DeviceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private UserRepository userRepository;

    private DeviceRepository deviceRepository;

    private DeviceService deviceService;
    @Autowired
    public UserService(UserRepository userRepository,DeviceRepository deviceRepository,DeviceService deviceService)
    {
        this.userRepository=userRepository;
        this.deviceRepository=deviceRepository;
        this.deviceService=deviceService;
        this.userRepository.save(new User(UUID.randomUUID(),"admin","admin", Role.ROLE_ADMIN));
        Assignment3();
    }

/*    public void addAdminUser()
    {
        User user = new User();
        user.setUsername("admin1");
        user.setPassword(passwordConfig.passwordEncoder().encode("admin1"));
        user.setRole(Role.ROLE_ADMIN);
        userRepository.save(user);
    }*/
    public List<UserDTO> getUsers()
    {
        List<User> users = userRepository.findAll();
        return users.stream().map(UserBuilder::toUserDTO).collect(Collectors.toList());
    }

    public UserDTO getUserById(UUID id)
    {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty())
        {
            LOGGER.error("User fetch error!");
            throw new ResourceNotFoundException("User with {id} doesn't exist!");
        }
        else {
            User u = user.get();
            return UserBuilder.toUserDTO(u);
        }
    }

    public UserDTO getUserByUsername(String username)
    {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if(userOptional.isEmpty())
        {
            LOGGER.error("User fetch error! User doesn't exist!");
            return null;
        }
        else
        {
            User user = userOptional.get();
            return UserBuilder.toUserDTO(user);
        }
    }
    /*public UserDTO getCurrentlyAuthenticatedUser()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Optional<User> user = userRepository.findByUsername(userDetails.getUsername());
        if(user.isEmpty())
        {
            LOGGER.error("Authentication error!");
            throw new ResourceNotFoundException("Cannot find the user specified!");
        }
        return UserBuilder.toUserDTO(user.get());
    }
*/
    public UUID addUser(UserDTO userDTO)
    {
        Optional<User> userOptional = userRepository.findByUsername(userDTO.getUsername());
        if(userOptional.isPresent())
        {
            LOGGER.error("User addition error!");
            throw new RuntimeException("User already exists!");
        }
        else
        {
            User user = UserBuilder.toEntityWithoutID(userDTO);
            user.setPassword(userDTO.getPassword());
            userRepository.save(user);
            return user.getId();
        }
    }

    public UUID deleteUser(UUID id)
    {
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isEmpty())
        {
            LOGGER.error("User deletion error!");
            throw new ResourceNotFoundException("User with {id} doesn't exist!");
        }
        else {
            User user = userOptional.get();
            Optional<List<Device>> devicesOptional = deviceRepository.findDevicesByUser(user);
            if(devicesOptional.isPresent())
            {
                List<Device> devices = devicesOptional.get();
                for (Device d: devices
                     ) {
                    d.setUser(null);
                    deviceService.deleteDevice(d.getId());
                }
            }
            userRepository.delete(user);
            return user.getId();
        }
    }

    public void deleteAll()
    {
        userRepository.deleteAll();
    }
    public UUID updateUser(UUID id,UserDTO userDTO)
    {
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isEmpty())
        {
            LOGGER.error("User update error!");
            throw new ResourceNotFoundException("User with {id} doesn't exist!");
        }
        else
        {
            User user = userOptional.get();
            user.setUsername(userDTO.getUsername());
            user.setPassword(userDTO.getPassword());
            user.setRole(userDTO.getRole());
            userRepository.save(user);
            return user.getId();
        }
    }

    public UserDTO getUserByUsernameAndPassword(String username,String password)
    {
        Optional<User> userOptional = userRepository.findByUsernameAndPassword(username,password);
        if(userOptional.isEmpty())
        {
            LOGGER.error("User fetch error!");
            throw new ResourceNotFoundException("User not found!");
        }
        else
        {
            User user = userOptional.get();
            return UserBuilder.toUserDTO(user);
        }
    }
    /*@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("No user found with this email address."));
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(()->user.getRole().name());
        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),authorities);
    }*/

    public void Assignment3()
    {
        User vlad = new User();
        vlad.setId(UUID.randomUUID());
        vlad.setUsername("Vlad");
        vlad.setPassword("Vlad");
        vlad.setRole(Role.ROLE_CLIENT);

        User david = new User();
        david.setId(UUID.randomUUID());
        david.setUsername("David");
        david.setPassword("David");
        david.setRole(Role.ROLE_CLIENT);

        userRepository.save(vlad);
        userRepository.save(david);
    }
}

