package com.bank.client.BankClient.integrationtest.controllers;

import com.bank.client.BankClient.data.TestData;
import com.bank.client.BankClient.model.Client;
import com.bank.client.BankClient.repository.ClientRepository;
import com.bank.client.BankClient.service.ClientService;
import com.bank.client.BankClient.service.ClientServiceImpl;
import com.bank.client.BankClient.service.RabbitMqSender;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ClientRepository clientRepository;

    ClientService clientService;
    RabbitMqSender rabbitMqSender;

    private static final String CLIENTPATH = "/clientes";

    @BeforeEach
    void setUp() {
        this.rabbitMqSender = mock(RabbitMqSender.class);
        this.clientService = new ClientServiceImpl(clientRepository, rabbitMqSender);
    }

    @Test
    @Order(1)
    public void getOneClientTest() throws Exception {
        mockMvc.perform(get(CLIENTPATH)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Maria"));
        assert (clientRepository.findById(1L).isPresent());
    }

    @Test
    @Order(2)
    public void createClientTest() throws Exception {
        Client client = TestData.client1();
        ObjectMapper mapper = new ObjectMapper();
        mockMvc.perform(post(CLIENTPATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(client))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(4))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Homero"));
    }

    @Test
    @Order(3)
    public void deleteClientTest() throws Exception {
        mockMvc.perform(delete(CLIENTPATH + "?clientId=4")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        assertThat(clientRepository.findAll()).hasSize(3);
    }

    @Test
    @Order(4)
    public void updateClientTest() throws Exception {
        //getting real client for update
        Client client = clientRepository.getReferenceById(1L);
        client.setAddress("changed address");
        ObjectMapper mapper = new ObjectMapper();
        mockMvc.perform(put(CLIENTPATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(client))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address").value("changed address"));
    }


}
