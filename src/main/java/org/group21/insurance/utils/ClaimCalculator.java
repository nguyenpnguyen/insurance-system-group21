package org.group21.insurance.utils;

import org.group21.insurance.controllers.ClaimController;
import org.group21.insurance.models.Beneficiary;
import org.group21.insurance.models.Claim;
import org.group21.insurance.models.PolicyOwner;

public class ClaimCalculator {
	
	public static long calculateYearlyClaimPayment(PolicyOwner policyOwner) {
		long totalClaim = 0;
		for (Beneficiary b : policyOwner.getBeneficiaries()) {
			totalClaim += b.getInsuranceRate();
		}
		return totalClaim;
	}
	
	public static long calculateTotalSuccessfulClaimAmount() {
		ClaimController claimController = ClaimController.getInstance();
		
		long totalSuccessfulClaimAmount = 0;
		for (Claim c : claimController.getClaimByStatus(Claim.ClaimStatus.DONE)) {
			totalSuccessfulClaimAmount += c.getClaimAmount();
		}
		return totalSuccessfulClaimAmount;
	}
}
