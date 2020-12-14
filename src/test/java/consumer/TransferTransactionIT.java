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
import fr.asterox.PayMyBuddy.consumer.IDebitBankDetailsRepository;
import fr.asterox.PayMyBuddy.consumer.ITransferTransactionRepository;
import fr.asterox.PayMyBuddy.consumer.IUserAccountRepository;
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
	Long userId;

	@BeforeEach
	private void setUpPerTest() throws Exception {
		userAccountRepository.deleteAll();
		creditBankDetailsRepository.deleteAll();
		debitBankDetailsRepository.deleteAll();
		transferTransactionRepository.deleteAll();

		UserAccount user = new UserAccount("email1", "nickname1", "password1", 50, new ArrayList<>(), new ArrayList<>(),
				new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
		userAccount = userAccountRepository.save(user);
		userId = userAccount.getUserId();
	}

	@Test
	public void givenAUserAccount_whenTransferToCreditBank_thenReturnUpdatedBalanceAndNewTransaction()
			throws Exception {

		// WHEN
		Optional<TransferTransaction> result = transferTransactionService.transferToCreditBank(userAccount, 25);

		// THEN
		assertEquals(25, userAccountRepository.findById(userId).get().getApplicationBalance());

		Long transferId = result.get().getTransferId();
		Optional<TransferTransaction> createdTransferTransaction = transferTransactionRepository.findById(transferId);

		assertEquals(25, createdTransferTransaction.get().getAmount());
		assertEquals("email1", createdTransferTransaction.get().getUser().getEmail());
		assertEquals(25, userAccount.getApplicationBalance());
	}

	@Test
	public void givenAUserAccount_whenTransferFromDebitBank_thenReturnUpdatedBalanceAndNewTransaction()
			throws Exception {

		// WHEN
		Optional<TransferTransaction> result = transferTransactionService.transferFromDebitBank(userAccount, 25);

		// THEN
		assertEquals(75, userAccountRepository.findById(userId).get().getApplicationBalance());

		Long transferId = result.get().getTransferId();
		Optional<TransferTransaction> createdTransferTransaction = transferTransactionRepository.findById(transferId);

		assertEquals(25, createdTransferTransaction.get().getAmount());
		assertEquals("email1", createdTransferTransaction.get().getUser().getEmail());
		assertEquals(75, userAccount.getApplicationBalance());
	}

}
