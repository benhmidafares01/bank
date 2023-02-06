package com.bank.repository;

import com.bank.models.Account;
import com.bank.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {
    List<Transaction> findByAccount(Account account);

}
