package fr.asterox.PayMyBuddy.model;

import java.util.List;

public class UserAccount {
	private Long userID;
	private String email;
	private String password;
	private double applicationBalance;
	private List<TransferTransaction> transactionsList;
	private List<PaymentTransaction> paymentsList;
	private List<UserAccount> friendsList;
	private List<CreditBankDetails> creditBankDetailsList;
	private List<DebitBankDetails> debitBankDetailsList;

	public UserAccount() {
		super();
	}

	public UserAccount(Long userID, String email, String password, double applicationBalance,
			List<TransferTransaction> transactionsList, List<PaymentTransaction> paymentsList,
			List<UserAccount> friendsList, List<CreditBankDetails> creditBankDetailsList,
			List<DebitBankDetails> debitBankDetailsList) {
		super();
		this.userID = userID;
		this.email = email;
		this.password = password;
		this.applicationBalance = applicationBalance;
		this.transactionsList = transactionsList;
		this.paymentsList = paymentsList;
		this.friendsList = friendsList;
		this.creditBankDetailsList = creditBankDetailsList;
		this.debitBankDetailsList = debitBankDetailsList;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(applicationBalance);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((creditBankDetailsList == null) ? 0 : creditBankDetailsList.hashCode());
		result = prime * result + ((debitBankDetailsList == null) ? 0 : debitBankDetailsList.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((friendsList == null) ? 0 : friendsList.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((paymentsList == null) ? 0 : paymentsList.hashCode());
		result = prime * result + ((transactionsList == null) ? 0 : transactionsList.hashCode());
		result = prime * result + ((userID == null) ? 0 : userID.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserAccount other = (UserAccount) obj;
		if (Double.doubleToLongBits(applicationBalance) != Double.doubleToLongBits(other.applicationBalance))
			return false;
		if (creditBankDetailsList == null) {
			if (other.creditBankDetailsList != null)
				return false;
		} else if (!creditBankDetailsList.equals(other.creditBankDetailsList))
			return false;
		if (debitBankDetailsList == null) {
			if (other.debitBankDetailsList != null)
				return false;
		} else if (!debitBankDetailsList.equals(other.debitBankDetailsList))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (friendsList == null) {
			if (other.friendsList != null)
				return false;
		} else if (!friendsList.equals(other.friendsList))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (paymentsList == null) {
			if (other.paymentsList != null)
				return false;
		} else if (!paymentsList.equals(other.paymentsList))
			return false;
		if (transactionsList == null) {
			if (other.transactionsList != null)
				return false;
		} else if (!transactionsList.equals(other.transactionsList))
			return false;
		if (userID == null) {
			if (other.userID != null)
				return false;
		} else if (!userID.equals(other.userID))
			return false;
		return true;
	}

	public Long getUserID() {
		return userID;
	}

	public void setUserID(Long userID) {
		this.userID = userID;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public double getApplicationBalance() {
		return applicationBalance;
	}

	public void setApplicationBalance(double applicationBalance) {
		this.applicationBalance = applicationBalance;
	}

	public List<TransferTransaction> getTransactionsList() {
		return transactionsList;
	}

	public void setTransactionsList(List<TransferTransaction> transactionsList) {
		this.transactionsList = transactionsList;
	}

	public List<PaymentTransaction> getPaymentsList() {
		return paymentsList;
	}

	public void setPaymentsList(List<PaymentTransaction> paymentsList) {
		this.paymentsList = paymentsList;
	}

	public List<UserAccount> getFriendsList() {
		return friendsList;
	}

	public void setFriendsList(List<UserAccount> friendsList) {
		this.friendsList = friendsList;
	}

	public List<CreditBankDetails> getCreditBankDetailsList() {
		return creditBankDetailsList;
	}

	public void setCreditBankDetailsList(List<CreditBankDetails> creditBankDetailsList) {
		this.creditBankDetailsList = creditBankDetailsList;
	}

	public List<DebitBankDetails> getDebitBankDetailsList() {
		return debitBankDetailsList;
	}

	public void setDebitBankDetailsList(List<DebitBankDetails> debitBankDetailsList) {
		this.debitBankDetailsList = debitBankDetailsList;
	}

}
