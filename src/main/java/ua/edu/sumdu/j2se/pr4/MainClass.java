package ua.edu.sumdu.j2se.pr4;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainClass {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Book> inventory = new ArrayList<>();

        while (true) {
            System.out.println("\n ГОЛОВНЕ МЕНЮ ");
            System.out.println("1. Створити новий об’єкт");
            System.out.println("2. Вивести інформацію про всі об’єкти");
            System.out.println("3. Завершити роботу");
            System.out.print("Вибір: ");

            String mainChoice = scanner.nextLine();
            if (mainChoice.equals("3")) break;

            if (mainChoice.equals("1")) {
                showCreateMenu(scanner, inventory);
            } else if (mainChoice.equals("2")) {
                System.out.println("\n СПИСОК КНИГ");
                for (Book b : inventory) System.out.println(b);
            }
        }
    }

    private static void showCreateMenu(Scanner sc, List<Book> list) {
        System.out.println("\n Оберіть тип книги:");
        System.out.println("1. Базова Книга");
        System.out.println("2. Електронна книга");
        System.out.println("3. Паперова книга");
        System.out.println("4. Аудіокнига");
        System.out.println("5. Рідкісна паперова книга");
        System.out.println("0. Повернутися назад");
        System.out.print("Вибір: ");

        String type = sc.nextLine();
        if (type.equals("0")) return;

        try {
            System.out.print("Назва: "); String t = sc.nextLine();
            System.out.print("Автор: "); String a = sc.nextLine();
            System.out.print("Рік: "); int y = Integer.parseInt(sc.nextLine());
            System.out.print("Ціна: "); double p = Double.parseDouble(sc.nextLine());
            System.out.println("Оберіть жанр: 1-FICTION, 2-FANTASY, 3-HISTORY, 4-CLASSIC");
            String gChoice = sc.nextLine();
            Genre g = Genre.FICTION;
            if (gChoice.equals("2")) g = Genre.FANTASY;
            else if (gChoice.equals("3")) g = Genre.HISTORY;
            else if (gChoice.equals("4")) g = Genre.CLASSIC;

            switch (type) {
                case "1" -> list.add(new Book(t, a, y, p, g));
                case "2" -> {
                    System.out.print("Розмір (MB): ");
                    double s = Double.parseDouble(sc.nextLine());
                    list.add(new EBook(t, a, y, p, g, s));
                }
                case "3" -> {
                    System.out.print("Вага (г): ");
                    double w = Double.parseDouble(sc.nextLine());
                    list.add(new PaperBook(t, a, y, p, g, w));
                }
                case "4" -> {
                    System.out.print("Тривалість (хв): ");
                    int d = Integer.parseInt(sc.nextLine());
                    list.add(new AudioBook(t, a, y, p, g, d));
                }
                case "5" -> {
                    System.out.print("Вага (г): ");
                    double w = Double.parseDouble(sc.nextLine());
                    System.out.print("Стан (1-10): ");
                    int c = Integer.parseInt(sc.nextLine());
                    list.add(new RareBook(t, a, y, p, g, w, c));
                }
            }
            System.out.println("Об'єкт успішно додано до колекції!");
        } catch (Exception e) {
            System.out.println("Помилка при створенні: " + e.getMessage());
        }
    }
}