package org.group21.insurance.models;

import java.time.LocalDate;
import java.util.List;

public class Claim {
	private String id;
	private LocalDate claimDate;
	private Customer insuredPerson;
	private LocalDate examDate;
	private InsuranceCard insuranceCard;
	private List<String> listOfDocuments;
	private long claimAmount;
	private BankingInfo receiverBankingInfo;
	private ClaimStatus status;
	
	private enum ClaimStatus {
		NEW, PROCESSING, DONE
	}
}
