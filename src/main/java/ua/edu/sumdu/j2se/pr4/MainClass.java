package ua.edu.sumdu.j2se.pr4;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainClass {
    private static final String FILE_NAME = "input.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Book> inventory = loadFromFile();

        while (true) {
            System.out.println("\n ГОЛОВНЕ МЕНЮ ");
            System.out.println("1. Створити новий об’єкт");
            System.out.println("2. Вивести всю колекцію");
            System.out.println("3. ПОШУК ОБ'ЄКТА");
            System.out.println("4. Завершити роботу");
            System.out.print("Вибір: ");

            String choice = scanner.nextLine();
            if (choice.equals("4")) {
                saveToFile(inventory);
                System.out.println("Дані збережено. Вихід...");
                break;
            }

            switch (choice) {
                case "1" -> showCreateMenu(scanner, inventory);
                case "2" -> {
                    System.out.println("\n ВМІСТ КОЛЕКЦІЇ ");
                    if (inventory.isEmpty()) System.out.println("Колекція порожня.");
                    for (Book b : inventory) System.out.println(b);
                }
                case "3" -> showSearchMenu(scanner, inventory);
                default -> System.out.println("Некоректний вибір.");
            }
        }
    }

    //  МЕТОДИ ПОШУКУ (Функціональна декомпозиція)

    private static List<Book> findByAuthor(List<Book> inventory, String author) {
        List<Book> result = new ArrayList<>();
        for (Book b : inventory) {
            if (b.getAuthor().toLowerCase().contains(author.toLowerCase())) {
                result.add(b);
            }
        }
        return result;
    }

    private static List<Book> findByPriceRange(List<Book> inventory, double min, double max) {
        List<Book> result = new ArrayList<>();
        for (Book b : inventory) {
            if (b.getPrice() >= min && b.getPrice() <= max) {
                result.add(b);
            }
        }
        return result;
    }

    private static List<Book> findByGenre(List<Book> inventory, Genre genre) {
        List<Book> result = new ArrayList<>();
        for (Book b : inventory) {
            if (b.getGenre() == genre) {
                result.add(b);
            }
        }
        return result;
    }

    //  ПІДМЕНЮ ПОШУКУ

    private static void showSearchMenu(Scanner sc, List<Book> inventory) {
        System.out.println("\n МЕНЮ ПОШУКУ");
        System.out.println("1. За автором");
        System.out.println("2. За діапазоном ціни");
        System.out.println("3. За жанром");
        System.out.println("0. Назад");
        System.out.print("Оберіть критерій: ");

        String searchChoice = sc.nextLine();
        List<Book> found = new ArrayList<>();

        try {
            switch (searchChoice) {
                case "1" -> {
                    System.out.print("Введіть автора (або частину імені): ");
                    found = findByAuthor(inventory, sc.nextLine());
                }
                case "2" -> {
                    System.out.print("Мінімальна ціна: "); double min = Double.parseDouble(sc.nextLine());
                    System.out.print("Максимальна ціна: "); double max = Double.parseDouble(sc.nextLine());
                    found = findByPriceRange(inventory, min, max);
                }
                case "3" -> {
                    System.out.println("Жанри: 1-FICTION, 2-FANTASY, 3-HISTORY, 4-CLASSIC");
                    String g = sc.nextLine();
                    Genre genre = (g.equals("2")) ? Genre.FANTASY : (g.equals("3")) ? Genre.HISTORY : (g.equals("4")) ? Genre.CLASSIC : Genre.FICTION;
                    found = findByGenre(inventory, genre);
                }
                case "0" -> { return; }
                default -> { System.out.println("Невірний критерій."); return; }
            }

            if (found.isEmpty()) {
                System.out.println("Нічого не знайдено за вашим запитом.");
            } else {
                System.out.println("\nЗнайдені об'єкти:");
                for (Book b : found) System.out.println(b);
            }
        } catch (Exception e) {
            System.out.println("Помилка при пошуку. Перевірте формат даних.");
        }
    }

    // СТВОРЕННЯ ОБ'ЄКТА Лаб 8-9

    private static void showCreateMenu(Scanner sc, List<Book> list) {
        System.out.println("\nТип: 1-Book, 2-EBook, 3-PaperBook, 4-AudioBook, 5-RareBook, 0-Назад");
        String type = sc.nextLine();
        if (type.equals("0")) return;

        try {
            System.out.print("Назва: "); String t = sc.nextLine();
            System.out.print("Автор: "); String a = sc.nextLine();
            System.out.print("Рік: "); int y = Integer.parseInt(sc.nextLine());
            System.out.print("Ціна: "); double p = Double.parseDouble(sc.nextLine());
            System.out.println("Жанр: 1-FICTION, 2-FANTASY, 3-HISTORY, 4-CLASSIC");
            String gc = sc.nextLine();
            Genre g = (gc.equals("2")) ? Genre.FANTASY : (gc.equals("3")) ? Genre.HISTORY : (gc.equals("4")) ? Genre.CLASSIC : Genre.FICTION;

            switch (type) {
                case "1" -> list.add(new Book(t, a, y, p, g));
                case "2" -> { System.out.print("Size(MB): "); list.add(new EBook(t, a, y, p, g, Double.parseDouble(sc.nextLine()))); }
                case "3" -> { System.out.print("Weight(g): "); list.add(new PaperBook(t, a, y, p, g, Double.parseDouble(sc.nextLine()))); }
                case "4" -> { System.out.print("Duration(min): "); list.add(new AudioBook(t, a, y, p, g, Integer.parseInt(sc.nextLine()))); }
                case "5" -> {
                    System.out.print("Weight(g): "); double w = Double.parseDouble(sc.nextLine());
                    System.out.print("Condition(1-10): "); int c = Integer.parseInt(sc.nextLine());
                    list.add(new RareBook(t, a, y, p, g, w, c));
                }
            }
            System.out.println("Об'єкт додано!");
        } catch (Exception e) { System.out.println("Помилка: " + e.getMessage()); }
    }

    // ФАЙЛОВІ ОПЕРАЦІЇ Лаб 9

    private static void saveToFile(List<Book> list) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Book b : list) {
                StringBuilder sb = new StringBuilder();
                sb.append(b.getClass().getSimpleName()).append(";")
                        .append(b.getTitle()).append(";")
                        .append(b.getAuthor()).append(";")
                        .append(b.getYear()).append(";")
                        .append(b.getPrice()).append(";")
                        .append(b.getGenre());
                if (b instanceof EBook) sb.append(";").append(((EBook) b).getFileSize());
                else if (b instanceof AudioBook) sb.append(";").append(((AudioBook) b).getDuration());
                else if (b instanceof RareBook) sb.append(";").append(((RareBook) b).getWeight()).append(";").append(((RareBook) b).getCondition());
                else if (b instanceof PaperBook) sb.append(";").append(((PaperBook) b).getWeight());
                writer.println(sb.toString());
            }
        } catch (IOException e) { System.out.println("Помилка запису."); }
    }

    private static List<Book> loadFromFile() {
        List<Book> list = new ArrayList<>();
        File file = new File(FILE_NAME);
        if (!file.exists()) return list;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(";");
                String type = p[0]; String title = p[1]; String author = p[2];
                int year = Integer.parseInt(p[3]); double price = Double.parseDouble(p[4]);
                Genre genre = Genre.valueOf(p[5]);
                switch (type) {
                    case "Book" -> list.add(new Book(title, author, year, price, genre));
                    case "EBook" -> list.add(new EBook(title, author, year, price, genre, Double.parseDouble(p[6])));
                    case "AudioBook" -> list.add(new AudioBook(title, author, year, price, genre, Integer.parseInt(p[6])));
                    case "PaperBook" -> list.add(new PaperBook(title, author, year, price, genre, Double.parseDouble(p[6])));
                    case "RareBook" -> list.add(new RareBook(title, author, year, price, genre, Double.parseDouble(p[6]), Integer.parseInt(p[7])));
                }
            }
        } catch (Exception e) { System.out.println("Помилка завантаження файлу."); }
        return list;
    }
}