package fr.asterox.PayMyBuddy.consumer;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.asterox.PayMyBuddy.model.DebitBankDetails;
import fr.asterox.PayMyBuddy.model.UserAccount;

public interface IDebitBankDetailsRepository extends JpaRepository<DebitBankDetails, Long> {
	// Ne pas écrire en camel case. Pas de majuscule à l'intérieur des propriétés.
	DebitBankDetails findByUser(UserAccount user);

	Iterable<DebitBankDetails> deleteAllByUser(UserAccount user);
}
