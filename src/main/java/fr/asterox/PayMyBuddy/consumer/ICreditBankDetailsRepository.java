package fr.asterox.PayMyBuddy.consumer;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.asterox.PayMyBuddy.model.CreditBankDetails;
import fr.asterox.PayMyBuddy.model.UserAccount;

public interface ICreditBankDetailsRepository extends JpaRepository<CreditBankDetails, Long> {
	CreditBankDetails findByUser(UserAccount user);

	Iterable<CreditBankDetails> deleteAllByUser(UserAccount user);
}
