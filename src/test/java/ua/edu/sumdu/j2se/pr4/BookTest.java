package ua.edu.sumdu.j2se.pr4;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BookTest {

    @Test
    void shouldThrowExceptionWhenPriceIsNegative() {
        // Створюємо книгу з нормальною ціною
        Book book = new Book("Назва", "Автор", 2024, 100.0);

        // Перевіряємо, що сетер викине помилку, якщо спробувати поставити -50
        assertThrows(IllegalArgumentException.class, () -> {
            book.setPrice(-50.0);
        });
    }

    @Test
    void shouldThrowExceptionWhenConstructorDataIsInvalid() {
        // Перевіряємо, що конструктор викине помилку, якщо назва порожня
        assertThrows(IllegalArgumentException.class, () -> {
            new Book("", "Автор", 2024, 100.0);
        });
    }
}
