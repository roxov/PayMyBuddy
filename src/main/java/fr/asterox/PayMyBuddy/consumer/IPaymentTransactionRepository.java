package fr.asterox.PayMyBuddy.consumer;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.asterox.PayMyBuddy.model.PaymentTransaction;
import fr.asterox.PayMyBuddy.model.UserAccount;

/**
 * Repository pattern for PaymentTransaction entities.
 *
 */
public interface IPaymentTransactionRepository extends JpaRepository<PaymentTransaction, Long> {
	/**
	 * Delete all payment transactions associated to a user account, who is the
	 * issuer of payment.
	 * 
	 * @param user
	 * @return Iterable<PaymentTransaction>
	 */
	Iterable<PaymentTransaction> deleteAllByIssuer(UserAccount issuer);

	/**
	 * Delete all payment transactions associated to a user account, who is the
	 * recipient of payment.
	 * 
	 * @param user
	 * @return Iterable<PaymentTransaction>
	 */
	Iterable<PaymentTransaction> deleteAllByRecipient(UserAccount recipient);

}
