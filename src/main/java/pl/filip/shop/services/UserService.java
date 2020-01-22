package pl.filip.shop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.filip.shop.dto.UserDto;
import pl.filip.shop.model.Role;
import pl.filip.shop.model.User;
import pl.filip.shop.repositories.UserRepository;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder encoder;

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

//    public void editPassword(User user) {
//    }

//    public User findByUsername(String username) {
//    }

//    public List<User> findAll() {
//    }

//    public User disableAcc(String username) {
//    }

}
