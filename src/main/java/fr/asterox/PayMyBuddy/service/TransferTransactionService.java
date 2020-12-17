package fr.asterox.PayMyBuddy.service;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.asterox.PayMyBuddy.consumer.ICreditBankDetailsRepository;
import fr.asterox.PayMyBuddy.consumer.IDebitBankDetailsRepository;
import fr.asterox.PayMyBuddy.consumer.ITransferTransactionRepository;
import fr.asterox.PayMyBuddy.consumer.IUserAccountRepository;
import fr.asterox.PayMyBuddy.model.CreditBankDetails;
import fr.asterox.PayMyBuddy.model.DebitBankDetails;
import fr.asterox.PayMyBuddy.model.TransferTransaction;
import fr.asterox.PayMyBuddy.model.UserAccount;

@Service
public class TransferTransactionService {

	@Autowired
	ITransferTransactionRepository transferTransactionRepository;

	@Autowired
	IDebitBankDetailsRepository debitBankDetailsRepository;

	@Autowired
	ICreditBankDetailsRepository creditBankDetailsRepository;

	@Autowired
	IUserAccountRepository userAccountRepository;

	private static final Logger LOGGER = LogManager.getLogger(TransferTransactionService.class);

	@Transactional
	public Optional<TransferTransaction> transferToCreditBank(UserAccount userAccount, double amount) {
		UserAccount user = userAccountRepository.findByEmail(userAccount.getEmail());

		if (user == null) {
			LOGGER.error("This user account does not exist.");
			return Optional.empty();
		}

		CreditBankDetails creditBankDetails = creditBankDetailsRepository.findByUser(user);

		if (creditBankDetails == null) {
			LOGGER.error("These credit bank details do not exist.");
			return Optional.empty();
		}

		double applicationBalance = Math.round(user.getApplicationBalance() - amount * 1.005 * 100);
		user.setApplicationBalance(applicationBalance);
		userAccountRepository.save(user);

		LOGGER.info("Transfering money to bank account");

		return Optional.of(transferTransactionRepository
				.save(new TransferTransaction(user, amount, true, creditBankDetails, null)));
	}

	@Transactional
	public Optional<TransferTransaction> transferFromDebitBank(UserAccount userAccount, double amount) {
		UserAccount user = userAccountRepository.findByEmail(userAccount.getEmail());

		if (user == null) {
			LOGGER.error("This user account does not exist.");
			return Optional.empty();
		}

		DebitBankDetails debitBankDetails = debitBankDetailsRepository.findByUser(user);

		if (debitBankDetails == null) {
			LOGGER.error("These debit bank details do not exist.");
			return Optional.empty();
		}

		double applicationBalance = Math.round(user.getApplicationBalance() + amount * 100);
		user.setApplicationBalance(applicationBalance);
		userAccountRepository.save(user);
		LOGGER.info("Transfering money from bank account");
		return Optional.of(transferTransactionRepository
				.save(new TransferTransaction(user, amount, false, null, debitBankDetails)));
	}
}
