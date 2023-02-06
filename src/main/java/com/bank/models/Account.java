package com.bank.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

import java.util.List;

@Entity
@Table(name="account")
@Getter@Setter
@Builder@NoArgsConstructor@AllArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long accountNumber;
    @Column
    private double balance;
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Transaction> transactions;
    @ManyToOne(fetch = FetchType.EAGER, optional = false,cascade=CascadeType.ALL)
    @JoinColumn(name = "client_id")
    private User user;
}
