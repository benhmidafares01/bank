package com.bank.repository;

import com.bank.models.Account;
import com.bank.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    List<Account> findByUser(Account account);
    Account findByAccountNumberAndUser(Long accountNumber, User user);
    Account findByAccountNumber(Long accountNumber);

}
