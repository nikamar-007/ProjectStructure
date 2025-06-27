package dao;

import model.Employee;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {
	private static EmployeeDAO instance;
	
	public static synchronized EmployeeDAO getInstance() {
		if (instance == null)
			instance = new EmployeeDAO();
		return instance;
	}
	
	public int addEmployee(Employee employee) {
		String sql = "INSERT INTO employees (last_name, first_name, middle_name, gender, position, salary, experience, email) " +
				             "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		try (Connection c = DBConnection.getInstance().getConnection();
		     PreparedStatement p = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			p.setString(1, employee.getLastName());
			p.setString(2, employee.getFirstName());
			p.setString(3, employee.getMiddleName());
			p.setString(4, employee.getGender());
			p.setString(5, employee.getPosition());
			p.setBigDecimal(6, new java.math.BigDecimal(employee.getSalary()));
			p.setInt(7, employee.getExperience());
			p.setString(8, employee.getEmail());
			p.executeUpdate();
			
			try (ResultSet r = p.getGeneratedKeys()) {
				if (r.next()) {
					employee.setIdEmployee(r.getInt(1));
					return r.getInt(1);
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("Ошибка добавления сотрудника: " + e.getMessage());
		}
		return 0;
	}
	
	public Employee getEmployeeById(int id) {
		String sql = "SELECT * FROM employees WHERE id_employee = ?";
		try (Connection c = DBConnection.getInstance().getConnection();
		     PreparedStatement p = c.prepareStatement(sql)) {
			p.setInt(1, id);
			ResultSet r = p.executeQuery();
			if (r.next()) {
				Employee employee = new Employee(
						r.getString("last_name"),
						r.getString("first_name"),
						r.getString("middle_name"),
						r.getString("gender"),
						r.getString("position"),
						r.getBigDecimal("salary").doubleValue(),
						r.getInt("experience"),
						r.getString("email")
				);
				employee.setIdEmployee(r.getInt("id_employee"));
				return employee;
			}
		} catch (SQLException e) {
			throw new RuntimeException("Ошибка получения сотрудника: " + e.getMessage());
		}
		return null;
	}
	
	public List<Employee> getAllEmployees() {
		List<Employee> employees = new ArrayList<>();
		String sql = "SELECT * FROM employees";
		try (Connection c = DBConnection.getInstance().getConnection();
		     PreparedStatement p = c.prepareStatement(sql);
		     ResultSet r = p.executeQuery()) {
			while (r.next()) {
				Employee employee = new Employee(
						r.getString("last_name"),
						r.getString("first_name"),
						r.getString("middle_name"),
						r.getString("gender"),
						r.getString("position"),
						r.getBigDecimal("salary").doubleValue(),
						r.getInt("experience"),
						r.getString("email")
				);
				employee.setIdEmployee(r.getInt("id_employee"));
				employees.add(employee);
			}
		} catch (SQLException e) {
			throw new RuntimeException("Ошибка получения списка сотрудников: " + e.getMessage());
		}
		return employees;
	}
	
	public void updateEmployee(Employee employee) {
		String sql = "UPDATE employees SET last_name = ?, first_name = ?, middle_name = ?, gender = ?, " +
				             "position = ?, salary = ?, experience = ?, email = ? WHERE id_employee = ?";
		try (Connection c = DBConnection.getInstance().getConnection();
		     PreparedStatement p = c.prepareStatement(sql)) {
			p.setString(1, employee.getLastName());
			p.setString(2, employee.getFirstName());
			p.setString(3, employee.getMiddleName());
			p.setString(4, employee.getGender());
			p.setString(5, employee.getPosition());
			p.setBigDecimal(6, new java.math.BigDecimal(employee.getSalary()));
			p.setInt(7, employee.getExperience());
			p.setString(8, employee.getEmail());
			p.setInt(9, employee.getIdEmployee());
			p.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException("Ошибка обновления сотрудника: " + e.getMessage());
		}
	}
	
	public void deleteEmployee(int idEmployee) {
		Connection c =  DBConnection.getInstance().getConnection();
		try {
			String deleteEmployeeWorksSql = "DELETE FROM employee_works WHERE id_employee = ?";
			try (PreparedStatement p = c.prepareStatement(deleteEmployeeWorksSql)) {
				p.setInt(1, idEmployee);
				p.executeUpdate();
			}
			
			String deleteUserSql = "DELETE FROM users WHERE id_employee = ?";
			try (PreparedStatement p = c.prepareStatement(deleteUserSql)) {
				p.setInt(1, idEmployee);
				p.executeUpdate();
			}
			
			String deleteEmployeeSql = "DELETE FROM employees WHERE id_employee = ?";
			try (PreparedStatement p = c.prepareStatement(deleteEmployeeSql)) {
				p.setInt(1, idEmployee);
				p.executeUpdate();
			}
		} catch (SQLException e) {
			throw new RuntimeException("Ошибка удаления сотрудника: " + e.getMessage());
		}
	}
}