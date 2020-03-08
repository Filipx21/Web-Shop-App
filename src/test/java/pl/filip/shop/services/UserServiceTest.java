package pl.filip.shop.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import pl.filip.shop.dto.PasswordUserDto;
import pl.filip.shop.model.SysUser;
import pl.filip.shop.repositories.SysUserRepository;
import pl.filip.shop.resource.DataTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    private DataTest dataTest;

    @BeforeEach
    void init() {
        dataTest = new DataTest();
    }

    @Test
    void shouldFindByEmail() throws NullPointerException {
        SysUser user = dataTest.prepareUser();

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        SysUser result = userService.findByEmail(user.getEmail());

        assertEquals(user, result);
    }

    @Test
    void shouldThrowNullPointerFindByEmail() throws NullPointerException {
        SysUser user = dataTest.prepareUser();

        when(userRepository.findByEmail(user.getEmail())).thenThrow(NullPointerException.class);

        assertThrows(NullPointerException.class, () ->
                userService.findByEmail(user.getEmail())
        );
    }

    @Test
    void shouldEditUser() {
        SysUser user = dataTest.prepareUser();

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        user.setCity("Krakow");
        when(userRepository.save(user)).thenReturn(user);

        SysUser result = userService.editUser(user, user.getEmail());

        assertEquals(user, result);

    }

    @Test
    void shouldLoadUserByUsername() {
        SysUser user = dataTest.prepareUser();

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        UserDetails userDetails = userService.loadUserByUsername(user.getEmail());

        assertEquals(user.getEmail(), userDetails.getUsername());
    }

    @Test
    void shouldThrowExeptionLoadUserByUsername() {
        SysUser user = dataTest.prepareUser();
        user.setInUse(false);

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        assertThrows(UsernameNotFoundException.class, () ->
                userService.loadUserByUsername(user.getEmail())
        ) ;
    }

    @Test
    void shouldNotFindThrowExceptionLoadUserByUsername() {
        SysUser user = dataTest.prepareUser();

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () ->
                userService.loadUserByUsername(user.getEmail())
        ) ;
    }

    @Test
    void shouldNotEditPassword() {
        SysUser user = dataTest.prepareUser();

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());

        assertThrows(NullPointerException.class, () ->
                userService.editPassword(user.getEmail(), new PasswordUserDto())
                );
    }

    @Test
    void shouldDeleteAcc() {
        SysUser user = dataTest.prepareUser();

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        assertTrue(userService.blockAcc(user.getId()));
    }

    @Test
    void shouldNotDeleteAcc() {
        SysUser user = dataTest.prepareUser();

        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());

        assertFalse(userService.blockAcc(user.getId()));
    }

    @Test
    void shouldBlockAcc() {
        SysUser user = dataTest.prepareUser();

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        boolean result = userService.blockAcc(user.getId());

        assertTrue(result);
    }

    @Test
    void shouldNotBlockAcc() {
        SysUser user = dataTest.prepareUser();

        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());

        boolean result = userService.blockAcc(user.getId());

        assertFalse(result);
    }

    @Test
    void shouldFindAll() {
        List<SysUser> users = List.of(
                dataTest.prepareUser(),
                dataTest.prepareUser(),
                dataTest.prepareUser(),
                dataTest.prepareUser()
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

}