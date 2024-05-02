package com.bank.client.BankClient.service;

import com.bank.client.BankClient.model.Client;

import java.util.List;

public interface ClientService {

    Client createClient (Client c);
    void deleteClient (long clientid);
    Client updateClient (Client c);
    List<Client> getAll();

}
