package malov.serg.Web;


import malov.serg.Model.CustomUser;
import malov.serg.Model.Dish;
import malov.serg.Service.DishService;
import malov.serg.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
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


@ComponentScan("malov.serg")
@Controller
public class DishController {

    static final int ITEMS_PER_PAGE_DISH = 6;

    @Autowired
    private DishService dishService;

    @Autowired
    private UserService userService;

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


    @RequestMapping(value = "/dish_edit_page_{dish_id}")
    public String dishEdit(Model model, @PathVariable("dish_id") long id) {

        Dish dish = dishService.findOne(id);
        model.addAttribute("bonus", dish.getBonus());
        model.addAttribute("cost", dish.getCost());
        model.addAttribute("discount", dish.getDiscount());
        model.addAttribute("duration", dish.getDuration());
        model.addAttribute("name", dish.getName());
        model.addAttribute("weight", dish.getWeight());
        model.addAttribute("type", dish.getType());
        model.addAttribute("photo", dish.getPhoto());
        model.addAttribute("dish_id", id);

        return "dish_add_page";
    }

    @RequestMapping(value = "/dishes/delete", method = RequestMethod.POST)
    public ResponseEntity<Void> deleteDishes(@RequestParam(value = "toDelete[]", required = false) long[] toDelete) {
        if (toDelete != null && toDelete.length > 0)
            dishService.deleteDishes(toDelete);

        return new ResponseEntity<>(HttpStatus.OK);
    }



    @RequestMapping("/add_dish")
    public String dishAddPage(Model model) {


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
                          @RequestParam String type,
                          @RequestParam(required = false) Long dish_id) {

        if(dish_id != null){
            Dish dish = dishService.findOne(dish_id);
            dish.setBonus(bonus);
            dish.setType(type);
            dish.setDiscount(discount);
            dish.setName(name);
            dish.setCost(cost);
            dish.setDuration(duration);
            dish.setWeight(weight);
            try {
                dish.setPhoto(body_photo.getBytes());
            } catch (IOException e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                e.printStackTrace();
            }
            dishService.addDish(dish);

        }else {

            try {
                Dish dish = new Dish(body_photo.getBytes(), name, cost, weight, discount, duration, type, bonus);
                dishService.addDish(dish);
            } catch (IOException e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                e.printStackTrace();
            }

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

    @RequestMapping(value = "/search_dish", method = RequestMethod.POST)
    public String searchDish(@RequestParam String pattern, Model model) {

        model.addAttribute("dishes", dishService.findByPattern(pattern));

        return "main";
    }

    @RequestMapping("/search_hot_snacks")
    public String searchHotSnacks(Model model) {


        getAuthentication(model);

        String pattern = "Горячие закуски";
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
    public String searchAll(Model model, @RequestParam(required = false, defaultValue = "0") Integer page) {

        getAuthentication(model);
        if (page < 0) page = 0;
        List <Dish> dishes = dishService.findAll(new PageRequest(page, ITEMS_PER_PAGE_DISH, Sort.Direction.DESC, "id"));
        String pattern = "Все блюда";
        model.addAttribute("dishes", dishes);
        model.addAttribute("pattern", pattern);
        model.addAttribute("allPages", page);
        model.addAttribute("allPages", getPageCountDish());
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

    @RequestMapping("/search_meat_dishes")
    public String searchMeat(Model model) {

        getAuthentication(model);

        String pattern = "Мясные и рыбные блюда";
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

    @RequestMapping("/search_soups")
    public String searchSoups(Model model) {

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


        String pattern = "Алкоголь";
        model.addAttribute("dishes", dishService.findByType(pattern));
        model.addAttribute("pattern", pattern);
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


    private long getPageCountDish() {
        long totalCount = dishService.count();
        return (totalCount / ITEMS_PER_PAGE_DISH) + ((totalCount % ITEMS_PER_PAGE_DISH > 0) ? 1 : 0);
    }

    private void getAuthentication(Model model) {
        if (!SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")) {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String login = user.getUsername();
            model.addAttribute("login", login);
            CustomUser customUser = userService.getUserByLogin(login);
            if (customUser.getBonus() != null) {
                int bonus = customUser.getBonus();
                if (bonus >= 15) {
                    model.addAttribute("bonus", bonus);
                }
            }
        }
    }

    private void responsePhoto(HttpServletResponse response, byte[] content) throws IOException {
        response.setContentType("photo/png");
        response.getOutputStream().write(content);
    }


}
