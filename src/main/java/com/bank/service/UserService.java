package com.bank.service;

import com.bank.models.ERole;
import com.bank.models.Role;
import com.bank.models.User;
import com.bank.payload.request.SignupRequest;
import com.bank.repository.RoleRepository;
import com.bank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    AccountService accountService;

    public User createUser(SignupRequest signUpRequest){

        User user = User.builder()
                .username(signUpRequest.getUsername())
                .email(signUpRequest.getEmail())
                .password(encoder.encode(signUpRequest.getPassword()))
                .build();


        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;

                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        user.setAccounts(List.of(accountService.createAccount()));
        return userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).get();
    }

    public void changerMotDePasse(Long id, String nouveauMotDePasse) {
        User user = findById(id);
        user.setPassword(nouveauMotDePasse);
        userRepository.save(user);
    }

    public UserDetails currentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails;
    }
}
