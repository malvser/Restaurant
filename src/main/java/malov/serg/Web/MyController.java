package malov.serg.Web;

import malov.serg.Model.*;
import malov.serg.PhotoNotFoundException;
import malov.serg.Service.*;
import malov.serg.ad.Advertisement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.PageRequest;

import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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


    //DishController

    @RequestMapping("/")
    public String index() {
        return "index";
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

        List<Order> orderList =  orderService.findAll();
        Order order = null;
        if(orderList.size() > 0){
           order = orderList.get(0);
           orderService.deleteOrder(order.getId());
        }

        model.addAttribute("allPages", page);
        if(order != null) {
            model.addAttribute("dishesList", order.getDishes());
            model.addAttribute("numberTable", order.getTablet().getNumber());
        }
        model.addAttribute("allPages", getPageCountOrders());

        return "order_for_cooks";
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
                order = new Order(tablet.get(random), dishService.findArrayId(id));
            } catch (IOException e) {
                e.printStackTrace();
            }
            orderService.addOrder(order);

            tabletService.addQueueOrder(tablet.get(random), order);


            model.addAttribute("dishesArrayId", dishService.findArrayId(id));
            return "order_cook";
    }



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
    public String onView(Model model) {

        List<AdvertisementPhoto> advertisementPhotos = advertisementPhotoService.findAll();

        for (AdvertisementPhoto adv : advertisementPhotos) {

            if(adv.getHits() <= 0){
                advertisementPhotoService.deleteId(adv.getId());
            }
        }

        advertisementPhotos = advertisementPhotoService.findAll();

        int random = (ThreadLocalRandom.current().nextInt(advertisementPhotos.size()));
        long id = advertisementPhotos.get(random).getId();

        AdvertisementPhoto adv = advertisementPhotos.get(random);
        int hits = adv.getHits();
        adv.setHits(hits - 1);
        advertisementPhotoService.addAdvertisement(adv);

        model.addAttribute("photo_id",id);

        return "advertisement_view";
    }

    @RequestMapping(value = "/advertisement/view/{photo_id}")
    public String onViewNext(Model model, @PathVariable("photo_id") long id) {

        List<AdvertisementPhoto> advertisementPhotos = advertisementPhotoService.findAll();

        for (AdvertisementPhoto adv : advertisementPhotos) {

            if(adv.getHits() <= 0){
                advertisementPhotoService.deleteId(adv.getId());
            }
        }

        advertisementPhotos = advertisementPhotoService.findAll();

        long idNext = 0;
        int y = 0;
        for (int i = 0; i < advertisementPhotos.size(); i++) {

            if(advertisementPhotos.get(i).getId() == id){
                if((y = i + 1) < advertisementPhotos.size()){
                    idNext = advertisementPhotos.get(y).getId();
                    AdvertisementPhoto adv = advertisementPhotos.get(y);
                    int hits = adv.getHits();
                    adv.setHits(hits - 1);
                    advertisementPhotoService.addAdvertisement(adv);
                    break;
                }else{
                    idNext = advertisementPhotos.get(0).getId();
                    AdvertisementPhoto adv = advertisementPhotos.get(0);
                    int hits = adv.getHits();
                    adv.setHits(hits - 1);
                    advertisementPhotoService.addAdvertisement(adv);
                    break;

                }
            }
        }



        model.addAttribute("photo_id",idNext);

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
                          @RequestParam int initialAmount,
                          @RequestParam int hits) {


        try {
            AdvertisementPhoto advertisementPhoto = new AdvertisementPhoto(body_photo.getBytes(), name, initialAmount, hits);
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

   /* @RequestMapping("/photo/advertisement/{photo_id}")
    public void getPhotoAdvertisement(HttpServletRequest request, HttpServletResponse response, @PathVariable("photo_id") long photoId) {
        try {
            byte[] content = advertisementPhotoService.getPhotoOne(photoId);
            responsePhoto(response, content);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }*/


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

    private ResponseEntity<byte[]> allphotos() {
        List<AdvertisementPhoto> advertisementPhotos = advertisementPhotoService.findAll();

        int random = (ThreadLocalRandom.current().nextInt(advertisementPhotos.size()));
        long id = advertisementPhotos.get(random).getId();
        byte[] bytes = advertisementPhotoService.get(id).getPhoto();
        if (bytes == null)
            throw new PhotoNotFoundException();


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);

        return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK);
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
