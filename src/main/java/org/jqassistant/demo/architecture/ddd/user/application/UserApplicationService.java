package org.jqassistant.demo.architecture.ddd.user.application;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.jqassistant.demo.architecture.ddd.user.domain.UserDomainService;
import org.jqassistant.demo.architecture.ddd.user.domain.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserApplicationService {

    private final UserDomainService userDomainService;

    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userDomainService.getAllUsers();
    }

    @Transactional
    public User create(User user) {
        return userDomainService.create(user);
    }

}
