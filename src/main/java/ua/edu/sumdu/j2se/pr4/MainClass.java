package ua.edu.sumdu.j2se.pr4;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainClass {
    private static final String FILE_NAME = "input.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // Читаємо дані з файлу при завантаженні
        List<Book> inventory = loadFromFile();

        while (true) {
            System.out.println("\n ГОЛОВНЕ МЕНЮ ");
            System.out.println("1. Створити новий об’єкт");
            System.out.println("2. Вивести інформацію про всі об’єкти");
            System.out.println("3. Завершити роботу (Зберегти зміни)");
            System.out.print("Вибір: ");

            String mainChoice = scanner.nextLine();
            if (mainChoice.equals("3")) {
                saveToFile(inventory); // Зберігаємо перед виходом
                System.out.println("Дані збережено. До побачення!");
                break;
            }

            if (mainChoice.equals("1")) {
                showCreateMenu(scanner, inventory);
            } else if (mainChoice.equals("2")) {
                System.out.println("\n СПИСОК КНИГ З ФАЙЛУ");
                if (inventory.isEmpty()) System.out.println("Список порожній.");
                for (Book b : inventory) System.out.println(b);
            }
        }
    }

    // МЕТОД ЗБЕРЕЖЕННЯ У ФАЙЛ
    private static void saveToFile(List<Book> list) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Book b : list) {
                String type = b.getClass().getSimpleName();
                // Базові поля: Тип;Назва;Автор;Рік;Ціна;Жанр
                StringBuilder sb = new StringBuilder();
                sb.append(type).append(";")
                        .append(b.getTitle()).append(";")
                        .append(b.getAuthor()).append(";")
                        .append(b.getYear()).append(";")
                        .append(b.getPrice()).append(";")
                        .append(b.getGenre());

                // Специфічні поля для нащадків
                if (b instanceof EBook) sb.append(";").append(((EBook) b).getFileSize());
                else if (b instanceof AudioBook) sb.append(";").append(((AudioBook) b).getDuration());
                else if (b instanceof RareBook) {
                    sb.append(";").append(((RareBook) b).getWeight());
                    sb.append(";").append(((RareBook) b).getCondition());
                } else if (b instanceof PaperBook) sb.append(";").append(((PaperBook) b).getWeight());

                writer.println(sb.toString());
            }
        } catch (IOException e) {
            System.out.println("Помилка запису у файл: " + e.getMessage());
        }
    }

    // МЕТОД ЧИТАННЯ З ФАЙЛУ
    private static List<Book> loadFromFile() {
        List<Book> list = new ArrayList<>();
        File file = new File(FILE_NAME);
        if (!file.exists()) return list;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length < 6) continue;

                String type = parts[0];
                String title = parts[1];
                String author = parts[2];
                int year = Integer.parseInt(parts[3]);
                double price = Double.parseDouble(parts[4]);
                Genre genre = Genre.valueOf(parts[5]);

                switch (type) {
                    case "Book" -> list.add(new Book(title, author, year, price, genre));
                    case "EBook" -> list.add(new EBook(title, author, year, price, genre, Double.parseDouble(parts[6])));
                    case "AudioBook" -> list.add(new AudioBook(title, author, year, price, genre, Integer.parseInt(parts[6])));
                    case "PaperBook" -> list.add(new PaperBook(title, author, year, price, genre, Double.parseDouble(parts[6])));
                    case "RareBook" -> list.add(new RareBook(title, author, year, price, genre, Double.parseDouble(parts[6]), Integer.parseInt(parts[7])));
                }
            }
        } catch (Exception e) {
            System.out.println("Файл знайдено, але сталася помилка зчитування. Починаємо з порожнього списку.");
        }
        return list;
    }

    private static void showCreateMenu(Scanner sc, List<Book> list) {
        System.out.println("\nОберіть тип книги: 1. Book, 2. EBook, 3. PaperBook, 4. AudioBook, 5. RareBook, 0. Назад");
        String typeChoice = sc.nextLine();
        if (typeChoice.equals("0")) return;

        try {
            System.out.print("Назва: "); String t = sc.nextLine();
            System.out.print("Автор: "); String a = sc.nextLine();
            System.out.print("Рік: "); int y = Integer.parseInt(sc.nextLine());
            System.out.print("Ціна: "); double p = Double.parseDouble(sc.nextLine());
            System.out.println("Жанр: 1-FICTION, 2-FANTASY, 3-HISTORY, 4-CLASSIC");
            String gChoice = sc.nextLine();
            Genre g = (gChoice.equals("2")) ? Genre.FANTASY : (gChoice.equals("3")) ? Genre.HISTORY : (gChoice.equals("4")) ? Genre.CLASSIC : Genre.FICTION;

            switch (typeChoice) {
                case "1" -> list.add(new Book(t, a, y, p, g));
                case "2" -> { System.out.print("Розмір (MB): "); list.add(new EBook(t, a, y, p, g, Double.parseDouble(sc.nextLine()))); }
                case "3" -> { System.out.print("Вага (г): "); list.add(new PaperBook(t, a, y, p, g, Double.parseDouble(sc.nextLine()))); }
                case "4" -> { System.out.print("Тривалість (хв): "); list.add(new AudioBook(t, a, y, p, g, Integer.parseInt(sc.nextLine()))); }
                case "5" -> {
                    System.out.print("Вага (г): "); double w = Double.parseDouble(sc.nextLine());
                    System.out.print("Стан (1-10): "); int c = Integer.parseInt(sc.nextLine());
                    list.add(new RareBook(t, a, y, p, g, w, c));
                }
            }
            System.out.println("Додано локально. Не забудьте вийти через '3' для збереження у файл!");
        } catch (Exception e) { System.out.println("Помилка: " + e.getMessage()); }
    }
}