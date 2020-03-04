package pl.filip.shop.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pl.filip.shop.dto.PasswordUserDto;
import pl.filip.shop.dto.UserDto;
import pl.filip.shop.model.Role;
import pl.filip.shop.model.SysUser;
import pl.filip.shop.repositories.SysUserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {


    @Mock
    SysUserRepository userRepository;

    @InjectMocks
    UserService userService;

    @Test
    void shouldFindByEmail() throws NullPointerException {
        SysUser user = prepareUser();

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        SysUser result = userService.findByEmail(user.getEmail());

        assertEquals(user, result);
    }

    @Test
    void shouldThrowNullPointerFindByEmail() throws NullPointerException {
        SysUser user = prepareUser();

        when(userRepository.findByEmail(user.getEmail())).thenThrow(NullPointerException.class);

        assertThrows(NullPointerException.class, () ->
                userService.findByEmail(user.getEmail())
        );
    }

    @Test
    void shouldEditUser() {
        SysUser user = prepareUser();

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        user.setCity("Krakow");
        when(userRepository.save(user)).thenReturn(user);

        SysUser result = userService.editUser(user, user.getEmail());

        assertEquals(user, result);

    }

    @Test
    void shouldSave() {
//        SysUser user = prepareUser();
//        user.setRoles(Collections.singletonList(new Role("ROLE_USER")));
//        user.setInUse(true);
//        UserDto userDto = prepareUserDto(user);
//
//        when(userRepository.save(user)).thenReturn(user);
//
//        SysUser result = userService.save(userDto);
//
//        System.out.println(result);
//
//        assertEquals(user, result);
    }

    @Test
    void shouldLoadUserByUsername() {
        SysUser user = prepareUser();

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        UserDetails userDetails = userService.loadUserByUsername(user.getEmail());

        assertEquals(user.getEmail(), userDetails.getUsername());
    }

    @Test
    void shouldThrowExeptionLoadUserByUsername() {
        SysUser user = prepareUser();
        user.setInUse(false);

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        assertThrows(UsernameNotFoundException.class, () ->
                userService.loadUserByUsername(user.getEmail())
        ) ;
    }

    @Test
    void shouldNotFindThrowExceptionLoadUserByUsername() {
        SysUser user = prepareUser();

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () ->
                userService.loadUserByUsername(user.getEmail())
        ) ;
    }

    @Test
    void shouldEditPassword() {
//        PasswordUserDto passwordUserDto = new PasswordUserDto();
//        passwordUserDto.setPassword("superMocneHaslo22");
//        SysUser user = prepareUser();
//
//        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
//        when(userRepository.save(user)).thenReturn(user);
//
//        user.setPassword(encoder.encode(passwordUserDto.getPassword()));
//
//        SysUser result = userService.editPassword(user.getEmail(), passwordUserDto);
//
//        assertEquals(user, result);
    }

    @Test
    void shouldNotEditPassword() {
        SysUser user = prepareUser();

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());

        assertThrows(NullPointerException.class, () ->
                userService.editPassword(user.getEmail(), new PasswordUserDto())
                );
    }

    @Test
    void shouldDeleteAcc() {
        SysUser user = prepareUser();

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        assertTrue(userService.blockAcc(user.getId()));
    }

    @Test
    void shouldNotDeleteAcc() {
        SysUser user = prepareUser();

        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());

        assertFalse(userService.blockAcc(user.getId()));
    }

    @Test
    void shouldBlockAcc() {
        SysUser user = prepareUser();

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        boolean result = userService.blockAcc(user.getId());

        assertTrue(result);
    }

    @Test
    void shouldNotBlockAcc() {
        SysUser user = prepareUser();

        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());

        boolean result = userService.blockAcc(user.getId());

        assertFalse(result);
    }

    @Test
    void shouldFindAll() {
        List<SysUser> users = List.of(
                prepareUser(),
                prepareUser(),
                prepareUser(),
                prepareUser()
        );

        when(userRepository.findAll()).thenReturn(users);

        List<SysUser> result = userService.findAll();

        assertEquals(users, result);
    }

    @Test
    void shouldNotFindAll() {
        List<SysUser> users = new ArrayList<>();

        when(userRepository.findAll()).thenReturn(users);

        List<SysUser> result = userService.findAll();

        assertEquals(users, result);
    }

    private UserDto prepareUserDto(SysUser user){
        UserDto userDto = new UserDto();

        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setAddress(user.getAddress());
        userDto.setPostCode(user.getPostCode());
        userDto.setCity(user.getCity());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());

        return userDto;
    }

    private SysUser prepareUser() {
        SysUser user = new SysUser();
        Random random = new Random();
        String[] firstNames = new String[] {"Eustachy","Gienia","Ewelina","Kornel","Bartek"};
        String[] lastNames = new String[] {"Kwadrat","Giwera","Maven","Konik","Ura"};
        String[] addresses = new String[] {"Kornicza 2","Ernesta 33","Mavena 3","Konika 66","Orki 99"};
        String[] cities = new String[] {"Wroclaw","Warszawa","Gdansk","Gdynia","Zakopane"};
        String[] emails = new String[] {"test1@gmail.com","test2@gmail.com","test3@gmail.com","test4@gmail.com","test5@gmail.com"};
        Collection roles = new ArrayList();
        roles.add(prepareRole());

        user.setId(Long.valueOf(random.nextInt(1000)));
        user.setFirstName(firstNames[random.nextInt(5)]);
        user.setLastName(lastNames[random.nextInt(5)]);
        user.setAddress(addresses[random.nextInt(5)]);
        user.setPostCode("22-321");
        user.setCity(cities[random.nextInt(5)]);
        user.setEmail(emails[random.nextInt(5)]);
        user.setPassword("123");
        user.setInUse(true);
        user.setRoles(roles);

        return user;
    }

    private Role prepareRole() {
        Role role = new Role();
        String[] roles = new String[] {"ADMIN", "USER"};
        Random random = new Random();
        role.setId(Long.valueOf(random.nextInt(1000)));
        role.setName(roles[random.nextInt(2)]);
        return role;
    }
}