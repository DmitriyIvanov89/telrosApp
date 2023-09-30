package com.divanov.telrosApp.services;

import com.divanov.telrosApp.entities.Role;
import com.divanov.telrosApp.entities.UserApp;
import com.divanov.telrosApp.repositories.RoleRepository;
import com.divanov.telrosApp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public List<UserApp> findAllUsers(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return userRepository.findAll();
        } else {
            return userRepository.search(stringFilter);
        }
    }

    public long countUser(UserApp userApp) {
        return userRepository.count();
    }

    public void deleteUser(UserApp userApp) {
        userRepository.delete(userApp);
    }

    public void saveUser(UserApp userApp) {
        if (userApp == null) {
            System.err.println("User data is null.");
            return;
        }
        userRepository.save(userApp);
    }

    public List<Role> findAllRoles() {
        return roleRepository.findAll();
    }
}
