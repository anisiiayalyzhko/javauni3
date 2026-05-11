package ua.edu.sumdu.j2se.pr4;

import java.io.Serializable;
import java.util.Objects;

/**
 * Базовий абстрактний клас Книга.
 * Реалізує Comparable для сортування за назвою.
 */
public abstract class Book implements Serializable, Comparable<Book> {
    private String title;
    private String author;
    private int year;
    private double price;
    private Genre genre;
    private int quantity = 1;

    public Book(String title, String author, int year, double price, Genre genre) {
        setTitle(title);
        setAuthor(author);
        setYear(year);
        setPrice(price);
        setGenre(genre);
    }

    // РЕАЛІЗАЦІЯ COMPARABLE
    // Сортування за назвою книги (алфавітний порядок)
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

    @Override
    public String toString() {
        return String.format("[%s] '%s', Автор: %s, Рік: %d, Ціна: %.2f, Жанр: %s",
                getClass().getSimpleName(), title, author, year, price, genre);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return year == book.year && Double.compare(book.price, price) == 0 &&
                Objects.equals(title, book.title) && Objects.equals(author, book.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, author, year, price);
    }
}