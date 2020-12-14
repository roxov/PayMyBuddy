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
import fr.asterox.PayMyBuddy.consumer.IPaymentTransactionRepository;
import fr.asterox.PayMyBuddy.consumer.IUserAccountRepository;
import fr.asterox.PayMyBuddy.model.PaymentTransaction;
import fr.asterox.PayMyBuddy.model.UserAccount;
import fr.asterox.PayMyBuddy.service.PaymentTransactionService;

@SpringBootTest(classes = PayMyBuddyApplication.class)
@ExtendWith(MockitoExtension.class)
public class PaymentTransactionServiceTest {

	@Autowired
	private PaymentTransactionService paymentTransactionService;

	@MockBean
	private IPaymentTransactionRepository paymentTransactionRepository;

	@MockBean
	private IUserAccountRepository userAccountRepository;

	UserAccount issuer;
	UserAccount recipient;

	@BeforeEach
	private void setUpPerTest() throws Exception {
		issuer = new UserAccount("email1", "nickname1", "password1", 10, new ArrayList<>(), new ArrayList<>(),
				new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
		List<UserAccount> friendsList = new ArrayList<>();
		friendsList.add(issuer);
		recipient = new UserAccount("email2", "nickname2", "password2", 20, new ArrayList<>(), new ArrayList<>(),
				new ArrayList<>(), friendsList, new ArrayList<>(), new ArrayList<>());
	}

	@Test
	public void givenATransferTransaction_whenTransferToAnotherUser_thenReturnSavedTransaction() {
		// GIVEN
		PaymentTransaction paymentTransaction = new PaymentTransaction(issuer, recipient, "blabla", 10);
		when(userAccountRepository.findByEmail("email1")).thenReturn(issuer);
		when(userAccountRepository.findByEmail("email2")).thenReturn(recipient);
		when(userAccountRepository.save(issuer)).thenReturn(issuer);
		when(userAccountRepository.save(recipient)).thenReturn(recipient);
		when(paymentTransactionRepository.save(paymentTransaction)).thenReturn(paymentTransaction);

		// WHEN
		Optional<PaymentTransaction> result = paymentTransactionService.transferToAnotherUser(issuer, "email2",
				"blabla", 10);

		// THEN
		assertEquals(0, issuer.getApplicationBalance());
		assertEquals(30, recipient.getApplicationBalance());
		assertEquals(issuer, result.get().getIssuer());
		assertEquals(recipient, result.get().getRecipient());
		assertEquals("blabla", result.get().getDescription());
		assertEquals(10, result.get().getAmount());
	}

	// TODO : test s'ils n'ont pas de relation

}
