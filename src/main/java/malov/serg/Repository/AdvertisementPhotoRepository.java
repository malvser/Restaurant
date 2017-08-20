package malov.serg.Repository;


import malov.serg.Model.AdvertisementPhoto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdvertisementPhotoRepository extends JpaRepository<AdvertisementPhoto, Long> {

    @Query("SELECT c FROM AdvertisementPhoto c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :pattern, '%'))")
    List<AdvertisementPhoto> findByPattern(@Param("pattern") String pattern, Pageable pageable);


}
