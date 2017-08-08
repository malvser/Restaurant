package malov.serg;

import malov.serg.kitchen.Dish;
import malov.serg.kitchen.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@ComponentScan
@Controller
public class MyController {

    static final int ITEMS_PER_PAGE = 4;

    @Autowired
    private DishService dishService;

    @RequestMapping("/")
    public String index(){
        return "index";
    }


    @RequestMapping("/menu")
    public String index(Model model, @RequestParam(required = false, defaultValue = "0") Integer page) {
        if (page < 0) page = 0;

        List<Dish> dishes = dishService
                .findAll(new PageRequest(page, ITEMS_PER_PAGE, Sort.Direction.DESC, "id"));


        model.addAttribute("allPages", page);

        model.addAttribute("dishes", dishes);
        model.addAttribute("allPages", getPageCount());

        return "main";
    }
/*
    @RequestMapping("/order")
    public String order(Model model, @RequestParam(required = false, defaultValue = "0") Integer page) {
        if (page < 0) page = 0;

        List<Dish> dishes = dishService
                .findAll(new PageRequest(page, ITEMS_PER_PAGE, Sort.Direction.DESC, "id"));


        model.addAttribute("allPages", page);

        model.addAttribute("dishesArrayId", dishes);
        model.addAttribute("allPages", getPageCount());

        return "order";
    }
*/

    @RequestMapping(value = "/order", method = RequestMethod.POST )
    public String order(Model model, @RequestParam(value = "id") long[] id){
        if(id == null){

        }
        model.addAttribute("dishesArrayId", dishService.findArrayId(id));
        return "order";

    }


    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public String search(@RequestParam String pattern, Model model) {
        model.addAttribute("dishes", dishService.findByPattern(pattern, null));

        return "main";
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
                             @RequestParam int duration
                             )  {


        try {
            Dish dish = new Dish(body_photo.getBytes(), name, cost, weight, discount, duration );
            dishService.addDish(dish);
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }


        return "redirect:/menu";
    }

    @RequestMapping("/photo/{photo_id}")
    public void getFile(HttpServletRequest request, HttpServletResponse response, @PathVariable("photo_id") long fileId) {
        try {
            byte[] content = dishService.getPhotoOne(fileId);
            response.setContentType("photo/png");
            response.getOutputStream().write(content);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    private long getPageCount() {
        long totalCount = dishService.count();
        return (totalCount / ITEMS_PER_PAGE) + ((totalCount % ITEMS_PER_PAGE > 0) ? 1 : 0);
    }


   /* @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ModelAndView delete(@RequestParam(value = "id") long[] id) {
        photoDAO.delete(id);
        return new ModelAndView("main", "photos", photoDAO.getWholeList());
    }*/



}
