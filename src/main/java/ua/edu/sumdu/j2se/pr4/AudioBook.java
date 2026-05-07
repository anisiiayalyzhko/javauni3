package ua.edu.sumdu.j2se.pr4;

public class AudioBook extends Book {
    private int duration; // Тривалість у хвилинах

    public AudioBook(String title, String author, int year, double price, Genre genre, int duration) {
        super(title, author, year, price, genre);
        setDuration(duration);
    }

    public int getDuration() { return duration; }
    public void setDuration(int duration) {
        if (duration <= 0) throw new IllegalArgumentException("Тривалість має бути більше 0");
        this.duration = duration;
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" [AudioBook, Duration: %d min]", duration);
    }
}