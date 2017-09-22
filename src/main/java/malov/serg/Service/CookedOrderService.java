package malov.serg.Service;


import malov.serg.Model.CookedOrder;
import malov.serg.Repository.CookedOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@ComponentScan(value = "malov.serg")
public class CookedOrderService {


    @Autowired
    private CookedOrderRepository cookedOrderRepository;

    @Transactional
    public void addCookedOrder(CookedOrder cookedOrder) {
        try {
            cookedOrderRepository.save(cookedOrder);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Transactional
    public void deleteCookedOrder(long[] idList) {
        for (long id : idList)
            cookedOrderRepository.delete(id);
    }

    @Transactional(readOnly = true)
    public List<CookedOrder> findAll(Pageable pageable) {
        return cookedOrderRepository.findAll(pageable).getContent();
    }

    @Transactional(readOnly = true)
    public List<CookedOrder> findAll() {
        return cookedOrderRepository.findAll();
    }


    @Transactional(readOnly = true)
    public long count() {
        return cookedOrderRepository.count();
    }

    @Transactional(readOnly = true)
    public List<CookedOrder> findByPattern(String pattern, Pageable pageable) {
        return cookedOrderRepository.findByPattern(pattern, pageable);
    }
}
