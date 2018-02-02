package malov.serg.Service;


import malov.serg.Model.AdvertisementPhoto;
import malov.serg.Model.Dish;
import malov.serg.Model.NoAdvertisement;
import malov.serg.Model.ViewedAdvertisement;
import malov.serg.PhotoNotFoundException;
import malov.serg.Repository.AdvertisementPhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
@ComponentScan(value = "malov.serg")
public class AdvertisementPhotoService {

    @Autowired
    private AdvertisementPhotoRepository advertisementPhotoRepository;

    @Autowired
    private ViewedAdvertisementService viewedAdvertisementService;

    @Autowired
    private NoAdvertisementService noAdvertisementService;

    @Autowired
    private DishService dishService;

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

    @Transactional
    public void advertisement_id(Long advertisement_id, HttpServletResponse response, String name,
                                 MultipartFile body_photo, long cost, long amount, long total_amount){

        if(advertisement_id != null){
            AdvertisementPhoto advertisementPhoto = findOne(advertisement_id);
            advertisementPhoto.setAmount(amount);
            advertisementPhoto.setTotal_amount(total_amount);
            advertisementPhoto.setName(name);
            advertisementPhoto.setCost(cost);
            try {
                advertisementPhoto.setPhoto(body_photo.getBytes());
            } catch (IOException e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                e.printStackTrace();
            }
            addAdvertisement(advertisementPhoto);
        }else {

            try {
                AdvertisementPhoto advertisementPhoto = new AdvertisementPhoto(body_photo.getBytes(), name,
                        cost, amount, total_amount);
                addAdvertisement(advertisementPhoto);
            } catch (IOException e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                e.printStackTrace();
            }
        }
    }

    @Transactional
    public long onViewNext(List<AdvertisementPhoto> advertisementPhotos, long id){

        long idNext = 0;
        int y;
        for (int i = 0; i < advertisementPhotos.size(); i++) {

            if (advertisementPhotos.get(i).getId() == id) {
                if ((y = i + 1) < advertisementPhotos.size()) {
                    idNext = advertisementPhotos.get(y).getId();
                    AdvertisementPhoto adv = advertisementPhotos.get(y);
                    Long amount = adv.getAmount();
                    adv.setAmount(amount - 1);
                    addAdvertisement(adv);
                    return idNext;
                } else {
                    idNext = advertisementPhotos.get(0).getId();
                    AdvertisementPhoto adv = advertisementPhotos.get(0);
                    Long amount = adv.getAmount();
                    adv.setAmount(amount - 1);
                    addAdvertisement(adv);
                    return idNext;

                }
            }
        }
        return idNext;
    }

    @Transactional
    public String onView(List<AdvertisementPhoto> advertisementPhotos, Model model, String idDishes){

        if((advertisementPhotos.size()  > 0))

        {

            int random = (ThreadLocalRandom.current().nextInt(advertisementPhotos.size()));
            long id = advertisementPhotos.get(random).getId();

            AdvertisementPhoto adv = advertisementPhotos.get(random);
            Long amount = adv.getAmount();
            adv.setAmount(amount - 1);
            addAdvertisement(adv);

            addViewedAdvertisement(id);

            model.addAttribute("photo_id", id);
        } else  {
            if (!idDishes.equals("")) {

                String[] array = idDishes.split(" ");
                long[] arrayId = new long[array.length];
                for (int i = 0; i < array.length; i++) {
                    arrayId[i] = Long.parseLong(array[i]);
                }


                List<Dish> dishes = dishService.findArrayId(arrayId);
                long sumDuration = 0;
                for (Dish dish : dishes) {
                    sumDuration += dish.getDuration();
                }
                NoAdvertisement noAdvertisement = new NoAdvertisement(sumDuration);
                noAdvertisementService.addNoAdvertisement(noAdvertisement);
            }
            return "redirect:/no_advertisement";

        }
        return "advertisement_view";
    }

    @Transactional
    public long onViewBack(List<AdvertisementPhoto> advertisementPhotos, long id){

        long idBack = 0;
        int y;
        for (int i = 0; i < advertisementPhotos.size(); i++) {

            if (advertisementPhotos.get(i).getId() == id) {
                if (i > 0) {
                    y = i - 1;
                    idBack = advertisementPhotos.get(y).getId();
                    AdvertisementPhoto adv = advertisementPhotos.get(y);
                    Long amount = adv.getAmount();
                    adv.setAmount(amount - 1);
                    addAdvertisement(adv);
                    return idBack;

                } else {
                    idBack = advertisementPhotos.get(advertisementPhotos.size() - 1).getId();
                    AdvertisementPhoto adv = advertisementPhotos.get(advertisementPhotos.size() - 1);
                    Long amount = adv.getAmount();
                    adv.setAmount(amount - 1);
                    addAdvertisement(adv);
                    return idBack;

                }
            }
        }
        return  idBack;

    }

    @Transactional
    public void PhotoAdvertisementNotFoundException(byte[] bytes) {

        if (bytes == null) {
            throw new PhotoNotFoundException();
        }
    }

    public void addViewedAdvertisement(long id) {
        ViewedAdvertisement viewedAdvertisement = new ViewedAdvertisement(findOne(id),
                findOne(id).getCost());
        viewedAdvertisementService.addViewedAdvertisement(viewedAdvertisement);
    }




}
