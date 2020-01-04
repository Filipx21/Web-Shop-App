package pl.filip.tosql.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.filip.tosql.model.Role;
import pl.filip.tosql.model.User;
import pl.filip.tosql.repositories.RoleRepository;
import pl.filip.tosql.repositories.UserRepository;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    public void register(User user){
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setEnabled(true);
        userRepository.save(newUser);

        Role role = new Role();
        role.setUsername(user.getUsername());
        role.setRole("ROLE_USER");
        roleRepository.save(role);
    }

    public User editUser(User userDetails, String username) {
        Optional<User> userObj = userRepository.findByUsername(username);
        if (userObj.isPresent()) {
            User user = userObj.get();
            user.setEmail(userDetails.getEmail());
            user.setFirstName(userDetails.getFirstName());
            user.setLastName(userDetails.getLastName());
            return userRepository.save(user);
        }
        return null;
    }

    public void editPassword(User user){

    }

    public User findByUsername(String username){
        Optional<User> object = userRepository.findByUsername(username);
        return object.orElse(null);
    }

    public List<User> findAll(){
        throw new NullPointerException();
    }


    public User disableAcc(String username) {
        Optional<User> userObj = userRepository.findByUsername(username);
        if(userObj.isPresent()){
            User user = userObj.get();
            user.setEnabled(!user.isEnabled());
            userRepository.save(user);

            return new User();
        }
        return null;
    }

}
