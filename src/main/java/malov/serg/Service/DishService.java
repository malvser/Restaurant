package malov.serg.Service;

import malov.serg.Model.Dish;
import malov.serg.Repository.DishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
            findId.add(dishRepository.findOne(i));
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
    public long count() {
        return dishRepository.count();
    }

    @Transactional(readOnly = true)
    public List<Dish> findByPattern(String pattern, Pageable pageable) {
        return dishRepository.findByPattern(pattern, pageable);
    }

}
