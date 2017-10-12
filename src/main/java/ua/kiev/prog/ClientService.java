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
    public ClientDTO getById(long id){
        Client client = clientRepository.findOne(id);
        if (client == null) return null;
        return client.toDTO();
    }

    @Transactional
    public void create(ClientDTO clientDTO){
        String phone = clientRepository.findPhone(clientDTO.getPhone());
        if (phone != null) {
            throw new RuntimeException("User with such phone already exists");
        }
        if (clientDTO.getPassword() != null){
            ShaPasswordEncoder encoder = new ShaPasswordEncoder();
            String passHash = encoder.encodePassword(clientDTO.getPassword(), null);
            clientDTO.setPassword(passHash);
        }
        Client client = Client.fromDTO(clientDTO);
        clientRepository.saveAndFlush(client);
    }

    @Transactional
    public void edit(ClientDTO clientDTO){
        Client client = clientRepository.findOne(clientDTO.getId());
        String phone = clientRepository.findPhone(clientDTO.getPhone());
        if (phone != null) {
            throw new RuntimeException("User with such phone already exists");
        }
        if (client == null) return;
        client.setName(clientDTO.getName());
        client.setSurname(clientDTO.getSurname());
        client.setPhone(clientDTO.getPhone());
        client.setPhone2(clientDTO.getPhone2());
        client.setPhone3(clientDTO.getPhone3());
        client.setSkype(clientDTO.getSkype());
        client.setFacebook(clientDTO.getFacebook());
        client.setEmail(clientDTO.getEmail());
        clientRepository.saveAndFlush(client);
    }

    @Transactional
    public void delete(long id){
        Client client = clientRepository.findOne(id);
        client.setDeleted(true);
        clientRepository.saveAndFlush(client);
    }

}
