package cachetask.db;

import cachetask.connect.Connect;
import cachetask.connect.ConnectPostgresQL;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    private Connect connection;

    public DatabaseInitializer() {
        this.connection = new ConnectPostgresQL();
    }

    public  void initializeDatabase() throws ClassNotFoundException {
    try (Connection conn = connection.connect()){
        InputStream scriptStream = DatabaseInitializer.class.getResourceAsStream("sql/create_schema.sql");
        BufferedReader reader = new BufferedReader(new InputStreamReader(scriptStream));
        StringBuilder script = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            script.append(line);
            script.append("\n");
        }

        Statement statement = conn.createStatement();
        statement.executeUpdate(script.toString());
    } catch (SQLException | IOException e) {
        e.printStackTrace();
    }
}

    public  void insertDatabase() throws ClassNotFoundException {
        try (Connection conn = connection.connect()){
            InputStream inputStream = DatabaseInitializer.class.getResourceAsStream("sql/insert_schema.sql");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuilder script = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                script.append(line);
                script.append("\n");
            }
            Statement statement = conn.createStatement();
            statement.executeUpdate(script.toString());
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}