package ua.kiev.prog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Transactional(readOnly = true)
    public Client findByPhone(String phone) {
        return clientRepository.findByPhone(phone);
    }

    @Transactional
    public void create(Client client){
        clientRepository.saveAndFlush(client);
    }
}
