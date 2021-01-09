package consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

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
import fr.asterox.PayMyBuddy.consumer.ICreditBankDetailsRepository;
import fr.asterox.PayMyBuddy.consumer.IDebitBankDetailsRepository;
import fr.asterox.PayMyBuddy.consumer.ITransferTransactionRepository;
import fr.asterox.PayMyBuddy.consumer.IUserAccountRepository;
import fr.asterox.PayMyBuddy.model.CreditBankDetails;
import fr.asterox.PayMyBuddy.model.DebitBankDetails;
import fr.asterox.PayMyBuddy.model.TransferTransaction;
import fr.asterox.PayMyBuddy.model.UserAccount;
import fr.asterox.PayMyBuddy.service.TransferTransactionService;

@SpringBootTest(classes = PayMyBuddyApplication.class)
@RunWith(SpringRunner.class)
public class TransferTransactionIT {

	@Autowired
	TransferTransactionService transferTransactionService;

	@Autowired
	ITransferTransactionRepository transferTransactionRepository;

	@Autowired
	IUserAccountRepository userAccountRepository;

	@Autowired
	ICreditBankDetailsRepository creditBankDetailsRepository;

	@Autowired
	IDebitBankDetailsRepository debitBankDetailsRepository;

	UserAccount userAccount;
	CreditBankDetails creditBankDetails;
	DebitBankDetails debitBankDetails;
	Long userId;

	@BeforeEach
	private void setUpPerTest() throws Exception {
		userAccountRepository.deleteAll();
		creditBankDetailsRepository.deleteAll();
		debitBankDetailsRepository.deleteAll();
		transferTransactionRepository.deleteAll();

		UserAccount user = new UserAccount("email1", "nickname1", "password1", 5000, new ArrayList<>(),
				new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
		creditBankDetails = new CreditBankDetails(user, "holdername1", "iban1", "bic1");
		List<CreditBankDetails> creditBankDetailsList = new ArrayList<>();
		creditBankDetailsList.add(creditBankDetails);
		debitBankDetails = new DebitBankDetails(user, "holdername1", 12345678, 1020);
		List<DebitBankDetails> debitBankDetailsList = new ArrayList<>();
		debitBankDetailsList.add(debitBankDetails);
		user.setCreditBankDetailsList(creditBankDetailsList);
		user.setDebitBankDetailsList(debitBankDetailsList);

		userAccount = userAccountRepository.save(user);
		userId = userAccount.getUserId();
	}

	@Test
	public void givenAUserAccount_whenTransferToCreditBank_thenReturnUpdatedBalanceAndNewTransaction()
			throws Exception {

		// WHEN
		Optional<TransferTransaction> result = transferTransactionService.transferToCreditBank(userAccount, 25);

		// THEN
		Long transferId = result.get().getTransferId();
		Optional<TransferTransaction> createdTransferTransaction = transferTransactionRepository.findById(transferId);

		assertEquals(25, createdTransferTransaction.get().getAmount());
		assertEquals("email1", createdTransferTransaction.get().getUser().getEmail());
		assertEquals(Math.round(5000 - 25 * 1.005 * 100),
				createdTransferTransaction.get().getUser().getApplicationBalance());
		assertNull(createdTransferTransaction.get().getDebitBankDetails());
		assertEquals("iban1", createdTransferTransaction.get().getCreditBankDetails().getIban());

	}

	@Test
	public void givenAUserAccount_whenTransferFromDebitBank_thenReturnUpdatedBalanceAndNewTransaction()
			throws Exception {

		// WHEN
		Optional<TransferTransaction> result = transferTransactionService.transferFromDebitBank(userAccount, 25);

		// THEN
		Long transferId = result.get().getTransferId();
		Optional<TransferTransaction> createdTransferTransaction = transferTransactionRepository.findById(transferId);

		assertEquals(25, createdTransferTransaction.get().getAmount());
		assertEquals("email1", createdTransferTransaction.get().getUser().getEmail());
		assertEquals(7500, createdTransferTransaction.get().getUser().getApplicationBalance());
		assertNull(createdTransferTransaction.get().getCreditBankDetails());
		assertEquals(1020, createdTransferTransaction.get().getDebitBankDetails().getExpirationDate());
	}

}
