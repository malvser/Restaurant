package malov.serg.Service;


import malov.serg.Model.Order;
import malov.serg.Repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@ComponentScan(value = "malov.serg")
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Transactional
    public List<Order> findArrayId(long[] id){
        List<Order> findId = new ArrayList<>();
        for (long i : id) {
            findId.add(orderRepository.findOne(i));
        }
        return findId;
    }

    @Transactional
    public void addOrder(Order order) {
        try {
            orderRepository.save(order);
        }catch(Exception e){
            e.printStackTrace();
        }
    }


}
