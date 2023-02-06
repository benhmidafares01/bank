package com.bank;


import com.bank.models.Account;
import com.bank.models.Transaction;
import com.bank.repository.TransactionRepository;
import com.bank.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionService transactionService;

    private Account account;
    private Transaction transaction1;
    private Transaction transaction2;
    private List<Transaction> transactions;

    @BeforeEach
    void setUp() {
        account = new Account();
        transaction1 = new Transaction();
        transaction2 = new Transaction();
        transactions = Arrays.asList(transaction1, transaction2);
    }

    @Test
    void findAll_ReturnsTransactions() {
        when(transactionRepository.findAll()).thenReturn(transactions);

        List<Transaction> result = transactionService.findAll();

        assertThat(result).isEqualTo(transactions);
    }

    @Test
    void findById_ExistingId_ReturnsTransaction() {
        when(transactionRepository.findById(1L)).thenReturn(Optional.of(transaction1));

        Transaction result = transactionService.findById(1L);

        assertThat(result).isEqualTo(transaction1);
    }

    @Test
    void findById_NonExistingId_ReturnsNull() {
        when(transactionRepository.findById(1L)).thenReturn(Optional.empty());

        Transaction result = transactionService.findById(1L);

        assertThat(result).isNull();
    }

    @Test
    void save_ValidTransaction_ReturnsSavedTransaction() {
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction1);

        Transaction result = transactionService.save(transaction1);

        assertThat(result).isEqualTo(transaction1);
    }

    @Test
    void deleteById_ValidId_DeletesTransaction() {
        transactionService.deleteById(1L);

        verify(transactionRepository).deleteById(1L);
    }

    @Test
    void findByAccount_ReturnsTransactions() {
        when(transactionRepository.findByAccount(account)).thenReturn(transactions);

        List<Transaction> result = transactionService.findByAccount(account);

        assertThat(result).isEqualTo(transactions);
    }
}
