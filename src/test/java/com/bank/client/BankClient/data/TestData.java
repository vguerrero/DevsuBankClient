package com.bank.client.BankClient.data;

import com.bank.client.BankClient.model.Client;
import com.bank.client.BankClient.model.Genre;

import java.util.ArrayList;
import java.util.List;

public class TestData {

    public static Client client1(){
        Client t = new Client();
        t.setGenre(Genre.MALE);
        t.setAge(22);
        t.setName("Homero");
        t.setAddress("5 avenue");
        t.setPassword("pira02");
        t.setIdentification("9875544");
        t.setId(10L);
        t.setState(true);
        return t;
    }

    public static Client client2(){
        Client t = new Client();
        t.setGenre(Genre.FEMALE);
        t.setAge(22);
        t.setName("Maria");
        t.setAddress("8 avenue");
        t.setPassword("pirs98");
        t.setIdentification("32556200");
        t.setId(2L);
        t.setState(true);
        return t;
    }

    public static List<Client> clientList(){
        List<Client> list = new ArrayList<>();
        list.add(client1());
        list.add(client2());
        return list;

    }

}
