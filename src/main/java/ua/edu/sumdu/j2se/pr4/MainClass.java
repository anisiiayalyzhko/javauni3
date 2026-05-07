package ua.edu.sumdu.j2se.pr4;

import java.util.Scanner;

public class MainClass {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // Створюємо об'єкт нашої бібліотеки
        Library myLibrary = new Library("Моя Бібліотека");

        while (true) {
            System.out.println("\nМЕНЮ");
            System.out.println("1. Додати нову книгу");
            System.out.println("2. Показати всі книги");
            System.out.println("3. Вихід");
            System.out.print("Оберіть дію: ");

            String choice = scanner.nextLine();

            if (choice.equals("1")) {
                try {
                    // Збираємо дані від користувача
                    System.out.print("Назва: ");
                    String title = scanner.nextLine();
                    System.out.print("Автор: ");
                    String author = scanner.nextLine();
                    System.out.print("Рік: ");
                    int year = Integer.parseInt(scanner.nextLine());
                    System.out.print("Ціна: ");
                    double price = Double.parseDouble(scanner.nextLine());

                    // Вибір жанру через меню
                    System.out.println("Оберіть жанр: 1-CLASSIC, 2-FANTASY, 3-HISTORY");
                    String gChoice = scanner.nextLine();
                    Genre genre = Genre.CLASSIC; // За замовчуванням
                    if (gChoice.equals("2")) genre = Genre.FANTASY;
                    if (gChoice.equals("3")) genre = Genre.HISTORY;

                    // Створюємо книгу і кладемо її в бібліотеку
                    Book b = new Book(title, author, year, price, genre);
                    myLibrary.addBook(b);

                    System.out.println("Книгу додано!");
                    // Перевірка статичного лічильника
                    System.out.println("Загалом книг у базі: " + Book.getBookCount());

                } catch (Exception e) {
                    System.out.println("Помилка при введенні: " + e.getMessage());
                }

            } else if (choice.equals("2")) {
                // Викликаємо метод нашої бібліотеки
                myLibrary.showLibraryInfo();
            } else if (choice.equals("3")) {
                System.out.println("Кінець роботи.");
                break;
            } else {
                System.out.println("Немає такого пункту.");
            }
        }
    }
}