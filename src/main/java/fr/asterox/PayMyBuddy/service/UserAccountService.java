package fr.asterox.PayMyBuddy.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.asterox.PayMyBuddy.consumer.IUserAccountRepository;
import fr.asterox.PayMyBuddy.model.UserAccount;

@Service
public class UserAccountService {
	@Autowired
	IUserAccountRepository userAccountRepository;

	private static final Logger LOGGER = LogManager.getLogger(UserAccountService.class);

	public void createUserAccount(UserAccount userAccount) {
		userAccountRepository.save(userAccount);
		LOGGER.info("Creating UserAccount");
	}

	public UserAccount findByEmail(String email) {
		UserAccount userAccount = userAccountRepository.findByEmailLike(email);
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
