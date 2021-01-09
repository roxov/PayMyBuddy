package service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

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
@ExtendWith(MockitoExtension.class)
public class TransferTransactionServiceTest {

	@Autowired
	private TransferTransactionService transferTransactionService;

	@MockBean
	private ITransferTransactionRepository transferTransactionRepository;

	@MockBean
	private IUserAccountRepository userAccountRepository;

	@MockBean
	private ICreditBankDetailsRepository creditBankDetailsRepository;

	@MockBean
	private IDebitBankDetailsRepository debitBankDetailsRepository;

	private UserAccount userAccount;

	@BeforeEach
	private void setUpPerTest() throws Exception {
		userAccount = new UserAccount("email1", "nickname1", "password1", 1000, new ArrayList<>(), new ArrayList<>(),
				new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
	}

	@Test
	public void givenAUserAccount_whenTransferToCreditBank_thenReturnSavedTransaction() {
		// GIVEN
		CreditBankDetails creditBankDetails = new CreditBankDetails(userAccount, "holdername1", "iban1", "bic1");
		TransferTransaction transferTransaction = new TransferTransaction(userAccount, 10, true, creditBankDetails,
				null);
		when(userAccountRepository.findByEmail("email1")).thenReturn(userAccount);
		when(creditBankDetailsRepository.findByUser(userAccount)).thenReturn(creditBankDetails);
		when(userAccountRepository.save(userAccount)).thenReturn(userAccount);
		when(transferTransactionRepository.save(transferTransaction)).thenReturn(transferTransaction);

		// WHEN
		Optional<TransferTransaction> result = transferTransactionService.transferToCreditBank(userAccount, 10);

		// THEN
		assertEquals(Math.round(1000 - (10 * 1.005 * 100)), result.get().getUser().getApplicationBalance());
		assertEquals(10, result.get().getAmount());
		assertNull(result.get().getDebitBankDetails());
		assertEquals("bic1", result.get().getCreditBankDetails().getBic());
	}

	@Test
	public void givenAUserAccount_whenTransferFromDebitBank_thenReturnSavedTransaction() {
		// GIVEN
		DebitBankDetails debitBankDetails = new DebitBankDetails(userAccount, "holdername1", 1234, 1221);
		TransferTransaction transferTransaction = new TransferTransaction(userAccount, 10, false, null,
				debitBankDetails);
		when(userAccountRepository.findByEmail("email1")).thenReturn(userAccount);
		when(debitBankDetailsRepository.findByUser(userAccount)).thenReturn(debitBankDetails);
		when(userAccountRepository.save(userAccount)).thenReturn(userAccount);
		when(transferTransactionRepository.save(transferTransaction)).thenReturn(transferTransaction);

		// WHEN
		Optional<TransferTransaction> result = transferTransactionService.transferFromDebitBank(userAccount, 10);

		// THEN
		assertEquals(2000, userAccount.getApplicationBalance());
		assertEquals(userAccount, result.get().getUser());
		assertEquals(10, result.get().getAmount());
		assertNull(result.get().getCreditBankDetails());
		assertEquals(1234, result.get().getDebitBankDetails().getCardNumber());
	}

}
