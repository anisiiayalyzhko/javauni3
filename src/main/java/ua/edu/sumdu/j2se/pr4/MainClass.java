package ua.edu.sumdu.j2se.pr4;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class MainClass {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Library library = new Library();

        while (true) {
            System.out.println("\nГОЛОВНЕ МЕНЮ ");
            System.out.println("1. Пошук об'єкта");
            System.out.println("2. Додати новий примірник");
            System.out.println("3. Вивести весь фонд бібліотеки");
            System.out.println("4. Вивести відсортовану інформацію (за назвою)");
            System.out.println("5. Завершити роботу");
            System.out.print("Вибір: ");

            String mainChoice = scanner.nextLine();

            if (mainChoice.equals("5")) {
                library.saveToFile();
                System.out.println("Дані збережено. Роботу завершено.");
                break;
            }

            switch (mainChoice) {
                case "1" -> showSearchMenu(scanner, library);
                case "2" -> showCreateMenu(scanner, library);
                case "3" -> {
                    System.out.println("\n ФОНД БІБЛІОТЕКИ");
                    if (library.getBooks().isEmpty()) System.out.println("Бібліотека порожня.");
                    for (Book b : library.getBooks()) {
                        System.out.println(b + " | Кількість: " + b.getQuantity());
                    }
                }
                case "4" -> {
                    System.out.println("\nВІДСОРТОВАНИЙ ФОНД (Comparable)");
                    List<Book> sortedBooks = new ArrayList<>(library.getBooks());
                    if (sortedBooks.isEmpty()) {
                        System.out.println("Бібліотека порожня, немає об'єктів для сортування.");
                    } else {
                        // Використання інтерфейсу Comparable через стандартне сортування
                        Collections.sort(sortedBooks);
                        for (Book b : sortedBooks) {
                            System.out.println(b + " | Кількість: " + b.getQuantity());
                        }
                    }
                }
                default -> System.out.println("Невірний вибір. Спробуйте ще раз.");
            }
        }
    }

    private static void showSearchMenu(Scanner sc, Library lib) {
        System.out.println("\n МЕНЮ ПОШУКУ ");
        System.out.println("1. За автором\n2. За роком\n3. За ціною\n0. Назад");
        String choice = sc.nextLine();
        List<Book> results = new ArrayList<>();

        try {
            switch (choice) {
                case "1" -> { System.out.print("Введіть автора: "); results = lib.searchByAuthor(sc.nextLine()); }
                case "2" -> { System.out.print("Введіть рік: "); results = lib.searchByYear(Integer.parseInt(sc.nextLine())); }
                case "3" -> { System.out.print("Введіть максимальну ціну: "); results = lib.searchByPrice(Double.parseDouble(sc.nextLine())); }
                case "0" -> { return; }
            }
            if (results.isEmpty()) System.out.println("За вашим запитом нічого не знайдено.");
            else for (Book b : results) System.out.println(b + " (К-сть: " + b.getQuantity() + ")");
        } catch (Exception e) { System.out.println("Помилка введення даних."); }
    }

    private static void showCreateMenu(Scanner sc, Library lib) {
        // Зверніть увагу: створення об'єкта Book видалено, оскільки він абстрактний
        System.out.println("\nОберіть тип: 1.EBook, 2.PaperBook, 3.AudioBook, 4.RareBook, 0.Назад");
        String typeChoice = sc.nextLine();
        if (typeChoice.equals("0")) return;

        try {
            System.out.print("Назва: "); String t = sc.nextLine();
            System.out.print("Автор: "); String a = sc.nextLine();
            System.out.print("Рік: "); int y = Integer.parseInt(sc.nextLine());
            System.out.print("Ціна: "); double p = Double.parseDouble(sc.nextLine());
            System.out.print("Кількість примірників: "); int q = Integer.parseInt(sc.nextLine());

            System.out.println("Оберіть жанр: 1-FICTION, 2-FANTASY, 3-HISTORY, 4-CLASSIC");
            String gChoice = sc.nextLine();
            Genre g = switch (gChoice) {
                case "2" -> Genre.FANTASY;
                case "3" -> Genre.HISTORY;
                case "4" -> Genre.CLASSIC;
                default -> Genre.FICTION;
            };

            Book newBook = null;
            switch (typeChoice) {
                case "1" -> {
                    System.out.print("Розмір файлу (MB): ");
                    newBook = new EBook(t, a, y, p, g, Double.parseDouble(sc.nextLine()));
                }
                case "2" -> {
                    System.out.print("Вага книги (г): ");
                    newBook = new PaperBook(t, a, y, p, g, Double.parseDouble(sc.nextLine()));
                }
                case "3" -> {
                    System.out.print("Тривалість запису (хв): ");
                    newBook = new AudioBook(t, a, y, p, g, Integer.parseInt(sc.nextLine()));
                }
                case "4" -> {
                    System.out.print("Вага книги (г): "); double w = Double.parseDouble(sc.nextLine());
                    System.out.print("Стан раритету (1-10): "); int c = Integer.parseInt(sc.nextLine());
                    newBook = new RareBook(t, a, y, p, g, w, c);
                }
                default -> System.out.println("Невірний тип об'єкта.");
            }

            if (newBook != null) {
                lib.addNewBook(newBook, q);
                System.out.println("Об'єкт успішно додано до фонду бібліотеки.");
            }
        } catch (Exception e) {
            System.out.println("Помилка при створенні об'єкта: " + e.getMessage());
        }
    }
}