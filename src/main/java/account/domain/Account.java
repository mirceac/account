package account.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "account")
public class Account {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String iban;
    private String currency;
    private BigDecimal balance;
    private Instant lastUpdateDate;

    public Account() {
    }

    public Account(Long id, String iban, String currency, BigDecimal balance, Instant lastUpdateDate) {
        this.id = id;
        this.iban = iban;
        this.currency = currency;
        this.balance = balance;
        this.lastUpdateDate = lastUpdateDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIban() {
        return iban;
    }

    public String getCurrency() {
        return currency;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public Instant getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public void setLastUpdateDate(Instant lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }
}
