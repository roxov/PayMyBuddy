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

		UserAccount user1 = new UserAccount("email1", "nickname1", "password1", 50, new ArrayList<>(),
				new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
		userAccount1 = userAccountRepository.save(user1);
		userAccount1Id = userAccount1.getUserId();
		UserAccount user2 = new UserAccount("email2", "nickname2", "password2", 0, new ArrayList<>(), new ArrayList<>(),
				new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
		userAccount2 = userAccountRepository.save(user2);
		userAccount2Id = userAccount2.getUserId();

	}

	@Test
	public void givenAnAmountToTransfer_whenTransferToAnotherUser_thenReturnActualizedBalances() throws Exception {
		// WHEN
		Optional<PaymentTransaction> result = paymentTransactionService.transferToAnotherUser(userAccount1, "email2",
				"blabla", 25);

		// THEN
		assertEquals(25, userAccountRepository.findById(userAccount1Id).get().getApplicationBalance());
		assertEquals(25, userAccountRepository.findById(userAccount2Id).get().getApplicationBalance());

		Long paymentId = result.get().getPaymentId();
		Optional<PaymentTransaction> createdPaymentTransaction = paymentTransactionRepository.findById(paymentId);

		assertEquals(25, createdPaymentTransaction.get().getAmount());
		assertEquals(userAccount1, createdPaymentTransaction.get().getIssuer());
		assertEquals(userAccount2, createdPaymentTransaction.get().getRecipient());
		assertEquals("blabla", createdPaymentTransaction.get().getDescription());
	}
//TODO : transaction interrompue

}
