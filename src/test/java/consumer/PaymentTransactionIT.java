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
import fr.asterox.PayMyBuddy.consumer.IPaymentTransactionRepository;
import fr.asterox.PayMyBuddy.consumer.IUserAccountRepository;
import fr.asterox.PayMyBuddy.model.PaymentTransaction;
import fr.asterox.PayMyBuddy.model.UserAccount;
import fr.asterox.PayMyBuddy.service.PaymentTransactionService;

@SpringBootTest(classes = PayMyBuddyApplication.class)
@RunWith(SpringRunner.class)
public class PaymentTransactionIT {

	@Autowired
	IUserAccountRepository userAccountRepository;

	@Autowired
	PaymentTransactionService paymentTransactionService;

	@Autowired
	IPaymentTransactionRepository paymentTransactionRepository;

	UserAccount userAccount1;
	Long userAccount1Id;
	UserAccount userAccount2;
	Long userAccount2Id;

	@BeforeEach
	private void setUpPerTest() throws Exception {
		userAccountRepository.deleteAll();
		paymentTransactionRepository.deleteAll();

		UserAccount user1 = new UserAccount("email1", "nickname1", "password1", 0, new ArrayList<>(), new ArrayList<>(),
				new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
		userAccount1 = userAccountRepository.save(user1);
		userAccount1Id = userAccount1.getUserId();
		List<UserAccount> friendsList = new ArrayList<>();
		friendsList.add(user1);
		UserAccount user2 = new UserAccount("email2", "nickname2", "password2", 5000, new ArrayList<>(),
				new ArrayList<>(), new ArrayList<>(), friendsList, new ArrayList<>(), new ArrayList<>());
		userAccount2 = userAccountRepository.save(user2);
		userAccount2Id = userAccount2.getUserId();

	}

	@Test
	public void givenAnAmountToTransfer_whenTransferToAnotherUser_thenReturnActualizedBalances() throws Exception {
		// WHEN
		Optional<PaymentTransaction> result = paymentTransactionService.transferToAnotherUser(userAccount2, "email1",
				"blabla", 20);

		// THEN
		Long paymentId = result.get().getPaymentId();
		Optional<PaymentTransaction> createdPaymentTransaction = paymentTransactionRepository.findById(paymentId);

		assertEquals(20, createdPaymentTransaction.get().getAmount());
		assertEquals("email2", createdPaymentTransaction.get().getIssuer().getEmail());
		assertEquals("email1", createdPaymentTransaction.get().getRecipient().getEmail());
		assertEquals(Math.round(5000 - 20 * 1.005 * 100),
				createdPaymentTransaction.get().getIssuer().getApplicationBalance());
		assertEquals(2000, createdPaymentTransaction.get().getRecipient().getApplicationBalance());
		assertEquals("blabla", createdPaymentTransaction.get().getDescription());
	}

	@Test
	public void givenAnInexistentIssuer_whenTransferToAnotherUser_thenReturnEmptyOptional() throws Exception {
		UserAccount user3 = new UserAccount("email3", "nickname3", "password3", 0, new ArrayList<>(), new ArrayList<>(),
				new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
		// WHEN
		Optional<PaymentTransaction> result = paymentTransactionService.transferToAnotherUser(user3, "email1", "blabla",
				20);

		// THEN

		assertEquals(Optional.empty(), result);

	}

}
