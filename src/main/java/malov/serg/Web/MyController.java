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

    static final int ITEMS_PER_PAGE = 4;
    static final int ITEMS_PER_PAGE_TABLET = 8;

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
    public String unauthorized(Model model){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("login", user.getUsername());
        return "unauthorized";
    }

    @RequestMapping("/register")
    public String register() {
        return "register";
    }


    @RequestMapping(value = "/newuser", method = RequestMethod.POST)
    public String update(@RequestParam String login,
                         @RequestParam String password,
                         @RequestParam(required = false) String email,
                         @RequestParam(required = false) String phone,
                         Model model) {
        if (userService.existsByLogin(login)) {
            model.addAttribute("exists", true);
            return "register";
        }

        ShaPasswordEncoder encoder = new ShaPasswordEncoder();
        String passHash = encoder.encodePassword(password, null);

        CustomUser dbUser = new CustomUser(login, passHash, UserRole.USER, email, phone);
        userService.addUser(dbUser);

        return "redirect:/";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(@RequestParam(required = false) String email, @RequestParam(required = false) String phone) {
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();

        //изменить код. на синхронизацию
        CustomUser dbUser = userService.getUserByLogin(login);
        dbUser.setEmail(email);
        dbUser.setPhone(phone);

        userService.updateUser(dbUser);

        return "redirect:/menu";
    }

    @RequestMapping("/name")
    public String name() {

        return "name";
    }


    //DishController

    @RequestMapping("/")
    public String index(Model model) {
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();

        CustomUser dbUser = userService.getUserByLogin(login);

        model.addAttribute("login", login);
        model.addAttribute("roles", user.getAuthorities());
        model.addAttribute("email", dbUser.getEmail());
        model.addAttribute("phone", dbUser.getPhone());

        return "index";
    }

    /*@RequestMapping("/login")
    public String login() {
        return "login";
    }*/

    @RequestMapping("/enter_cook")
    public String indexCook(Model model) {

        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        model.addAttribute("login", login);
        return "index_cook";
    }




    @RequestMapping("/menu")
    public String index(Model model, @RequestParam(required = false, defaultValue = "0") Integer page) {

        if (page < 0) page = 0;

        List<Dish> dishes = dishService
                .findAll(new PageRequest(page, ITEMS_PER_PAGE, Sort.Direction.DESC, "id"));


        model.addAttribute("allPages", page);

        model.addAttribute("dishes", dishes);
        model.addAttribute("allPages", getPageCountDish());

        return "main";
    }


    @RequestMapping("/add_dish")
    public String dishAddPage() {

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
                          @RequestParam int duration
    ) {


        try {
            Dish dish = new Dish(body_photo.getBytes(), name, cost, weight, discount, duration);
            dishService.addDish(dish);
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }


        return "redirect:/menu";
    }




    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public String search(@RequestParam String pattern, Model model) {
        model.addAttribute("dishes", dishService.findByPattern(pattern, null));

        return "main";
    }


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

        List<Tablet> tablets = tabletService
                .findAll(new PageRequest(page, ITEMS_PER_PAGE_TABLET, Sort.Direction.DESC, "id"));


        model.addAttribute("allPages", page);

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
        if (page < 0) page = 0;

        List<Cook> cookList = cookService
                .findAll(new PageRequest(page, ITEMS_PER_PAGE, Sort.Direction.DESC, "id"));

        model.addAttribute("allPages", page);
        model.addAttribute("cook", cookList);
        model.addAttribute("allPages", getPageCountCook());

        return "cookList";
    }

    @RequestMapping("/order_for_cooks")
    public String orderForCooks(Model model, @RequestParam(required = false, defaultValue = "0") Integer page){

        List<Order> orderList = orderService.findAll();
        Order order = null;
        if (orderList.size() > 0) {
            for(int i = 0; i < orderList.size(); i++){
                if(!orderList.get(i).getCooking()){
                    order = orderList.get(i);
                    order.setCooking(true);
                    orderService.addOrder(order);
                    break;
                }
            }
        }
        if(order != null) {
            model.addAttribute("dishesList", order.getDishes());
            model.addAttribute("numberTable", order.getTablet().getNumber());
            model.addAttribute("orderId", order.getId());
        }
        model.addAttribute("allPages", page);
        model.addAttribute("allPages", getPageCountOrders());

        return "order_for_cooks";
    }

    @RequestMapping(value = "/cooked_order", method = RequestMethod.POST)
    public String cookedOrder(@RequestParam(value = "order_id") long order_id){
        Order order = orderService.findOne(order_id);
        Date date = new Date();
        long[] id = new long[order.getDishes().size()];
        int i = 0;
        for (Dish dish : order.getDishes()) {
            id[i] = dish.getId();
            i++;
        }

        List <Cook> cooks = cookService.findAll();
        //переделать с рандом на конкрет. повара
        int random = (ThreadLocalRandom.current().nextInt(cooks.size()));
        CookedOrder cookedOrder = new CookedOrder(order.getTablet().getNumber(),
                cooks.get(random), dishService.findArrayId(id), order.getTotalCookingTime(),date);
//        StatisticManager.getInstance().register(cookedOrder);
        cookedOrderService.addCookedOrder(cookedOrder);
        orderService.deleteOrder(order_id);

        return "redirect:/order_for_cooks";
    }

    @RequestMapping(value = "/cook/delete", method = RequestMethod.POST)
    public String deleteCook(@RequestParam(value = "toDelete[]", required = false) long[] toDelete) {
        if (toDelete != null && toDelete.length > 0)
            cookService.deleteCookes(toDelete);
        return "cookList";
    }


    //...OrderController

    @RequestMapping(value = "/order", method = RequestMethod.POST)
    public String order(Model model, @RequestParam(value = "id") long[] id) {

        model.addAttribute("dishesArrayId", dishService.findArrayId(id));
        return "order";

    }


    @RequestMapping(value = "/made/order", method = RequestMethod.POST)
    public String orderCook(Model model, @RequestParam(value = "id") long[] id) {

            List<Tablet> tablet = tabletService.findAll();
            int random = (ThreadLocalRandom.current().nextInt(tablet.size()));
            Order order = null;

            try {
                order = new Order(tablet.get(random), dishService.findArrayId(id), false);
            } catch (IOException e) {
                e.printStackTrace();
            }
            orderService.addOrder(order);
            orderService.addOrder(order);
        String s = "";
        for (long i : id ) {
            s += "" + i + " ";
        }

            model.addAttribute("dishesArrayId", dishService.findArrayId(id));
            model.addAttribute("Id", s);
            return "order_cook";
    }




    // AdvertisementController

    @RequestMapping("/advertisementList")
    public String advertisementList(Model model, @RequestParam(required = false, defaultValue = "0") Integer page) {
        if (page < 0) page = 0;

        List<AdvertisementPhoto> advertisementPhotos = advertisementPhotoService
                .findAll(new PageRequest(page, ITEMS_PER_PAGE, Sort.Direction.DESC, "id"));


        model.addAttribute("allPages", page);
        model.addAttribute("advertisement", advertisementPhotos);
        model.addAttribute("allPages", getPageCountAdvertisement());

        return "advertisementList";
    }


    @RequestMapping(value = "/advertisement/view")
    public String onView(Model model, @RequestParam("IdDishes") String idDishes) {

        List<AdvertisementPhoto> advertisementPhotos = getAdvertisementPhotos();
        if(advertisementPhotos.size() > 0) {

            int random = (ThreadLocalRandom.current().nextInt(advertisementPhotos.size()));
            long id = advertisementPhotos.get(random).getId();

            AdvertisementPhoto adv = advertisementPhotos.get(random);
            Long amount = adv.getAmount();
            adv.setAmount(amount - 1);
            advertisementPhotoService.addAdvertisement(adv);

            addViewedAdvertisement(id);

            model.addAttribute("photo_id", id);
        }else{

                String[] array = idDishes.split(" ");
                long[] arrayId = new long[array.length];
            for (int i = 0; i < array.length; i++) {
                arrayId[i] = Long.parseLong(array[i]);
            }
               // System.out.println("idDishes = " + idDishes);

            List<Dish> dishes = dishService.findArrayId(arrayId);
            long sumDuration = 0;
            for (Dish dish :dishes) {
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
                if(i > 0) {
                        y = i - 1;
                        idBack = advertisementPhotos.get(y).getId();
                        AdvertisementPhoto adv = advertisementPhotos.get(y);
                        Long amount = adv.getAmount();
                        adv.setAmount(amount - 1);
                        advertisementPhotoService.addAdvertisement(adv);
                        break;

                }else {
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
    public ResponseEntity<Void> deleteAdvertisement(@RequestParam(value = "toDelete[]", required = false) long[] toDelete) {
        if (toDelete != null && toDelete.length > 0)
            advertisementPhotoService.deleteAdvertisement(toDelete);


        return new ResponseEntity<>(HttpStatus.OK);
    }

    //StatisticController

    @RequestMapping("/statistic/cooked_order")
    public String statisticCookedOrder(Model model, @RequestParam(required = false, defaultValue = "0") Integer page){

        List<CookedOrder> cookedOrderList = cookedOrderService.findAll(new PageRequest
                (page, ITEMS_PER_PAGE, Sort.Direction.DESC, "id"));

        model.addAttribute("cookedOrderList", cookedOrderList);
        //model.addAttribute("cookedOrderList", cookedOrderList);



        model.addAttribute("allPages", page);
        model.addAttribute("allPages", getPageCountOrders());

        return "statistics_cooked_order";
    }


    @RequestMapping("/statistic/viewed_advertisement")
    public String statisticViewedAdvertisement(Model model){

        List<ViewedAdvertisement> viewedAdvertisements = viewedAdvertisementService.findAll();
        model.addAttribute("viewedAdvertisements", viewedAdvertisements);
        return "statistics_viewed_advertisement";
    }

    @RequestMapping("/statistic/no_advertisement")
    public String statisticNoAdvertisement(Model model){

        List<NoAdvertisement> noAdvertisements = noAdvertisementService.findAll();
        model.addAttribute("noAdvertisements", noAdvertisements);
        return "statistics_no_advertisement";
    }

    @RequestMapping("/director/pages")
    public String statisticForDirector(){
        return "director_page";
    }


    //methods

    private List<AdvertisementPhoto> getAdvertisementPhotos() {
        List<AdvertisementPhoto> advertisementPhotos = advertisementPhotoService.findAll();

        for (AdvertisementPhoto adv : advertisementPhotos) {

            if(adv.getAmount() <= 0){
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
        return (totalCount / ITEMS_PER_PAGE) + ((totalCount % ITEMS_PER_PAGE > 0) ? 1 : 0);
    }

    private long getPageCountOrders() {
        long totalCount = orderService.count();
        return (totalCount / ITEMS_PER_PAGE) + ((totalCount % ITEMS_PER_PAGE > 0) ? 1 : 0);
    }

    private long getPageCountCook() {
        long totalCount = cookService.count();
        return (totalCount / ITEMS_PER_PAGE) + ((totalCount % ITEMS_PER_PAGE > 0) ? 1 : 0);
    }

    private void responsePhoto(HttpServletResponse response, byte[] content) throws IOException {
        response.setContentType("photo/png");
        response.getOutputStream().write(content);
    }


    private long getPageCountDish() {
        long totalCount = dishService.count();
        return (totalCount / ITEMS_PER_PAGE) + ((totalCount % ITEMS_PER_PAGE > 0) ? 1 : 0);
    }

    private long getPageCountTablet() {
        long totalCount = tabletService.count();
        return (totalCount / ITEMS_PER_PAGE_TABLET) + ((totalCount % ITEMS_PER_PAGE_TABLET > 0) ? 1 : 0);
    }


    private ResponseEntity<byte[]> photoById(long id) {
        byte[] bytes = advertisementPhotoService.get(id).getPhoto();
        if (bytes == null)
            throw new PhotoNotFoundException();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);

        return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK);
    }

}
