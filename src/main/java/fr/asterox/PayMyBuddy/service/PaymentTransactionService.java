package fr.asterox.PayMyBuddy.service;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.asterox.PayMyBuddy.consumer.IPaymentTransactionRepository;
import fr.asterox.PayMyBuddy.consumer.IUserAccountRepository;
import fr.asterox.PayMyBuddy.model.PaymentTransaction;
import fr.asterox.PayMyBuddy.model.UserAccount;

@Service
public class PaymentTransactionService {

	@Autowired
	IPaymentTransactionRepository paymentTransactionRepository;

	@Autowired
	IUserAccountRepository userAccountRepository;

	private static final Logger LOGGER = LogManager.getLogger(PaymentTransactionService.class);

	// @Transactional
	public Optional<PaymentTransaction> transferToAnotherUser(UserAccount issuer, String recipientEmail,
			String description, double amount) {
		UserAccount issuerOfPayment = userAccountRepository.findByEmail(issuer.getEmail());
		UserAccount recipientOfPayment = userAccountRepository.findByEmail(recipientEmail);

		issuerOfPayment.setApplicationBalance(issuerOfPayment.getApplicationBalance() - amount);
		userAccountRepository.save(issuerOfPayment);

		recipientOfPayment.setApplicationBalance(recipientOfPayment.getApplicationBalance() + amount);
		userAccountRepository.save(recipientOfPayment);

		LOGGER.info("Transfering money to bank account");

		return Optional.of(paymentTransactionRepository
				.save(new PaymentTransaction(issuerOfPayment, recipientOfPayment, description, amount)));
	}

}
