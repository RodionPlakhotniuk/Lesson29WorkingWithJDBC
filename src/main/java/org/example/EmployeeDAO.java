package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {
    private Connection connection;

    public EmployeeDAO(Connection connection) {
        this.connection = connection;
    }

    public void createTable() throws SQLException {
        String sql = """
                CREATE TABLE IF NOT EXISTS employees (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    name VARCHAR(100),
                    age INT,
                    position VARCHAR(100),
                    salary FLOAT
                )
                """;
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Таблиця 'employees' створена або вже існує.");
        }
    }

    public void addEmployee(String name, int age, String position, float salary) throws SQLException {
        String sql = "INSERT INTO employees (name, age, position, salary) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setString(3, position);
            ps.setFloat(4, salary);
            ps.executeUpdate();
            System.out.printf("Співробітник '%s' успішно доданий.%n", name);
        }
    }

    public void updateEmployee(int id, String name, int age, String position, float salary) throws SQLException {
        String sql = "UPDATE employees SET name = ?, age = ?, position = ?, salary = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setString(3, position);
            ps.setFloat(4, salary);
            ps.setInt(5, id);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.printf("Співробітник з ID %d успішно оновлений.%n", id);
            } else {
                System.out.printf("Співробітник з ID %d не знайдений для оновлення (можливо, ID не існує).%n", id);
            }
        }
    }

    public void deleteEmployee(int id) throws SQLException {
        String sql = "DELETE FROM employees WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.printf("Співробітник з ID %d успішно видалений.%n", id);
            } else {
                System.out.printf("Співробітник з ID %d не знайдений для видалення (можливо, ID не існує).%n", id);
            }
        }
    }

    public List<String> getAllEmployees() throws SQLException {
        List<String> employees = new ArrayList<>();
        String sql = "SELECT id, name, age, position, salary FROM employees ORDER BY id";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String emp = String.format("ID: %d, Name: %s, Age: %d, Position: %s, Salary: %.2f",
                        rs.getInt("id"), rs.getString("name"), rs.getInt("age"),
                        rs.getString("position"), rs.getFloat("salary"));
                employees.add(emp);
            }
        }
        return employees;
    }
}