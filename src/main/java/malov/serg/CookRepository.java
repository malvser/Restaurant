package malov.serg;

import malov.serg.kitchen.Cook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CookRepository extends JpaRepository<Cook, Long> {
}
