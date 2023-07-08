package com.example.project_application;

// Singleton class
public class MySingleton {
    private static MySingleton instance;
    private String variableValue;


    private MySingleton() {
        // Private constructor to prevent instantiation
    }

    public static MySingleton getInstance() {
        if (instance == null) {
            instance = new MySingleton();
        }
        return instance;
    }

    public String getVariableValue() {
        return variableValue;
    }

    public void setVariableValue(String value) {
        variableValue = value;

    }
};


