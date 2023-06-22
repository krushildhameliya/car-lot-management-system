package com.example.midtermexam;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;
import javafx.util.converter.LocalDateStringConverter;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class CarLotController implements Initializable {

    @FXML
    private ComboBox<Integer> yearComboBox;
    @FXML
    private TableView<Car> carTableView;
    @FXML
    private TableColumn<Car, Integer> carIDColumn;
    @FXML
    private TableColumn<Car, Integer> modelYearColumn;
    @FXML
    private TableColumn<Car, String> makeColumn;
    @FXML
    private TableColumn<Car, String> modelColumn;
    @FXML
    private TableColumn<Car, Integer> priceColumn;
    @FXML
    private TableColumn<Car, LocalDate> dateSoldColumn;
    @FXML
    private Label totalCarsLabel;
    @FXML
    private Label totalSalesLabel;
    @FXML
    private BarChart<String, Integer> barChart;
    @FXML
    private CategoryAxis manufacturerAxis;
    @FXML
    private NumberAxis carCountAxis;

    private List<Car> cars;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeTableView();
        initializeBarChart();
        populateComboBox();
        fetchCarData();
    }

    private void initializeTableView() {
        carIDColumn.setCellValueFactory(new PropertyValueFactory<>("carID"));
        modelYearColumn.setCellValueFactory(new PropertyValueFactory<>("modelYear"));
        makeColumn.setCellValueFactory(new PropertyValueFactory<>("make"));
        modelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        dateSoldColumn.setCellValueFactory(new PropertyValueFactory<>("dateSold"));
    }

    private void initializeBarChart() {
        manufacturerAxis.setLabel("Manufacturer");
        carCountAxis.setLabel("Car Count");
    }

    private void populateComboBox() {
        List<Integer> years = getYearsFromDatabase();
        yearComboBox.setItems(FXCollections.observableArrayList(years));

        yearComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(Integer year) {
                if (year != null) {
                    return year.toString();
                }
                return null;
            }

            @Override
            public Integer fromString(String s) {
                if (s != null && !s.isEmpty()) {
                    return Integer.parseInt(s);
                }
                return null;
            }
        });
    }

    private List<Integer> getYearsFromDatabase() {
        List<Integer> years = new ArrayList<>();

        String url = "jdbc:mysql://localhost:3306/F22Midterm";
        String username = "student";
        String password = "student";

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT DISTINCT modelYear FROM carSales");

            while (resultSet.next()) {
                int year = resultSet.getInt("modelYear");
                years.add(year);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return years;
    }

    private void fetchCarData() {
        String url = "jdbc:mysql://localhost:3306/F22Midterm";
        String username = "student";
        String password = "student";

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM carSales");

            cars = new ArrayList<>();
            while (resultSet.next()) {
                int carID = resultSet.getInt("carID");
                int modelYear = resultSet.getInt("modelYear");
                String make = resultSet.getString("make");
                String model = resultSet.getString("model");
                int price = resultSet.getInt("price");
                LocalDate dateSold = resultSet.getDate("dateSold").toLocalDate();

                Car car = new Car(carID, modelYear, make, model, price, dateSold);
                cars.add(car);
            }

            carTableView.setItems(FXCollections.observableArrayList(cars));
            updateBarChart(0);
            updateLabels(cars.size(), calculateTotalSales(cars));

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int calculateTotalSales(List<Car> cars) {
        int totalSales = 0;
        for (Car car : cars) {
            totalSales += car.getPrice();
        }
        return totalSales;
    }

    private void updateLabels(int carCount, int totalSales) {
        totalCarsLabel.setText(String.valueOf(carCount));
        totalSalesLabel.setText(String.format("$%,d", totalSales));
    }

    private void updateBarChart(int selectedYear) {
        barChart.getData().clear();

        ObservableList<String> manufacturers = FXCollections.observableArrayList("Acura", "Ford", "Honda", "Nissan", "Tesla");

        for (String manufacturer : manufacturers) {
            int carCount = (int) cars.stream()
                    .filter(car -> car.getMake().equalsIgnoreCase(manufacturer))
                    .filter(car -> selectedYear == 0 || car.getModelYear() == selectedYear)
                    .count();

            XYChart.Series<String, Integer> series = new XYChart.Series<>();
            series.getData().add(new XYChart.Data<>(manufacturer, carCount));
            barChart.getData().add(series);
        }
    }

    @FXML
    private void onYearSelected(ActionEvent event) {
        int selectedYear = yearComboBox.getSelectionModel().getSelectedItem();

        if (selectedYear != 0) {
            List<Car> filteredCars = cars.stream()
                    .filter(car -> car.getModelYear() == selectedYear)
                    .collect(Collectors.toList());

            carTableView.setItems(FXCollections.observableArrayList(filteredCars));
            updateBarChart(selectedYear);
            updateLabels(filteredCars.size(), calculateTotalSales(filteredCars));
        } else {
            carTableView.setItems(FXCollections.observableArrayList(cars));
            updateBarChart(0);
            updateLabels(cars.size(), calculateTotalSales(cars));
        }
    }
}

