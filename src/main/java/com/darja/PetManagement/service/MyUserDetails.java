package com.darja.PetManagement.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.darja.PetManagement.dao.UserDao;
import com.darja.PetManagement.model.User;
import com.darja.PetManagement.model.UserPrincipal;


@Service
public class MyUserDetails implements UserDetailsService {

    @Autowired
    private UserDao repo;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user= repo.findByUsername(username);

        if (user==null) {
            System.out.println("User 404");
            throw new UsernameNotFoundException("User 404");
        }
        return new UserPrincipal(user);
    }

}
