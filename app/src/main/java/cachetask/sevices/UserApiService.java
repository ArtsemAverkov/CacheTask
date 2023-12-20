package cachetask.sevices;


import cachetask.aop.cache.Cacheable;
import cachetask.aop.cache.CachingAspect;
import cachetask.entity.User;
import cachetask.repository.UserRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class UserApiService implements UserService{

    private UserRepository userRepository;
    public UserApiService(UserRepository userRepository){
        this.userRepository = userRepository;

    }

    @Cacheable("userCache")
    @Override
    public boolean create(User user) {
        return userRepository.create(user);
    }

    @Cacheable("userCache")
    @Override
    public User read(Long id) {
        Optional<User> read = userRepository.read(id);
        return read.orElseThrow(() -> new NoSuchElementException("User not found with id: " + id));
    }

    @Cacheable("userCache")
    @Override
    public boolean update(User user, Long id) {
        return userRepository.update(user, id);
    }

    @Cacheable("userCache")
    @Override
    public boolean delete(Long id) {
        return userRepository.delete(id);
    }


    @Override
    public List<User> readAll(int page, int pageSize) {
        int startIndex = (page - 1) * pageSize;
        return userRepository.readAll(startIndex, pageSize);
    }
    }
}
