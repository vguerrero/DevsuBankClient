package com.bank.client.BankClient.unittest.services;

import com.bank.client.BankClient.data.TestData;
import com.bank.client.BankClient.model.Client;
import com.bank.client.BankClient.repository.ClientRepository;
import com.bank.client.BankClient.service.ClientService;
import com.bank.client.BankClient.service.ClientServiceImpl;
import com.bank.client.BankClient.service.RabbitMqSender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


public class ClientServiceTest {

    ClientRepository clientRepository;
    ClientService clientService;
    RabbitMqSender rabbitMqSender;
    Client client1;


    @BeforeEach
    void setUp() {
        this.clientRepository = mock(ClientRepository.class);
        this.rabbitMqSender = mock(RabbitMqSender.class);
        this.clientService= new ClientServiceImpl(clientRepository, rabbitMqSender);
        this.client1 = TestData.client1();
    }

    @Test
    void testCreateClient() {
        Long clientId = 10L;
        doAnswer(invocation -> {
            Client clientDB = invocation.getArgument(0);
            clientDB.setId(10L);
            return clientDB;
        }).when(this.clientRepository).save(any(Client.class));

        Client saved = this.clientService.createClient(client1);
        assertNotNull(saved);
        assertNotNull(saved.getId());
        assertEquals(clientId, saved.getId());
        assertEquals(client1.getIdentification(), saved.getIdentification());
        assertEquals(client1.getName(), saved.getName());
    }

    @Test
    void testGetClientById(){
        Long clientId = 10L;
        when(this.clientRepository.getReferenceById(clientId)).thenReturn(client1);
        var myClient = this.clientService.getClientById(clientId);
        assertNotNull(myClient.getIdentification());
        assertEquals(clientId, myClient.getId());
        assertEquals(client1.getIdentification(), myClient.getIdentification());
        assertEquals(client1.getName(), myClient.getName());
    }

    @Test
    void testDeleteClient() {

        doAnswer(invocation -> {
            Client clientDB = invocation.getArgument(0);
            clientDB.setId(10L);
            return clientDB;
        }).when(this.clientRepository).save(any(Client.class));
        Client saved = this.clientService.createClient(client1);
        assertNotNull(saved);
        assertEquals(client1.getId(), saved.getId());
        //now delete the same client
        this.clientService.deleteClient(saved.getId());
        //find it will be null because it was delete
        var myClient = this.clientService.getClientById(saved.getId());
        assertEquals(myClient, null);
    }

    @Test
    void testUpdateClient() {
        long clientId = 10L;
        when(this.clientRepository.getReferenceById(clientId)).thenReturn(client1);
        var myClient = this.clientService.getClientById(clientId);
        //updating the name
        myClient.setName("Platon");
        this.clientService.updateClient(myClient);
        var platon = this.clientService.getClientById(clientId);
        assertEquals(platon.getName(),"Platon");
        assertNotEquals(TestData.client1().getName(),platon.getName());
    }

    @Test
    void testGetAll() {
        when(this.clientRepository.findAll()).thenReturn(TestData.clientList());
        var clientList = this.clientService.getAll();
        assertEquals(TestData.clientList().size(),clientList.size());
    }

}
