package ua.edu.sumdu.j2se.pr4;

// Клас для електронних книг, наслідується від Book
public class EBook extends Book {
    private double fileSize; // Розмір у Мегабайтах

    public EBook(String title, String author, int year, double price, Genre genre, double fileSize) {
        // super викликає конструктор батьківського класу Book
        super(title, author, year, price, genre);
        setFileSize(fileSize);
    }

    public double getFileSize() { return fileSize; }

    public void setFileSize(double fileSize) {
        if (fileSize <= 0) {
            throw new IllegalArgumentException("Розмір файлу має бути більшим за 0");
        }
        this.fileSize = fileSize;
    }

    // Перевизначаємо метод toString, щоб додати інфо про файл
    @Override
    public String toString() {
        return super.toString() + " (Електронна книга, розмір: " + fileSize + " MB)";
    }
}