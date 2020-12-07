package consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import fr.asterox.PayMyBuddy.PayMyBuddyApplication;
import fr.asterox.PayMyBuddy.consumer.IUserAccountRepository;
import fr.asterox.PayMyBuddy.model.UserAccount;
import fr.asterox.PayMyBuddy.service.UserAccountService;

@SpringBootTest(classes = PayMyBuddyApplication.class)
@RunWith(SpringRunner.class)
public class UserAccountIT {

	@Autowired
	IUserAccountRepository userAccountRepository;

	@Autowired
	UserAccountService userAccountService;

	Long id;

	@BeforeEach
	private void setUpPerTest() throws Exception {
		userAccountRepository.deleteAll();

		UserAccount userAccount = new UserAccount("email1", "nickname1", "password1", 0, new ArrayList<>(),
				new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
		UserAccount createdUserAccount = userAccountRepository.save(userAccount);
		id = createdUserAccount.getUserId();
	}

	@Test
	public void givenAUserAccount_whenCreateUserAccount_thenReturnTheUserAccountAtNextId() throws Exception {
		// GIVEN
		UserAccount userAccount = new UserAccount("email2", "nickname2", "password2", 10, new ArrayList<>(),
				new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

		// WHEN
		UserAccount createdUserAccount = userAccountRepository.save(userAccount);

		// THEN
		assertEquals(id + 1, createdUserAccount.getUserId());
		assertEquals("email2", createdUserAccount.getEmail());
		assertEquals("nickname2", createdUserAccount.getNickname());
		assertEquals("password2", createdUserAccount.getPassword());
		assertEquals(10, createdUserAccount.getApplicationBalance());
	}

	@Test
	public void givenAnUpdatedUserAccount_whenUpdateThisUserAccount_thenReturnUpdatedUserAccount() throws Exception {
		// GIVEN
		UserAccount updatedUserAccount = new UserAccount(id, "email2", "nickname2", "password2", 10, new ArrayList<>(),
				new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

		// WHEN
		UserAccount result = userAccountRepository.save(updatedUserAccount);

		// THEN
		assertEquals(id, result.getUserId());
		assertEquals("email2", result.getEmail());
		assertEquals("nickname2", result.getNickname());
		assertEquals("password2", result.getPassword());
		assertEquals(10, result.getApplicationBalance());
	}

	@Test
	public void givenAUserAccountInDB_whenFindThisAccountByTheEmail_thenReturnTheUserAccount() throws Exception {
		// WHEN
		UserAccount result = userAccountService.findByEmail("email1");

		// THEN
		assertEquals(id, result.getUserId());
		assertEquals("email1", result.getEmail());
		assertEquals("nickname1", result.getNickname());
		assertEquals("password1", result.getPassword());
		assertEquals(0, result.getApplicationBalance());
	}

	@Test
	public void givenAUserAccountInDB_whenDeleteThisAccountById_thenReturnNoUserAccountInDB() throws Exception {
		// WHEN
		userAccountRepository.deleteById(id);

		// THEN
		Long accountsNb = userAccountRepository.count();
		assertEquals(0L, accountsNb);
	}
}
