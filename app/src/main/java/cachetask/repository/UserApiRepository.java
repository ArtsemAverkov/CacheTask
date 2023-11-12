package cachetask.repository;

import cachetask.connect.Connect;
import cachetask.connect.ConnectPostgresQL;
import cachetask.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class UserApiRepository implements UserRepository{
    Connect connection;

    public UserApiRepository(){
        connection = new ConnectPostgresQL();
    }

    @Override
    public boolean create(User user) {
        try (Connection conn = connection.connect()) {
            PreparedStatement statement =
                    conn.prepareStatement("INSERT INTO users (name, last_name, email) VALUES (?,?,?)");
            statement.setString(1, user.getName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getEmail());
            int rowsInserted = statement.executeUpdate();

            if (rowsInserted == 0) {
                throw new SQLException("Вставка записи не удалась, ни одна строка не была изменена.");
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return true;
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
