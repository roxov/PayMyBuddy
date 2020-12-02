package consumer.DBConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class DataBaseTestConfig {
	private static final Logger logger = LogManager.getLogger("DataBaseTestConfig");

	public Connection getConnection(String driver, String url, String username, String password)
			throws SQLException, ClassNotFoundException {
		logger.info("Create DB Test connection");
		Class.forName(driver);
		return DriverManager.getConnection(url, username, password);
	}

	public void clearDataBaseEntries(Connection con) {
		String SQL_CLEAR_TABLES = "TRUNCATE table user_account, credit_bank_details, debit_bank_details, friends_network, payment_transaction, transfer_transaction";
		String SQL_CREATE_USERACCOUNT = "INSERT INTO user_account VALUES(1,'email1','nickname1','password1',25)";

		Connection connection = null;
		PreparedStatement ps = null;
		try {
			ps = this.PreparedRequestInitialization(con, SQL_CLEAR_TABLES, false);
			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.closePreparedStatement(ps);
			this.closeConnection(connection);

		}
	}

	public static PreparedStatement PreparedRequestInitialization(Connection con, String sql,
			boolean returnGeneratedKeys, Object... objets) throws SQLException {
		PreparedStatement preparedStatement = con.prepareStatement(sql,
				returnGeneratedKeys ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS);
		for (int i = 0; i < objets.length; i++) {
			preparedStatement.setObject(i + 1, objets[i]);
		}
		return preparedStatement;
	}

	public void closeConnection(Connection con) {
		if (con != null) {
			try {
				con.close();
				logger.info("Closing DB connection");
			} catch (SQLException e) {
				logger.error("Error while closing connection", e);
			}
		}
	}

	public void closePreparedStatement(PreparedStatement ps) {
		if (ps != null) {
			try {
				ps.close();
				logger.info("Closing Prepared Statement");
			} catch (SQLException e) {
				logger.error("Error while closing prepared statement", e);
			}
		}
	}

	public void closeResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
				logger.info("Closing Result Set");
			} catch (SQLException e) {
				logger.error("Error while closing result set", e);
			}
		}
	}

}
