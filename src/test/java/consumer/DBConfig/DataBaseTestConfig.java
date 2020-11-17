package consumer.DBConfig;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DataBaseTestConfig {
	private static final Logger logger = LogManager.getLogger("DataBaseConfig");
	private static final String PROPERTIES_FILE = "src/main/resources/dao.properties";
	private static final String PROPERTY_URL = "url.for.tests";
	private static final String PROPERTY_DRIVER = "driver";
	private static final String PROPERTY_USER_NAME = "user.name";
	private static final String PROPERTY_PASSWORD = "password";

	private String url;
	private String username;
	private String password;

	DataBaseTestConfig(String url, String username, String password) {
		this.url = url;
		this.username = username;
		this.password = password;
	}

	public static DataBaseTestConfig getInstance() throws Exception {
		Properties properties = new Properties();
		String url;
		String driver;
		String userName;
		String password;

		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream propertiesFile = classLoader.getResourceAsStream(PROPERTIES_FILE);

		if (propertiesFile == null) {
			throw new Exception(PROPERTIES_FILE + " not found.");
		}

		try {
			properties.load(propertiesFile);
			url = properties.getProperty(PROPERTY_URL);
			driver = properties.getProperty(PROPERTY_DRIVER);
			userName = properties.getProperty(PROPERTY_USER_NAME);
			password = properties.getProperty(PROPERTY_PASSWORD);
		} catch (IOException e) {
			throw new Exception("Impossible to charge properties file " + PROPERTIES_FILE, e);
		}

		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			throw new Exception("Driver is not found in classpath.", e);
		}

		DataBaseTestConfig instance = new DataBaseTestConfig(url, userName, password);
		return instance;
	}

	public Connection getConnection() throws SQLException {
		logger.info("Create DB connection");
		return DriverManager.getConnection(url, username, password);
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

	public DataBasePrepareService getDataBasePrepareService() {
		return new DataBasePrepareService(this);
	}

//	public UserAccountDAO getUserAccountDAO() {
//		return new UserAccountDAO(this);
//	}
//
//	public CreditBankDetailsDAO getCreditBankDetailsDAO() {
//		return new CreditBankDetailsDAO(this);
//	}
//
//	public DebitBankDetailsDAO getDeditBankDetailsDAO() {
//		return new DebitBankDetailsDAO(this);
//	}
//
//	public PaymentTransactionDAO getPaymentTransactionDAO() {
//		return new PaymentTransactionDAO(this);
//	}
//
//	public TransferTransactionDAO getTransferTransactionDAO() {
//		return new TransferTransactionDAO(this);
//	}
}
