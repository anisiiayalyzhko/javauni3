package ua.edu.sumdu.j2se.pr4;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainApp extends Application {
    private Library library = new Library();
    private ListView<String> listView = new ListView<>();
    private TextArea detailsArea = new TextArea();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Library Management (UUID System)");

        // Поля для додавання
        TextField titleField = new TextField(); titleField.setPromptText("Назва");
        TextField authorField = new TextField(); authorField.setPromptText("Автор");
        Button addButton = new Button("Додати PaperBook");

        // Пошук
        TextField searchField = new TextField(); searchField.setPromptText("Введіть UUID для пошуку");
        Button searchButton = new Button("Знайти за UUID");

        addButton.setOnAction(e -> {
            PaperBook b = new PaperBook(titleField.getText(), authorField.getText(), 2024, 100.0, Genre.FICTION, 300);
            library.addNewBook(b, 1);
            updateList();
        });

        searchButton.setOnAction(e -> {
            Book found = library.findByUuid(searchField.getText());
            if (found != null) detailsArea.setText("ЗНАЙДЕНО:\n" + found.toFullString());
            else detailsArea.setText("Об'єкт не знайдено або формат UUID невірний.");
        });

        VBox layout = new VBox(10, new Label("Додати книгу:"), titleField, authorField, addButton,
                new Label("Список (Коротко):"), listView,
                new Label("Пошук по UUID:"), searchField, searchButton, detailsArea);
        layout.setPadding(new Insets(15));

        primaryStage.setScene(new Scene(layout, 450, 600));
        primaryStage.show();
    }

    private void updateList() {
        listView.getItems().clear();
        for (Book b : library.getBooks()) {
            listView.getItems().add(b.getTitle() + " | UUID: " + b.getUuid());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
