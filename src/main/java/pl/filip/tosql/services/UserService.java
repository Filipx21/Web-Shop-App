package pl.filip.tosql.services;

import org.springframework.stereotype.Service;

@Service
public class UserService {

//    private UserRepository userRepository;
//    private UserRoleRepository userRoleRepository;
//    private PasswordEncoder passwordEncoder;
//
//    public UserService(UserRepository userRepository,
//        UserRoleRepository userRoleRepository,
//        PasswordEncoder passwordEncoder) {
//        this.userRepository = userRepository;
//        this.userRoleRepository = userRoleRepository;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//    public User register(User user) {
//        Optional<User> _user = userRepository.findByUsername(user.getUsername());
//        return _user.orElse(null);
//    }
//
//    public User editAccount(User user) {
//        Optional<User> _user = userRepository.findById(user.getId());
//        if (_user.isPresent()) {
//            String encode = passwordEncoder.encode(_user.get().getPassword());
//            User userIn = _user.get();
//            User edited = new User(userIn);
//            return userRepository.save(edited);
//        }
//        return null;
//    }
//
//    public List<User> findAllUsers() {
//        return userRepository.findAll();
//    }
//
//    public User findUserById(Long id) {
//        return userRepository.findById(id).orElse(null);
//    }
//
//    public User changeRole(User user, String role) {
//        Optional<User> _user = userRepository.findById(user.getId());
//        Optional<UserRole> _role = userRoleRepository.findByRole(role);
//        if (_user.isPresent() && _role.isPresent()) {
//            User existUser = _user.get();
//            UserRole userRole = _role.get();
//            existUser.setRole(userRole);
//            return userRepository.save(existUser);
//        }
//        return null;
//    }
//
//    public User status(String username) {
//        Optional<User> _user = userRepository.findByUsername(username);
//        if (_user.isPresent()) {
//            User existUser = _user.get();
//            existUser.setActive(!existUser.isActive());
//            return userRepository.save(existUser);
//        }
//        return null;
//    }

}
