package malov.serg.Service;

import malov.serg.Model.NoAdvertisement;
import malov.serg.Repository.NoAdvertisementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@ComponentScan(value = "malov.serg")
public class NoAdvertisementService {

    @Autowired
    private NoAdvertisementRepository noAdvertisementRepository;

    @Transactional
    public void addNoAdvertisement(NoAdvertisement noAdvertisement) {
        try {
            noAdvertisementRepository.save(noAdvertisement);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Transactional(readOnly = true)
    public List<NoAdvertisement> findByPattern(String pattern, Pageable pageable) {
           return noAdvertisementRepository.findByPattern(pattern, pageable);
    }

    @Transactional
    public void deleteNoAdvertisement(long[] idList) {
        for (long id : idList) {
            noAdvertisementRepository.delete(id);
        }
    }

    @Transactional(readOnly = true)
    public long count() {
        return noAdvertisementRepository.count();
    }

    @Transactional(readOnly = true)
    public List<NoAdvertisement> findAll(Pageable pageable) {
        return noAdvertisementRepository.findAll(pageable).getContent();
    }

    @Transactional(readOnly = true)
    public List<NoAdvertisement> findAll() {
        return noAdvertisementRepository.findAll();
    }

}
