package cachetask.repository;



import cachetask.entity.User;

import java.util.List;

public interface UserRepository {
    boolean create(User user);
    User read (Long id);
    boolean update(User user, Long id);
    boolean delete(Long id);
    List<User> readAll();
}
