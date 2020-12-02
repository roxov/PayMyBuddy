package consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import consumer.DBConfig.DataBaseTestConfig;
import fr.asterox.PayMyBuddy.PayMyBuddyApplication;
import fr.asterox.PayMyBuddy.consumer.IUserAccountRepository;
import fr.asterox.PayMyBuddy.model.UserAccount;
import fr.asterox.PayMyBuddy.service.UserAccountService;

@SpringBootTest(classes = { PayMyBuddyApplication.class, DataBaseTestConfig.class })
@RunWith(SpringRunner.class)
public class UserAccountIT {

	@Autowired
	IUserAccountRepository userAccountRepository;

	@Autowired
	UserAccountService userAccountService;

	@Autowired
	private DataBaseTestConfig dataBaseTestConfig;

	private Connection con;

	@BeforeEach
	private void setUpPerTest(@Value("${spring.datasource.driver-class-name}") String driver,
			@Value("${spring.datasource.url}") String url, @Value("${spring.datasource.username}") String username,
			@Value("${spring.datasource.password}") String password) throws Exception {
		con = dataBaseTestConfig.getConnection(driver, url, username, password);
		dataBaseTestConfig.clearDataBaseEntries(con);
	}

	@AfterEach
	private void closeResources() {
		dataBaseTestConfig.closeConnection(con);
	}

	@Test
	public void givenAUserAccount_whenCreateUserAccount_thenReturnTheUserAccount() throws Exception {
		// GIVEN
		UserAccount userAccount = new UserAccount(1L, "email2", "nickname2", "password2", 35, new ArrayList<>(),
				new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

		// WHEN
		userAccountRepository.save(userAccount);

		// THEN
		PreparedStatement ps = null;
		ResultSet rs = null;
		final String SQL_GET_USER_ACCOUNT = "SELECT USER_ID, EMAIL, PASSWORD, APPLICATION_BALANCE FROM user_account WHERE EMAIL = 'email2'";

		try {
			ps = DataBaseTestConfig.PreparedRequestInitialization(con, SQL_GET_USER_ACCOUNT, true);
			rs = ps.executeQuery();
			assertEquals(2, rs.getLong(1));
			assertEquals("email2", rs.getString(2));
			assertEquals("password2", rs.getString(3));
			assertEquals(35, rs.getDouble(4));
			ResultSet modifiedLines = ps.getGeneratedKeys();
			assertEquals(1, modifiedLines);
		} catch (SQLException e) {
			throw new Exception(e);
		} finally {
			dataBaseTestConfig.closeResultSet(rs);
			dataBaseTestConfig.closePreparedStatement(ps);
		}
	}

	@Test
	public void givenAnUpdatedUserAccount_whenUpdateThisUserAccount_thenReturnUpdatedUserAccount() throws Exception {
		// GIVEN
		UserAccount userAccount = new UserAccount(1L, "email1", "nickname2", "password2", 35, new ArrayList<>(),
				new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

		// WHEN
		userAccountRepository.save(userAccount);

		// THEN
		PreparedStatement ps = null;
		ResultSet rs = null;
		final String SQL_GET_USER_ACCOUNT = "SELECT USER_ID, EMAIL, PASSWORD, APPLICATION_BALANCE FROM user_account WHERE EMAIL = 'email1'";

		try {
			ps = DataBaseTestConfig.PreparedRequestInitialization(con, SQL_GET_USER_ACCOUNT, true);
			rs = ps.executeQuery();
			assertEquals(2, rs.getLong(1));
			assertEquals("email2", rs.getString(2));
			assertEquals("password2", rs.getString(3));
			assertEquals(35, rs.getDouble(4));
			ResultSet modifiedLines = ps.getGeneratedKeys();
			assertEquals(1, modifiedLines);
		} catch (SQLException e) {
			throw new Exception(e);
		} finally {
			dataBaseTestConfig.closeResultSet(rs);
			dataBaseTestConfig.closePreparedStatement(ps);
		}
	}

	@Test
	public void givenAUserAccountInDB_whenFindThisAccountByTheEmail_thenReturnTheUserAccount() throws Exception {
		// GIVEN

		// WHEN
		UserAccount result = userAccountService.findByEmail("email1");

		// THEN
		assertEquals(1, result.getUserId());
		assertEquals("email1", result.getEmail());
		assertEquals("nickname1", result.getNickname());
		assertEquals("password1", result.getPassword());
		assertEquals(25, result.getApplicationBalance());
	}

	@Test
	public void givenAUserAccountInDB_whenDeleteThisAccountById_thenReturnNoUserAccountInDB() throws Exception {
		// WHEN
		userAccountRepository.deleteById(1L);

		// THEN
		Long accountsNb = userAccountRepository.count();
		assertEquals(0L, accountsNb);
	}
}
