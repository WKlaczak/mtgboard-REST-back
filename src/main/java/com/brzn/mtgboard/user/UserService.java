package com.brzn.mtgboard.user;

import com.brzn.mtgboard.exceptionHandler.SQLRecordNotUniqueException;
import com.brzn.mtgboard.security.Role;
import com.brzn.mtgboard.security.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.HashSet;

@Service
@Transactional
class UserService {

    private UserRepo userRepo;
    private RoleRepo roleRepo;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepo userRepo, RoleRepo roleRepo, BCryptPasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public User findByUserName(String username) {
        return userRepo.findByUsername(username);
    }

    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(1);
        Role userRole = roleRepo.findByName("ROLE_USER");
        user.setRoles(new HashSet<>(Arrays.asList(userRole))); //todo to jest chyba bezsensu
        userRepo.save(user);
    }

    public void saveUser(User user) throws SQLRecordNotUniqueException {
        if (isUsernameUnique(user.getUsername())) {
            throw new SQLRecordNotUniqueException("użytkownik o takiej nazwie już jest zarejestrowany");
        } else if (isUserEmailUnique(user.getEmail())) {
            throw new SQLRecordNotUniqueException("taki e-mail już jest zarejestrowany");
        }

        userRepo.save(user);
    }
//    private PasswordEncoder passwordEncoder;


//    private User getUserWithHashedPassword(User user) {
//
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        return user;
//    }

    private boolean isUsernameUnique(String username) {
        return userRepo.findByUsername(username) != null;
    }

    private boolean isUserEmailUnique(String email) {
        return userRepo.findByEmail(email) != null;
    }
}