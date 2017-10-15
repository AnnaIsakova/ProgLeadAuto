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
        if (client == null) throw new RuntimeException("Client you're trying to edit does not exist");
        return client.toDTO();
    }

    @Transactional
    public List<ClientDTO> getAll(){
        List<Client> clients = clientRepository.findAll();
        List<ClientDTO> clientDTOS = new ArrayList<>();
        for (Client client:clients) {
            clientDTOS.add(client.toDTO());
        }
        return clientDTOS;
    }

    @Transactional
    public List<ClientDTO> getAllClients(){
        List<Client> clients = clientRepository.findAllClients();
        List<ClientDTO> clientDTOS = new ArrayList<>();
        for (Client client:clients) {
            clientDTOS.add(client.toDTO());
        }
        return clientDTOS;
    }

    @Transactional
    public ClientDTO getById(long id){
        Client client = clientRepository.findOne(id);
        if (client == null) throw new RuntimeException("Client you're trying to get does not exist");
        return client.toDTO();
    }

    @Transactional
    public void create(ClientDTO clientDTO){
        if (hasSomeoneSamePhoneForCreating(
                clientDTO.getPhone(),
                clientDTO.getPhone2(),
                clientDTO.getPhone3())){
            throw new RuntimeException("Someone else has the same phone");
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
        if (client == null) throw new RuntimeException("Client you're trying to edit does not exist");
        if (hasSomeoneSamePhoneForEditing(
                clientDTO.getPhone(),
                clientDTO.getPhone2(),
                clientDTO.getPhone3(),
                clientDTO.getId())){
            throw new RuntimeException("Someone else has the same phone");
        }
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
        if (client == null) throw new RuntimeException("Client you're trying to delete does not exist");
        client.setDeleted(true);
        clientRepository.saveAndFlush(client);
    }

    private boolean hasSomeoneSamePhoneForEditing(String phone, String phone2, String phone3, long id){
        System.out.println(phone);
        System.out.println(phone2);
        System.out.println(phone3);
        System.out.println(id);
        if (phone != null) {
            phone = clientRepository.findPhone(phone, id);
            System.out.println("PHONE: " + phone);
        }
        if (phone2 != null){
            phone2 = clientRepository.findPhone(phone2, id);
            System.out.println("PHONE2: " + phone2);
        }
        if (phone3 != null){
            phone3 = clientRepository.findPhone(phone3, id);
            System.out.println("PHONE3: " + phone3);
        }

        return (phone != null || phone2 != null || phone3 != null);
    }

    private boolean hasSomeoneSamePhoneForCreating(String phone, String phone2, String phone3){
        Client client = null;
        if (phone != null) {
            client = clientRepository.findByPhone(phone);
        }
        Client client2 = null;
        if (phone2 != null){
            client2 = clientRepository.findByPhone(phone2);
        }
        Client client3 = null;
        if (phone3 != null){
            client3 = clientRepository.findByPhone(phone3);
        }
        return (client != null || client2 != null || client3 != null);
    }
}
