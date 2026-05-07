package ua.edu.sumdu.j2se.pr4;

public class RareBook extends PaperBook {
    private int condition; // Стан від 1 до 10

    public RareBook(String title, String author, int year, double price, Genre genre, double weight, int condition) {
        super(title, author, year, price, genre, weight);
        setCondition(condition);
    }

    public int getCondition() { return condition; }
    public void setCondition(int condition) {
        if (condition < 1 || condition > 10) throw new IllegalArgumentException("Стан має бути від 1 до 10");
        this.condition = condition;
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" [RARE, Condition: %d/10]", condition);
    }
}