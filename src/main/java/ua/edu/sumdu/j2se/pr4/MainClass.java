package ua.edu.sumdu.j2se.pr4;

import java.util.Scanner;

public class MainClass {
    public static void main(String[] args) {
        // 1. Перевірка наявності аргументу (шлях до db.properties)
        if (args.length == 0) {
            System.out.println("Помилка: вкажіть шлях до файлу конфігурації БД (наприклад, db.properties) в аргументах!");
            return;
        }

        Scanner scanner = new Scanner(System.in);

        // 2. Ініціалізація Library та DatabaseManager
        Library library = new Library();
        DatabaseManager dbManager = new DatabaseManager(args[0]);

        while (true) {
            System.out.println("\nГОЛОВНЕ МЕНЮ (JDBC Mode)");
            System.out.println("1. Пошук об'єкта");
            System.out.println("2. Додати нову книгу (збереження в БД)");
            System.out.println("3. Вивести весь фонд бібліотеки");
            System.out.println("4. Завершити роботу");
            System.out.print("Вибір: ");

            String mainChoice = scanner.nextLine();

            if (mainChoice.equals("4")) {
                library.saveToFile();
                System.out.println("Дані збережено у файл. До побачення!");
                break;
            }

            switch (mainChoice) {
                case "1" -> showSearchMenu(scanner, library);
                case "2" -> showCreateMenu(scanner, library, dbManager); // Передаємо dbManager
                case "3" -> {
                    System.out.println("\n ФОНД БІБЛІОТЕКИ");
                    if (library.getBooks().isEmpty()) System.out.println("Бібліотека порожня.");
                    for (Book b : library.getBooks()) {
                        System.out.println(b + " | Кількість: " + b.getQuantity());
                    }
                }
                default -> System.out.println("Невірний вибір.");
            }
        }
    }

    private static void showSearchMenu(Scanner sc, Library lib) {
        System.out.println("\n МЕНЮ ПОШУКУ");
        System.out.println("1. За автором\n2. За роком\n3. За ціною\n0. Назад");
        String choice = sc.nextLine();
        java.util.List<Book> results = new java.util.ArrayList<>();

        try {
            switch (choice) {
                case "1" -> { System.out.print("Автор: "); results = lib.searchByAuthor(sc.nextLine()); }
                case "2" -> { System.out.print("Рік: "); results = lib.searchByYear(Integer.parseInt(sc.nextLine())); }
                case "3" -> { System.out.print("Макс. ціна: "); results = lib.searchByPrice(Double.parseDouble(sc.nextLine())); }
                case "0" -> { return; }
            }
            if (results.isEmpty()) System.out.println("Нічого не знайдено.");
            else for (Book b : results) System.out.println(b + " (К-сть: " + b.getQuantity() + ")");
        } catch (Exception e) { System.out.println("Помилка введення."); }
    }

    // Додали DatabaseManager у параметри
    private static void showCreateMenu(Scanner sc, Library lib, DatabaseManager db) {
        System.out.println("\nОберіть тип: 1.Book, 2.EBook, 3.PaperBook, 4.AudioBook, 5.RareBook, 0.Назад");
        String typeChoice = sc.nextLine();
        if (typeChoice.equals("0")) return;

        try {
            System.out.print("Назва: "); String t = sc.nextLine();
            System.out.print("Автор: "); String a = sc.nextLine();
            System.out.print("Рік: "); int y = Integer.parseInt(sc.nextLine());
            System.out.print("Ціна: "); double p = Double.parseDouble(sc.nextLine());
            System.out.print("Кількість примірників: "); int q = Integer.parseInt(sc.nextLine());
            System.out.println("Жанр: 1-FICTION, 2-FANTASY, 3-HISTORY, 4-CLASSIC");
            String gChoice = sc.nextLine();
            Genre g = (gChoice.equals("2")) ? Genre.FANTASY : (gChoice.equals("3")) ? Genre.HISTORY : (gChoice.equals("4")) ? Genre.CLASSIC : Genre.FICTION;

            Book newBook = null;
            switch (typeChoice) {
                case "1" -> newBook = new Book(t, a, y, p, g);
                case "2" -> { System.out.print("Розмір (MB): "); newBook = new EBook(t, a, y, p, g, Double.parseDouble(sc.nextLine())); }
                case "3" -> { System.out.print("Вага (г): "); newBook = new PaperBook(t, a, y, p, g, Double.parseDouble(sc.nextLine())); }
                case "4" -> { System.out.print("Тривалість (хв): "); newBook = new AudioBook(t, a, y, p, g, Integer.parseInt(sc.nextLine())); }
                case "5" -> {
                    System.out.print("Вага (г): "); double w = Double.parseDouble(sc.nextLine());
                    System.out.print("Стан (1-10): "); int c = Integer.parseInt(sc.nextLine());
                    newBook = new RareBook(t, a, y, p, g, w, c);
                }
            }
            if (newBook != null) {
                lib.addNewBook(newBook, q);
                // 3. ЗБЕРЕЖЕННЯ В БАЗУ ДАНИХ (JDBC)
                db.insertBook(newBook);
                System.out.println("Об'єкт додано локально та збережено в БД!");
            }
        } catch (Exception e) { System.out.println("Помилка: " + e.getMessage()); }
    }
}