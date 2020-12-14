package consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
//@Sql("setup.sql")
public class UserAccountIT {

	@Autowired
	IUserAccountRepository userAccountRepository;

	@Autowired
	UserAccountService userAccountService;

	UserAccount createdUserAccount;
	Long id;

	@BeforeEach
	private void setUpPerTest() throws Exception {
		userAccountRepository.deleteAll();

		UserAccount userAccount = new UserAccount("email1", "nickname1", "password1", 0, new ArrayList<>(),
				new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
		createdUserAccount = userAccountRepository.save(userAccount);
		id = createdUserAccount.getUserId();
	}

	@Test
	public void givenAUserAccount_whenCreateUserAccount_thenReturnTheUserAccountAtNextId() throws Exception {
		// GIVEN
		UserAccount userAccount = new UserAccount("email2", "nickname2", "password2", 10, new ArrayList<>(),
				new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

		// WHEN
		UserAccount newUserAccount = userAccountRepository.save(userAccount);

		// THEN
		assertEquals(id + 1, newUserAccount.getUserId());
		List<UserAccount> userAccountsList = userAccountRepository.findAll();
		assertEquals(2, userAccountsList.size());
		assertEquals("email1", userAccountsList.get(0).getEmail());
		assertEquals("email2", userAccountsList.get(1).getEmail());
		assertEquals("nickname2", userAccountsList.get(1).getNickname());
		assertEquals("password2", userAccountsList.get(1).getPassword());
		assertEquals(10, userAccountsList.get(1).getApplicationBalance());
	}

	@Test
	public void givenAnUpdatedUserAccount_whenUpdateThisUserAccount_thenReturnUpdatedUserAccount() throws Exception {
		// GIVEN
		UserAccount updatedUserAccount = new UserAccount(id, "email2", "nickname2", "password2", 10, new ArrayList<>(),
				new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

		// WHEN
		UserAccount resultForId = userAccountRepository.save(updatedUserAccount);

		// THEN
		assertEquals(id, resultForId.getUserId());
		List<UserAccount> userAccountsList = userAccountRepository.findAll();
		assertEquals(1, userAccountsList.size());
		assertEquals("email2", userAccountsList.get(0).getEmail());
		assertEquals("nickname2", userAccountsList.get(0).getNickname());
		assertEquals("password2", userAccountsList.get(0).getPassword());
		assertEquals(10, userAccountsList.get(0).getApplicationBalance());
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

	@Test
	public void givenAUserAccountInDB_whenSaveAnotherUserAccountWithRelationShip_thenReturnAFriendsList()
			throws Exception {
		// GIVEN
		UserAccount friend = new UserAccount("email2", "nickname2", "password2", 0, new ArrayList<>(),
				new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
		UserAccount createdFriend = userAccountRepository.save(friend);

		// WHEN
		Optional<UserAccount> createdUserWithFriend = userAccountService.addFriend(createdUserAccount, createdFriend);

		// THEN
		Long userId = createdFriend.getUserId();
		Optional<UserAccount> resultFriend = userAccountRepository.findById(userId);
		assertEquals(new ArrayList<>(), resultFriend.get().getFriendsList());

		Optional<UserAccount> resultInOriginalUserAccount = userAccountRepository.findById(id);
		assertEquals("email2", resultInOriginalUserAccount.get().getFriendsList().get(0).getEmail());

		assertEquals("email2", createdUserWithFriend.get().getFriendsList().get(0).getEmail());
	}

	@Test
	public void givenAUserAccountInDBWithExistingRelationship_whenSaveSameRelationship_thenReturnEmptyOptional()
			throws Exception {
		// GIVEN
		List<UserAccount> friendsList = new ArrayList<>();
		friendsList.add(createdUserAccount);
		UserAccount friend = new UserAccount("email2", "nickname2", "password2", 0, new ArrayList<>(),
				new ArrayList<>(), new ArrayList<>(), friendsList, new ArrayList<>(), new ArrayList<>());
		UserAccount savedFriend = userAccountRepository.save(friend);

		// WHEN
		Optional<UserAccount> friendWithRelationship = userAccountService.addFriend(savedFriend, createdUserAccount);

		// THEN
		assertEquals(Optional.empty(), friendWithRelationship);
	}

	@Test
	public void givenAUserAccountInDBWithRelationship_whenDeleteRelationShip_thenReturnEmptyFriendsList()
			throws Exception {
		// GIVEN
		List<UserAccount> friendsList = new ArrayList<>();
		friendsList.add(createdUserAccount);
		UserAccount friend = new UserAccount("email2", "nickname2", "password2", 0, new ArrayList<>(),
				new ArrayList<>(), new ArrayList<>(), friendsList, new ArrayList<>(), new ArrayList<>());
		UserAccount savedFriend = userAccountRepository.save(friend);

		// WHEN
		Optional<UserAccount> friendWithNoRelationship = userAccountService.deleteFriend(savedFriend,
				createdUserAccount);

		// THEN
		assertEquals(new ArrayList<>(), friendWithNoRelationship.get().getFriendsList());
	}

	@Test
	public void givenAUserAccountInDBWithNoRelationship_whenDeleteRelationShip_thenReturnEmptyOptional()
			throws Exception {
		// GIVEN
		UserAccount friend = new UserAccount("email2", "nickname2", "password2", 0, new ArrayList<>(),
				new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
		UserAccount savedFriend = userAccountRepository.save(friend);

		// WHEN
		Optional<UserAccount> userWithNoRelationship = userAccountService.deleteFriend(createdUserAccount, savedFriend);

		// THEN
		assertEquals(Optional.empty(), userWithNoRelationship);
	}

}
