package malov.serg.Repository;

import malov.serg.Model.NoAdvertisement;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoAdvertisementRepository extends JpaRepository<NoAdvertisement, Long> {

    @Query("SELECT c FROM NoAdvertisement c WHERE LOWER(c.date) LIKE LOWER(CONCAT('%', :pattern, '%'))")
    List<NoAdvertisement> findByPattern(@Param("pattern") String pattern, Pageable pageable);
}
