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

    static final int ITEMS_PER_PAGE_ADVERTISEMENT = 6;
    static final int ITEMS_PER_PAGE_TABLET = 8;
    static final int ITEMS_PER_PAGE_COOK = 10;
    static final int ITEMS_PER_PAGE_DISH = 6;
    static final int ITEMS_PER_PAGE_NO_ADVERTISEMENT = 6;
    static final int ITEMS_PER_PAGE_VIEWED_ADVERTISEMENT = 6;
    static final int ITEMS_PER_PAGE_COOKED_ORDERS = 3;
    static final int ITEMS_PER_PAGE_COUNT_ORDERS = 3;
    static final int ITEMS_PER_PAGE_USER = 6;


    @Autowired
    private DishService dishService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private TabletService tabletService;

    @Autowired
    private AdvertisementPhotoService advertisementPhotoService;

    @Autowired
    private CookService cookService;

    @Autowired
    private CookedOrderService cookedOrderService;

    @Autowired
    private ViewedAdvertisementService viewedAdvertisementService;

    @Autowired
    private NoAdvertisementService noAdvertisementService;

    @Autowired
    private UserService userService;

    //Spring Security


    @RequestMapping("/unauthorized")
    public String unauthorized(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("login", user.getUsername());
        return "unauthorized";
    }

    @RequestMapping("/register")
    public String register() {
        return "register";
    }

    @RequestMapping("/register_admin")
    public String registerCook(Model model) {
        model.addAttribute("userRole", UserRole.values());

        return "register_for_admin";
    }

    @RequestMapping("/userlist")
    public String userList(Model model, @RequestParam(required = false, defaultValue = "0") Integer page) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        List<CustomUser> customUsers = userService.findAll(new PageRequest(page, ITEMS_PER_PAGE_USER, Sort.Direction.DESC, "id"));
        model.addAttribute("login", login);
        model.addAttribute("allPages", page);
        model.addAttribute("customUsers", customUsers);
        model.addAttribute("allPages", getPageCountUser());

        return "userList";
    }


    @RequestMapping(value = "/newuser", method = RequestMethod.POST)
    public String newUser(@RequestParam String login,
                          @RequestParam String password,
                          @RequestParam String email,
                          @RequestParam String phone,
                          Model model) {
        if (userService.existsByLogin(login)) {
            model.addAttribute("exists", true);
            return "register";
        }

        ShaPasswordEncoder encoder = new ShaPasswordEncoder();
        String passHash = encoder.encodePassword(password, null);

        CustomUser dbUser = new CustomUser(login, passHash, UserRole.USER, email, phone, 0);
        userService.addUser(dbUser);

        return "redirect:/";
    }

    @RequestMapping(value = "/newanyuser", method = RequestMethod.POST)
    public String newCook(@RequestParam String login,
                          @RequestParam String password,
                          @RequestParam(value = "role") String role,
                          @RequestParam String email,
                          @RequestParam String phone,
                          Model model) {
        if (userService.existsByLogin(login)) {
            model.addAttribute("exists", true);
            return "register_for_admin";
        }

        ShaPasswordEncoder encoder = new ShaPasswordEncoder();
        /*BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String passwordHash = bCryptPasswordEncoder.encode(password);*/
        String passHash = encoder.encodePassword(password, null);
        CustomUser dbUser;

        if (("ROLE_" + role).equals(UserRole.ADMIN.toString())) {
            dbUser = new CustomUser(login, passHash, UserRole.ADMIN, email, phone, 0);
        } else if (("ROLE_" + role).equals(UserRole.COOK.toString())) {
            dbUser = new CustomUser(login, passHash, UserRole.COOK, email, phone, 0);
        } else {
            dbUser = new CustomUser(login, passHash, UserRole.USER, email, phone, 0);
        }

        userService.addUser(dbUser);

        return "redirect:/";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(@RequestParam(required = false) String email, @RequestParam(required = false) String phone) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();

        //изменить код. на синхронизацию
        CustomUser dbUser = userService.getUserByLogin(login);
        dbUser.setEmail(email);
        dbUser.setPhone(phone);

        userService.updateUser(dbUser);

        return "redirect:/";
    }

    @RequestMapping("/name")
    public String name() {

        return "name";
    }


    //DishController

    @RequestMapping("/")
    public String index(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();

        CustomUser dbUser = userService.getUserByLogin(login);

        model.addAttribute("login", login);
        model.addAttribute("roles", user.getAuthorities());
        model.addAttribute("email", dbUser.getEmail());
        model.addAttribute("phone", dbUser.getPhone());

        return "index";
    }


    @RequestMapping("/enter_cook")
    public String indexCook(Model model) {

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
        return "index_cook";
    }


    @RequestMapping("/dishesList")
    public String dishList(Model model, @RequestParam(required = false, defaultValue = "0") Integer page) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        if (page < 0) page = 0;

        List<Dish> dishes = dishService
                .findAll(new PageRequest(page, ITEMS_PER_PAGE_DISH, Sort.Direction.DESC, "id"));
        model.addAttribute("login", login);
        model.addAttribute("allPages", page);
        model.addAttribute("dishes", dishes);
        model.addAttribute("allPages", getPageCountDish());

        return "dishesList";
    }

    @RequestMapping(value = "/dishes/delete", method = RequestMethod.POST)
    public ResponseEntity<Void> deleteDishes(@RequestParam(value = "toDelete[]", required = false) long[] toDelete) {
        if (toDelete != null && toDelete.length > 0)
            dishService.deleteDishes(toDelete);

        return new ResponseEntity<>(HttpStatus.OK);
    }


    @RequestMapping("/add_dish")
    public String dishAddPage(Model model, @RequestParam(required = false) Long id) {

        if (id != 0) {
            Dish dish = dishService.findOne(id);

            model.addAttribute("bonus", dish.getBonus());
            model.addAttribute("cost", dish.getCost());
            model.addAttribute("discount", dish.getDiscount());
            model.addAttribute("duration", dish.getDuration());
            model.addAttribute("name", dish.getName());
            model.addAttribute("weight", dish.getWeight());
            model.addAttribute("type", dish.getType());
            model.addAttribute("photo", dish.getPhoto());
        }
        return "dish_add_page";
    }


    @RequestMapping(value = "/dish/add", method = RequestMethod.POST)
    public String dishAdd(@RequestParam String name,
                          @RequestParam(value = "photo") MultipartFile body_photo,
                          HttpServletRequest request,
                          HttpServletResponse response,
                          @RequestParam int cost,
                          @RequestParam int weight,
                          @RequestParam int discount,
                          @RequestParam int bonus,
                          @RequestParam int duration,
                          @RequestParam String type) {


        try {
            Dish dish = new Dish(body_photo.getBytes(), name, cost, weight, discount, duration, type, bonus);
            dishService.addDish(dish);
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }


        return "redirect:/dishesList";
    }


    //Search

    @RequestMapping(value = "/search_dishes", method = RequestMethod.POST)
    public String search(@RequestParam String pattern, Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String login = user.getUsername();
        model.addAttribute("login", login);
        model.addAttribute("dishes", dishService.findByPattern(pattern, null));

        return "dishesList";
    }


    @RequestMapping(value = "/search_advertisement", method = RequestMethod.POST)
    public String searchAdvertisement(@RequestParam String pattern, Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        model.addAttribute("login", login);
        model.addAttribute("advertisement", advertisementPhotoService.findByPattern(pattern, null));
        return "advertisementList";
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

    @RequestMapping(value = "/search_cook", method = RequestMethod.POST)
    public String searchCook(@RequestParam String pattern, Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        model.addAttribute("login", login);
        model.addAttribute("cook", cookService.findByPattern(pattern, null));
        return "cookList";
    }

    @RequestMapping(value = "/search_cooked_order", method = RequestMethod.POST)
    public String searchCookedOrder(@RequestParam String pattern, Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        model.addAttribute("login", login);
        model.addAttribute("cookedOrderList", cookedOrderService.findByPattern(pattern, null));

        return "statistics_cooked_order";
    }

    @RequestMapping(value = "/search_dish", method = RequestMethod.POST)
    public String searchDish(@RequestParam String pattern, Model model) {

        model.addAttribute("dishes", dishService.findByPattern(pattern));
       // model.addAttribute("pattern", pattern);


        return "main";
    }

    @RequestMapping("/search_hot_snacks")
    public String searchHotSnacks(Model model) {


        getAuthentication(model);

        String pattern = "Горячии закуски";
        model.addAttribute("dishes", dishService.findByType(pattern));
        model.addAttribute("pattern", pattern);


        return "main";
    }

    @RequestMapping("/search_cold_snacks")
    public String searchColdSnacks(Model model) {

        getAuthentication(model);

        String pattern = "Холодные закуски";
        model.addAttribute("dishes", dishService.findByType(pattern));
        model.addAttribute("pattern", pattern);
        return "main";
    }

    @RequestMapping("/search_all")
    public String searchAll(Model model) {

        getAuthentication(model);

        String pattern = "Все блюда";
        model.addAttribute("dishes", dishService.findByType(pattern));
        model.addAttribute("pattern", pattern);
        return "main";
    }

    @RequestMapping("/search_salads")
    public String searchSalads(Model model) {

        getAuthentication(model);

        String pattern = "Салаты";
        model.addAttribute("dishes", dishService.findByType(pattern));
        model.addAttribute("pattern", pattern);
        return "main";
    }

    @RequestMapping("/search_first_meal")
    public String searchFirstMeal(Model model) {

        getAuthentication(model);

        String pattern = "Первые блюда";
        model.addAttribute("dishes", dishService.findByType(pattern));
        model.addAttribute("pattern", pattern);
        return "main";
    }


    @RequestMapping("/search_garnishes")
    public String searchGarnishes(Model model) {

        getAuthentication(model);

        String pattern = "Гарниры";
        model.addAttribute("dishes", dishService.findByType(pattern));
        model.addAttribute("pattern", pattern);
        return "main";
    }

    @RequestMapping("/search_supe")
    public String searchSupe(Model model) {

        getAuthentication(model);

        String pattern = "Супы";
        model.addAttribute("dishes", dishService.findByType(pattern));
        model.addAttribute("pattern", pattern);
        return "main";
    }


    @RequestMapping("/search_beverages")
    public String searchBeverages(Model model) {

        getAuthentication(model);

        String pattern = "Напитки";
        model.addAttribute("dishes", dishService.findByType(pattern));
        model.addAttribute("pattern", pattern);
        return "main";
    }


    @RequestMapping("/search_alcoholic_beverages")
    public String searchAlcoholicBeverages(Model model) {

        getAuthentication(model);


        String pattern = "Алкогольные напитки";
        model.addAttribute("dishes", dishService.findByType(pattern));
        model.addAttribute("pattern", pattern);
        return "main";
    }


    //.....

    @RequestMapping("/photo/{photo_id}")
    public void getPhotoDish(HttpServletRequest request, HttpServletResponse response, @PathVariable("photo_id") long fileId) {
        try {
            byte[] content = dishService.getPhotoOne(fileId);
            responsePhoto(response, content);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
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


    @RequestMapping("/cook_add")
    public String cookAddPage() {

        return "cook_add_page";
    }

    @RequestMapping(value = "/cook/add", method = RequestMethod.POST)
    public String cookAdd(@RequestParam String name) {

        Cook cook = new Cook(name);
        cookService.addCook(cook);
        return "redirect:/cookList";
    }

    @RequestMapping("/cookList")
    public String cookList(Model model, @RequestParam(required = false, defaultValue = "0") Integer page) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();

        if (page < 0) page = 0;

        List<Cook> cookList = cookService
                .findAll(new PageRequest(page, ITEMS_PER_PAGE_COOK, Sort.Direction.DESC, "id"));

        model.addAttribute("allPages", page);
        model.addAttribute("cook", cookList);
        model.addAttribute("allPages", getPageCountCook());
        model.addAttribute("login", login);

        return "cookList";
    }

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
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();

            Order order = orderService.findOne(id);
            long[] id_dish = new long[order.getDishes().size()];
            int i = 0;
            for (Dish dish : order.getDishes()) {
                id_dish[i] = dish.getId();
                i++;
            }

            List<Cook> cooks = cookService.findAll();
            //переделать с рандом на конкрет. повара
            int random = (ThreadLocalRandom.current().nextInt(cooks.size()));
            CookedOrder cookedOrder = new CookedOrder(order.getTablet().getNumber(),
                    cooks.get(random), dishService.findArrayId(id_dish), order.getTotalCookingTime());

            cookedOrderService.addCookedOrder(cookedOrder);
            orderService.deleteOrder(id);

        return "redirect:/order_for_cooks";

    }


    @RequestMapping(value = "/cooked_order_exit/{id}")
    public String cookedOrderExit(@PathVariable("id") long id) {
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();

            Order order = orderService.findOne(id);
            long[] id_dish = new long[order.getDishes().size()];
            int i = 0;
            for (Dish dish : order.getDishes()) {
                id_dish[i] = dish.getId();
                i++;
            }

            List<Cook> cooks = cookService.findAll();
            //переделать с рандом на конкрет. повара
            int random = (ThreadLocalRandom.current().nextInt(cooks.size()));
            CookedOrder cookedOrder = new CookedOrder(order.getTablet().getNumber(),
                    cooks.get(random), dishService.findArrayId(id_dish), order.getTotalCookingTime());

            cookedOrderService.addCookedOrder(cookedOrder);
            orderService.deleteOrder(id);

        return "redirect:/enter_cook";

    }

    @RequestMapping(value = "/cook/delete", method = RequestMethod.POST)
    public ResponseEntity<Void> deleteCook(@RequestParam(value = "toDelete[]", required = false) long[] toDelete) {
        if (toDelete != null && toDelete.length > 0)
            cookService.deleteCookes(toDelete);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    //...OrderController


    @RequestMapping(value = "/made/order_bonus", method = RequestMethod.POST)
    public String orderCookBonus(Model model, @RequestParam(value = "toOrder[]") long[] id) {


        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();

        CustomUser customUser = userService.getUserByLogin(login);
        int bonus = customUser.getBonus();

        List<Tablet> tablet = tabletService.findAll();
        int random = (ThreadLocalRandom.current().nextInt(tablet.size()));
        Order order = null;
        List<Dish> dishList = dishService.findArrayId(id);
        for (Dish dish : dishList) {
            if (dish.getBonus() <= bonus) {
                bonus = bonus - dish.getBonus();
            }
        }
        customUser.setBonus(bonus + dishList.size());
        userService.addUser(customUser);

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
        return "order_cook";
    }


    @RequestMapping(value = "/made/order", method = RequestMethod.POST)
    public String orderCook(Model model, @RequestParam(value = "toOrder[]") long[] id) {

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
        return "order_cook";
    }

    // AdvertisementController

    @RequestMapping("/advertisementList")
    public String advertisementList(Model model, @RequestParam(required = false, defaultValue = "0") Integer page) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();


        if (page < 0) page = 0;

        List<AdvertisementPhoto> advertisementPhotos = advertisementPhotoService
                .findAll(new PageRequest(page, ITEMS_PER_PAGE_ADVERTISEMENT, Sort.Direction.DESC, "id"));

        model.addAttribute("login", login);
        model.addAttribute("allPages", page);
        model.addAttribute("advertisement", advertisementPhotos);
        model.addAttribute("allPages", getPageCountAdvertisement());

        return "advertisementList";
    }


    @RequestMapping(value = "/advertisement/view", method = RequestMethod.POST)
    public String onView(Model model, @RequestParam("IdDishes") String idDishes) {

        List<AdvertisementPhoto> advertisementPhotos = getAdvertisementPhotos();
        if (advertisementPhotos.size() > 0) {

            int random = (ThreadLocalRandom.current().nextInt(advertisementPhotos.size()));
            long id = advertisementPhotos.get(random).getId();

            AdvertisementPhoto adv = advertisementPhotos.get(random);
            Long amount = adv.getAmount();
            adv.setAmount(amount - 1);
            advertisementPhotoService.addAdvertisement(adv);

            addViewedAdvertisement(id);

            model.addAttribute("photo_id", id);
        } else {

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

        return "advertisement_view";
    }


    @RequestMapping(value = "/advertisement/view/{photo_id}")
    public String onViewNext(Model model, @PathVariable("photo_id") long id) {

        List<AdvertisementPhoto> advertisementPhotos = getAdvertisementPhotos();


        long idNext = 0;
        int y;
        for (int i = 0; i < advertisementPhotos.size(); i++) {

            if (advertisementPhotos.get(i).getId() == id) {
                if ((y = i + 1) < advertisementPhotos.size()) {
                    idNext = advertisementPhotos.get(y).getId();
                    AdvertisementPhoto adv = advertisementPhotos.get(y);
                    Long amount = adv.getAmount();
                    adv.setAmount(amount - 1);
                    advertisementPhotoService.addAdvertisement(adv);
                    break;
                } else {
                    idNext = advertisementPhotos.get(0).getId();
                    AdvertisementPhoto adv = advertisementPhotos.get(0);
                    Long amount = adv.getAmount();
                    adv.setAmount(amount - 1);
                    advertisementPhotoService.addAdvertisement(adv);
                    break;

                }
            }
        }

        addViewedAdvertisement(idNext);

        model.addAttribute("photo_id", idNext);


        return "advertisement_view";
    }


    @RequestMapping(value = "/advertisement/viewed/{photo_id}")
    public String onViewBack(Model model, @PathVariable("photo_id") long id) {

        List<AdvertisementPhoto> advertisementPhotos = getAdvertisementPhotos();


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
                    advertisementPhotoService.addAdvertisement(adv);
                    break;

                } else {
                    idBack = advertisementPhotos.get(advertisementPhotos.size() - 1).getId();
                    AdvertisementPhoto adv = advertisementPhotos.get(advertisementPhotos.size() - 1);
                    Long amount = adv.getAmount();
                    adv.setAmount(amount - 1);
                    advertisementPhotoService.addAdvertisement(adv);
                    break;

                }
            }
        }

        addViewedAdvertisement(idBack);

        model.addAttribute("photo_id", idBack);


        return "advertisement_view";
    }


    @RequestMapping("/advertisement/add_page")
    public String advertisementAddPage() {

        return "advertisement_add_page";
    }

    @RequestMapping(value = "/advertisement/add", method = RequestMethod.POST)
    public String dishAdd(@RequestParam String name,
                          @RequestParam(value = "photo") MultipartFile body_photo,
                          HttpServletRequest request,
                          HttpServletResponse response,
                          @RequestParam long cost,
                          @RequestParam long amount,
                          @RequestParam long total_amount) {

        if (name == null || body_photo == null || cost == 0 || amount == 0 || total_amount == 0) {
            return "redirect:/advertisementList";
        }
        try {
            AdvertisementPhoto advertisementPhoto = new AdvertisementPhoto(body_photo.getBytes(), name,
                    cost, amount, total_amount);
            advertisementPhotoService.addAdvertisement(advertisementPhoto);
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }


        return "redirect:/advertisementList";
    }


    @RequestMapping("/photo/advertisement/{photo_id}")
    public ResponseEntity<byte[]> onPhoto(@PathVariable("photo_id") long id) {
        return photoById(id);
    }


    @RequestMapping(value = "/advertisement/delete", method = RequestMethod.POST)
    public String deleteAdvertisement(@RequestParam(value = "toDelete[]", required = false) long[] toDelete) {
        if (toDelete != null && toDelete.length > 0) {
            advertisementPhotoService.deleteAdvertisement(toDelete);
        }

        return "redirect:/advertisementList";
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


    @RequestMapping("/admin")
    public String Admin(Model model) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        model.addAttribute("login", login);
        return "admin";
    }

    @RequestMapping("/user")
    public String User(Model model) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        model.addAttribute("login", login);
        return "user";
    }


    //methods

    private List<AdvertisementPhoto> getAdvertisementPhotos() {
        List<AdvertisementPhoto> advertisementPhotos = advertisementPhotoService.findAll();

        for (AdvertisementPhoto adv : advertisementPhotos) {

            if (adv.getAmount() <= 0) {
                advertisementPhotoService.deleteId(adv.getId());
            }
        }

        advertisementPhotos = advertisementPhotoService.findAll();
        return advertisementPhotos;
    }

    private void addViewedAdvertisement(long id) {
        ViewedAdvertisement viewedAdvertisement = new ViewedAdvertisement(advertisementPhotoService.findOne(id),
                advertisementPhotoService.findOne(id).getCost());
        viewedAdvertisementService.addViewedAdvertisement(viewedAdvertisement);
    }


    private long getPageCountAdvertisement() {
        long totalCount = advertisementPhotoService.count();
        return (totalCount / ITEMS_PER_PAGE_ADVERTISEMENT) + ((totalCount % ITEMS_PER_PAGE_ADVERTISEMENT > 0) ? 1 : 0);
    }

    private long getPageCookedOrders() {
        long totalCount = cookedOrderService.count();
        return (totalCount / ITEMS_PER_PAGE_COOKED_ORDERS) + ((totalCount % ITEMS_PER_PAGE_COOKED_ORDERS > 0) ? 1 : 0);
    }

    private long getPageCountOrders() {
        long totalCount = orderService.count();
        return (totalCount / ITEMS_PER_PAGE_COUNT_ORDERS) + ((totalCount % ITEMS_PER_PAGE_COUNT_ORDERS > 0) ? 1 : 0);
    }

    private long getPageCountCook() {
        long totalCount = cookService.count();
        return (totalCount / ITEMS_PER_PAGE_COOK) + ((totalCount % ITEMS_PER_PAGE_COOK > 0) ? 1 : 0);
    }

    private void responsePhoto(HttpServletResponse response, byte[] content) throws IOException {
        response.setContentType("photo/png");
        response.getOutputStream().write(content);
    }


    private long getPageCountDish() {
        long totalCount = dishService.count();
        return (totalCount / ITEMS_PER_PAGE_DISH) + ((totalCount % ITEMS_PER_PAGE_DISH > 0) ? 1 : 0);
    }

    private long getPageCountUser() {
        long totalCount = userService.count();
        return (totalCount / ITEMS_PER_PAGE_USER) + ((totalCount % ITEMS_PER_PAGE_USER > 0) ? 1 : 0);
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


    private ResponseEntity<byte[]> photoById(long id) {
        byte[] bytes = advertisementPhotoService.get(id).getPhoto();
        advertisementPhotoService.PhotoAdvertisementNotFoundException(bytes);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);

        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
    }


    private void getAuthentication(Model model) {
        if (!SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")) {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String login = user.getUsername();
            model.addAttribute("login", login);
            CustomUser customUser = userService.getUserByLogin(login);
            if(customUser.getBonus() != null) {
                int bonus = customUser.getBonus();
                if (bonus >= 15) {
                    model.addAttribute("bonus", bonus);
                }
            }
        }
    }

}
