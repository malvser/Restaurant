package malov.serg.Service;

import malov.serg.Model.CustomUser;
import malov.serg.Model.UserRole;
import malov.serg.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public CustomUser getUserByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByLogin(String login) {
        return userRepository.existsByLogin(login);
    }

    @Override
    @Transactional
    public void addUser(CustomUser customUser) {
        userRepository.save(customUser);
    }

    @Override
    public CustomUser findOne(long id) {
        return userRepository.findOne(id);
    }

    @Override
    @Transactional
    public void updateUser(CustomUser customUser) {
        userRepository.save(customUser);
    }

    @Transactional
    public void deleteDishes(long[] idList) {
        for (long id : idList)
            userRepository.delete(id);
    }

    @Transactional(readOnly = true)
    public List<CustomUser> findAll(Pageable pageable) {
        return userRepository.findAll(pageable).getContent();
    }

    @Override
    @Transactional
    public void newAnyUser(String passHash, String login, String role, String email, String phone, String full_name,
                        Long user_id,
                        Integer bonus){

        if(user_id != null){
            System.out.println("role = " + "ROLE_" + role);
            System.out.println("UserRole.ADMIN.toString() = " + UserRole.ADMIN.toString());
            if (("ROLE_" + role).equals(UserRole.ADMIN.toString())) {
                CustomUser user = findOne(user_id);
                user.setBonus(bonus);
                user.setEmail(email);
                user.setFull_name(full_name);
                user.setLogin(login);
                user.setPassword(passHash);
                user.setPhone(phone);
                user.setRole(UserRole.ADMIN);
                addUser(user);
            } else if (("ROLE_" + role).equals(UserRole.COOK.toString())) {
                CustomUser user = findOne(user_id);
                user.setBonus(bonus);
                user.setEmail(email);
                user.setFull_name(full_name);
                user.setLogin(login);
                user.setPassword(passHash);
                user.setPhone(phone);
                user.setRole(UserRole.COOK);
                addUser(user);
            } else {
                CustomUser user = findOne(user_id);
                user.setBonus(bonus);
                user.setEmail(email);
                user.setFull_name(full_name);
                user.setLogin(login);
                user.setPassword(passHash);
                user.setPhone(phone);
                user.setRole(UserRole.USER);
                addUser(user);

            }

        }else{

            if (("ROLE_" + role).equals(UserRole.ADMIN.toString())) {
                CustomUser user = new CustomUser(login, passHash, UserRole.ADMIN, email, phone, 0, full_name);
                addUser(user);
            } else if (("ROLE_" + role).equals(UserRole.COOK.toString())) {
                CustomUser user = new CustomUser(login, passHash, UserRole.COOK, email, phone, 0, full_name);
                addUser(user);
            } else {
                CustomUser user = new CustomUser(login, passHash, UserRole.USER, email, phone, 0, full_name);
                addUser(user);
            }

        }
    }


    @Override
    public List<CustomUser> findFullName(String full_name) {
        return userRepository.findFullName(full_name);
    }


    @Transactional(readOnly = true)
    public long count() {
        return userRepository.count();
    }
}
