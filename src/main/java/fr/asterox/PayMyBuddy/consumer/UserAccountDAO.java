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

	private static final Logger LOGGER = LogManager.getLogger(UserAccountDAO.class);
	private DataBaseConfig dataBaseConfig;

	UserAccountDAO(DataBaseConfig dataBaseConfig) {
		this.dataBaseConfig = dataBaseConfig;
	}

	@Override
	public void createUserAccount(UserAccount userAcount) throws Exception {
		// TODO Auto-generated method stub

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
		// Inutile de faire circuler le mdp ?
		userAccount.setPassword(resultSet.getString(3));
		userAccount.setApplicationBalance(resultSet.getDouble(4));
		return userAccount;
	}
}
