package ua.edu.sumdu.j2se.pr4;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Library {
    private List<Book> books;
    private static final String FILE_NAME = "input.txt";

    public Library() {
        this.books = loadFromFile();
    }

    /**
     * Пошук об'єкта за UUID (Завдання 1, ЛР16)
     */
    public Book findByUuid(String uuidStr) {
        if (uuidStr == null || uuidStr.trim().isEmpty()) return null;
        try {
            // Перетворюємо рядок у об'єкт UUID
            UUID searchId = UUID.fromString(uuidStr.trim());
            for (Book b : books) {
                if (b.getUuid().equals(searchId)) {
                    return b;
                }
            }
        } catch (IllegalArgumentException e) {
            // Обробка некоректного формату без падіння програми
            System.out.println("Помилка: Некоректний формат UUID.");
        }
        return null;
    }

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

    /**
     * Оновлено: додано запис UUID у файл (ЛР16)
     */
    public void saveToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Book b : books) {
                StringBuilder sb = new StringBuilder();
                sb.append(b.getClass().getSimpleName()).append(";")
                        .append(b.getUuid().toString()).append(";") // Записуємо UUID
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

    /**
     * Оновлено: додано зчитування UUID з файлу (ЛР16)
     */
    private List<Book> loadFromFile() {
        List<Book> list = new ArrayList<>();
        File file = new File(FILE_NAME);
        if (!file.exists()) return list;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] p = line.split(";");
                if (p.length < 8) continue; // Тепер мінімум 8 полів (додався UUID)

                String type = p[0];
                String title = p[2];
                String author = p[3];
                int year = Integer.parseInt(p[4]);
                double price = Double.parseDouble(p[5]);
                Genre genre = Genre.valueOf(p[6]);
                int qty = Integer.parseInt(p[7]);

                Book b = null;
                switch (type) {
                    case "EBook" -> b = new EBook(title, author, year, price, genre, Double.parseDouble(p[8]));
                    case "AudioBook" -> b = new AudioBook(title, author, year, price, genre, Integer.parseInt(p[8]));
                    case "PaperBook" -> b = new PaperBook(title, author, year, price, genre, Double.parseDouble(p[8]));
                    case "RareBook" -> b = new RareBook(title, author, year, price, genre, Double.parseDouble(p[8]), Integer.parseInt(p[9]));
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