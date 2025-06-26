package dao;

import model.User;
import java.sql.*;

public class UserDAO {
	private static UserDAO instance;
	
	public static synchronized UserDAO getInstance() {
		if (instance == null)
			instance = new UserDAO();
		return instance;
	}
	
	public User getUserByEmail(String email) {
		String sql = "SELECT u.*, e.last_name, e.first_name, e.middle_name, e.gender, e.position, e.salary, e.experience, e.email " +
				             "FROM users u JOIN employees e ON u.id_employee = e.id_employee WHERE e.email = ?";
		try (Connection c = DBConnection.getInstance().getConnection();
		     PreparedStatement p = c.prepareStatement(sql)) {
			p.setString(1, email);
			ResultSet r = p.executeQuery();
			if (r.next()) {
				return createUserFromResultSet(r);
			}
		} catch (SQLException e) {
			throw new RuntimeException("Ошибка получения пользователя по email: " + e.getMessage());
		}
		return null;
	}
	
	public void updateUser(User user) {
		String sql = "UPDATE users SET password = ?, role = ? WHERE id_user = ?";
		try (Connection c = DBConnection.getInstance().getConnection();
		     PreparedStatement p = c.prepareStatement(sql)) {
			p.setString(1, user.getPassword());
			p.setString(2, user.getRole());
			p.setInt(3, user.getIdUser());
			p.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException("Ошибка обновления пользователя: " + e.getMessage());
		}
	}
	
	private User createUserFromResultSet(ResultSet r) throws SQLException {
		User user = new User(
				r.getString("email"),
				r.getString("password"),
				r.getString("role"),
				r.getString("last_name"),
				r.getString("first_name"),
				r.getString("middle_name"),
				r.getString("gender"),
				r.getString("position"),
				r.getBigDecimal("salary").doubleValue(),
				r.getInt("experience")
		);
		user.setIdUser(r.getInt("id_user"));
		user.setIdEmployee(r.getInt("id_employee"));
		return user;
	}
	
	public boolean registerUser(User user) {
		String sql = "INSERT INTO users (id_employee, password, role) VALUES (?, ?, ?)";
		try (Connection c = DBConnection.getInstance().getConnection();
		     PreparedStatement p = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			p.setObject(1, user.getIdEmployee() == 0 ? null : user.getIdEmployee());
			p.setString(2, user.getPassword());
			p.setString(3, user.getRole());
			p.executeUpdate();
			
			try (ResultSet r = p.getGeneratedKeys()) {
				if (r.next()) {
					user.setIdUser(r.getInt(1));
					return true;
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("Ошибка регистрации пользователя: " + e.getMessage());
		}
		return false;
	}
}