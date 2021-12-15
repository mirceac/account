package account.service;

import account.domain.Account;
import account.domain.ExchangeRates;
import account.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.Optional;

@Service
public class AccountService {
    private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(3);

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    private WebClient ratesClient;

    private Optional cachedRates = Optional.empty();

    public void storeAccount(Account account) {
        accountRepository.save(account);
    }

    public Optional getRates() {
        Optional rates = Optional.empty();
        try {
            ExchangeRates exRates = ratesClient.get()
                    .retrieve()
                    .bodyToMono(ExchangeRates.class)
                    .block(REQUEST_TIMEOUT);
            cachedRates = Optional.of(exRates);
            rates = cachedRates;
        } catch (Exception ex) {
            rates = cachedRates.isPresent() ? cachedRates : Optional.empty();
            System.out.println("Error getting rates");
        }

        return rates;
    }

    public Optional getCachedRates() {
        return cachedRates;
    }

    public void setCachedRates(Optional cachedRates) {
        this.cachedRates = cachedRates;
    }

    public Optional getAccount(Long accountId) {
        return accountRepository.findById(accountId);
    }
}
