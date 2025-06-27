package dao;

import model.EmployeeWork;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeWorkDAO {
	private static EmployeeWorkDAO instance;
	
	public static synchronized EmployeeWorkDAO getInstance() {
		if (instance == null)
			instance = new EmployeeWorkDAO();
		return instance;
	}
	
	public void addEmployeeWork(EmployeeWork employeeWork) {
		String sql = "INSERT INTO employee_works (id_employee, id_work, urgency, start_date, end_date, additional_payment) " +
				             "VALUES (?, ?, ?, ?, ?, ?)";
		try (Connection c = DBConnection.getInstance().getConnection();
		     PreparedStatement p = c.prepareStatement(sql)) {
			p.setInt(1, employeeWork.getIdEmployee());
			p.setInt(2, employeeWork.getIdWork());
			p.setString(3, employeeWork.getUrgency());
			p.setDate(4, Date.valueOf(employeeWork.getStartDate()));
			p.setObject(5, employeeWork.getEndDate() != null ? Date.valueOf(employeeWork.getEndDate()) : null);
			p.setBigDecimal(6, new java.math.BigDecimal(employeeWork.getAdditionalPayment()));
			p.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException("Ошибка добавления назначения: " + e.getMessage());
		}
	}
	
	public List<EmployeeWork> getEmployeeWorksByWorkId(int idWork) {
		List<EmployeeWork> employeeWorks = new ArrayList<>();
		String sql = "SELECT * FROM employee_works WHERE id_work = ?";
		try (Connection c = DBConnection.getInstance().getConnection();
		     PreparedStatement p = c.prepareStatement(sql)) {
			p.setInt(1, idWork);
			ResultSet r = p.executeQuery();
			while (r.next()) {
				EmployeeWork employeeWork = new EmployeeWork(
						r.getInt("id_employee"),
						r.getInt("id_work"),
						r.getString("urgency"),
						r.getDate("start_date").toLocalDate(),
						r.getDate("end_date") != null ? r.getDate("end_date").toLocalDate() : null,
						r.getBigDecimal("additional_payment").doubleValue()
				);
				employeeWorks.add(employeeWork);
			}
		} catch (SQLException e) {
			throw new RuntimeException("Ошибка получения назначений: " + e.getMessage());
		}
		return employeeWorks;
	}
	
	public List<EmployeeWork> getEmployeeWorksByEmployeeId(int idEmployee) {
		List<EmployeeWork> employeeWorks = new ArrayList<>();
		String sql = "SELECT * FROM employee_works WHERE id_employee = ?";
		try (Connection c = DBConnection.getInstance().getConnection();
		     PreparedStatement p = c.prepareStatement(sql)) {
			p.setInt(1, idEmployee);
			ResultSet r = p.executeQuery();
			while (r.next()) {
				EmployeeWork employeeWork = new EmployeeWork(
						r.getInt("id_employee"),
						r.getInt("id_work"),
						r.getString("urgency"),
						r.getDate("start_date").toLocalDate(),
						r.getDate("end_date") != null ? r.getDate("end_date").toLocalDate() : null,
						r.getBigDecimal("additional_payment").doubleValue()
				);
				employeeWorks.add(employeeWork);
			}
		} catch (SQLException e) {
			throw new RuntimeException("Ошибка получения назначений сотрудника: " + e.getMessage());
		}
		return employeeWorks;
	}
	
	public List<EmployeeWork> getAllEmployeeWorks() {
		List<EmployeeWork> employeeWorks = new ArrayList<>();
		String sql = "SELECT * FROM employee_works";
		try (Connection c = DBConnection.getInstance().getConnection();
		     PreparedStatement p = c.prepareStatement(sql);
		     ResultSet r = p.executeQuery(sql)) {
			while (r.next()) {
				EmployeeWork employeeWork = new EmployeeWork(
						r.getInt("id_employee"),
						r.getInt("id_work"),
						r.getString("urgency"),
						r.getDate("start_date").toLocalDate(),
						r.getDate("end_date") != null ? r.getDate("end_date").toLocalDate() : null,
						r.getBigDecimal("additional_payment").doubleValue()
				);
				employeeWorks.add(employeeWork);
			}
		} catch (SQLException e) {
			throw new RuntimeException("Ошибка получения всех назначений: " + e.getMessage());
		}
		return employeeWorks;
	}
	
	public void updateEmployeeWork(EmployeeWork employeeWork) {
		String sql = "UPDATE employee_works SET urgency = ?, end_date = ?, additional_payment = ? " +
				             "WHERE id_employee = ? AND id_work = ? AND start_date = ?";
		try (Connection c = DBConnection.getInstance().getConnection();
		     PreparedStatement p = c.prepareStatement(sql)) {
			p.setString(1, employeeWork.getUrgency());
			p.setObject(2, employeeWork.getEndDate() != null ? Date.valueOf(employeeWork.getEndDate()) : null);
			p.setBigDecimal(3, new java.math.BigDecimal(employeeWork.getAdditionalPayment()));
			p.setInt(4, employeeWork.getIdEmployee());
			p.setInt(5, employeeWork.getIdWork());
			p.setDate(6, Date.valueOf(employeeWork.getStartDate()));
			p.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException("Ошибка обновления назначения: " + e.getMessage());
		}
	}
	
	public void deleteEmployeeWork(int idEmployee, int idWork, java.time.LocalDate startDate) {
		String sql = "DELETE FROM employee_works WHERE id_employee = ? AND id_work = ? AND start_date = ?";
		try (Connection c = DBConnection.getInstance().getConnection();
		     PreparedStatement p = c.prepareStatement(sql)) {
			p.setInt(1, idEmployee);
			p.setInt(2, idWork);
			p.setDate(3, Date.valueOf(startDate));
			p.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException("Ошибка удаления назначения: " + e.getMessage());
		}
	}
}