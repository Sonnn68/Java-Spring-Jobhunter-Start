package vn.hoidanit.jobhunter.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User handleCreateUser(User user) {
        return this.userRepository.save(user);
    }

    public void handleCreateUser(long id) {
        this.userRepository.deleteById(id);
    }

    public User fetchUserById(long id) {
        Optional<User> userOption = this.userRepository.findById(id);
        if (userOption.isPresent()) {
            return userOption.get();
        }
        return null;
    }

    public List<User> fechAllUsers() {
        return this.userRepository.findAll();
    }

    public User handleUdapteUser(User reqUser) {
        User currentUser = this.fetchUserById(reqUser.getId());
        if (currentUser != null) {
            currentUser.setEmail(reqUser.getEmail());
            currentUser.setName(reqUser.getName());
            currentUser.setPassword(reqUser.getPassword());
            // update
            currentUser = this.userRepository.save(currentUser);
        }
        return currentUser;
    }

    public User handleGetUserByUsername(String username) {
        return this.userRepository.findByEmail(username);
    }

}
