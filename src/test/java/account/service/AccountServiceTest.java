package account.service;

import account.domain.Account;
import account.domain.ExchangeRates;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Optional;


import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


@SpringBootTest
@AutoConfigureMockMvc

@TestPropertySource(
        locations = "classpath:test-application.properties")
public class AccountServiceTest {
    @Autowired
    private AccountService accountService;

    private Optional rates = Optional.empty();

    @BeforeEach
    public void setUp() {
        Account account = new Account("IBAN123321", "RON", BigDecimal.valueOf(5000), Instant.now());
        account.setId(Long.valueOf(1));
        accountService.storeAccount(account);
        accountService.setCachedRates(Optional.empty());
    }

    @Test
    public void accountNotFound() throws Exception {
        Optional<Account> account = accountService.getAccount(Long.valueOf(5));
        assertThat(account.isPresent()).isFalse();
    }

    @Test
    public void accountFoundRatesNotCached() throws Exception {
        Optional<Account> account = accountService.getAccount(Long.valueOf(1));
        assertThat(account.isPresent()).isTrue();
        assertThat(accountService.getCachedRates().isPresent()).isFalse();
    }

    @Test
    public void accountFoundRatesCached() throws Exception {
        Optional<Account> account = accountService.getAccount(Long.valueOf(1));
        assertThat(account.isPresent()).isTrue();
        accountService.getRates();
        assertThat(accountService.getCachedRates().isPresent()).isTrue();
    }

    public void testFlow() throws Exception {
        rates = Optional.of(new ExchangeRates(
                Instant.now(), "EUR", LocalDate.now(),
                new HashMap<String, String>() {{
                    put("EUR", "5");
                    put("USD", "4");
                }}));
    }

}
