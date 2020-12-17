package fr.asterox.PayMyBuddy.service;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	@Transactional
	public Optional<PaymentTransaction> transferToAnotherUser(UserAccount issuer, String recipientEmail,
			String description, double amount) {
		UserAccount issuerOfPayment = userAccountRepository.findByEmail(issuer.getEmail());
		UserAccount recipientOfPayment = userAccountRepository.findByEmail(recipientEmail);

		if (issuerOfPayment == null) {
			LOGGER.error("This issuer does not exist.");
			return Optional.empty();
		}

		if (recipientOfPayment == null) {
			LOGGER.error("This recipient does not exist.");
			return Optional.empty();
		}

		List<UserAccount> friendsList = issuerOfPayment.getFriendsList();
		for (UserAccount existingFriend : friendsList) {
			if ((existingFriend.getEmail()).equals(recipientEmail)) {
				double balanceOfIssuer = Math.round(issuerOfPayment.getApplicationBalance() - amount * 100 * 1.005);
				issuerOfPayment.setApplicationBalance(balanceOfIssuer);
				userAccountRepository.save(issuerOfPayment);

				recipientOfPayment.setApplicationBalance(recipientOfPayment.getApplicationBalance() + amount * 100);
				userAccountRepository.save(recipientOfPayment);

				LOGGER.info("Transfering money to bank account");

				return Optional.of(paymentTransactionRepository
						.save(new PaymentTransaction(issuerOfPayment, recipientOfPayment, description, amount)));
			}
		}
		LOGGER.info("No relationship with recipient.");
		return Optional.empty();
	}
}
