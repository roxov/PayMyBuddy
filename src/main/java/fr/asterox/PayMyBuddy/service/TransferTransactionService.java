package fr.asterox.PayMyBuddy.service;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.asterox.PayMyBuddy.consumer.ICreditBankDetailsRepository;
import fr.asterox.PayMyBuddy.consumer.IDebitBankDetailsRepository;
import fr.asterox.PayMyBuddy.consumer.ITransferTransactionRepository;
import fr.asterox.PayMyBuddy.consumer.IUserAccountRepository;
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

	// @Transactional
	public Optional<TransferTransaction> transferToCreditBank(UserAccount userAccount, double amount) {
		UserAccount user = userAccountRepository.findByEmail(userAccount.getEmail());
		creditBankDetailsRepository.findByUser(user);
		user.setApplicationBalance(user.getApplicationBalance() - amount);
		userAccountRepository.save(user);
		LOGGER.info("Transfering money to bank account");
		return Optional.of(transferTransactionRepository.save(new TransferTransaction(user, amount, true)));
	}

	// @Transactional
	public Optional<TransferTransaction> transferFromDebitBank(UserAccount userAccount, double amount) {
		UserAccount user = userAccountRepository.findByEmail(userAccount.getEmail());
		debitBankDetailsRepository.findByUser(user);
		user.setApplicationBalance(user.getApplicationBalance() + amount);
		userAccountRepository.save(user);
		LOGGER.info("Transfering money from bank account");
		return Optional.of(transferTransactionRepository.save(new TransferTransaction(user, amount, false)));
	}
}
