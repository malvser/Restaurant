package malov.serg.Service;


import malov.serg.Model.Order;
import malov.serg.Model.Tablet;
import malov.serg.Repository.OrderRepository;
import malov.serg.Repository.TabletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@ComponentScan(value = "malov.serg")
public class TabletService {



    @Autowired
    private TabletRepository tabletRepository;




    @Transactional
    public void addTablet(Tablet tablet) {
        try {
            tabletRepository.save(tablet);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Transactional
    public void deleteTablet(long[] idList) {
        for (long id : idList)
            tabletRepository.delete(id);
    }

    @Transactional(readOnly = true)
    public long count() {
        return tabletRepository.count();
    }

    @Transactional(readOnly = true)
    public List<Tablet> findAll(Pageable pageable) {
        return tabletRepository.findAll(pageable).getContent();
    }

    @Transactional(readOnly = true)
    public List<Tablet> findAll() {
        return tabletRepository.findAll() ;
    }



}