package fr.asterox.PayMyBuddy.service;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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

	@Autowired
	PasswordEncoder passwordEncoder;

	private static final Logger LOGGER = LogManager.getLogger(UserAccountService.class);

	public Optional<UserAccount> addFriend(UserAccount userAccount, String friendEmail) {
		UserAccount user = userAccountRepository.findByEmail(userAccount.getEmail());

		if (user == null) {
			LOGGER.error("This user account does not exist.");
			return Optional.empty();
		}

		UserAccount friend = userAccountRepository.findByEmail(friendEmail);

		if (friend == null) {
			LOGGER.error("This user account (friend) does not exist.");
			return Optional.empty();
		}

		List<UserAccount> friendsList = user.getFriendsList();

		for (UserAccount existingFriend : friendsList) {
			if (existingFriend.getEmail().equals(friendEmail)) {
				LOGGER.info("This relationship already exists.");
				return Optional.empty();
			}
		}
		friendsList.add(friend);
		user.setFriendsList(friendsList);
		LOGGER.info("Updating UserAccount friendslist");
		return Optional.of(userAccountRepository.save(user));
	}

	public Optional<UserAccount> deleteFriend(UserAccount userAccount, String friendEmail) {
		UserAccount user = userAccountRepository.findByEmail(userAccount.getEmail());

		if (user == null) {
			LOGGER.error("This user account does not exist.");
			return Optional.empty();
		}

		List<UserAccount> friendsList = user.getFriendsList();

		for (UserAccount existingFriend : friendsList) {
			if (existingFriend.getEmail().equals(friendEmail)) {
				LOGGER.info("Deleting friendUser from friendslist");
				friendsList.remove(existingFriend);
				user.setFriendsList(friendsList);
				return Optional.of(userAccountRepository.save(user));

			}
		}
		LOGGER.info("No relation to delete");
		return Optional.empty();
	}

	public UserAccount createUserAccount(UserAccount userAccount) {
		String encodedPassword = passwordEncoder.encode(userAccount.getPassword());
		userAccount.setPassword(encodedPassword);
		LOGGER.info("Creating UserAccount");
		return userAccountRepository.save(userAccount);
	}

	public UserAccount findByEmail(String email) {
		UserAccount userAccount = userAccountRepository.findByEmail(email);

		if (userAccount == null) {
			LOGGER.error("This user account does not exist.");
			return null;
		}

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
