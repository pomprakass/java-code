package com.dataProvider;

import java.io.File;
import java.util.Properties;
import org.testng.annotations.DataProvider;
import com.generic.*;

public class WalletDP {
	
	static Properties props = ToolUtil.openPropertyFile(new File(System.getProperty("user.dir")+File.separator+"config.properties"));
	
	private String walletHubSignUpURL;
	private String signUpEmail;
	private String signUpPassword; 
	private String insuranceUrl;
	private String reviewOption;
	private String reviewText;
	
	public String getInsuranceUrl() {
		return insuranceUrl;
	}

	public void setInsuranceUrl(String insuranceUrl) {
		this.insuranceUrl = insuranceUrl;
	}

	public String getWalletHubSignUpURL() {
		return walletHubSignUpURL;
	}

	public void setWalletHubSignUpURL(String walletHubSignUpURL) {
		this.walletHubSignUpURL = walletHubSignUpURL;
	}


	public String getSignUpEmail() {
		return signUpEmail;
	}


	public void setSignUpEmail(String signUpEmail) {
		this.signUpEmail = signUpEmail;
	}


	public String getSignUpPassword() {
		return signUpPassword;
	}


	public void setSignUpPassword(String signUpPassword) {
		this.signUpPassword = signUpPassword;
	}

	public String getReviewOption() {
		return reviewOption;
	}
	public void setReviewOption(String reviewOption) {
		this.reviewOption = reviewOption;
	}
	public String getReviewText() {
		return reviewText;
	}
	public void setReviewText(String reviewText) {
		this.reviewText = reviewText;
	}
	
	@DataProvider(name = "WalletHubData")
	public static Object[][] getWalletHubData() {
		WalletDP walletData = new WalletDP();
		walletData.setWalletHubSignUpURL(props.getProperty("walletHubSignUpURL"));
		walletData.setSignUpEmail(props.getProperty("signUpEmail"));
		walletData.setSignUpPassword(props.getProperty("signUpPassword"));
		walletData.setInsuranceUrl(props.getProperty("insuranceUrl"));
		walletData.setReviewText(props.getProperty("reviewText"));
		walletData.setReviewOption(props.getProperty("reviewOption"));
		
		return new WalletDP[][] { { walletData } };
	}
}
