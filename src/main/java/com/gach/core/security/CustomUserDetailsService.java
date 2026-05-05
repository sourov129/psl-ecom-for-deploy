package com.gach.core.security;

import com.gach.core.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import com.gach.core.entity.Admin;
import com.gach.core.repository.AdminRepository;

import java.util.Optional;

@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("Loading user details for: {}", username);
        
        // Try to find user first
        Optional<com.gach.core.entity.User> user = userRepository.findByEmail(username);
        if(user.isPresent()) {
            log.info("✅ Found regular User: {}", username);
            return User.builder()
                    .username(user.get().getEmail())
                    .password(user.get().getPassword())
                    .roles("USER")
                    .build();
        }
        
        // Try to find admin
        log.debug("User not found, searching for admin: {}", username);
        Optional<Admin> merchant = adminRepository.findByEmail(username);
        if(merchant.isPresent()) {
            log.info("✅ Found admin user: {}", username);
            return User.builder()
                    .username(merchant.get().getEmail())
                    .password(merchant.get().getPassword())
                    .roles("ADMIN", "USER")
                    .build();
        }
        
        log.error("❌ User not found in either table: {}", username);
        throw new UsernameNotFoundException("User or Admin not found: " + username);
    }
}
