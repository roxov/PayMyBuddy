package fr.asterox.PayMyBuddy.consumer;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.asterox.PayMyBuddy.model.PaymentTransaction;
import fr.asterox.PayMyBuddy.model.UserAccount;

public interface IPaymentTransactionRepository extends JpaRepository<PaymentTransaction, Long> {
	Iterable<PaymentTransaction> deleteAllByIssuer(UserAccount issuer);

	Iterable<PaymentTransaction> deleteAllByRecipient(UserAccount recipient);

}
