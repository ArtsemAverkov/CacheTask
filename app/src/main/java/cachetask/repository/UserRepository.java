package cachetask.repository;



import cachetask.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    boolean create(User user);
    Optional<User> read (Long id);
    boolean update(User user, Long id);
    boolean delete(Long id);
    List<User> readAll(int startIndex, int pageSize);
}
