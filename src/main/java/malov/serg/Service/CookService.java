package malov.serg.Service;

import malov.serg.Model.Cook;
import malov.serg.Repository.CookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@ComponentScan(value = "malov.serg")
public class CookService {


    @Autowired
    private CookRepository cookRepository;

    @Transactional
    public void addCook(Cook cook) {
        try {
            cookRepository.save(cook);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Transactional
    public void deleteCookes(long[] idList) {
        for (long id : idList)
            cookRepository.delete(id);
    }

    @Transactional(readOnly = true)
    public List<Cook> findAll(Pageable pageable) {
        return cookRepository.findAll(pageable).getContent();
    }

    @Transactional(readOnly = true)
    public List<Cook> findAll() {
        return cookRepository.findAll();
    }


    @Transactional(readOnly = true)
    public Cook findOne(Long id) {
        return cookRepository.findOne(id);
    }

    @Transactional(readOnly = true)
    public long count() {
        return cookRepository.count();
    }

    @Transactional(readOnly = true)
    public List<Cook> findByPattern(String pattern, Pageable pageable) {
        return cookRepository.findByPattern(pattern, pageable);
    }
}
