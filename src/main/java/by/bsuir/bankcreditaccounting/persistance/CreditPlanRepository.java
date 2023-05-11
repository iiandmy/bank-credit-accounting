package by.bsuir.bankcreditaccounting.persistance;

import by.bsuir.bankcreditaccounting.domain.CreditPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CreditPlanRepository extends JpaRepository<CreditPlan, Long> {
    Optional<List<CreditPlan>> findAllByRate(Double rate);
    Optional<List<CreditPlan>> findAllByCreditAmount(Double amount);
}
