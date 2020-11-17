package fr.asterox.PayMyBuddy.model;

public class DebitBankDetails {
	private Long debitID;
	private String holderName;
	private int cardNumber;
	private int expirationDate;
	private int CVV;

	public DebitBankDetails() {
		super();
	}

	public DebitBankDetails(Long debitID, String holderName, int cardNumber, int expirationDate, int cVV) {
		super();
		this.debitID = debitID;
		this.holderName = holderName;
		this.cardNumber = cardNumber;
		this.expirationDate = expirationDate;
		CVV = cVV;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + CVV;
		result = prime * result + cardNumber;
		result = prime * result + ((debitID == null) ? 0 : debitID.hashCode());
		result = prime * result + expirationDate;
		result = prime * result + ((holderName == null) ? 0 : holderName.hashCode());
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
		if (CVV != other.CVV)
			return false;
		if (cardNumber != other.cardNumber)
			return false;
		if (debitID == null) {
			if (other.debitID != null)
				return false;
		} else if (!debitID.equals(other.debitID))
			return false;
		if (expirationDate != other.expirationDate)
			return false;
		if (holderName == null) {
			if (other.holderName != null)
				return false;
		} else if (!holderName.equals(other.holderName))
			return false;
		return true;
	}

	public Long getDebitID() {
		return debitID;
	}

	public void setDebitID(Long debitID) {
		this.debitID = debitID;
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

	public int getCVV() {
		return CVV;
	}

	public void setCVV(int cVV) {
		CVV = cVV;
	}

}
