package fr.asterox.PayMyBuddy.consumer;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.asterox.PayMyBuddy.model.TransferTransaction;
import fr.asterox.PayMyBuddy.model.UserAccount;

/**
 * Repository pattern for TransferTransaction entities.
 *
 */
public interface ITransferTransactionRepository extends JpaRepository<TransferTransaction, Long> {
	/**
	 * Delete all transfer transactions associated to a user account.
	 * 
	 * @param user
	 * @return Iterable<TransferTransaction>
	 */
	Iterable<TransferTransaction> deleteAllByUser(UserAccount user);
}
