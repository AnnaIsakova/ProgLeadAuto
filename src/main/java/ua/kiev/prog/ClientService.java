package ua.kiev.prog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Transactional(readOnly = true)
    public ClientDTO findByPhone(String phone) {
        Client client = clientRepository.findByPhone(phone);
        if (client == null) return null;
        return client.toDTO();
    }

    @Transactional
    public void create(Client client){
        ShaPasswordEncoder encoder = new ShaPasswordEncoder();
        String passHash = encoder.encodePassword(client.getPassword(), null);
        client.setPassword(passHash);
        clientRepository.saveAndFlush(client);
    }
}
