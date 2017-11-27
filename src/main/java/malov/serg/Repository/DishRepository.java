package malov.serg.Repository;


import malov.serg.Model.Dish;
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

    @Query("SELECT c FROM Dish c WHERE LOWER(c.type) LIKE LOWER(CONCAT('%', :pattern, '%'))")
    List<Dish> findByType(@Param("pattern") String pattern);

    @Query("SELECT c FROM Dish c WHERE (c.discount) > 0")
    List<Dish> findByDiscount();
}
