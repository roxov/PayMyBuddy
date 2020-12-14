package fr.asterox.PayMyBuddy.service;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.asterox.PayMyBuddy.consumer.ICreditBankDetailsRepository;
import fr.asterox.PayMyBuddy.consumer.IDebitBankDetailsRepository;
import fr.asterox.PayMyBuddy.consumer.IPaymentTransactionRepository;
import fr.asterox.PayMyBuddy.consumer.ITransferTransactionRepository;
import fr.asterox.PayMyBuddy.consumer.IUserAccountRepository;
import fr.asterox.PayMyBuddy.model.UserAccount;

@Service
public class UserAccountService {
	@Autowired
	IUserAccountRepository userAccountRepository;

	@Autowired
	ICreditBankDetailsRepository creditBankDetailsRepository;

	@Autowired
	IDebitBankDetailsRepository debitBankDetailsRepository;

	@Autowired
	IPaymentTransactionRepository paymentTransactionRepository;

	@Autowired
	ITransferTransactionRepository transferTransactionRepository;

	private static final Logger LOGGER = LogManager.getLogger(UserAccountService.class);

	public Optional<UserAccount> addFriend(UserAccount userAccount, UserAccount friendUser) {
		UserAccount user = userAccountRepository.findByEmail(userAccount.getEmail());
		UserAccount friend = userAccountRepository.findByEmail(friendUser.getEmail());
		List<UserAccount> friendsList = user.getFriendsList();

		for (UserAccount existingFriend : friendsList) {
			if (existingFriend.equals(friend)) {
				LOGGER.info("This relationship already exists.");
				return Optional.empty();
			}
		}
		friendsList.add(friend);
		user.setFriendsList(friendsList);
		LOGGER.info("Updating UserAccount friendslist");
		return Optional.of(userAccountRepository.save(user));
	}

	public Optional<UserAccount> deleteFriend(UserAccount userAccount, UserAccount friendUser) {
		UserAccount user = userAccountRepository.findByEmail(userAccount.getEmail());
		UserAccount friend = userAccountRepository.findByEmail(friendUser.getEmail());
		List<UserAccount> friendsList = user.getFriendsList();

		for (UserAccount existingFriend : friendsList) {
			if (existingFriend.equals(friend)) {
				LOGGER.info("Deleting friendUser from friendslist");
				friendsList.remove(friend);
				user.setFriendsList(friendsList);
				return Optional.of(userAccountRepository.save(user));
			}
		}
		LOGGER.info("No relation to delete");
		return Optional.empty();
	}

	public void createUserAccount(UserAccount userAccount) {
		userAccountRepository.save(userAccount);
		LOGGER.info("Creating UserAccount");
	}

	public UserAccount findByEmail(String email) {
		UserAccount userAccount = userAccountRepository.findByEmail(email);
		LOGGER.info("Creating UserAccount");
		return userAccount;
	}

	public void updateUserAccount(UserAccount userAccount) {
		userAccountRepository.save(userAccount);
		LOGGER.info("Updating UserAccount");
	}

	public void deleteUserAccount(UserAccount userAccount) {
		userAccountRepository.delete(userAccount);
		LOGGER.info("Deleting UserAccount");
	}

}
