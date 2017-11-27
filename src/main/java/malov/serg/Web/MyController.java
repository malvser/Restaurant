package malov.serg.Web;

import malov.serg.Model.*;
import malov.serg.PhotoNotFoundException;
import malov.serg.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.PageRequest;

import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@ComponentScan("malov.serg")
@Controller
public class MyController {


    static final int ITEMS_PER_PAGE_TABLET = 8;
    static final int ITEMS_PER_PAGE_NO_ADVERTISEMENT = 6;
    static final int ITEMS_PER_PAGE_VIEWED_ADVERTISEMENT = 6;
    static final int ITEMS_PER_PAGE_COOKED_ORDERS = 3;
    static final int ITEMS_PER_PAGE_COUNT_ORDERS = 3;



    @Autowired
    private DishService dishService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private TabletService tabletService;

    @Autowired
    private CookedOrderService cookedOrderService;

    @Autowired
    private ViewedAdvertisementService viewedAdvertisementService;

    @Autowired
    private NoAdvertisementService noAdvertisementService;

    @Autowired
    private UserService userService;



    @RequestMapping("/enter_cook")
    public String indexCook(Model model) {

        infoForCook(model);
        return "index_cook";
    }


    @RequestMapping("/enter_cook_admin")
    public String indexCookAdmin(Model model) {

        infoForCook(model);
        return "index_cook_admin";
    }

    @RequestMapping(value = "/search_no_advertisement", method = RequestMethod.POST)
    public String searchNoAdvertisement(@RequestParam String pattern, Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        model.addAttribute("login", login);
        model.addAttribute("noAdvertisements", noAdvertisementService.findByPattern(pattern, null));
        return "statistics_no_advertisement";
    }

    @RequestMapping(value = "/search_viewed_advertisement", method = RequestMethod.POST)
    public String searchViewedAdvertisement(@RequestParam String pattern, Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        model.addAttribute("login", login);
        model.addAttribute("viewedAdvertisements", viewedAdvertisementService.findByPattern(pattern, null));
        return "statistics_viewed_advertisement";
    }


    @RequestMapping(value = "/search_cooked_order", method = RequestMethod.POST)
    public String searchCookedOrder(@RequestParam String pattern, Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        model.addAttribute("login", login);
        model.addAttribute("cookedOrderList", cookedOrderService.findByPattern(pattern, null));

        return "statistics_cooked_order";
    }



    //TabletController

    @RequestMapping("/add_tablet")
    public String tabletAddPage() {

        return "tablet_add_page";
    }


    @RequestMapping(value = "/tablet/add", method = RequestMethod.POST)
    public String tabletAdd(@RequestParam int number) {

        Tablet tablet = new Tablet(number);
        tabletService.addTablet(tablet);
        return "redirect:/tabletList";
    }


    @RequestMapping("/tabletList")
    public String tabletList(Model model, @RequestParam(required = false, defaultValue = "0") Integer page) {
        if (page < 0) page = 0;

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();

        List<Tablet> tablets = tabletService
                .findAll(new PageRequest(page, ITEMS_PER_PAGE_TABLET, Sort.Direction.DESC, "id"));


        model.addAttribute("allPages", page);
        model.addAttribute("login", login);
        model.addAttribute("tablets", tablets);
        model.addAttribute("allPages", getPageCountTablet());

        return "tabletList";
    }

    @RequestMapping(value = "/tablet/delete", method = RequestMethod.POST)
    public ResponseEntity<Void> deleteTablet(@RequestParam(value = "toDelete[]", required = false) long[] toDelete) {
        if (toDelete != null && toDelete.length > 0)
            tabletService.deleteTablet(toDelete);


        return new ResponseEntity<>(HttpStatus.OK);
    }


    // CookController



    @RequestMapping("/order_for_cooks")
    public String orderForCooks(Model model) {

        List<Order> orderList = orderService.findAll();
        Order order = null;
        if (orderList.size() > 0) {
            for (int i = 0; i < orderList.size(); i++) {
                if (!orderList.get(i).getCooking()) {
                    order = orderList.get(i);
                    order.setCooking(true);
                    orderService.addOrder(order);
                    break;
                }
            }
        }
        if (order != null) {
            String order_id = "" + order.getId();
            model.addAttribute("dishesList", order.getDishes());
            model.addAttribute("numberTable", order.getTablet().getNumber());
            model.addAttribute("orderId", order_id);
        }


        return "order_for_cooks";
    }


    @RequestMapping(value = "/cooked_order/{id}")
    public String cookedOrderNewOrder(@PathVariable("id") long id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser cook = userService.getUserByLogin(login);
        Order order = orderService.findOne(id);
        long[] id_dish = new long[order.getDishes().size()];
        int i = 0;
        for (Dish dish : order.getDishes()) {
            id_dish[i] = dish.getId();
            i++;
        }

        CookedOrder cookedOrder = new CookedOrder(order.getTablet().getNumber(),
                cook, dishService.findArrayId(id_dish), order.getTotalCookingTime());

        cookedOrderService.addCookedOrder(cookedOrder);
        orderService.deleteOrder(id);

        return "redirect:/order_for_cooks";

    }


    @RequestMapping(value = "/cooked_order_exit/{id}")
    public String cookedOrderExit(@PathVariable("id") long id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser cook = userService.getUserByLogin(login);
        Order order = orderService.findOne(id);
        long[] id_dish = new long[order.getDishes().size()];
        int i = 0;
        for (Dish dish : order.getDishes()) {
            id_dish[i] = dish.getId();
            i++;
        }

        CookedOrder cookedOrder = new CookedOrder(order.getTablet().getNumber(),
                cook, dishService.findArrayId(id_dish), order.getTotalCookingTime());

        cookedOrderService.addCookedOrder(cookedOrder);
        orderService.deleteOrder(id);

        return "redirect:/enter_cook";

    }


    //...OrderController


    @RequestMapping(value = "/made_order_bonus", method = RequestMethod.POST)
    public String orderCookBonus(Model model, @RequestParam(value = "toOrder[]") long[] id) {

        if (id.length > 0) {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String login = user.getUsername();

            CustomUser customUser = userService.getUserByLogin(login);
            int bonus = customUser.getBonus();

            List<Tablet> tablet = tabletService.findAll();
            int random = (ThreadLocalRandom.current().nextInt(tablet.size()));

            List<Dish> dishList = dishService.findArrayId(id);
            for (Dish dish : dishList) {
                if (dish.getBonus() <= bonus) {
                    bonus = bonus - dish.getBonus();
                }
            }
            customUser.setBonus(bonus + dishList.size());
            userService.addUser(customUser);
            Order order = null;
            try {
                order = new Order(tablet.get(random), dishList, false);
            } catch (IOException e) {
                e.printStackTrace();
            }
            orderService.addOrder(order);

            String s = "";
            for (long i : id) {
                s += "" + i + " ";
            }

            model.addAttribute("dishesArrayId", dishList);
            model.addAttribute("Id", s);
            model.addAttribute("login", login);
        }
        return "order_cook";
    }


    @RequestMapping(value = "/made_order", method = RequestMethod.POST)
    public String orderCook(Model model, @RequestParam(required = false, value = "toOrder[]") long[] id) {

        if (id.length > 0) {
            List<Tablet> tablet = tabletService.findAll();
            int random = (ThreadLocalRandom.current().nextInt(tablet.size()));
            Order order = null;
            List<Dish> dishList = dishService.findArrayId(id);

            if (!SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")) {
                User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                String login = user.getUsername();
                CustomUser customUser = userService.getUserByLogin(login);
                customUser.setBonus(customUser.getBonus() + dishList.size());
                userService.updateUser(customUser);
                model.addAttribute("login", login);
            }


            try {
                order = new Order(tablet.get(random), dishList, false);
            } catch (IOException e) {
                e.printStackTrace();
            }
            orderService.addOrder(order);

            String s = "";
            for (long i : id) {
                s += "" + i + " ";
            }

            model.addAttribute("dishesArrayId", dishList);
            model.addAttribute("Id", s);
        }
        return "order_cook";
    }



    //StatisticController

    @RequestMapping("/statistic_cooked_order")
    public String statisticCookedOrder(Model model, @RequestParam(required = false, defaultValue = "0") Integer page) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        List<CookedOrder> cookedOrderList = cookedOrderService.findAll(new PageRequest
                (page, ITEMS_PER_PAGE_COOKED_ORDERS, Sort.Direction.DESC, "id"));

        model.addAttribute("cookedOrderList", cookedOrderList);
        model.addAttribute("login", login);
        model.addAttribute("allPages", page);
        model.addAttribute("allPages", getPageCookedOrders());

        return "statistics_cooked_order";
    }

    @RequestMapping(value = "/cooked_order/delete", method = RequestMethod.POST)
    public ResponseEntity<Void> deleteCookedOrder(@RequestParam(value = "toDelete[]", required = false) long[] toDelete) {
        if (toDelete != null && toDelete.length > 0)
            cookedOrderService.deleteCookedOrder(toDelete);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @RequestMapping("/statistic_viewed_advertisement")
    public String statisticViewedAdvertisement(Model model, @RequestParam(required = false, defaultValue = "0") Integer page) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        if (page < 0) page = 0;


        List<ViewedAdvertisement> viewedAdvertisements = viewedAdvertisementService.findAll(new PageRequest(page, ITEMS_PER_PAGE_VIEWED_ADVERTISEMENT, Sort.Direction.DESC, "id"));
        model.addAttribute("viewedAdvertisements", viewedAdvertisements);
        model.addAttribute("login", login);
        model.addAttribute("allPages", page);
        model.addAttribute("allPages", getPageCountViewedAdvertisement());
        return "statistics_viewed_advertisement";
    }

    @RequestMapping(value = "/viewed_advertisement/delete", method = RequestMethod.POST)
    public ResponseEntity<Void> deleteViewedAdvertisement(@RequestParam(value = "toDelete[]", required = false) long[] toDelete) {
        if (toDelete != null && toDelete.length > 0)
            viewedAdvertisementService.deleteViewedAdvertisement(toDelete);


        return new ResponseEntity<>(HttpStatus.OK);
    }


    @RequestMapping("/statistic_no_advertisement")
    public String statisticNoAdvertisement(Model model, @RequestParam(required = false, defaultValue = "0") Integer page) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        if (page < 0) page = 0;
        List<NoAdvertisement> noAdvertisements = noAdvertisementService.findAll(new PageRequest(page, ITEMS_PER_PAGE_NO_ADVERTISEMENT, Sort.Direction.DESC, "id"));
        model.addAttribute("noAdvertisements", noAdvertisements);
        model.addAttribute("login", login);
        model.addAttribute("allPages", page);
        model.addAttribute("allPages", getPageCountNoAdvertisement());
        return "statistics_no_advertisement";
    }

    @RequestMapping(value = "/no_advertisement/delete", method = RequestMethod.POST)
    public ResponseEntity<Void> deleteNoAdvertisement(@RequestParam(value = "toDelete[]", required = false) long[] toDelete) {
        if (toDelete != null && toDelete.length > 0) {
            noAdvertisementService.deleteNoAdvertisement(toDelete);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }





    //methods


    private long getPageCookedOrders() {
        long totalCount = cookedOrderService.count();
        return (totalCount / ITEMS_PER_PAGE_COOKED_ORDERS) + ((totalCount % ITEMS_PER_PAGE_COOKED_ORDERS > 0) ? 1 : 0);
    }

    private long getPageCountOrders() {
        long totalCount = orderService.count();
        return (totalCount / ITEMS_PER_PAGE_COUNT_ORDERS) + ((totalCount % ITEMS_PER_PAGE_COUNT_ORDERS > 0) ? 1 : 0);
    }


    private long getPageCountViewedAdvertisement() {
        long totalCount = viewedAdvertisementService.count();
        return (totalCount / ITEMS_PER_PAGE_VIEWED_ADVERTISEMENT) + ((totalCount % ITEMS_PER_PAGE_VIEWED_ADVERTISEMENT > 0) ? 1 : 0);

    }

    private long getPageCountNoAdvertisement() {
        long totalCount = noAdvertisementService.count();
        return (totalCount / ITEMS_PER_PAGE_NO_ADVERTISEMENT) + ((totalCount % ITEMS_PER_PAGE_NO_ADVERTISEMENT > 0) ? 1 : 0);

    }


    private long getPageCountTablet() {
        long totalCount = tabletService.count();
        return (totalCount / ITEMS_PER_PAGE_TABLET) + ((totalCount % ITEMS_PER_PAGE_TABLET > 0) ? 1 : 0);
    }




    private void infoForCook(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        int count_order = 0;
        int count_cooking_order = 0;
        int totalCookingTime = 0;
        int totalCookingTimeNewOrder = 0;
        int countDishesNewOrder = 0;
        int countDishesCookingOrder = 0;
        List<Order> orders = orderService.findAll();
        for (Order order : orders) {
            if (!order.getCooking()) {
                count_order += 1;
                totalCookingTimeNewOrder += order.getTotalCookingTime();
                countDishesNewOrder += order.getDishes().size();

            } else {
                count_cooking_order += 1;
                totalCookingTime += order.getTotalCookingTime();
                countDishesCookingOrder += order.getDishes().size();
            }

        }

        model.addAttribute("login", login);
        model.addAttribute("count_order", count_order);
        model.addAttribute("count_cooking_order", count_cooking_order);
        model.addAttribute("totalCookingTimeNewOrder", totalCookingTimeNewOrder);
        model.addAttribute("totalCookingTime", totalCookingTime);
        model.addAttribute("countDishesNewOrder", countDishesNewOrder);
        model.addAttribute("countDishesCookingOrder", countDishesCookingOrder);
    }

}
