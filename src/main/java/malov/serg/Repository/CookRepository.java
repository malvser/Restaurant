package malov.serg.Repository;

import malov.serg.Model.Cook;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CookRepository extends JpaRepository<Cook, Long> {

    @Query("SELECT c FROM Cook c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :pattern, '%'))")
    List<Cook> findByPattern(@Param("pattern") String pattern, Pageable pageable);
}
