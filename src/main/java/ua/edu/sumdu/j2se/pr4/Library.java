package ua.edu.sumdu.j2se.pr4;

import java.util.ArrayList;
import java.util.List;

// Клас бібліотеки, який збирає в собі книги (агрегація)
public class Library {
    private String name; // Назва бібліотеки
    private List<Book> books; // Наш список книг

    public Library(String name) {
        this.name = name;
        this.books = new ArrayList<>();
    }

    // Додаємо книгу до нашого списку
    public void addBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Книга порожня!");
        }
        books.add(book);
    }

    // Метод для показу всього, що є в бібліотеці
    public void showLibraryInfo() {
        System.out.println("Вітаємо в: " + name);
        System.out.println("У нас зараз книг: " + books.size());
        for (Book b : books) {
            System.out.println(b);
        }
    }
}