package cachetask.sevices;


import cachetask.entity.User;
import cachetask.repository.UserRepository;

import java.util.List;

public class UserApiService implements UserService{

    private UserRepository userRepository;
    public UserApiService(UserRepository userRepository){
        this.userRepository = userRepository;

    }
    @Override
    public boolean create(User user) {
        return userRepository.create(user);
    }

    @Override
    public User read(Long id) {
        return null;
    }

    @Override
    public boolean update(User user, Long id) {
        return false;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public List<User> readAll() {
        return null;
    }
}
