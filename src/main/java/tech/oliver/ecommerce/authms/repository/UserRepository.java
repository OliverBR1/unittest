package tech.oliver.ecommerce.authms.repository;

import tech.oliver.ecommerce.authms.User;

public interface UserRepository {
    void save(User user);
    User findByUsername(String username);
}
