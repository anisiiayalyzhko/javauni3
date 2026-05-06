package ua.edu.sumdu.j2se.pr4;

import java.util.Objects;

public class Book {
    // Поля для зберігання даних про книгу
    private String title;
    private String author;
    private int year;
    private double price;
    private Genre genre; // Нове поле для жанру (enum)

    // Статична змінна - одна на всі книги, щоб рахувати їхню кількість
    private static int bookCount = 0;

    // Головний конструктор для створення нової книги
    public Book(String title, String author, int year, double price, Genre genre) {
        setTitle(title);
        setAuthor(author);
        setYear(year);
        setPrice(price);
        setGenre(genre);
        bookCount++; // Додаємо +1 до лічильника при кожному створенні
    }

    // Конструктор копіювання - робить дублікат існуючої книги
    public Book(Book other) {
        this(other.title, other.author, other.year, other.price, other.genre);
    }

    // Статичний метод, щоб дізнатися загальну кількість книг
    public static int getBookCount() {
        return bookCount;
    }

    // Нижче йдуть звичайні методи для отримання та зміни даних (гетери/сетери)

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
            throw new IllegalArgumentException("Ціна не буває від'ємною");
        }
        this.price = price;
    }

    public Genre getGenre() { return genre; }
    public void setGenre(Genre genre) {
        if (genre == null) {
            throw new IllegalArgumentException("Треба обрати жанр");
        }
        this.genre = genre;
    }

    // Методи для виводу та порівняння об'єктів
    @Override
    public String toString() {
        return "Книга: " + title + ", Автор: " + author + ", Рік: " + year + ", Ціна: " + price + ", Жанр: " + genre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return year == book.year && Double.compare(book.price, price) == 0 &&
                Objects.equals(title, book.title) && Objects.equals(author, book.author) && genre == book.genre;
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, author, year, price, genre);
    }
}