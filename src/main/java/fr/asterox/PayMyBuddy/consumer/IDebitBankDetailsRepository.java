package fr.asterox.PayMyBuddy.consumer;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.asterox.PayMyBuddy.model.DebitBankDetails;
import fr.asterox.PayMyBuddy.model.UserAccount;

/**
 * Repository pattern for DebitBankDetails entities.
 *
 */
public interface IDebitBankDetailsRepository extends JpaRepository<DebitBankDetails, Long> {
	// Ne pas écrire en camel case. Pas de majuscule à l'intérieur des propriétés.
	/**
	 * Find debit bank associated to a user account, using its unique email
	 * (identification).
	 * 
	 * @param user
	 * @return DebitBankDetails
	 */
	DebitBankDetails findByUser(UserAccount user);

	/**
	 * Delete all debit bank associated to a user account.
	 * 
	 * @param user
	 * @return Iterable<DebitBankDetails>
	 */
	Iterable<DebitBankDetails> deleteAllByUser(UserAccount user);
}
