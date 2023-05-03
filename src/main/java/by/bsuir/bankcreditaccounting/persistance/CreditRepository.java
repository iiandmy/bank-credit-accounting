package by.bsuir.bankcreditaccounting.persistance;

import by.bsuir.bankcreditaccounting.domain.Credit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditRepository extends JpaRepository<Credit, Long> {
}
