package fr.asterox.PayMyBuddy.consumer;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.asterox.PayMyBuddy.model.UserAccount;

/**
 * Repository pattern for UserAccount entities.
 *
 */
public interface IUserAccountRepository extends JpaRepository<UserAccount, Long> {

	/**
	 * Find user account, throw its unique email.
	 * 
	 * @param email
	 * @return UserAccount
	 * 
	 */

	UserAccount findByEmail(String email);

}
