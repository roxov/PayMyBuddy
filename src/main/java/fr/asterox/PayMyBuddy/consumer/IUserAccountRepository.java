package fr.asterox.PayMyBuddy.consumer;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.asterox.PayMyBuddy.model.UserAccount;

public interface IUserAccountRepository extends JpaRepository<UserAccount, Long> {

	/**
	 * Find user account when user is connecting with his email.
	 * 
	 * @param email
	 * @return UserAccount
	 * @throws Exception
	 */

	UserAccount findByEmail(String email);

}
