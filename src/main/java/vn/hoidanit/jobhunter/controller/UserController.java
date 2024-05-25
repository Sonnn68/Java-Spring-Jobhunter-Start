package vn.hoidanit.jobhunter.controller;

import org.springframework.web.bind.annotation.RestController;
import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.service.UserService;
import vn.hoidanit.jobhunter.until.error.IdInvalidException;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
public class UserController {

    private final UserService userService;
    // gọi đến Security password để có thể ghi đè mk
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/users")
    public ResponseEntity<User> createNewUser(@RequestBody User postManUser) {
        // dùng để tạo ra phần mã hóa và ghi đè trước khi lưu vào data
        String hashPassword = this.passwordEncoder.encode(postManUser.getPassword());
        postManUser.setPassword(hashPassword);
        User ericUser = this.userService.handleCreateUser(postManUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(ericUser);
    }

    // Dùng để ra lỗi 400
    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUSer(@PathVariable("id") long id)
            throws IdInvalidException {
        if (id >= 1500) {
            throw new IdInvalidException("id khong lon hon 1501");
        }
        this.userService.handleCreateUser(id);
        return ResponseEntity.ok("SunUser");
        // return ResponseEntity.status(HttpStatus.OK).body("SunUser");
    }

    // fecht user by i
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") long id) {
        User fetchUser = this.userService.fetchUserById(id);
        return ResponseEntity.ok(fetchUser);
        // return ResponseEntity.status(HttpStatus.ok).body(fetchedUser);
    }

    // fecht all users
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(this.userService.fechAllUsers());
        // return
        // ResponseEntity.status(HttpStatus.OK).body(this.userService.fechAllUsers());
    }

    @PutMapping("/users")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        User update = this.userService.handleUdapteUser(user);
        return ResponseEntity.ok(update);
    }

}
