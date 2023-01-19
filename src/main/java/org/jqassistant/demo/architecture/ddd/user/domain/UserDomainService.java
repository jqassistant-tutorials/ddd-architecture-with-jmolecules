package org.jqassistant.demo.architecture.ddd.user.domain;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.jqassistant.demo.architecture.ddd.user.domain.model.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDomainService {

    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User create(User user) {
        return userRepository.save(user);
    }

}
