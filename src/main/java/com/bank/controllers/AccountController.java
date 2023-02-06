package com.bank.controllers;

import com.bank.models.Account;
import com.bank.models.User;
import com.bank.service.AccountService;
import com.bank.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {
   @Autowired
    private AccountService accountService;

    @Autowired
    private UserService userService;


    @GetMapping
    public List<Account> findAll() {
        return accountService.findAll();
    }

    @GetMapping("/{id}")
    public Account findById(@PathVariable Long id) {
        return accountService.findById(id);
    }

    @PostMapping
    public Account save(@RequestBody Account account) {
        return accountService.save(account);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        accountService.deleteById(id);
    }


    @PutMapping("/withdraw/{accountNumber}/{amount}")
    public void withdraw(@PathVariable Long accountNumber, @PathVariable double amount) {
        var currentUser =  userService.currentUser();
        User user = userService.findByUsername(currentUser.getUsername());
        accountService.withdraw(accountNumber, amount, user);
    }

    @PutMapping("/deposit/{accountNumber}/{amount}")
    public void deposit(@PathVariable Long accountNumber, @PathVariable double amount) {
        Account account = accountService.findByAccountNumber(accountNumber);
        User user = account.getUser();
        accountService.deposit(accountNumber, amount, user);
    }




}
