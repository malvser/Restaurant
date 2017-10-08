package malov.serg.Service;


import malov.serg.Model.AdvertisementPhoto;
import malov.serg.Repository.AdvertisementPhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@ComponentScan(value = "malov.serg")
public class AdvertisementPhotoService {

    @Autowired
    private AdvertisementPhotoRepository advertisementPhotoRepository;

    @Transactional
    public void addAdvertisement(AdvertisementPhoto advertisementPhoto) {
        try {
            advertisementPhotoRepository.save(advertisementPhoto);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Transactional
    public byte[] getPhotoOne(Long id){
        return advertisementPhotoRepository.getOne(id).getPhoto();
    }

    @Transactional
    public List<AdvertisementPhoto> findArrayId(long[] id){
        List<AdvertisementPhoto> findId = new ArrayList<>();
        for (long i : id) {
            findId.add(advertisementPhotoRepository.findOne(i));
        }
        return findId;
    }



    @Transactional
    public AdvertisementPhoto get(long id){

        return advertisementPhotoRepository.getOne(id);

    }

    @Transactional
    public void deleteId(long id){
        advertisementPhotoRepository.delete(id);
    }

    @Transactional(readOnly = true)
    public List<AdvertisementPhoto> findAll(Pageable pageable) {
        return advertisementPhotoRepository.findAll(pageable).getContent();
    }

    @Transactional(readOnly = true)
    public List<AdvertisementPhoto> findAll() {
        return advertisementPhotoRepository.findAll();
    }


    @Transactional(readOnly = true)
    public long count() {
        return advertisementPhotoRepository.count();
    }

    @Transactional(readOnly = true)
    public List<AdvertisementPhoto> findByPattern(String pattern, Pageable pageable) {
        return advertisementPhotoRepository.findByPattern(pattern, pageable);
    }

    @Transactional(readOnly = true)
    public AdvertisementPhoto findOne(long id){
        return advertisementPhotoRepository.findOne(id);
    }

    @Transactional
    public void deleteAdvertisement(long[] idList) {
        for (long id : idList)
            advertisementPhotoRepository.delete(id);
    }


}
