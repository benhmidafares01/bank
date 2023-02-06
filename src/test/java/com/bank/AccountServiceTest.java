package com.bank;

import com.bank.models.Account;
import com.bank.models.User;
import com.bank.repository.AccountRepository;
import com.bank.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AccountServiceTest {

    @InjectMocks
    private AccountService accountService;

    @Mock
    private AccountRepository accountRepository;

    private User user;
    private Account account ;
    @BeforeEach
    public void setUp() {
        user = new User();
         account = Account.builder()
                .accountNumber(123456L)
                .balance(100.0)
                .user(user)
                .build();
    }
    @Test
    public void testFindAll() {
        List<Account> expectedAccounts = Arrays.asList(account);
        when(accountRepository.findAll()).thenReturn(expectedAccounts);

        List<Account> actualAccounts = accountService.findAll();

        assertEquals(expectedAccounts, actualAccounts);
    }

    @Test
    public void testFindById() {
        Account expectedAccount = account;
        when(accountRepository.findById(1L)).thenReturn(Optional.of(expectedAccount));

        Account actualAccount = accountService.findById(1L);

        assertEquals(expectedAccount, actualAccount);
    }

    @Test
    public void testFindByAccountNumber() {
        Account expectedAccount = account;
        when(accountRepository.findByAccountNumber(123456L)).thenReturn(expectedAccount);

        Account actualAccount = accountService.findByAccountNumber(123456L);

        assertEquals(expectedAccount, actualAccount);
    }

    @Test
    public void testSave() {
        Account expectedAccount = account;
        when(accountRepository.save(expectedAccount)).thenReturn(expectedAccount);

        Account actualAccount = accountService.save(account);

        assertEquals(account, actualAccount);
    }


    @Test
    public void testDeleteById() {
        accountService.deleteById(123456L);

        verify(accountRepository, times(1)).deleteById(123456L);
    }

    @Test
    public void testFindByAccountNumberAndUser() {
        Account expectedAccount = account;

        when(accountRepository.findByAccountNumberAndUser(123456L, user)).thenReturn(expectedAccount);

        Account actualAccount = accountService.findByAccountNumberAndUser(123456L, user);

        assertEquals(expectedAccount, actualAccount);
    }

    @Test
    public void testWithdraw_notEnoughMoney() {
        when(accountRepository.findByAccountNumberAndUser(123456L, user)).thenReturn(account);
        double amount = 200.0;
        assertThrows(ResponseStatusException.class, () -> accountService.withdraw(123456L, amount, user));
    }

    @Test
    public void testDeposit() {
        when(accountRepository.findByAccountNumberAndUser(123456L, user)).thenReturn(account);
        double amount = 50.0;
        accountService.deposit(123456L, amount, user);
        verify(accountRepository, times(1)).save(account);
        assertEquals(150.0, account.getBalance(), 0.0);
    }




}
