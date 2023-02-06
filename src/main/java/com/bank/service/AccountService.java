package com.bank.service;

import com.bank.models.Account;
import com.bank.models.User;
import com.bank.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    public Account findById(Long id) {
        return accountRepository.findById(id).get();
    }
    public Account findByAccountNumber(Long id) {
        return accountRepository.findByAccountNumber(id);
    }

    public Account save(Account account) {
        return accountRepository.save(account);
    }

    public void deleteById(Long id) {
        accountRepository.deleteById(id);
    }

    public Account findByAccountNumberAndUser(Long accountNumber, User user) {
        return accountRepository.findByAccountNumberAndUser(accountNumber, user);
    }
    public Account createAccount() {
        UUID uuid = UUID.randomUUID();
        Long uniqueLong = uuid.getMostSignificantBits();
        var account = Account.builder().balance(0.0).accountNumber(Math.abs(uniqueLong)).build();
        accountRepository.save(account);

        return accountRepository.findByAccountNumber(account.getAccountNumber());
    }
    public void withdraw(Long accountNumber, double amount,  User user) {
        Account account = findByAccountNumberAndUser(accountNumber, user);
        if(account.getBalance() < amount ){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not enough money in your demand deposit account!");
        }
        account.setBalance(account.getBalance() - amount);
        save(account);
    }


    public void deposit(Long accountNumber, double amount, User user) {
        Account account = findByAccountNumberAndUser(accountNumber, user);
        account.setBalance(account.getBalance() + amount);
        save(account);
    }


}
