package com.example.zadanie_29.config;

import com.example.zadanie_29.user.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        Optional<com.example.zadanie_29.user.User> userOptional = userRepository.findByEmail(userName);

        if(userOptional.isPresent()) {
            com.example.zadanie_29.user.User user = userOptional.get();
            Set<SimpleGrantedAuthority> roles = user.getRoles()
                    .stream()
                    .map(userRole -> new SimpleGrantedAuthority(userRole.getRole().name()))
                    .collect(Collectors.toSet());


            return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), roles);
        }


        throw new  UsernameNotFoundException("Username" + userName + "not found");

    }
}
