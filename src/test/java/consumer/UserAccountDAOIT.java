package consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import consumer.DBConfig.DataBasePrepareService;
import consumer.DBConfig.DataBaseTestConfig;
import fr.asterox.PayMyBuddy.consumer.UserAccountDAO;
import fr.asterox.PayMyBuddy.model.UserAccount;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserAccountDAOIT {

	@Autowired
	private UserAccountDAO userAccountDAO;

	private static DataBasePrepareService dataBasePrepareService;
	private DataBaseTestConfig dataBaseTestConfig;

	@BeforeAll
	private static void setUp() throws Exception {
//		userAccountDAO.dataBaseConfig = dataBaseTestConfig;
//		dataBasePrepareService = new DataBasePrepareService(dataBaseTestConfig);
	}

	@BeforeEach
	private void setUpPerTest() throws Exception {
		dataBasePrepareService.clearDataBaseEntries();
	}

	@AfterEach
	private void closeResources() {

	}

	@Test
	public void givenAUser_whenCreateUserAccount_thenReturnTheUserAccount() throws Exception {
		// GIVEN
		UserAccount userAccount = new UserAccount(1L, "emailTest", "password", 25, null, null, null, null, null);

		// WHEN
		userAccountDAO.createUserAccount(userAccount);

		// THEN
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		final String SQL_GET_USER_ACCOUNT = "SELECT USER_ID, EMAIL, PASSWORD, APPLICATION_BALANCE FROM user_account WHERE EMAIL = emailTest";

		try {
			con = dataBaseTestConfig.getConnection();
			ps = DataBaseTestConfig.PreparedRequestInitialization(con, SQL_GET_USER_ACCOUNT, false);
			rs = ps.executeQuery();
			assertEquals(1, rs.getLong(1));
			assertEquals("emailTest", rs.getString(2));
			assertEquals("password", rs.getString(3));
			assertEquals(25, rs.getDouble(4));
		} catch (SQLException e) {
			throw new Exception(e);
		} finally {
			dataBaseTestConfig.closeResultSet(rs);
			dataBaseTestConfig.closePreparedStatement(ps);
			dataBaseTestConfig.closeConnection(con);
		}

	}

	@Test
	public void givenAUserInDB_whenFindThisPersonByTheEmail_thenReturnTheUserAccount() throws Exception {
		// GIVEN
		Connection con = null;
		PreparedStatement ps = null;
		final String SQL_INSERT_USER_ACCOUNT = "INSERT INTO user_account VALUES(1,emailTest,password,25)";

		try {
			con = dataBaseTestConfig.getConnection();
			ps = DataBaseTestConfig.PreparedRequestInitialization(con, SQL_INSERT_USER_ACCOUNT, false);
		} catch (SQLException e) {
			throw new Exception(e);
		} finally {
			dataBaseTestConfig.closePreparedStatement(ps);
			dataBaseTestConfig.closeConnection(con);
		}
		// WHEN
		UserAccount result = userAccountDAO.findUserAccountByEmail("emailTest");

		// THEN
		assertEquals(1, result.getUserID());
		assertEquals("emailTest", result.getEmail());
		assertEquals("password", result.getPassword());
		assertEquals(25, result.getApplicationBalance());

	}

}
