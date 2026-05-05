package ua.edu.sumdu.j2se.pr4;

import java.util.Scanner;

public class MainClass {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // Створюємо масив саме на 5 об'єктів (Варіант 2)
        Book[] books = new Book[5];

        System.out.println("Введіть дані для 5 книг:");

        for (int i = 0; i < books.length; i++) {
            System.out.println("\nКнига №" + (i + 1));

            System.out.print("Назва: ");
            String title = scanner.nextLine();

            System.out.print("Автор: ");
            String author = scanner.nextLine();

            System.out.print("Рік видання: ");
            // Перетворюємо рядок у число, щоб уникнути багів зі Scanner
            int year = Integer.parseInt(scanner.nextLine());

            // Створюємо нову книгу і кладемо її в масив
            books[i] = new Book(title, author, year);
        }

        System.out.println("\nВаша бібліотека");
        for (Book b : books) {
            // Тут спрацює метод toString()
            System.out.println(b);
        }

        scanner.close();
    }
}