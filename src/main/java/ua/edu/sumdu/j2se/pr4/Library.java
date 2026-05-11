package ua.edu.sumdu.j2se.pr4;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Library {
    private List<Book> books;
    private static final String FILE_NAME = "input.txt";

    public Library() {
        this.books = loadFromFile();
    }

    /**
     * Додавання нової книги до фонду.
     * Якщо книга з такими ж параметрами вже існує, оновлюється її кількість.
     */
    public void addNewBook(Book bk, int quantity) {
        for (Book existingBook : books) {
            if (existingBook.equals(bk)) {
                existingBook.setQuantity(existingBook.getQuantity() + quantity);
                System.out.println("Інформацію про кількість примірників оновлено.");
                return;
            }
        }
        bk.setQuantity(quantity);
        books.add(bk);
        System.out.println("Новий об'єкт успішно додано до фонду.");
    }

    public List<Book> getBooks() {
        return books;
    }

    // МЕТОДИ ПОШУКУ
    public List<Book> searchByAuthor(String author) {
        List<Book> found = new ArrayList<>();
        for (Book b : books) {
            if (b.getAuthor().equalsIgnoreCase(author.trim())) found.add(b);
        }
        return found;
    }

    public List<Book> searchByYear(int year) {
        List<Book> found = new ArrayList<>();
        for (Book b : books) {
            if (b.getYear() == year) found.add(b);
        }
        return found;
    }

    public List<Book> searchByPrice(double maxPrice) {
        List<Book> found = new ArrayList<>();
        for (Book b : books) {
            if (b.getPrice() <= maxPrice) found.add(b);
        }
        return found;
    }

    // МЕТОДИ РОБОТИ З ФАЙЛАМИ
    public void saveToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Book b : books) {
                StringBuilder sb = new StringBuilder();
                sb.append(b.getClass().getSimpleName()).append(";")
                        .append(b.getTitle()).append(";")
                        .append(b.getAuthor()).append(";")
                        .append(b.getYear()).append(";")
                        .append(b.getPrice()).append(";")
                        .append(b.getGenre()).append(";")
                        .append(b.getQuantity());

                if (b instanceof EBook) sb.append(";").append(((EBook) b).getFileSize());
                else if (b instanceof AudioBook) sb.append(";").append(((AudioBook) b).getDuration());
                else if (b instanceof RareBook) {
                    sb.append(";").append(((RareBook) b).getWeight());
                    sb.append(";").append(((RareBook) b).getCondition());
                } else if (b instanceof PaperBook) sb.append(";").append(((PaperBook) b).getWeight());

                writer.println(sb.toString());
            }
        } catch (IOException e) {
            System.out.println("Помилка при збереженні даних: " + e.getMessage());
        }
    }

    private List<Book> loadFromFile() {
        List<Book> list = new ArrayList<>();
        File file = new File(FILE_NAME);
        if (!file.exists()) return list;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] p = line.split(";");
                if (p.length < 7) continue;

                String type = p[0];
                String title = p[1];
                String author = p[2];
                int year = Integer.parseInt(p[3]);
                double price = Double.parseDouble(p[4]);
                Genre genre = Genre.valueOf(p[5]);
                int qty = Integer.parseInt(p[6]);

                Book b = null;
                // Зверніть увагу: створення об'єкта "Book" видалено, бо він став абстрактним
                switch (type) {
                    case "EBook" -> b = new EBook(title, author, year, price, genre, Double.parseDouble(p[7]));
                    case "AudioBook" -> b = new AudioBook(title, author, year, price, genre, Integer.parseInt(p[7]));
                    case "PaperBook" -> b = new PaperBook(title, author, year, price, genre, Double.parseDouble(p[7]));
                    case "RareBook" -> b = new RareBook(title, author, year, price, genre, Double.parseDouble(p[7]), Integer.parseInt(p[8]));
                }

                if (b != null) {
                    b.setQuantity(qty);
                    list.add(b);
                }
            }
        } catch (Exception e) {
            System.out.println("Помилка при завантаженні бази даних: " + e.getMessage());
        }
        return list;
    }
}