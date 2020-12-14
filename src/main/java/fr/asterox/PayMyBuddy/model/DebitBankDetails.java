package fr.asterox.PayMyBuddy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class DebitBankDetails {
	@Column(name = "DEBIT_ID")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long debitId;
	@ManyToOne
	@JoinColumn(name = "USER_ID", nullable = false)
	private UserAccount user;
	@Column(name = "HOLDER_NAME")
	private String holderName;
	@Column(name = "CARD_NUMBER")
	// @ColumnTransformer(read = "pgp_sym_decrypt(cardNumber,'cryptedCardNumber')",
	// write = "pgp_sym_encrypt(?,'cryptedCardNumber')")
	private int cardNumber;
	@Column(name = "EXPIRATION_DATE")
	private int expirationDate;
	private int cvv;

	public DebitBankDetails() {
		super();
	}

	public DebitBankDetails(UserAccount user, String holderName, int cardNumber, int expirationDate, int cvv) {
		super();
		this.user = user;
		this.holderName = holderName;
		this.cardNumber = cardNumber;
		this.expirationDate = expirationDate;
		this.cvv = cvv;
	}

	public DebitBankDetails(Long debitId, UserAccount user, String holderName, int cardNumber, int expirationDate,
			int cvv) {
		super();
		this.debitId = debitId;
		this.user = user;
		this.holderName = holderName;
		this.cardNumber = cardNumber;
		this.expirationDate = expirationDate;
		this.cvv = cvv;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + cardNumber;
		result = prime * result + cvv;
		result = prime * result + ((debitId == null) ? 0 : debitId.hashCode());
		result = prime * result + expirationDate;
		result = prime * result + ((holderName == null) ? 0 : holderName.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
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
		DebitBankDetails other = (DebitBankDetails) obj;
		if (cardNumber != other.cardNumber)
			return false;
		if (cvv != other.cvv)
			return false;
		if (debitId == null) {
			if (other.debitId != null)
				return false;
		} else if (!debitId.equals(other.debitId))
			return false;
		if (expirationDate != other.expirationDate)
			return false;
		if (holderName == null) {
			if (other.holderName != null)
				return false;
		} else if (!holderName.equals(other.holderName))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

	public Long getDebitId() {
		return debitId;
	}

	public void setDebitId(Long debitId) {
		this.debitId = debitId;
	}

	public UserAccount getUser() {
		return user;
	}

	public void setUser(UserAccount user) {
		this.user = user;
	}

	public String getHolderName() {
		return holderName;
	}

	public void setHolderName(String holderName) {
		this.holderName = holderName;
	}

	public int getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(int cardNumber) {
		this.cardNumber = cardNumber;
	}

	public int getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(int expirationDate) {
		this.expirationDate = expirationDate;
	}

	public int getCvv() {
		return cvv;
	}

	public void setCvv(int cvv) {
		this.cvv = cvv;
	}

}
