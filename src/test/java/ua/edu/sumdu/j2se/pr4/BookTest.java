package ua.edu.sumdu.j2se.pr4;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BookTest {

    @Test
    void shouldThrowExceptionWhenPriceIsNegative() {
        // Додали Genre.CLASSIC п'ятим параметром
        Book book = new Book("Назва", "Автор", 2024, 100.0, Genre.CLASSIC);
        assertThrows(IllegalArgumentException.class, () -> {
            book.setPrice(-50.0);
        });
    }

    @Test
    void shouldThrowExceptionWhenConstructorDataIsInvalid() {
        // Тут теж додали жанр в кінці
        assertThrows(IllegalArgumentException.class, () -> {
            new Book("", "Автор", 2024, 100.0, Genre.CLASSIC);
        });
    }
}