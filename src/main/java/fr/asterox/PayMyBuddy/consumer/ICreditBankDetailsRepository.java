package fr.asterox.PayMyBuddy.consumer;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.asterox.PayMyBuddy.model.CreditBankDetails;
import fr.asterox.PayMyBuddy.model.UserAccount;

/**
 * Repository pattern for CreditBankDetails entities.
 *
 */
public interface ICreditBankDetailsRepository extends JpaRepository<CreditBankDetails, Long> {
	/**
	 * Find credit bank associated to a user account, using its unique email
	 * (identification).
	 * 
	 * @param user
	 * @return CreditBankDetails
	 */
	CreditBankDetails findByUser(UserAccount user);

	/**
	 * Delete all credit bank associated to a user account.
	 * 
	 * @param user
	 * @return Iterable<CreditBankDetails>
	 */
	Iterable<CreditBankDetails> deleteAllByUser(UserAccount user);
}
