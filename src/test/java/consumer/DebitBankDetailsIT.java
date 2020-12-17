package consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import fr.asterox.PayMyBuddy.PayMyBuddyApplication;
import fr.asterox.PayMyBuddy.consumer.IDebitBankDetailsRepository;
import fr.asterox.PayMyBuddy.consumer.IUserAccountRepository;
import fr.asterox.PayMyBuddy.model.DebitBankDetails;
import fr.asterox.PayMyBuddy.model.UserAccount;

@SpringBootTest(classes = PayMyBuddyApplication.class)
@RunWith(SpringRunner.class)
public class DebitBankDetailsIT {

	@Autowired
	IDebitBankDetailsRepository debitBankDetailsRepository;

	@Autowired
	IUserAccountRepository userAccountRepository;

	UserAccount createdUserAccount;
	Long userId;

	@BeforeEach
	private void setUpPerTest() throws Exception {
		userAccountRepository.deleteAll();
		debitBankDetailsRepository.deleteAll();

		UserAccount userAccount = new UserAccount("email1", "nickname1", "password1", 0, new ArrayList<>(),
				new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
		createdUserAccount = userAccountRepository.save(userAccount);
		userId = createdUserAccount.getUserId();
	}

	@Test
	public void givenDebitBankDetails_whenCreateDebitBankDetails_thenReturnAssociatedUserAccount() throws Exception {
		// GIVEN
		DebitBankDetails debitBankDetails = new DebitBankDetails(createdUserAccount, "holdername1", 12345678, 0120,
				222);

		// WHEN
		DebitBankDetails createdDebitBankDetails = debitBankDetailsRepository.save(debitBankDetails);

		// THEN
		Long debitId = createdDebitBankDetails.getDebitId();
		Optional<DebitBankDetails> result = debitBankDetailsRepository.findById(debitId);
		assertEquals("email1", result.get().getUser().getEmail());
		assertEquals("holdername1", result.get().getHolderName());
		assertEquals(12345678, result.get().getCardNumber());
		assertEquals(0120, result.get().getExpirationDate());
		assertEquals(222, result.get().getCvv());
	}

	@Test
	public void givenADebitBankDetails_whenDeleteThisDebitBankDetails_thenReturnNoDebitBankDetails() throws Exception {
		// GIVEN
		DebitBankDetails debitBankDetails = new DebitBankDetails(createdUserAccount, "holdername1", 12345678, 0120,
				222);
		DebitBankDetails createdDebitBankDetails = debitBankDetailsRepository.save(debitBankDetails);
		Long debitId = createdDebitBankDetails.getDebitId();

		// WHEN
		debitBankDetailsRepository.deleteById(debitId);

		// THEN
		Long accountsNb = debitBankDetailsRepository.count();
		assertEquals(0L, accountsNb);
		assertEquals(new ArrayList<>(), createdUserAccount.getDebitBankDetailsList());
	}
}
