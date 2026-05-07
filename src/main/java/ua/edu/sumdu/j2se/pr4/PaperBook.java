package ua.edu.sumdu.j2se.pr4;

// Клас для паперових книг
public class PaperBook extends Book {
    private double weight; // Вага в грамах

    public PaperBook(String title, String author, int year, double price, Genre genre, double weight) {
        super(title, author, year, price, genre);
        setWeight(weight);
    }

    public double getWeight() { return weight; }

    public void setWeight(double weight) {
        if (weight <= 0) {
            throw new IllegalArgumentException("Вага має бути більшою за 0");
        }
        this.weight = weight;
    }

    @Override
    public String toString() {
        return super.toString() + " (Паперова книга, вага: " + weight + " г)";
    }
}