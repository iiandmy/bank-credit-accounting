package by.bsuir.bankcreditaccounting.persistance;

import by.bsuir.bankcreditaccounting.domain.Credit;
import by.bsuir.bankcreditaccounting.domain.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CreditRepository extends JpaRepository<Credit, Long> {
    Optional<List<Credit>> findAllByStatusIs(Status status);
}
