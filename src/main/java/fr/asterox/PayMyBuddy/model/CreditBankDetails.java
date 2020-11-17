package fr.asterox.PayMyBuddy.model;

public class CreditBankDetails {
	private Long creditID;
	private String holderName;
	private String IBAN;
	private String BIC;

	public CreditBankDetails() {
		super();
	}

	public CreditBankDetails(Long creditID, String holderName, String iBAN, String bIC) {
		super();
		this.creditID = creditID;
		this.holderName = holderName;
		IBAN = iBAN;
		BIC = bIC;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((BIC == null) ? 0 : BIC.hashCode());
		result = prime * result + ((IBAN == null) ? 0 : IBAN.hashCode());
		result = prime * result + ((creditID == null) ? 0 : creditID.hashCode());
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
		CreditBankDetails other = (CreditBankDetails) obj;
		if (BIC == null) {
			if (other.BIC != null)
				return false;
		} else if (!BIC.equals(other.BIC))
			return false;
		if (IBAN == null) {
			if (other.IBAN != null)
				return false;
		} else if (!IBAN.equals(other.IBAN))
			return false;
		if (creditID == null) {
			if (other.creditID != null)
				return false;
		} else if (!creditID.equals(other.creditID))
			return false;
		if (holderName == null) {
			if (other.holderName != null)
				return false;
		} else if (!holderName.equals(other.holderName))
			return false;
		return true;
	}

	public Long getCreditID() {
		return creditID;
	}

	public void setCreditID(Long creditID) {
		this.creditID = creditID;
	}

	public String getHolderName() {
		return holderName;
	}

	public void setHolderName(String holderName) {
		this.holderName = holderName;
	}

	public String getIBAN() {
		return IBAN;
	}

	public void setIBAN(String iBAN) {
		IBAN = iBAN;
	}

	public String getBIC() {
		return BIC;
	}

	public void setBIC(String bIC) {
		BIC = bIC;
	}

}
