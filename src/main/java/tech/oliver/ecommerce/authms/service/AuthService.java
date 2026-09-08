package tech.oliver.ecommerce.authms.service;

import tech.oliver.ecommerce.authms.User;
import tech.oliver.ecommerce.authms.repository.UserRepository;

public class AuthService {
    private UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean authenticate(String username, String password) {
        User user = userRepository.findByUsername(username);
        return user != null && user.isValidPassword(password);
    }

    public void register(String username, String password) {
        if (userRepository.findByUsername(username) != null) {
            throw new IllegalArgumentException("Usuário já existe");
        }
        userRepository.save(new User(username, password));
    }
}