package fr.asterox.PayMyBuddy.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class UserAccount {
	@Column(name = "USER_ID")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;
	private String email;
	private String nickname;
	private String password;
	@Column(name = "APPLICATION_BALANCE")
	private double applicationBalance;
	@OneToMany(mappedBy = "user")
	private List<TransferTransaction> transactionsList;
	@OneToMany(mappedBy = "issuer")
	private List<PaymentTransaction> issuerPaymentsList;
	@OneToMany(mappedBy = "recipient")
	private List<PaymentTransaction> recipientPaymentsList;
	@ManyToMany
	@JoinTable(name = "FRIENDS_NETWORK", joinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID"), inverseJoinColumns = @JoinColumn(name = "FRIEND_ID", referencedColumnName = "USER_ID"))
	private List<UserAccount> friendsList;
	@OneToMany(mappedBy = "user")
	private List<CreditBankDetails> creditBankDetailsList;
	@OneToMany(mappedBy = "user")
	private List<DebitBankDetails> debitBankDetailsList;

	public UserAccount() {
		super();
	}

	public UserAccount(Long userId, String email, String nickname, String password, double applicationBalance,
			List<TransferTransaction> transactionsList, List<PaymentTransaction> issuerPaymentsList,
			List<PaymentTransaction> recipientPaymentsList, List<UserAccount> friendsList,
			List<CreditBankDetails> creditBankDetailsList, List<DebitBankDetails> debitBankDetailsList) {
		super();
		this.userId = userId;
		this.email = email;
		this.nickname = nickname;
		this.password = password;
		this.applicationBalance = applicationBalance;
		this.transactionsList = transactionsList;
		this.issuerPaymentsList = issuerPaymentsList;
		this.recipientPaymentsList = recipientPaymentsList;
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
		result = prime * result + ((issuerPaymentsList == null) ? 0 : issuerPaymentsList.hashCode());
		result = prime * result + ((nickname == null) ? 0 : nickname.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((recipientPaymentsList == null) ? 0 : recipientPaymentsList.hashCode());
		result = prime * result + ((transactionsList == null) ? 0 : transactionsList.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
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
		if (issuerPaymentsList == null) {
			if (other.issuerPaymentsList != null)
				return false;
		} else if (!issuerPaymentsList.equals(other.issuerPaymentsList))
			return false;
		if (nickname == null) {
			if (other.nickname != null)
				return false;
		} else if (!nickname.equals(other.nickname))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (recipientPaymentsList == null) {
			if (other.recipientPaymentsList != null)
				return false;
		} else if (!recipientPaymentsList.equals(other.recipientPaymentsList))
			return false;
		if (transactionsList == null) {
			if (other.transactionsList != null)
				return false;
		} else if (!transactionsList.equals(other.transactionsList))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
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

	public List<PaymentTransaction> getIssuerPaymentsList() {
		return issuerPaymentsList;
	}

	public void setIssuerPaymentsList(List<PaymentTransaction> issuerPaymentsList) {
		this.issuerPaymentsList = issuerPaymentsList;
	}

	public List<PaymentTransaction> getRecipientPaymentsList() {
		return recipientPaymentsList;
	}

	public void setRecipientPaymentsList(List<PaymentTransaction> recipientPaymentsList) {
		this.recipientPaymentsList = recipientPaymentsList;
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
