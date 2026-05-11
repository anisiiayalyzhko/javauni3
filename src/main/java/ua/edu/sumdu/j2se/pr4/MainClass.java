package ua.edu.sumdu.j2se.pr4;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainClass {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Library library = new Library();

        while (true) {
            System.out.println("\nГОЛОВНЕ МЕНЮ");
            System.out.println("1. Пошук об'єкта");
            System.out.println("2. Додати новий примірник");
            System.out.println("3. Вивести весь фонд бібліотеки");
            System.out.println("4. Сортування за критеріями (Comparator)");
            System.out.println("5. Завершити роботу");
            System.out.print("Вибір: ");

            String mainChoice = scanner.nextLine();

            if (mainChoice.equals("5")) {
                library.saveToFile();
                System.out.println("Дані збережено. До побачення!");
                break;
            }

            switch (mainChoice) {
                case "1" -> showSearchMenu(scanner, library);
                case "2" -> showCreateMenu(scanner, library);
                case "3" -> {
                    System.out.println("\nФОНД БІБЛІОТЕКИ");
                    printBookList(library.getBooks());
                }
                case "4" -> showSortMenu(scanner, library);
                default -> System.out.println("Невірний вибір.");
            }
        }
    }

    private static void showSortMenu(Scanner sc, Library lib) {
        List<Book> sortList = new ArrayList<>(lib.getBooks());
        if (sortList.isEmpty()) {
            System.out.println("Бібліотека порожня, нічого сортувати.");
            return;
        }

        System.out.println("\n ОБЕРІТЬ КРИТЕРІЙ СОРТУВАННЯ");
        System.out.println("1. За назвою (А-Я)");
        System.out.println("2. За автором (А-Я)");
        System.out.println("3. За ціною (0-1000)");
        System.out.println("0. Повернутися назад");
        System.out.print("Вибір: ");

        String choice = sc.nextLine();
        Comparator<Book> comparator = null;

        switch (choice) {
            case "1" -> {
                // Анонімний внутрішній клас для сортування за назвою
                comparator = new Comparator<Book>() {
                    @Override
                    public int compare(Book b1, Book b2) {
                        return b1.getTitle().compareToIgnoreCase(b2.getTitle());
                    }
                };
            }
            case "2" -> {
                // Анонімний внутрішній клас для сортування за автором
                comparator = new Comparator<Book>() {
                    @Override
                    public int compare(Book b1, Book b2) {
                        return b1.getAuthor().compareToIgnoreCase(b2.getAuthor());
                    }
                };
            }
            case "3" -> {
                // Анонімний внутрішній клас для сортування за ціною
                comparator = new Comparator<Book>() {
                    @Override
                    public int compare(Book b1, Book b2) {
                        return Double.compare(b1.getPrice(), b2.getPrice());
                    }
                };
            }
            case "0" -> { return; }
            default -> {
                System.out.println("Невірний вибір.");
                return;
            }
        }

        if (comparator != null) {
            Collections.sort(sortList, comparator);
            System.out.println("\nРЕЗУЛЬТАТ СОРТУВАННЯ");
            printBookList(sortList);
        }
    }

    private static void printBookList(List<Book> list) {
        if (list.isEmpty()) {
            System.out.println("Список порожній.");
        } else {
            for (Book b : list) {
                System.out.println(b + " | Кількість: " + b.getQuantity());
            }
        }
    }

    private static void showSearchMenu(Scanner sc, Library lib) { /* ... */ }
    private static void showCreateMenu(Scanner sc, Library lib) { /* ... */ }
}