package com.bank.client.BankClient.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="client")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Client extends Person{


    private String password;

    private boolean state;



    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id='" + super.getId() + '\'' +
                "getIdentification='" + super.getIdentification() + '\'' +
                ", state=" + state +
                '}';
    }
}
