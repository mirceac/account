package account.endpoint;

import account.domain.Account;
import account.domain.ExchangeRates;
import account.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Optional;


import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class AccountControllerTest {
    @Mock
    private AccountService accountService;

    private AccountController controller;

    @BeforeEach
    public void setUp() {

        Optional rates = Optional.of(new ExchangeRates(
                Instant.now(), "EUR", LocalDate.now(),
                new HashMap<String, String>() {{
                    put("RON", "5");
                    put("USD", "1.13");
                }}));
        Account account = new Account(Long.valueOf(1), "IBAN123321", "RON", BigDecimal.valueOf(5000), Instant.now());
        accountService = mock(AccountService.class);
        Mockito.when(accountService.getAccount(anyLong())).thenReturn(Optional.of(account));
        Mockito.when(accountService.getRates()).thenReturn(rates);
        controller = new AccountController(accountService);
    }

    @Test
    public void testFlow() throws Exception {
        ResponseEntity responseAccount = controller.findAccount(Long.valueOf(1));
        Account acc = (Account)responseAccount.getBody();
        assertThat(acc.getBalance()).isEqualTo(BigDecimal.valueOf(1000));
    }

}

