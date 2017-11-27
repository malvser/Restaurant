package malov.serg.Web;


import malov.serg.Model.*;
import malov.serg.Service.AdvertisementPhotoService;
import malov.serg.Service.DishService;
import malov.serg.Service.NoAdvertisementService;
import malov.serg.Service.ViewedAdvertisementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@ComponentScan("malov.serg")
@Controller
public class AdvertisementController {

    static final int ITEMS_PER_PAGE_ADVERTISEMENT = 6;

    @Autowired
    private DishService dishService;

    @Autowired
    private NoAdvertisementService noAdvertisementService;

    @Autowired
    private AdvertisementPhotoService advertisementPhotoService;

    @Autowired
    private ViewedAdvertisementService viewedAdvertisementService;


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

    @RequestMapping("/no_advertisement")
    public String noAdvertisement(Model model) {
        if (!SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")) {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String login = user.getUsername();
            model.addAttribute("login", login);

        }


        return "no_advertisement";
    }


    @RequestMapping(value = "/advertisement_view", method = RequestMethod.POST)
    public String onView(Model model, @RequestParam(required = false, value = "IdDishes") String idDishes) {

        List<AdvertisementPhoto> advertisementPhotos;
        if((advertisementPhotos = getAdvertisementPhotos()).size() > 0)

         {

            int random = (ThreadLocalRandom.current().nextInt(advertisementPhotos.size()));
            long id = advertisementPhotos.get(random).getId();

            AdvertisementPhoto adv = advertisementPhotos.get(random);
            Long amount = adv.getAmount();
            adv.setAmount(amount - 1);
            advertisementPhotoService.addAdvertisement(adv);

            addViewedAdvertisement(id);

            model.addAttribute("photo_id", id);
        } else if (!idDishes.equals("")) {

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


        }else{
            return "redirect:/no_advertisement";
        }

        return "advertisement_view";
    }


    @RequestMapping(value = "/advertisement_view_{photo_id}")
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


    @RequestMapping(value = "/advertisement_viewed_{photo_id}")
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


    @RequestMapping(value = "/advertisement_edit_page_{advertisement_id}")
    public String advertisementEdit(Model model, @PathVariable("advertisement_id") long id) {

        AdvertisementPhoto advertisementPhoto = advertisementPhotoService.findOne(id);
        byte[] photo = advertisementPhoto.getPhoto();
        String name = advertisementPhoto.getName();
        long cost = advertisementPhoto.getCost();
        long amount = advertisementPhoto.getAmount();
        long total_amount = advertisementPhoto.getTotal_amount();

        model.addAttribute("photo",photo);
        model.addAttribute("name",name);
        model.addAttribute("cost",cost);
        model.addAttribute("amount",amount);
        model.addAttribute("total_amount",total_amount);
        model.addAttribute("advertisement_id", id);

        return "advertisement_add_page";
    }

    @RequestMapping("/advertisement_add_page")
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
                          @RequestParam long total_amount,
                          @RequestParam(required = false) Long advertisement_id) {


        if(advertisement_id != null){
            AdvertisementPhoto advertisementPhoto = advertisementPhotoService.findOne(advertisement_id);
            advertisementPhoto.setAmount(amount);
            advertisementPhoto.setTotal_amount(total_amount);
            advertisementPhoto.setName(name);
            advertisementPhoto.setCost(cost);
            try {
                advertisementPhoto.setPhoto(body_photo.getBytes());
            } catch (IOException e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                e.printStackTrace();
            }
            advertisementPhotoService.addAdvertisement(advertisementPhoto);
        }else {

            try {
                AdvertisementPhoto advertisementPhoto = new AdvertisementPhoto(body_photo.getBytes(), name,
                        cost, amount, total_amount);
                advertisementPhotoService.addAdvertisement(advertisementPhoto);
            } catch (IOException e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                e.printStackTrace();
            }
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

    @RequestMapping(value = "/search_advertisement", method = RequestMethod.POST)
    public String searchAdvertisement(@RequestParam String pattern, Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        model.addAttribute("login", login);
        model.addAttribute("advertisement", advertisementPhotoService.findByPattern(pattern, null));
        return "advertisementList";
    }

    private long getPageCountAdvertisement() {
        long totalCount = advertisementPhotoService.count();
        return (totalCount / ITEMS_PER_PAGE_ADVERTISEMENT) + ((totalCount % ITEMS_PER_PAGE_ADVERTISEMENT > 0) ? 1 : 0);
    }

    private ResponseEntity<byte[]> photoById(long id) {
        byte[] bytes = advertisementPhotoService.get(id).getPhoto();
        advertisementPhotoService.PhotoAdvertisementNotFoundException(bytes);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);

        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
    }

    private void addViewedAdvertisement(long id) {
        ViewedAdvertisement viewedAdvertisement = new ViewedAdvertisement(advertisementPhotoService.findOne(id),
                advertisementPhotoService.findOne(id).getCost());
        viewedAdvertisementService.addViewedAdvertisement(viewedAdvertisement);
    }

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


}
