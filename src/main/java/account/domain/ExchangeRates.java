package account.domain;

import java.time.Instant;
import java.time.LocalDate;
import java.util.HashMap;

public class ExchangeRates {
    private Instant timestamp;
    private String base;
    private LocalDate date;
    private HashMap<String,String> rates;

    public ExchangeRates() {

    }

    public ExchangeRates(Instant timestamp, String base, LocalDate date, HashMap<String, String> rates) {
        this.timestamp = timestamp;
        this.base = base;
        this.date = date;
        this.rates = rates;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public HashMap<String, String> getRates() {
        return rates;
    }

    public void setRates(HashMap<String, String> rates) {
        this.rates = rates;
    }
}
