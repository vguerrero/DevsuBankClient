package com.bank.client.BankClient.service;

import com.bank.client.BankClient.model.Client;
import com.bank.client.BankClient.model.ClientMessageDTO;
import com.bank.client.BankClient.repository.ClientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService{

    private static final Logger logger = LoggerFactory.getLogger(ClientServiceImpl.class);

    private ClientRepository repository;

    private RabbitMqSender rabbitMqSender;

    public ClientServiceImpl(ClientRepository repository,RabbitMqSender rabbitMqSender) {
        this.repository = repository;
        this.rabbitMqSender=rabbitMqSender;
    }

    @Override
    public Client createClient(Client c) {
        return repository.save(c);
    }

    @Override
    public Client getClientById(long id) {
        return repository.getReferenceById(id);
    }

    @Override
    public void deleteClient(long c) {
        Client f = repository.getReferenceById(c);
        if(f != null) {
            repository.delete(f);
            logger.info("sending to rabitmq the client: " + f);
            ClientMessageDTO clientMessageDTO = new ClientMessageDTO();
            clientMessageDTO.setClientId(f.getId());
            rabbitMqSender.send(clientMessageDTO);
            logger.info("to delete his accounts ");
        }
    }

    @Override
    public Client updateClient(Client c) {
        Client f = repository.getById(c.getId());
        if(f != null){
            f.setPassword(c.getPassword());
            f.setAge(c.getAge());
            f.setAddress(c.getAddress());
            f.setGenre(c.getGenre());
            return repository.save(f);
        }
        return null;
    }

    @Override
    public List<Client> getAll() {
        return repository.findAll();
    }
}
