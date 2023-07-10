# car-lot-management-system

The Car Lot Management System is a JavaFX application for managing car sales data. It provides a user-friendly interface to view and analyze car sales information, including model year, make, model, price, and date sold. The application connects to a MySQL database to fetch and display the data.

## Features

- View and search car sales data
- Filter data by model year
- Display total number of cars sold and total sales amount
- Visualize car sales data with a bar chart by manufacturer

## Technologies

- Java Development Kit (JDK) 11 or higher
- MySQL database server
- MySQL Connector/J library

## Installation

1. Clone the repository: `git clone https://github.com/krushildhameliya/car-lot-management-system.git`
2. Import the project into your Java IDE.
3. Configure the MySQL database connection in the `CarLotController` class.
4. Add the MySQL Connector/J library to your project's dependencies.
5. Build and run the application.

## File Structure

The project has the following file structure:

- `src/`: Contains the Java source code files.
- `com.example.midtermexam/`: Package containing the main application files.
- `Car.java`: Class representing a Car object.
- `CarLotApp.java`: Main application entry point.
- `CarLotController.java`: Controller class handling UI logic.
- `car_lot.fxml`: FXML file defining the application's UI layout.


## Usage

1. Launch the application.
2. The table view will be populated with car sales data from the database.
3. Use the year selection dropdown to filter the data by a specific year.
4. The total number of cars sold and total sales amount will be displayed.
5. The bar chart will show the number of cars sold by manufacturer.
6. Explore and analyze the car sales data using the provided features.

## Contributing

Contributions to the Car Lot Management System are welcome! If you find any bugs or have suggestions for new features, please create an issue or submit a pull request.



