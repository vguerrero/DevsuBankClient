package com.bank.client.BankClient.controller;

import com.bank.client.BankClient.model.Client;
import com.bank.client.BankClient.service.ClientService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("clientes")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping
    public ResponseEntity<?> createClient(@RequestBody Client client) {
        return new ResponseEntity<>(clientService.createClient(client), HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteClient(@RequestParam long clientId) {
        try {
            clientService.deleteClient(clientId);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>("Error api deleteClient: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateClient(@RequestBody Client client) {
        return new ResponseEntity<>(clientService.updateClient(client), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Client>> getAccounts() {
        return new ResponseEntity<>(clientService.getAll(), HttpStatus.OK);
    }


}
