package account.endpoint;

import account.domain.Account;
import account.domain.ExchangeRates;
import account.service.AccountService;
import org.assertj.core.api.AssertionsForClassTypes;
import org.checkerframework.checker.units.qual.A;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

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
        Account account = new Account("IBAN123321", "RON", BigDecimal.valueOf(5000), Instant.now());
        account.setId(Long.valueOf(1));
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

