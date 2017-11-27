package malov.serg.Web;

import malov.serg.Model.CustomUser;
import malov.serg.Model.UserRole;
import malov.serg.Service.DishService;
import malov.serg.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
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

import java.util.List;

@Controller
@ComponentScan("malov.serg")
public class LoginController {

    static final int ITEMS_PER_PAGE_USER = 6;

    @Autowired
    private UserService userService;

    @Autowired
    private DishService dishService;

    @RequestMapping("/")
    public String index(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        model.addAttribute("login", login);
        return "index";
    }

    @RequestMapping("/login")
    public String loginPage() {
        return "login";
    }

    @RequestMapping("/unauthorized")
    public String unauthorized(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("login", user.getUsername());
        return "unauthorized";
    }

    @RequestMapping("/admin")
    public String admin(Model model) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        model.addAttribute("login", login);
        return "admin";
    }

    @RequestMapping(value = "/user/delete", method = RequestMethod.POST)
    public ResponseEntity<Void> deleteUser(@RequestParam(value = "toDelete[]", required = false) long[] toDelete) {
        if (toDelete != null && toDelete.length > 0) {
            userService.deleteDishes(toDelete);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @RequestMapping("/user")
    public String User(Model model) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser client = userService.getUserByLogin(login);
        if (client.getBonus() != null) {
            int bonus = client.getBonus();
            if (bonus >= 10) {
                model.addAttribute("bonus", bonus);
            }
        }
        model.addAttribute("login", login);
        model.addAttribute("bonus_user", client.getBonus());
        model.addAttribute("dishes", dishService.findByDiscount());
        return "user";
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


    @RequestMapping(value = "/register_edit_admin_{user_id}")
    public String userEdit(Model model, @PathVariable("user_id") long id) {

        CustomUser customUser = userService.findOne(id);
        model.addAttribute("bonus", customUser.getBonus());
        model.addAttribute("login", customUser.getLogin());
        model.addAttribute("email", customUser.getEmail());
        model.addAttribute("full_name", customUser.getFull_name());
        model.addAttribute("phone", customUser.getPhone());
        model.addAttribute("role", customUser.getRole());
        model.addAttribute("userRole", UserRole.values());
        model.addAttribute("user_id", id);

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
                          @RequestParam String full_name,
                          Model model) {
        if (userService.existsByLogin(login)) {
            model.addAttribute("exists", true);
            return "register";
        }

        //ShaPasswordEncoder encoder = new ShaPasswordEncoder();
       // String passHash = encoder.encodePassword(password, null);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String passHash = bCryptPasswordEncoder.encode(password);
        CustomUser dbUser;
        if(full_name != null){
            dbUser = new CustomUser(login, passHash, UserRole.USER, email, phone, 0, full_name);
        }else{
            dbUser = new CustomUser(login, passHash, UserRole.USER, email, phone, 0);
        }

        userService.addUser(dbUser);

        return "redirect:/";
    }

    @RequestMapping(value = "/newanyuser", method = RequestMethod.POST)
    public String newCook(@RequestParam String login,
                          @RequestParam String password,
                          @RequestParam(value = "role") String role,
                          @RequestParam String email,
                          @RequestParam String phone,
                          @RequestParam String full_name,
                          @RequestParam(required = false) Long user_id,
                          @RequestParam Integer bonus,
                          Model model) {
        if (userService.existsByLogin(login) && user_id == null) {
            model.addAttribute("exists", true);
            model.addAttribute("userRole", UserRole.values());
            return "register_for_admin";
        }


        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String passHash = bCryptPasswordEncoder.encode(password);

        /*ShaPasswordEncoder encoder = new ShaPasswordEncoder();
        String passHash = encoder.encodePassword(password, null);*/

        if(user_id != null){
            System.out.println("role = " + "ROLE_" + role);
            System.out.println("UserRole.ADMIN.toString() = " + UserRole.ADMIN.toString());
            if (("ROLE_" + role).equals(UserRole.ADMIN.toString())) {
                CustomUser user = userService.findOne(user_id);
                user.setBonus(bonus);
                user.setEmail(email);
                user.setFull_name(full_name);
                user.setLogin(login);
                user.setPassword(passHash);
                user.setPhone(phone);
                user.setRole(UserRole.ADMIN);
                userService.addUser(user);
            } else if (("ROLE_" + role).equals(UserRole.COOK.toString())) {
                CustomUser user = userService.findOne(user_id);
                user.setBonus(bonus);
                user.setEmail(email);
                user.setFull_name(full_name);
                user.setLogin(login);
                user.setPassword(passHash);
                user.setPhone(phone);
                user.setRole(UserRole.COOK);
                userService.addUser(user);
            } else {
                CustomUser user = userService.findOne(user_id);
                user.setBonus(bonus);
                user.setEmail(email);
                user.setFull_name(full_name);
                user.setLogin(login);
                user.setPassword(passHash);
                user.setPhone(phone);
                user.setRole(UserRole.USER);
                userService.addUser(user);

            }

        }else{

            if (("ROLE_" + role).equals(UserRole.ADMIN.toString())) {
                CustomUser user = new CustomUser(login, passHash, UserRole.ADMIN, email, phone, 0, full_name);
                userService.addUser(user);
            } else if (("ROLE_" + role).equals(UserRole.COOK.toString())) {
                CustomUser user = new CustomUser(login, passHash, UserRole.COOK, email, phone, 0, full_name);
                userService.addUser(user);
            } else {
                CustomUser user = new CustomUser(login, passHash, UserRole.USER, email, phone, 0, full_name);
                userService.addUser(user);
            }

        }


        return "redirect:/userlist";
    }

    @RequestMapping(value = "/search_user", method = RequestMethod.POST)
    public String searchUsers(@RequestParam(required = false) String pattern, Model model) {
        if(pattern != null) {
            model.addAttribute("customUsers", userService.findFullName(pattern));
        }
        return "userList";
    }

    private long getPageCountUser() {
        long totalCount = userService.count();
        return (totalCount / ITEMS_PER_PAGE_USER) + ((totalCount % ITEMS_PER_PAGE_USER > 0) ? 1 : 0);
    }
}
