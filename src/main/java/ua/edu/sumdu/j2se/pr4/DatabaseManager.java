package ua.edu.sumdu.j2se.pr4;

import java.io.FileInputStream;
import java.sql.*;
import java.util.Properties;

    public class DatabaseManager {
    private String url;
    private String user;
    private String password;

    // Конструктор зчитує файл, шлях до якого ми передамо в Main
    public DatabaseManager(String configPath) {
        try (FileInputStream fis = new FileInputStream(configPath)) {
            Properties props = new Properties();
            props.load(fis);
            this.url = props.getProperty("db.url");
            this.user = props.getProperty("db.user");
            this.password = props.getProperty("db.password");
        } catch (Exception e) {
            System.err.println("Помилка конфігурації: " + e.getMessage());
        }
    }

    public void insertBook(Book b) throws ClassNotFoundException {
        String sql = "INSERT INTO books (type, title, author, publish_year, price, genre, quantity, extra_data) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        Class.forName("org.postgresql.Driver");
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, b.getClass().getSimpleName());
            pstmt.setString(2, b.getTitle());
            pstmt.setString(3, b.getAuthor());
            pstmt.setInt(4, b.getYear());
            pstmt.setDouble(5, b.getPrice());
            pstmt.setString(6, b.getGenre().toString());
            pstmt.setInt(7, b.getQuantity());

            // Специфічні дані для нащадків (вага, розмір тощо)
            String extra = "";
            if (b instanceof EBook) extra = "Size: " + ((EBook) b).getFileSize();
            else if (b instanceof AudioBook) extra = "Duration: " + ((AudioBook) b).getDuration();
            else if (b instanceof PaperBook) extra = "Weight: " + ((PaperBook) b).getWeight();

            pstmt.setString(8, extra);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Помилка SQL: " + e.getMessage());
        }
    }
}