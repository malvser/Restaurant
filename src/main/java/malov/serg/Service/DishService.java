package malov.serg.Service;

import malov.serg.Model.Dish;
import malov.serg.Repository.DishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@ComponentScan(value = "malov.serg")
public class DishService {

    @Autowired
    private DishRepository dishRepository;

    @Transactional
    public void addDish(Dish dish) {
        try {
            dishRepository.save(dish);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Transactional
    public byte[] getPhotoOne(Long id){
        return dishRepository.getOne(id).getPhoto();
    }

    @Transactional
    public List<Dish> findArrayId(long[] id){
        List<Dish> findId = new ArrayList<>();
        for (long i : id) {
            if(i > 1){
                findId.add(dishRepository.findOne(i));
            }

        }
        return findId;
    }

    @Transactional
    public void deleteDishes(long[] idList) {
        for (long id : idList)
            dishRepository.delete(id);
    }

    @Transactional(readOnly = true)
    public List<Dish> findAll(Pageable pageable) {
        return dishRepository.findAll(pageable).getContent();
    }

    @Transactional(readOnly = true)
    public Dish findOne(long id) {
        return dishRepository.findOne(id);
    }

    @Transactional(readOnly = true)
    public long count() {
        return dishRepository.count();
    }

    @Transactional(readOnly = true)
    public List<Dish> findByPattern(String pattern, Pageable pageable) {
        return dishRepository.findByPattern(pattern, pageable);
    }

    @Transactional(readOnly = true)
    public List<Dish> findByPattern(String pattern) {
        return dishRepository.findByPattern(pattern, null);
    }

    @Transactional(readOnly = true)
    public List<Dish> findByType(String pattern) {
        return dishRepository.findByType(pattern);
    }

    @Transactional(readOnly = true)
    public List<Dish> findByDiscount() {
        return dishRepository.findByDiscount();
    }

    @Transactional
    public void dish_id(Long dish_id, HttpServletResponse response, String name, MultipartFile body_photo, int cost, int weight, int discount, int bonus, int duration, String type){

        if(dish_id != null){
            Dish dish = findOne(dish_id);
            dish.setBonus(bonus);
            dish.setType(type);
            dish.setDiscount(discount);
            dish.setName(name);
            dish.setCost(cost);
            dish.setDuration(duration);
            dish.setWeight(weight);
            try {
                dish.setPhoto(body_photo.getBytes());
            } catch (IOException e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                e.printStackTrace();
            }
            addDish(dish);

        }else {

            try {
                Dish dish = new Dish(body_photo.getBytes(), name, cost, weight, discount, duration, type, bonus);
                addDish(dish);
            } catch (IOException e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                e.printStackTrace();
            }

        }
    }

}
