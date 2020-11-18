package fr.asterox.PayMyBuddy.consumer;

import fr.asterox.PayMyBuddy.model.UserAccount;

public interface IUserAccountDAO {
	/**
	 * Create new user account.
	 * 
	 * @param userAcount
	 * @throws Exception
	 */
	void createUserAccount(UserAccount userAcount) throws Exception;

	/**
	 * Update user account : email, password or application balance
	 * 
	 * @param userAcount
	 * @throws Exception
	 */
	void updateUserAccount(UserAccount userAcount) throws Exception;

	/**
	 * Find user account when user is connecting with his email.
	 * 
	 * @param email
	 * @return UserAccount
	 * @throws Exception
	 */

	UserAccount findUserAccountByEmail(String email) throws Exception;

	/**
	 * Delete user account using the email of the account.
	 * 
	 * @param email
	 * @throws Exception
	 */
	void deleteUserAccountByEmail(String email) throws Exception;
}
