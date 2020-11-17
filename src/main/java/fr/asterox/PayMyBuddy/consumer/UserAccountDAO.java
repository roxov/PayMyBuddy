package fr.asterox.PayMyBuddy.consumer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import fr.asterox.PayMyBuddy.model.UserAccount;

@Repository
public class UserAccountDAO implements IUserAccountDAO {

	private DataBaseConfig dataBaseConfig;

	private static final Logger LOGGER = LogManager.getLogger(UserAccountDAO.class);

	UserAccountDAO(DataBaseConfig dataBaseConfig) {
		this.dataBaseConfig = dataBaseConfig;
	}

	@Override
	public void create(UserAccount userAcount) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public UserAccount findByEmail(String email) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
