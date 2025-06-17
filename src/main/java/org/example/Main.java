package org.example;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try (Connection connection = DatabaseConnector.getConnection()) {
            System.out.println("Успішне підключення до бази даних MySQL.");

            EmployeeDAO dao = new EmployeeDAO(connection);

            dao.createTable();

            System.out.println("\n--- Додаємо співробітників ---");
            dao.addEmployee("Родіон Плахотнюк", 22, "Java Developer", 200000.0f);
            dao.addEmployee("Дмитро Мазур", 21, "HR Manager", 120000.50f);
            dao.addEmployee("Олена Гуржій", 28, "QA Engineer", 100000.0f);
            dao.addEmployee("Каріна Плахотнюк", 21, "Project Manager", 250000.0f);

            System.out.println("\n--- Список всіх співробітників після додавання ---");
            List<String> initialEmployees = dao.getAllEmployees();
            if (initialEmployees.isEmpty()) {
                System.out.println("Список співробітників порожній.");
            } else {
                for (String emp : initialEmployees) {
                    System.out.println(emp);
                }
            }

            System.out.println("\n--- Оновлюємо співробітника ---");
            dao.updateEmployee(1, "Дмитро Мазур", 21, "Intern Java Developer", 25000.0f);
            dao.updateEmployee(2, "Олена Ковальчук", 26, "Senior HR Manager", 150000.0f);


            System.out.println("\n--- Видаляємо співробітників ---");
            dao.deleteEmployee(3);
            dao.deleteEmployee(999);


            System.out.println("\n--- Список всіх співробітників після оновлення та видалення ---");
            List<String> finalEmployees = dao.getAllEmployees();
            if (finalEmployees.isEmpty()) {
                System.out.println("Список співробітників порожній.");
            } else {
                for (String emp : finalEmployees) {
                    System.out.println(emp);
                }
            }

        } catch (SQLException e) {
            System.err.println("Помилка при роботі з базою даних: " + e.getMessage());
            e.printStackTrace();
        }
    }
}