package by.bsuir.bankcreditaccounting.persistance;

import by.bsuir.bankcreditaccounting.domain.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends JpaRepository<Status, Long> {
}
