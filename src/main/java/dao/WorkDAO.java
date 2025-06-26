package dao;

import model.Work;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WorkDAO {
	private static WorkDAO instance;
	
	public static synchronized WorkDAO getInstance() {
		if (instance == null)
			instance = new WorkDAO();
		return instance;
	}
	
	public void addWork(Work work) {
		String sql = "INSERT INTO works (id_responsible, title, labor_intensity, fixed_payment, recommended_employees, description) " +
				             "VALUES (?, ?, ?, ?, ?, ?)";
		try (Connection c = DBConnection.getInstance().getConnection();
		     PreparedStatement p = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			p.setObject(1, work.getIdResponsible() == 0 ? null : work.getIdResponsible());
			p.setString(2, work.getTitle());
			p.setInt(3, work.getLaborIntensity());
			p.setObject(4, work.getFixedPayment() != null ? new java.math.BigDecimal(work.getFixedPayment()) : null);
			p.setInt(5, work.getRecommendedEmployees());
			p.setString(6, work.getDescription());
			p.executeUpdate();
			
			try (ResultSet rs = p.getGeneratedKeys()) {
				if (rs.next()) {
					work.setIdWork(rs.getInt(1));
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("Ошибка добавления задачи: " + e.getMessage());
		}
	}
	
	public Work getWorkById(int id) {
		String sql = "SELECT * FROM works WHERE id_work = ?";
		try (Connection c = DBConnection.getInstance().getConnection();
		     PreparedStatement p = c.prepareStatement(sql)) {
			p.setInt(1, id);
			ResultSet r = p.executeQuery();
			if (r.next()) {
				Work work = new Work(
						r.getInt("id_responsible"),
						r.getString("title"),
						r.getInt("labor_intensity"),
						r.getBigDecimal("fixed_payment") != null ? r.getBigDecimal("fixed_payment").doubleValue() : null,
						r.getInt("recommended_employees"),
						r.getString("description")
				);
				work.setIdWork(r.getInt("id_work"));
				return work;
			}
		} catch (SQLException e) {
			throw new RuntimeException("Ошибка получения задачи: " + e.getMessage());
		}
		return null;
	}
	
	public List<Work> getAllWorks() {
		List<Work> works = new ArrayList<>();
		String sql = "SELECT * FROM works";
		try (Connection c = DBConnection.getInstance().getConnection();
		     PreparedStatement p = c.prepareStatement(sql);
		     ResultSet r = p.executeQuery()) {
			while (r.next()) {
				Work work = new Work(
						r.getInt("id_responsible"),
						r.getString("title"),
						r.getInt("labor_intensity"),
						r.getBigDecimal("fixed_payment") != null ? r.getBigDecimal("fixed_payment").doubleValue() : null,
						r.getInt("recommended_employees"),
						r.getString("description")
				);
				work.setIdWork(r.getInt("id_work"));
				works.add(work);
			}
		} catch (SQLException e) {
			throw new RuntimeException("Ошибка получения списка задач: " + e.getMessage());
		}
		return works;
	}
	
	public void updateWork(Work work) {
		String sql = "UPDATE works SET id_responsible = ?, title = ?, labor_intensity = ?, fixed_payment = ?, " +
				             "recommended_employees = ?, description = ? WHERE id_work = ?";
		try (Connection c = DBConnection.getInstance().getConnection();
		     PreparedStatement p = c.prepareStatement(sql)) {
			p.setObject(1, work.getIdResponsible() == 0 ? null : work.getIdResponsible());
			p.setString(2, work.getTitle());
			p.setInt(3, work.getLaborIntensity());
			p.setObject(4, work.getFixedPayment() != null ? new java.math.BigDecimal(work.getFixedPayment()) : null);
			p.setInt(5, work.getRecommendedEmployees());
			p.setString(6, work.getDescription());
			p.setInt(7, work.getIdWork());
			p.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException("Ошибка обновления задачи: " + e.getMessage());
		}
	}
	
	public void deleteWork(int idWork) {
		String sql = "DELETE FROM works WHERE id_work = ?";
		try (Connection c = DBConnection.getInstance().getConnection();
		     PreparedStatement p = c.prepareStatement(sql)) {
			p.setInt(1, idWork);
			p.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException("Ошибка удаления работы: " + e.getMessage());
		}
	}
}