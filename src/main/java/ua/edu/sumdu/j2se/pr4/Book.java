package ua.edu.sumdu.j2se.pr4;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * Базовий абстрактний клас Книга.
 * Реалізує Comparable (ЛР13), Identifiable (ЛР16).
 */
public abstract class Book implements Serializable, Comparable<Book>, Identifiable {
    private final UUID uuid; // Унікальний ідентифікатор (ЛР16)
    private String title;
    private String author;
    private int year;
    private double price;
    private Genre genre;
    private int quantity = 1;

    public Book(String title, String author, int year, double price, Genre genre) {
        // Автоматична генерація UUID при створенні об'єкта
        this.uuid = UUID.randomUUID();

        setTitle(title);
        setAuthor(author);
        setYear(year);
        setPrice(price);
        setGenre(genre);
    }

    // Реалізація інтерфейсу Identifiable
    @Override
    public UUID getUuid() {
        return uuid;
    }

    // Реалізація Comparable
    @Override
    public int compareTo(Book other) {
        if (other == null) return 1;
        return this.title.compareToIgnoreCase(other.getTitle());
    }

    // ГЕТТЕРИ ТА СЕТТЕРИ
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getTitle() { return title; }
    public void setTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Назва не може бути порожньою");
        }
        this.title = title;
    }

    public String getAuthor() { return author; }
    public void setAuthor(String author) {
        if (author == null || author.trim().isEmpty()) {
            throw new IllegalArgumentException("Автор не може бути порожнім");
        }
        this.author = author;
    }

    public int getYear() { return year; }
    public void setYear(int year) {
        if (year < 1450 || year > 2026) {
            throw new IllegalArgumentException("Некоректний рік");
        }
        this.year = year;
    }

    public double getPrice() { return price; }
    public void setPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Ціна не може бути від'ємною");
        }
        this.price = price;
    }

    public Genre getGenre() { return genre; }
    public void setGenre(Genre genre) { this.genre = genre; }

    /**
     * Оновлений toString для виводу короткого UUID (перші 8 символів)
     */
    @Override
    public String toString() {
        return String.format("[%s] ID:%s... '%s', Автор: %s, Ціна: %.2f",
                getClass().getSimpleName(),
                uuid.toString().substring(0, 8),
                title, author, price);
    }

    /**
     * Спеціальний метод для виводу ПОВНОЇ інформації в GUI
     */
    public String toFullString() {
        return String.format("UUID: %s\nТип: %s\nНазва: %s\nАвтор: %s\nРік: %d\nЦіна: %.2f\nЖанр: %s\nКількість: %d",
                uuid, getClass().getSimpleName(), title, author, year, price, genre, quantity);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        // Тепер ми можемо порівнювати об'єкти суворо за UUID
        return Objects.equals(uuid, book.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }
}