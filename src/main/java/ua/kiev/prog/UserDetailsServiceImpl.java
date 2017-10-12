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
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        ClientDTO client = clientService.findByPhone(phone);
        System.out.println("CLIENT loadUserByUsername " + client);
        if (client == null)
            throw new UsernameNotFoundException("User with phone " + phone + " not found");

        Set<GrantedAuthority> roles = new HashSet<>();
        roles.add(new SimpleGrantedAuthority(client.getRole().toString()));

        User user = new User(client.getPhone(), client.getPassword(), roles);
        System.out.println(user);
        return user;
    }
}
