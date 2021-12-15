package account;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;

@Configuration
public class ApplicationDataConfiguration {
    @Bean
    public WebClient ratesClient() {
        return WebClient.create("http://api.exchangeratesapi.io/latest?access_key=b1744fad4506dc47e3b6cb05f40790cb");
    }
}
