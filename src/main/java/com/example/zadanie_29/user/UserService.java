package com.example.zadanie_29.user;

import com.example.zadanie_29.mail.MailSenderService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;


@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final MailSenderService mailSenderService;


    public UserService(PasswordEncoder passwordEncoder,
                       UserRepository userRepository,
                       MailSenderService mailSenderService) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.mailSenderService = mailSenderService;
    }

    public void registerUser(String username, String rawPassword) {
        User userToAdd = new User();

        userToAdd.setEmail(username);
        String encryptedPassword = passwordEncoder.encode(rawPassword);
        userToAdd.setPassword(encryptedPassword);

        List<UserRole> list = Collections.singletonList(new UserRole(userToAdd, Role.ROLE_USER));
        userToAdd.setRoles(new HashSet<>(list));

        userRepository.save(userToAdd);
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    public List<User> findAllWithoutCurrentUser() {
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();

        return userRepository.findAll()
                .stream()
                .filter(user -> !user.getEmail().equals(currentUser.getName()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void addAdminUserRoleByID(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            UserRole userRole1 = new UserRole(user, Role.ROLE_ADMIN);
            UserRole userRole2 = new UserRole(user, Role.ROLE_USER);

            user.addRole(userRole1);
            user.addRole(userRole2);
        }
    }

    @Transactional
    public void deleteAdminUserRoleByID(Long id) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.deleteRole(user);
        }
    }

    public void sendPasswordResetLink(String email) {
        userRepository.findByEmail(email).ifPresent(user -> {

            String key = UUID.randomUUID().toString();

            user.setPasswordResetKey(key);
            userRepository.save(user);

            try {
                mailSenderService.sendPasswordResetLink(email, key);
            } catch (MessagingException e) {
                e.printStackTrace();
            }

        });

    }

    public void updateUserPassword(String key, String password) {
         userRepository.findByPasswordResetKey(key).ifPresent(
                 user -> {
                     user.setPassword(passwordEncoder.encode(password));
                     user.setPasswordResetKey(null);
                     userRepository.save(user);
                 }
         );
    }
}
