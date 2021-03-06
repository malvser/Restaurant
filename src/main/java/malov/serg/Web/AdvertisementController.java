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
    private AdvertisementPhotoService advertisementPhotoService;



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

        List<AdvertisementPhoto> advertisementPhotos = getAdvertisementPhotos();


        return advertisementPhotoService.onView(advertisementPhotos, model, idDishes);
    }


    @RequestMapping(value = "/advertisement_view_{photo_id}")
    public String onViewNext(Model model, @PathVariable("photo_id") long id) {

        List<AdvertisementPhoto> advertisementPhotos = getAdvertisementPhotos();

        long idNext = advertisementPhotoService.onViewNext(advertisementPhotos, id);
       advertisementPhotoService.addViewedAdvertisement(idNext);

        model.addAttribute("photo_id", idNext);


        return "advertisement_view";
    }


    @RequestMapping(value = "/advertisement_viewed_{photo_id}")
    public String onViewBack(Model model, @PathVariable("photo_id") long id) {

        List<AdvertisementPhoto> advertisementPhotos = getAdvertisementPhotos();


        long idBack = advertisementPhotoService.onViewBack(advertisementPhotos, id);


        advertisementPhotoService.addViewedAdvertisement(idBack);

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

        advertisementPhotoService.advertisement_id(advertisement_id, response, name, body_photo,
                cost, amount, total_amount);


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
