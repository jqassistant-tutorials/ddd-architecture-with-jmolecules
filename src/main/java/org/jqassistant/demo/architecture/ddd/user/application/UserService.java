package org.jqassistant.demo.architecture.ddd.user.application;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.jqassistant.demo.architecture.ddd.user.domain.UserRepository;
import org.jqassistant.demo.architecture.ddd.user.domain.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository
                .findAll();
    }

    @Transactional
    public User create(String email, String firstName, String lastName) {
        User user = User.builder()
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .build();
        return userRepository.save(user);
    }

}
