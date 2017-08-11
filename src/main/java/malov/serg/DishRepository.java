package malov.serg;


import malov.serg.kitchen.Dish;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DishRepository extends JpaRepository<Dish, Long> {

    @Query("SELECT c FROM Dish c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :pattern, '%'))")
    List<Dish> findByPattern(@Param("pattern") String pattern, Pageable pageable);
}
