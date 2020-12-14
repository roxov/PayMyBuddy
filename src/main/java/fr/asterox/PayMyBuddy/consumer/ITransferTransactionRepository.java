package fr.asterox.PayMyBuddy.consumer;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.asterox.PayMyBuddy.model.TransferTransaction;
import fr.asterox.PayMyBuddy.model.UserAccount;

public interface ITransferTransactionRepository extends JpaRepository<TransferTransaction, Long> {
	Iterable<TransferTransaction> deleteAllByUser(UserAccount user);
}
