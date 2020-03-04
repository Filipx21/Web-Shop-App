package pl.filip.webShop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.filip.webShop.dto.PasswordUserDto;
import pl.filip.webShop.dto.UserDto;
import pl.filip.webShop.model.Role;
import pl.filip.webShop.model.SysUser;
import pl.filip.webShop.repositories.SysUserRepository;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private SysUserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder encoder;

    public UserService(SysUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public SysUser findByEmail(String email) {
        Optional<SysUser> object = userRepository.findByEmail(email);
        return object.orElse(null);
    }

    public SysUser editUser(SysUser sysUser, String user_email) {
        Optional<SysUser> userObj = userRepository.findByEmail(user_email);
        if (userObj.isPresent()) {
            SysUser toEdit = userObj.get();
            toEdit.setFirstName(sysUser.getFirstName());
            toEdit.setLastName(sysUser.getLastName());
            toEdit.setAddress(sysUser.getAddress());
            toEdit.setPostCode(sysUser.getPostCode());
            toEdit.setCity(sysUser.getCity());
            return userRepository.save(toEdit);
        }
        return null;
    }

    public SysUser save(UserDto userDto) {
        SysUser sysUser = new SysUser();
        sysUser.setFirstName(userDto.getFirstName());
        sysUser.setLastName(userDto.getLastName());
        sysUser.setAddress(userDto.getAddress());
        sysUser.setPostCode(userDto.getPostCode());
        sysUser.setCity(userDto.getCity());
        sysUser.setEmail(userDto.getEmail());
        sysUser.setPassword(encoder.encode(userDto.getPassword()));
        sysUser.setRoles(Collections.singletonList(new Role("ROLE_USER")));
        sysUser.setInUse(true);
        return userRepository.save(sysUser);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<SysUser> object = userRepository.findByEmail(email);
        if (!object.isPresent()) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        SysUser sysUser = object.get();
        if (!sysUser.isInUse()) {
            throw new UsernameNotFoundException("Konto zablokowane.");
        }
        return new org.springframework.security.core.userdetails.User(
                sysUser.getEmail(),
                sysUser.getPassword(),
                mapRolesToAuthorities(sysUser.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

    public SysUser editPassword(String user_email, PasswordUserDto passwordUserDto) {
        Optional<SysUser> userObj = userRepository.findByEmail(user_email);
        if (userObj.isPresent()) {
            SysUser sysUser = userObj.get();
            sysUser.setPassword(encoder.encode(passwordUserDto.getPassword()));
            return userRepository.save(sysUser);
        }
        throw new NullPointerException();
    }

    public boolean deleteAcc(String user_email) {
        Optional<SysUser> userObj = userRepository.findByEmail(user_email);
        if (userObj.isPresent()) {
            SysUser sysUser = userObj.get();
            sysUser.setInUse(false);
            userRepository.save(sysUser);
            return true;
        }
        return false;
    }

    public boolean blockAcc(Long id) {
        Optional<SysUser> userObj = userRepository.findById(id);
        if (userObj.isPresent()) {
            SysUser sysUser = userObj.get();
            sysUser.setInUse(!sysUser.isInUse());
            userRepository.save(sysUser);
            return true;
        }
        return false;
    }

    public List<SysUser> findAll() {
        return userRepository.findAll();
    }

}
