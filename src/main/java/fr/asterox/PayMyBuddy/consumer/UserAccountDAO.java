package fr.asterox.PayMyBuddy.consumer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import fr.asterox.PayMyBuddy.model.UserAccount;

@Repository
public class UserAccountDAO implements IUserAccountDAO {

	private static final String SQL_SELECT_BY_EMAIL = "SELECT USER_ID, EMAIL, PASSWORD, APPLICATION_BALANCE FROM user_account WHERE EMAIL = ?";
	private static final String SQL_CREATE_USER_ACCOUNT = "INSERT INTO user_account (EMAIL, PASSWORD, APPLICATION_BALANCE) VALUES(?,?,0)";

	private static final Logger LOGGER = LogManager.getLogger(UserAccountDAO.class);
	private DataBaseConfig dataBaseConfig;

	UserAccountDAO(DataBaseConfig dataBaseConfig) {
		this.dataBaseConfig = dataBaseConfig;
	}

	@Override
	public void createUserAccount(UserAccount userAccount) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = dataBaseConfig.getConnection();
			ps = DataBaseConfig.PreparedRequestInitialization(con, SQL_CREATE_USER_ACCOUNT, true,
					userAccount.getEmail(), userAccount.getPassword(), userAccount.getApplicationBalance());
			int status = ps.executeUpdate();
			if (status == 0) {
				throw new Exception("Error creating user account, no line added");
			}
			rs = ps.getGeneratedKeys();
			if (rs.next()) {
				userAccount.setUserID(rs.getLong(1));
				LOGGER.info("Creating user account.");
			} else {
				throw new Exception("Error creating user account, no generated ID returned.");
			}
		} catch (SQLException e) {
			throw new Exception(e);
		} finally {
			dataBaseConfig.closeResultSet(rs);
			dataBaseConfig.closePreparedStatement(ps);
			dataBaseConfig.closeConnection(con);
		}
	}

	@Override
	public void updateUserAccount(UserAccount userAcount) throws Exception {
		// TODO Auto-generated method stub GET BY ID

	}

	@Override
	public UserAccount findUserAccountByEmail(String email) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		UserAccount userAccount = null;

		try {
			con = dataBaseConfig.getConnection();
			ps = DataBaseConfig.PreparedRequestInitialization(con, SQL_SELECT_BY_EMAIL, false, email);
			// executeQuery() pour un SELECT
			rs = ps.executeQuery();
			if (rs.next()) {
				userAccount = map(rs);
			}
		} catch (SQLException e) {
			throw new Exception(e);
		} finally {
			dataBaseConfig.closeResultSet(rs);
			dataBaseConfig.closePreparedStatement(ps);
			dataBaseConfig.closeConnection(con);
		}
		return userAccount;
	}

	private static UserAccount map(ResultSet resultSet) throws SQLException {
		UserAccount userAccount = new UserAccount();
		userAccount.setUserID(resultSet.getLong(1));
		userAccount.setEmail(resultSet.getString(2));
		userAccount.setPassword(resultSet.getString(3));
		userAccount.setApplicationBalance(resultSet.getDouble(4));
		return userAccount;
	}

	@Override
	public void deleteUserAccountByEmail(String email) throws Exception {
		// TODO Auto-generated method stub

	}
}
