package service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import fr.asterox.PayMyBuddy.PayMyBuddyApplication;
import fr.asterox.PayMyBuddy.consumer.IUserAccountRepository;
import fr.asterox.PayMyBuddy.model.UserAccount;
import fr.asterox.PayMyBuddy.service.UserAccountService;

@SpringBootTest(classes = PayMyBuddyApplication.class)
@ExtendWith(MockitoExtension.class)
public class UserAccountServiceTest {

	@Autowired
	private UserAccountService userAccountService;

	@MockBean
	private IUserAccountRepository userAccountRepository;

	UserAccount userAccount1;
	UserAccount userAccount2;

	@BeforeEach
	private void setUpPerTest() throws Exception {
		userAccount1 = new UserAccount("email1", "nickname1", "password1", 1000, new ArrayList<>(), new ArrayList<>(),
				new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
		List<UserAccount> friendsList = new ArrayList<>();
		friendsList.add(userAccount1);
		userAccount2 = new UserAccount("email2", "nickname2", "password2", 2000, new ArrayList<>(), new ArrayList<>(),
				new ArrayList<>(), friendsList, new ArrayList<>(), new ArrayList<>());
	}

	@Test
	public void givenAnEmail_whenFindByEmail_thenReturnCorrespondingUserAccount() {
		// GIVEN
		when(userAccountRepository.findByEmail("email1")).thenReturn(userAccount1);

		// WHEN
		UserAccount result = userAccountService.findByEmail("email1");

		// THEN
		assertEquals("email1", result.getEmail());
		assertEquals("nickname1", result.getNickname());
		assertEquals("password1", result.getPassword());
		assertEquals(1000, result.getApplicationBalance());
		assertEquals(new ArrayList<>(), result.getFriendsList());
	}

	@Test
	public void givenAUserAccountWithExistingRelationship_whenAddFriend_thenReturnOptionalEmpty() {
		// GIVEN
		when(userAccountRepository.findByEmail("email1")).thenReturn(userAccount1);
		when(userAccountRepository.findByEmail("email2")).thenReturn(userAccount2);

		// WHEN
		Optional<UserAccount> result = userAccountService.addFriend(userAccount2, "email1");

		// THEN
		assertEquals(Optional.empty(), result);
	}

	@Test
	public void givenAUserAccount_whenAddFriend_thenReturnListWithTheRelationship() {
		// GIVEN
		when(userAccountRepository.findByEmail("email1")).thenReturn(userAccount1);
		when(userAccountRepository.findByEmail("email2")).thenReturn(userAccount2);
		when(userAccountRepository.save(userAccount1)).thenReturn(userAccount1);

		// WHEN
		Optional<UserAccount> result = userAccountService.addFriend(userAccount1, "email2");

		// THEN
		assertEquals("email1", result.get().getEmail());
		assertEquals("email2", result.get().getFriendsList().get(0).getEmail());
	}

	@Test
	public void givenAUserAccountWithOneRelatonship_whenAddFriend_thenReturnListWithTwoRelationships() {
		// GIVEN
		UserAccount userAccount3 = new UserAccount("email3", "nickname3", "password3", 2000, new ArrayList<>(),
				new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
		when(userAccountRepository.findByEmail("email2")).thenReturn(userAccount2);
		when(userAccountRepository.findByEmail("email3")).thenReturn(userAccount3);
		when(userAccountRepository.save(userAccount2)).thenReturn(userAccount2);

		// WHEN
		Optional<UserAccount> result = userAccountService.addFriend(userAccount2, "email3");

		// THEN
		assertEquals("email2", result.get().getEmail());
		assertEquals(2, result.get().getFriendsList().size());
		assertEquals("email1", result.get().getFriendsList().get(0).getEmail());
		assertEquals("email3", result.get().getFriendsList().get(1).getEmail());
		assertEquals(0, result.get().getFriendsList().get(1).getFriendsList().size());
	}

	@Test
	public void givenAUserAccountWithExistingRelationship_whenDeleteFriend_thenReturnUpdatedUserAccount() {
		// GIVEN
		when(userAccountRepository.findByEmail("email1")).thenReturn(userAccount1);
		when(userAccountRepository.findByEmail("email2")).thenReturn(userAccount2);
		when(userAccountRepository.save(userAccount2)).thenReturn(userAccount2);

		// WHEN
		Optional<UserAccount> result = userAccountService.deleteFriend(userAccount2, "email1");

		// THEN
		assertEquals("email2", result.get().getEmail());
		assertEquals(0, result.get().getFriendsList().size());
	}

	@Test
	public void givenAUserAccountWithNoRelationship_whenDeleteFriend_thenReturnOptionalEmpty() {
		// GIVEN
		when(userAccountRepository.findByEmail("email1")).thenReturn(userAccount1);
		when(userAccountRepository.findByEmail("email2")).thenReturn(userAccount2);

		// WHEN
		Optional<UserAccount> result = userAccountService.deleteFriend(userAccount1, "email2");

		// THEN
		assertEquals(Optional.empty(), result);
	}

}
