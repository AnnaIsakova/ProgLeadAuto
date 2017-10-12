package ua.kiev.prog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
    public List<ClientDTO> getAll(){
        List<Client> clients = clientRepository.findAll();
        List<ClientDTO> clientDTOS = new ArrayList<>();
        for (Client client:clients) {
            if (client.getRole() != UserRole.ADMIN)
                clientDTOS.add(client.toDTO());
        }
        return clientDTOS;
    }

    @Transactional
    public void create(Client client){
        if (client.getPassword() != null){
            ShaPasswordEncoder encoder = new ShaPasswordEncoder();
            String passHash = encoder.encodePassword(client.getPassword(), null);
            client.setPassword(passHash);
        }
        clientRepository.saveAndFlush(client);
    }

    @Transactional
    public void delete(long id){
        Client client = clientRepository.findOne(id);
        System.out.println("CLIENT: " + client.getPhone());
        System.out.println("CLIENT: " + client.isDeleted());
        client.setDeleted(true);
        System.out.println(client.isDeleted());
        clientRepository.saveAndFlush(client);
    }
}
