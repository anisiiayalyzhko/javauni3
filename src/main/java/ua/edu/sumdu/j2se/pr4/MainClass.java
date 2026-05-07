package ua.edu.sumdu.j2se.pr4;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainClass {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Поліморфна колекція: список типу Book може містити і EBook, і PaperBook
        List<Book> inventory = new ArrayList<>();

        while (true) {
            System.out.println("\nУПРАВЛІННЯ КНИГАМИ");
            System.out.println("1. Додати електронну книгу (E-Book)");
            System.out.println("2. Додати паперову книгу (Paper Book)");
            System.out.println("3. Вивести весь список");
            System.out.println("4. Вихід");
            System.out.print("Обери пункт: ");

            String choice = scanner.nextLine();
            if (choice.equals("4")) break;

            try {
                if (choice.equals("1") || choice.equals("2")) {
                    // Спільні дані для обох типів
                    System.out.print("Назва: "); String title = scanner.nextLine();
                    System.out.print("Автор: "); String author = scanner.nextLine();
                    System.out.print("Рік: "); int year = Integer.parseInt(scanner.nextLine());
                    System.out.print("Ціна: "); double price = Double.parseDouble(scanner.nextLine());

                    if (choice.equals("1")) {
                        System.out.print("Розмір файлу (MB): ");
                        double size = Double.parseDouble(scanner.nextLine());
                        // Додаємо Електронну книгу
                        inventory.add(new EBook(title, author, year, price, Genre.FICTION, size));
                    } else {
                        System.out.print("Вага книги (г): ");
                        double weight = Double.parseDouble(scanner.nextLine());
                        // Додаємо Паперову книгу
                        inventory.add(new PaperBook(title, author, year, price, Genre.CLASSIC, weight));
                    }
                    System.out.println("Книгу успішно додано!");

                } else if (choice.equals("3")) {
                    System.out.println("\nВМІСТ БІБЛІОТЕКИ");
                    if (inventory.isEmpty()) {
                        System.out.println("Список порожній.");
                    }
                    // ДЕМОНСТРАЦІЯ ПОЛІМОРФІЗМУ
                    for (Book b : inventory) {
                        // Хоча b має тип Book, Java викличе toString() нащадка (EBook або PaperBook)
                        System.out.println(b);
                    }
                }
            } catch (Exception e) {
                System.out.println("Помилка: введіть коректні дані! (" + e.getMessage() + ")");
            }
        }
    }
}