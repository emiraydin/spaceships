package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

	private Connection connect = null;
	private Statement statement = null;
	private ResultSet resultSet = null;
	private PreparedStatement preparedStatement = null;


	public Database() throws ClassNotFoundException, SQLException {

		Class.forName("com.mysql.jdbc.Driver");

		connect = DriverManager.getConnection("jdbc:mysql://" + Properties.MYSQL_HOST + '/' + Properties.MYSQL_DBNAME
				+ "/user=" + Properties.MYSQL_USER + "&password=" + Properties.MYSQL_PASSWORD);

		statement = connect.createStatement();

		resultSet = statement.executeQuery("SELECT * FROM stats");

		if (resultSet.next()) {
			System.out.println(resultSet.getString(1));
		}


	}

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		new Database();
	}
	
	public boolean addUser(String username, String password) throws SQLException {
		// Initialize user info
		preparedStatement = connect.prepareStatement("INSERT INTO users (username, password) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);
		preparedStatement.setString(0, username);
		preparedStatement.setString(1, password);
		preparedStatement.executeUpdate();
		// Get the user id just created
		ResultSet keys = preparedStatement.getGeneratedKeys();
		keys.next();
		int uid = keys.getInt(1);
		// Initialize stats data
		preparedStatement = connect.prepareStatement("INSERT INTO stats (uid, score, wins, losses) VALUES (?, ?, ?, ?)");
		preparedStatement.setInt(0, uid);
		preparedStatement.setInt(1, 0);
		preparedStatement.setInt(2, 0);
		preparedStatement.setInt(3, 0);
		preparedStatement.executeUpdate();
		// Commit everything
		connect.commit();
		
		return true;
		
	}
	
	public boolean updateUser(String username, String password) throws SQLException {
		
		preparedStatement = connect.prepareStatement("UPDATE users SET password=? WHERE username=?");
		preparedStatement.setString(0, password);
		preparedStatement.setString(1, username);
		int result = preparedStatement.executeUpdate();

		if (result > -1)
			return true;
		else
			return false;
				
	}
	
	public boolean addHistory(int gid, int uid, String latest) throws SQLException {
		
		preparedStatement = connect.prepareStatement("INSERT INTO history (gid, uid, latest) VALUES (?, ?, ?)");
		preparedStatement.setInt(0, gid);
		preparedStatement.setInt(1, uid);
		preparedStatement.setString(2, latest);
		int result = preparedStatement.executeUpdate();

		if (result > -1)
			return true;
		else
			return false;
		
	}
	
	public boolean createGame(int gid, int uid1, int uid2, boolean finished) throws SQLException {
		
		preparedStatement = connect.prepareStatement("INSERT INTO games (gid, uid1, uid2, finished, last_updated) VALUES (?, ?, ?, ?, now())");
		preparedStatement.setInt(0, gid);
		preparedStatement.setInt(1, uid1);
		preparedStatement.setInt(2, uid2);
		preparedStatement.setBoolean(3, finished);
		int result = preparedStatement.executeUpdate();

		if (result > -1)
			return true;
		else
			return false;

	}
	
	public boolean updateGame(int gid, boolean finished) throws SQLException {
		
		preparedStatement = connect.prepareStatement("UPDATE games SET finished=? WHERE gid=?");
		preparedStatement.setBoolean(0, finished);
		preparedStatement.setInt(1, gid);
		
		int result = preparedStatement.executeUpdate();
		
		if (result > -1)
			return true;
		else
			return false;
		
	}
		
	public boolean updateStats(int uid, int score, int wins, int losses) throws SQLException {
		
		preparedStatement = connect.prepareStatement("UPDATE stats SET score=? AND wins=? AND losses=? WHERE uid=?");
		preparedStatement.setInt(0, score);
		preparedStatement.setInt(1, wins);
		preparedStatement.setInt(2, losses);
		preparedStatement.setInt(3, uid);
		int result = preparedStatement.executeUpdate();
		
		if (result > -1)
			return true;
		else
			return false;

	}

}
