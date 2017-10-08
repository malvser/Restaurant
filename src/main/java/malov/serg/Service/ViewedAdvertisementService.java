package malov.serg.Service;

import malov.serg.Model.ViewedAdvertisement;
import malov.serg.Repository.ViewedAdvertisementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@ComponentScan(value = "malov.serg")
public class ViewedAdvertisementService {

    @Autowired
    private ViewedAdvertisementRepository viewedAdvertisementRepository;


    @Transactional
    public void addViewedAdvertisement(ViewedAdvertisement viewedAdvertisement) {
        try {
            viewedAdvertisementRepository.save(viewedAdvertisement);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Transactional
    public void deleteViewedAdvertisement(long[] idList) {
        for (long id : idList)
            viewedAdvertisementRepository.delete(id);
    }

    @Transactional(readOnly = true)
    public List<ViewedAdvertisement> findByPattern(String pattern, Pageable pageable) {
        return viewedAdvertisementRepository.findByPattern(pattern, pageable);
    }

    @Transactional(readOnly = true)
    public long count() {
        return viewedAdvertisementRepository.count();
    }

    @Transactional(readOnly = true)
    public List<ViewedAdvertisement> findAll() {
        return viewedAdvertisementRepository.findAll() ;
    }

    @Transactional(readOnly = true)
    public List<ViewedAdvertisement> findAll(Pageable pageable) {
        return viewedAdvertisementRepository.findAll(pageable).getContent();
    }
}
