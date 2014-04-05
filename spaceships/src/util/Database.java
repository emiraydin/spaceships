package util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import messageprotocol.NewTurnMessage;

public class Database {

	private Connection connect = null;
	private PreparedStatement preparedStatement = null;


	public Database() throws ClassNotFoundException, SQLException {

		Class.forName("com.mysql.jdbc.Driver");

		connect = DriverManager.getConnection("jdbc:mysql://" + Properties.MYSQL_HOST + '/' + Properties.MYSQL_DBNAME,
					Properties.MYSQL_USER, Properties.MYSQL_PASSWORD);
		connect.setAutoCommit(false);

	}
	
	/**
	 * Adds a new user to the database.
	 * @param username
	 * @param password
	 * @return
	 * @throws SQLException
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public boolean addUser(String username, String password) throws SQLException, NoSuchAlgorithmException, UnsupportedEncodingException {

		// If the user already exists, return false
		preparedStatement = connect.prepareStatement("SELECT COUNT(*) FROM users WHERE username=?");
		preparedStatement.setString(1, username);
		ResultSet resultSet = preparedStatement.executeQuery();
		int result = 0;
		while (resultSet.next())
			result = resultSet.getInt(1);
		if (result > 0)
			return false;
		
		// Initialize user info
		preparedStatement = connect.prepareStatement("INSERT INTO users (username, password) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);
		preparedStatement.setString(1, username);
		preparedStatement.setString(2, SHA1(password));
		preparedStatement.executeUpdate();

		// Get the user id just created
		ResultSet keys = preparedStatement.getGeneratedKeys();
		keys.next();
		int uid = keys.getInt(1);

		// Initialize stats data
		preparedStatement = connect.prepareStatement("INSERT INTO stats (uid, score, wins, losses) VALUES (?, ?, ?, ?)");
		preparedStatement.setInt(1, uid);
		preparedStatement.setInt(2, 0);
		preparedStatement.setInt(3, 0);
		preparedStatement.setInt(4, 0);
		preparedStatement.executeUpdate();
		// Commit everything
		connect.commit();

		return true;

	}

	/**
	 * Logs in a user, and marks them online.
	 * @param username
	 * @param password
	 * @return
	 * @throws SQLException
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public boolean loginUser(String username, String password) throws SQLException, NoSuchAlgorithmException, UnsupportedEncodingException {

		preparedStatement = connect.prepareStatement("SELECT COUNT(*) FROM users WHERE username=? AND password=?");
		preparedStatement.setString(1, username);
		preparedStatement.setString(2, SHA1(password));
		ResultSet resultSet = preparedStatement.executeQuery();
		int result = 0;
		while (resultSet.next())
			result = resultSet.getInt(1);

		if (result > 0) {
			// Update login variable
			preparedStatement = connect.prepareStatement("UPDATE users SET online=? WHERE username=?");
			preparedStatement.setBoolean(1, true);
			preparedStatement.setString(2, username);
			preparedStatement.executeUpdate();
			connect.commit();
			return true;
		} else {
			return false;
		}

	}

	/**
	 * Logs out a user, and marks them offline.
	 * @param username
	 * @return
	 * @throws SQLException
	 */
	public boolean logoutUser(String username) throws SQLException {

		// Mark the user as offline
		preparedStatement = connect.prepareStatement("UPDATE users SET online=? WHERE username=?");
		preparedStatement.setBoolean(1, false);
		preparedStatement.setString(2, username);
		int result = preparedStatement.executeUpdate();
		connect.commit();
		
		if (result > -1)
			return true;
		else
			return false;

	}

	/**
	 * Updates the user password. Usernames are non-changeable by definition.
	 * @param username
	 * @param password
	 * @return
	 * @throws SQLException
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public boolean updateUser(String username, String password) throws SQLException, NoSuchAlgorithmException, UnsupportedEncodingException {

		preparedStatement = connect.prepareStatement("UPDATE users SET password=? WHERE username=?");
		preparedStatement.setString(1, SHA1(password));
		preparedStatement.setString(2, username);
		int result = preparedStatement.executeUpdate();
		connect.commit();
		
		if (result > -1)
			return true;
		else
			return false;

	}

	/**
	 * If showOnline = 0, only show offline users
	 * If showOnline = 1, only show online games
	 * Else, show all users
	 * @param showOnline
	 * @return an ArrayList of usernames
	 * @throws SQLException
	 */
	public ArrayList<String> showUsers(int showOnline) throws SQLException {

		if (showOnline == 0 || showOnline == 1) {
			preparedStatement = connect.prepareStatement("SELECT * FROM users WHERE online=?");
			preparedStatement.setBoolean(1, showOnline != 0);
		} else {
			preparedStatement = connect.prepareStatement("SELECT * FROM users");
		}

		preparedStatement.executeQuery();
		ResultSet resultSet = preparedStatement.executeQuery();

		ArrayList<String> result = new ArrayList<>();
		while (resultSet.next()) {
			result.add(resultSet.getString("username"));
		}

		return result;

	}

	/**
	 * Adds the latest move for each player in any given game
	 * @param gid
	 * @param uid
	 * @param latest: NewTurnMessage object
	 * @return
	 * @throws SQLException
	 * @throws IOException 
	 */
	public boolean addHistory(int gid, int uid, NewTurnMessage latest) throws SQLException, IOException {

		// Convert the message to string for storage
		String message = ObjectConverter.objectToString(latest);

		// Check if a history exists for same gid & uid
		preparedStatement = connect.prepareStatement("SELECT COUNT(*) FROM history WHERE gid=? AND uid=?");
		preparedStatement.setInt(1, gid);
		preparedStatement.setInt(2, uid);
		ResultSet resultSet = preparedStatement.executeQuery();
		int result = 0;
		while (resultSet.next())
			result = resultSet.getInt(1);

		// It already exists, so just update
		if (result > 0) {
			preparedStatement = connect.prepareStatement("UPDATE history SET latest=? WHERE gid=? AND uid=?");
			preparedStatement.setString(1, message);
			preparedStatement.setInt(2, gid);
			preparedStatement.setInt(3, uid);
			preparedStatement.executeUpdate();
			connect.commit();
		} else {
			// It doesn't exist, add a new history item
			preparedStatement = connect.prepareStatement("INSERT INTO history (gid, uid, latest) VALUES (?, ?, ?)");
			preparedStatement.setInt(1, gid);
			preparedStatement.setInt(2, uid);
			preparedStatement.setString(3, message);
			preparedStatement.executeUpdate();
			connect.commit();
		}

		return true;

	}

	/**
	 * Gets the latest NewTurnMessage recorded for a user within a particular game.
	 * @param gid
	 * @param uid
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public NewTurnMessage getHistory(int gid, int uid) throws SQLException, ClassNotFoundException, IOException {

		preparedStatement = connect.prepareStatement("SELECT latest FROM history WHERE gid=? AND uid=?");
		preparedStatement.setInt(1, gid);
		preparedStatement.setInt(2, uid);
		ResultSet resultSet = preparedStatement.executeQuery();

		NewTurnMessage latest = null;

		while (resultSet.next())
			latest = (NewTurnMessage) ObjectConverter.stringtoObject(resultSet.getString("latest"));

		return latest;

	}

	/**
	 * Creates a new game in the database.
	 * @param gid
	 * @param uid1
	 * @param uid2
	 * @param active
	 * @return gid: game id
	 * @throws SQLException
	 */
	public int createGame(int uid1, int uid2, boolean active) throws SQLException {

		preparedStatement = connect.prepareStatement("INSERT INTO games (uid1, uid2, active, last_updated) VALUES (?, ?, ?, now())", Statement.RETURN_GENERATED_KEYS);
		preparedStatement.setInt(1, uid1);
		preparedStatement.setInt(2, uid2);
		preparedStatement.setBoolean(3, active);
		preparedStatement.executeUpdate();
		connect.commit();
		
		ResultSet keys = preparedStatement.getGeneratedKeys();
		keys.next();
		int gid = keys.getInt(1);

		return gid;

	}

	/**
	 * If showActive = 0, only show finished games
	 * If showActive = 1, only show active games
	 * Else, show all games
	 * @param showActive
	 * @return an ArrayList of (gid, uid1, uid2, last_updated)
	 * @throws SQLException
	 */
	public ArrayList<String[]> showGames(int showActive) throws SQLException {

		if (showActive == 0 || showActive == 1) {
			preparedStatement = connect.prepareStatement("SELECT * FROM games WHERE active=?");
			preparedStatement.setBoolean(1, showActive != 0);
		} else {
			preparedStatement = connect.prepareStatement("SELECT * FROM games");
		}

		preparedStatement.executeQuery();
		ResultSet resultSet = preparedStatement.executeQuery();

		ArrayList<String[]> result = new ArrayList<>();
		while (resultSet.next()) {
			DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
			String[] row = new String[4];
			row[0] = Integer.toString(resultSet.getInt("gid"));
			row[1] = Integer.toString(resultSet.getInt("uid1"));
			row[2] = Integer.toString(resultSet.getInt("uid2"));
			row[3] = df.format(resultSet.getTimestamp("last_updated"));
			result.add(row);
		}

		return result;

	}

	/**
	 * Updates the game status i.e. active or finished.
	 * @param gid
	 * @param active
	 * @return
	 * @throws SQLException
	 */
	public boolean updateGame(int gid, boolean active) throws SQLException {

		preparedStatement = connect.prepareStatement("UPDATE games SET active=?, last_updated=now() WHERE gid=?");
		preparedStatement.setBoolean(1, active);
		preparedStatement.setInt(2, gid);
		int result = preparedStatement.executeUpdate();
		connect.commit();

		if (result > -1)
			return true;
		else
			return false;

	}

	/**
	 * Updates statistics for a given user.
	 * @param uid
	 * @param score
	 * @param wins
	 * @param losses
	 * @return
	 * @throws SQLException
	 */
	public boolean updateStats(int uid, int score, int wins, int losses) throws SQLException {

		preparedStatement = connect.prepareStatement("UPDATE stats SET score=?, wins=?, losses=? WHERE uid=?");
		preparedStatement.setInt(1, score);
		preparedStatement.setInt(2, wins);
		preparedStatement.setInt(3, losses);
		preparedStatement.setInt(4, uid);
		int result = preparedStatement.executeUpdate();
		connect.commit();
		
		if (result > -1)
			return true;
		else
			return false;

	}

	/**
	 * If showOnline = 0, only show offline users' stats
	 * If showOnline = 1, only show online users' stats
	 * Else, show all users' stats
	 * @param showOnline
	 * @return an ArrayList of (gid, uid1, uid2, last_updated)
	 * @throws SQLException
	 */
	public ArrayList<String[]> showStats(int showOnline) throws SQLException {

		if (showOnline == 0 || showOnline == 1) {
			preparedStatement = connect.prepareStatement("SELECT * FROM stats JOIN users ON "
					+ "stats.uid = users.uid WHERE users.online=?");
			preparedStatement.setBoolean(1, showOnline != 0);
		} else {
			preparedStatement = connect.prepareStatement("SELECT * FROM stats");
		}
		ResultSet resultSet = preparedStatement.executeQuery();

		ArrayList<String[]> result = new ArrayList<>();
		while (resultSet.next()) {
			String[] row = new String[5];
			row[0] = Integer.toString(resultSet.getInt("uid"));
			row[1] = resultSet.getString("username");
			row[2] = Integer.toString(resultSet.getInt("score"));
			row[3] = Integer.toString(resultSet.getInt("wins"));
			row[4] = Integer.toString(resultSet.getInt("losses"));
			result.add(row);
		}

		return result;

	}

	/**
	 * Returns a SHA1-encrypted version of the input string.
	 * @param password
	 * @return SHA1 hashed string of the input
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	private static String SHA1(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {

		byte[] bytesOfMessage = password.getBytes("UTF-8");

		MessageDigest md = MessageDigest.getInstance("SHA");
		byte[] thedigest = md.digest(bytesOfMessage);

		BigInteger bi = new BigInteger(1, thedigest);

		return String.format("%0" + (thedigest.length << 1) + "X", bi);


	}

}
