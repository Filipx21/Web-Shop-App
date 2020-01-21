package pl.filip.shop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.filip.shop.dto.UserDto;
import pl.filip.shop.model.Role;
import pl.filip.shop.model.User;
import pl.filip.shop.repositories.RoleRepository;
import pl.filip.shop.repositories.UserRepository;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder encoder;

//    public UserService(UserRepository userRepository, BCryptPasswordEncoder encoder) {
//        this.userRepository = userRepository;
//        this.encoder = encoder;
//    }

    public User findByEmail(String email) {
        Optional<User> object = userRepository.findByEmail(email);
        return object.orElse(null);
    }

    public User findByUsername(String userName) {
        Optional<User> object = userRepository.findByUserName(userName);
        return object.orElse(null);
    }

    public User save(UserDto userDto) {
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setUserName(userDto.getUserName());
        user.setEmail(userDto.getEmail());
        user.setPassword(encoder.encode(userDto.getPassword()));
        user.setRoles(Arrays.asList(new Role("ROLE_USER")));
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> object = userRepository.findByEmail(email);
        if(!object.isPresent()) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        User user = object.get();
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }




//    private UserRepository userRepository;
//    private PasswordEncoder passwordEncoder;
//    private RoleRepository roleRepository;
//
//    public UserService(UserRepository userRepository,
//                       PasswordEncoder passwordEncoder,
//                       RoleRepository roleRepository) {
//        this.userRepository = userRepository;
//        this.passwordEncoder = passwordEncoder;
//        this.roleRepository = roleRepository;
//    }
//
//    public void register(User user) {
//        User newUser = new User();
//        newUser.setUsername(user.getUsername());
//        newUser.setEmail(user.getEmail());
//        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
//        newUser.setFirstName(user.getFirstName());
//        newUser.setLastName(user.getLastName());
//        newUser.setEnabled(true);
//        userRepository.save(newUser);
//
//        Role role = new Role();
//        role.setUsername(user.getUsername());
//        role.setRole("ROLE_USER");
//        roleRepository.save(role);
//    }
//
//    public User editUser(User userDetails, String username) {
//        Optional<User> userObj = userRepository.findByUsername(username);
//        if (userObj.isPresent()) {
//            User user = userObj.get();
//            user.setEmail(userDetails.getEmail());
//            user.setFirstName(userDetails.getFirstName());
//            user.setLastName(userDetails.getLastName());
//            return userRepository.save(user);
//        }
//        return null;
//    }
//
//    public void editPassword(User user) {
//
//    }
//
//    public User findByUsername(String username) {
//        Optional<User> object = userRepository.findByUsername(username);
//        return object.orElse(null);
//    }
//
//    public List<User> findAll() {
//        throw new NullPointerException();
//    }
//
//
//    public User disableAcc(String username) {
//        Optional<User> userObj = userRepository.findByUsername(username);
//        if (userObj.isPresent()) {
//            User user = userObj.get();
//            user.setEnabled(!user.isEnabled());
//            userRepository.save(user);
//
//            return new User();
//        }
//        return null;
//    }

}
