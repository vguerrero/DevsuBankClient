package com.bank.client.BankClient.service;

import com.bank.client.BankClient.model.Client;
import com.bank.client.BankClient.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService{

    private ClientRepository repository;

    public ClientServiceImpl(ClientRepository repository) {
        this.repository = repository;
    }

    @Override
    public Client createClient(Client c) {
        return repository.save(c);
    }

    @Override
    public void deleteClient(long c) {
        Client f = repository.getById(c);
        if(f != null)
            repository.delete(f);
    }

    @Override
    public Client updateClient(Client c) {
        Client f = repository.getById(c.getId());
        if(f != null){
            f.setClientId(c.getClientId());
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
