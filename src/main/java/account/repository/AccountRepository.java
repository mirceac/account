package account.repository;

import account.domain.Account;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AccountRepository extends CrudRepository<Account, Long> {
    public Account save(Account account);
    public Optional<Account> findById(Long id);
    public Iterable<Account> findAll();
    public void deleteAllById(Iterable<? extends Long> longs);
}
