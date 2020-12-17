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
import fr.asterox.PayMyBuddy.consumer.ICreditBankDetailsRepository;
import fr.asterox.PayMyBuddy.consumer.IUserAccountRepository;
import fr.asterox.PayMyBuddy.model.CreditBankDetails;
import fr.asterox.PayMyBuddy.model.UserAccount;

@SpringBootTest(classes = PayMyBuddyApplication.class)
@RunWith(SpringRunner.class)
public class CreditBankDetailsIT {

	@Autowired
	ICreditBankDetailsRepository creditBankDetailsRepository;

	@Autowired
	IUserAccountRepository userAccountRepository;

	UserAccount createdUserAccount;
	Long userId;

	@BeforeEach
	private void setUpPerTest() throws Exception {
		userAccountRepository.deleteAll();
		creditBankDetailsRepository.deleteAll();

		UserAccount userAccount = new UserAccount("email1", "nickname1", "password1", 0, new ArrayList<>(),
				new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
		createdUserAccount = userAccountRepository.save(userAccount);
		userId = createdUserAccount.getUserId();
	}

	@Test
	public void givenCreditBankDetails_whenCreateCreditBankDetails_thenReturnAssociatedUserAccount() throws Exception {
		// GIVEN
		CreditBankDetails creditBankDetails = new CreditBankDetails(createdUserAccount, "holdername1", "iban1", "bic1");

		// WHEN
		CreditBankDetails createdCreditBankDetails = creditBankDetailsRepository.save(creditBankDetails);

		// THEN
		Long creditId = createdCreditBankDetails.getCreditId();
		Optional<CreditBankDetails> result = creditBankDetailsRepository.findById(creditId);
		assertEquals("email1", result.get().getUser().getEmail());
		assertEquals("holdername1", result.get().getHolderName());
		assertEquals("iban1", result.get().getIban());
		assertEquals("bic1", result.get().getBic());
	}

	@Test
	public void givenACreditBankDetails_whenDeleteThisCreditBankDetails_thenReturnNoCreditBankDetails()
			throws Exception {
		// GIVEN
		CreditBankDetails creditBankDetails = new CreditBankDetails(createdUserAccount, "holdername1", "iban1", "bic1");
		CreditBankDetails createdCreditBankDetails = creditBankDetailsRepository.save(creditBankDetails);
		Long creditId = createdCreditBankDetails.getCreditId();

		// WHEN
		creditBankDetailsRepository.deleteById(creditId);

		// THEN
		Long creditBanksNb = creditBankDetailsRepository.count();
		assertEquals(0L, creditBanksNb);
		assertEquals(new ArrayList<>(), createdUserAccount.getCreditBankDetailsList());
	}
}
