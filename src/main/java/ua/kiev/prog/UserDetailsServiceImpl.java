package ua.kiev.prog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private ClientService clientService;

    @Override
    public UserDetails loadUserByUsername(String phone)
            throws UsernameNotFoundException {
        Client client = clientService.findByPhone(phone);
        if (client == null)
            throw new UsernameNotFoundException(phone + " not found");

        Set<GrantedAuthority> roles = new HashSet<>();
        roles.add(new SimpleGrantedAuthority(client.getRole().toString()));

        return new User(client.getPhone(), client.getPassword(), roles);
    }
}