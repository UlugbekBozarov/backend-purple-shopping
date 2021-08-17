package com.example.online_shopping.security;

import com.example.online_shopping.database.domain.Users;
import com.example.online_shopping.database.repository.UsersRepository;
import com.example.online_shopping.erroe.SystemError;
import com.example.online_shopping.erroe.message.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserPrincipalDetailsService implements UserDetailsService {

    private UsersRepository usersRepository;

    @Autowired
    public UserPrincipalDetailsService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            Users user = usersRepository.findByUsername(username);
            if (user == null) {
                throw new UsernameNotFoundException(username);
            }
            return new UserPrincipal(user);

        } catch (Exception e) {
            throw new SystemError("application users", 7, new ErrorMessage(450, "Database Connection Error!"));
        }
    }
}
