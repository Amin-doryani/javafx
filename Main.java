import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    ObservableList<Car> cars = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) {
        
        TableView<Car> table = new TableView<>();

        TableColumn<Car, Integer> carid = new TableColumn<>("ID");
        carid.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Car, String> carnom = new TableColumn<>("Nom");
        carnom.setCellValueFactory(new PropertyValueFactory<>("nom"));

        TableColumn<Car, Integer> carprix = new TableColumn<>("Prix");
        carprix.setCellValueFactory(new PropertyValueFactory<>("prix"));

        TableColumn<Car, Void> actionCol = new TableColumn<>("Action");
        actionCol.setCellFactory(col -> new TableCell<>() {
            private final Button deleteButton = new Button("Delete");
            private final Button updateButton = new Button("Update");
            private final HBox buttons = new HBox(5, updateButton, deleteButton);

            {
                deleteButton.setOnAction(e -> {
                    Car car = getTableView().getItems().get(getIndex());
                    cars.remove(car);
                });

                updateButton.setOnAction(e -> {
                    Car car = getTableView().getItems().get(getIndex());
                    showUpdateCarForm(primaryStage, car);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(buttons);
                }
            }
        });
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.getColumns().addAll(carid, carnom, carprix, actionCol);
        table.setItems(cars);

        Button addButton = new Button("Ajouter");
        addButton.setOnAction(e -> showAddCarForm(primaryStage));

        VBox layout = new VBox(10, table, addButton);
        Scene mainScene = new Scene(layout, 900, 400);
        primaryStage.setScene(mainScene);
        primaryStage.setTitle("Car List");
        primaryStage.show();
    }

    private void showAddCarForm(Stage stage) {
        TextField idField = new TextField();
        idField.setPromptText("ID");

        TextField nameField = new TextField();
        nameField.setPromptText("Nom");

        TextField priceField = new TextField();
        priceField.setPromptText("Prix");

        Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                String name = nameField.getText();
                int price = Integer.parseInt(priceField.getText());

                cars.add(new Car(id, name, price));
                start(stage); 
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter valid numbers for ID and Price.");
                alert.show();
            }
        });

        VBox formLayout = new VBox(10, idField, nameField, priceField, saveButton);
        Scene formScene = new Scene(formLayout, 400, 300);
        stage.setScene(formScene);
    }

    private void showUpdateCarForm(Stage stage, Car car) {
        TextField idField = new TextField(String.valueOf(car.getId()));
        idField.setDisable(true); // Disable editing of ID

        TextField nameField = new TextField(car.getNom());
        TextField priceField = new TextField(String.valueOf(car.getPrix()));

        Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> {
            try {
                String name = nameField.getText();
                int price = Integer.parseInt(priceField.getText());

                car.SetNom(name);
                car.SetPrix(price);

                start(stage); // Return to main view and refresh table

            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a valid number for Price.");
                alert.show();
            }
        });

        VBox formLayout = new VBox(10, idField, nameField, priceField, saveButton);
        Scene formScene = new Scene(formLayout, 400, 300);
        stage.setScene(formScene);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
