package account.endpoint;

import account.domain.Account;
import account.domain.ExchangeRates;
import account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/account")
public class AccountController {
    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/store")
    public ResponseEntity storeReadings(@RequestBody Account account) {
            accountService.storeAccount(account);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/read/rates")
    public ResponseEntity readRates() {
        Optional opRates = accountService.getRates();
        return opRates.isPresent()
                ? ResponseEntity.ok(opRates.get())
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/find/{accountId}")
    public ResponseEntity findAccount(@PathVariable Long accountId) {
        Optional<Account> account = accountService.getAccount(accountId);
        Optional<ExchangeRates> rates = accountService.getRates();
        BigDecimal divider = BigDecimal.ONE;
        if (rates.isPresent()) {
            ExchangeRates exRates = (ExchangeRates)rates.get();
            divider = new BigDecimal((String)exRates.getRates().get("RON"));
        }
        if (account.isPresent()) {
            Account accountObj = account.get();
            accountObj.setBalance(accountObj.getBalance().divide(divider, RoundingMode.HALF_UP));
            return ResponseEntity.ok(accountObj);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
