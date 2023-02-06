package com.bank.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Builder
@AllArgsConstructor @NoArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date date;
    private String type;
    private double amount;
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
}
