package malov.serg.Repository;


import malov.serg.Model.CookedOrder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CookedOrderRepository extends JpaRepository<CookedOrder, Long> {

    @Query("SELECT c FROM CookedOrder c WHERE LOWER(c.date) LIKE LOWER(CONCAT('%', :pattern, '%'))")
    List<CookedOrder> findByPattern(@Param("pattern") String pattern, Pageable pageable);
}
