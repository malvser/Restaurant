package malov.serg.Service;

import malov.serg.Model.CustomUser;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    CustomUser getUserByLogin(String login);
    boolean existsByLogin(String login);
    void addUser(CustomUser customUser);
    CustomUser findOne(long id);
    void updateUser(CustomUser customUser);
    List<CustomUser> findAll(Pageable pageable);
    List<CustomUser> findFullName(String full_name);
    void deleteDishes(long[] idList);
    long count();
}
