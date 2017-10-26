package malov.serg.Service;

import malov.serg.Model.CustomUser;
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
    @Transactional
    public void updateUser(CustomUser customUser) {
        userRepository.save(customUser);
    }

    @Transactional(readOnly = true)
    public List<CustomUser> findAll(Pageable pageable) {
        return userRepository.findAll(pageable).getContent();
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
