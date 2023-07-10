package com.example.midtermexam;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

    public class CarModel {

        private String url;
        private String username;
        private String password;

        public CarModel(String url, String username, String password) {
            this.url = url;
            this.username = username;
            this.password = password;
        }

        public List<Integer> getYearsFromDatabase() {
            List<Integer> years = new ArrayList<>();

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

        public List<Car> fetchCarData() {
            List<Car> cars = new ArrayList<>();

            try {
                Connection connection = DriverManager.getConnection(url, username, password);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM carSales");

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

                resultSet.close();
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return cars;
        }
    }

