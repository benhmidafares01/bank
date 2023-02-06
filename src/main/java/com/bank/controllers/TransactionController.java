package com.bank.controllers;

import com.bank.models.Account;
import com.bank.models.Transaction;
import com.bank.service.AccountService;
import com.bank.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;
    @Autowired
    private AccountService accountService;
    @GetMapping
    public List<Transaction> findAll() {
        return transactionService.findAll();
    }

    @GetMapping("/{id}")
    public Transaction findById(@PathVariable Long id) {return transactionService.findById(id);
    }

    @PostMapping
    public Transaction save(@RequestBody Transaction transaction) {
        return transactionService.save(transaction);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        transactionService.deleteById(id);
    }

    @GetMapping("/account/{compteId}")
    public List<Transaction> findByAccount(@PathVariable Long compteId) {
        Account account = accountService.findById(compteId);
        return transactionService.findByAccount(account);
    }
}
