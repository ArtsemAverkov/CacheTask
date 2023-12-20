package cachetask.repository;

import cachetask.aop.cache.Cacheable;
import cachetask.connect.Connect;
import cachetask.connect.ConnectPostgresQL;
import cachetask.entity.User;
import lombok.SneakyThrows;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserApiRepository implements UserRepository {
    private static final Logger logger = LoggerFactory.getLogger(UserApiRepository.class);

    private Connect connection;

    public UserApiRepository() {
        connection = new ConnectPostgresQL();
    }

    @Override
    public boolean create(User user) {
        try (Connection conn = connection.connect();
             PreparedStatement statement =
                     conn.prepareStatement("INSERT INTO users (name, last_name, email) VALUES (?,?,?)")) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getEmail());
            int rowsInserted = statement.executeUpdate();

            if (rowsInserted == 0) {
                throw new SQLException("Вставка записи не удалась, ни одна строка не была изменена.");
            }

            logger.info("Создан новый пользователь: {}", user);
        } catch (SQLException | ClassNotFoundException e) {
            logger.error("Ошибка при создании пользователя", e);
        }
        return true;
    }

    @SneakyThrows
    @Override
    public Optional<User> read(Long id) {
        try (Connection conn = connection.connect();
             PreparedStatement statement = conn.prepareStatement("SELECT * FROM users WHERE user_id=?")) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    User user = new User(resultSet.getLong("user_id"),
                            resultSet.getString("name"),
                            resultSet.getString("last_name"),
                            resultSet.getString("email"));
                    return Optional.of(user);
                }
            }
        } catch (SQLException e) {
            logger.error("Ошибка при чтении пользователя", e);
        }
        return Optional.empty();
    }

    @SneakyThrows
    @Override
    public boolean update(User user, Long id) {
        try (Connection conn = connection.connect()) {
            Optional<User> read = read(id);
            if (read.isPresent()) {
                try (PreparedStatement statement = conn.prepareStatement(
                        "UPDATE users SET name=?, last_name=?, email=? WHERE user_id=?")) {
                    statement.setString(1, user.getName());
                    statement.setString(2, user.getLastName());
                    statement.setString(3, user.getEmail());
                    statement.setLong(4, id);
                    int rowsUpdated = statement.executeUpdate();

                    if (rowsUpdated > 0) {
                        logger.info("Пользователь с id={} обновлен: {}", id, user);
                        return true;
                    } else {
                        logger.warn("Не удалось обновить пользователя с id={}", id);
                    }
                }
            } else {
                logger.warn("Пользователь с id={} не найден, обновление невозможно", id);
            }
        } catch (SQLException e) {
            logger.error("Ошибка при обновлении пользователя", e);
        }
        return false;
    }

    @SneakyThrows
    @Override
    public boolean delete(Long id) {
        try (Connection conn = connection.connect()) {
            Optional<User> read = read(id);
            if (read.isPresent()) {
                try (PreparedStatement statement = conn.prepareStatement("DELETE FROM users WHERE user_id=?")) {
                    statement.setLong(1, id);
                    int rowsUpdated = statement.executeUpdate();

                    if (rowsUpdated > 0) {
                        logger.info("Пользователь с id={} удален", id);
                        return true;
                    } else {
                        logger.warn("Не удалось удалить пользователя с id={}", id);
                    }
                }
            } else {
                logger.warn("Пользователь с id={} не найден, удаление невозможно", id);
            }
        } catch (SQLException e) {
            logger.error("Ошибка при удалении пользователя", e);
        }
        return false;
    }

    @SneakyThrows
    @Override
    public List<User> readAll(int startIndex, int pageSize) {
        List<User> users = new ArrayList<>();
        try (Connection conn = connection.connect()) {
            // Используем PreparedStatement для безопасного выполнения запроса
            String query = "SELECT * FROM users LIMIT ?, ?";
            try (PreparedStatement statement = conn.prepareStatement(query)) {
                statement.setInt(1, startIndex);
                statement.setInt(2, pageSize);

                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        users.add(new User(
                                resultSet.getLong("user_id"),
                                resultSet.getString("name"),
                                resultSet.getString("last_name"),
                                resultSet.getString("email")
                        ));
                    }
                }
            }
        } catch (SQLException e) {
            logger.error("Ошибка при чтении пользователей с пагинацией", e);
        }
        return users;
    }
}