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
    public Order findOne(long id) {

        return orderRepository.getOne(id);
    }

    @Transactional
    public void deleteOrder(long id) {

            orderRepository.delete(id);
    }

    @Transactional
    public List<Order> findAll(){

        return orderRepository.findAll();
    }

    @Transactional
    public void addOrder(Order order) {
        try {
            orderRepository.save(order);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Transactional
    public Order orderForCook(List<Order> orderList){

        Order order = null;
        if (orderList.size() > 0) {
            for (int i = 0; i < orderList.size(); i++) {
                if (!orderList.get(i).getCooking()) {
                    order = orderList.get(i);
                    order.setCooking(true);
                    addOrder(order);
                    break;
                }
            }
        }
        return order;
    }

    @Transactional(readOnly = true)
    public long count() {
        return orderRepository.count();
    }


}
