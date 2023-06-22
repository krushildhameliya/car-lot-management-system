package com.example.midtermexam;

import javafx.beans.property.*;

import java.time.LocalDate;

public class Car {
    private final IntegerProperty carID;
    private final IntegerProperty modelYear;
    private final StringProperty make;
    private final StringProperty model;
    private final IntegerProperty price;
    private final ObjectProperty<LocalDate> dateSold;

    public Car(int carID, int modelYear, String make, String model, int price, LocalDate dateSold) {
        this.carID = new SimpleIntegerProperty(carID);
        this.modelYear = new SimpleIntegerProperty(modelYear);
        this.make = new SimpleStringProperty(make);
        this.model = new SimpleStringProperty(model);
        this.price = new SimpleIntegerProperty(price);
        this.dateSold = new SimpleObjectProperty<>(dateSold);
    }

    // Getters for the properties

    public int getCarID() {
        return carID.get();
    }

    public IntegerProperty carIDProperty() {
        return carID;
    }

    public int getModelYear() {
        return modelYear.get();
    }

    public IntegerProperty modelYearProperty() {
        return modelYear;
    }

    public String getMake() {
        return make.get();
    }

    public StringProperty makeProperty() {
        return make;
    }

    public String getModel() {
        return model.get();
    }

    public StringProperty modelProperty() {
        return model;
    }

    public int getPrice() {
        return price.get();
    }

    public IntegerProperty priceProperty() {
        return price;
    }

    public LocalDate getDateSold() {
        return dateSold.get();
    }

    public ObjectProperty<LocalDate> dateSoldProperty() {
        return dateSold;
    }
}

