package com.wl4g.devops.iam.config.properties;

import com.wl4g.devops.iam.common.config.AbstractIamProperties.ParamProperties;

/**
 * IAM server parameters configuration properties
 * 
 * @author Wangl.sir <983708408@qq.com>
 * @version v1.0
 * @date 2018年11月29日
 * @since
 */
public class ServerParamProperties extends ParamProperties {
	private static final long serialVersionUID = 3258460473711285504L;

	/**
	 * Account parameter name at login time of account password.
	 */
	private String principalName = "principal";

	/**
	 * Password parameter name at login time of account password.
	 */
	private String credentialName = "credential";

	/**
	 * Client type reference parameter name at login time of account password.
	 */
	private String clientRefName = "client_ref";

	/**
	 * Verification verifiedToken parameter name.
	 */
	private String verifiedTokenName = "verifiedToken";

	/**
	 * Dynamic verification code operation action type parameter key-name.
	 */
	private String smsActionName = "action";

	public String getPrincipalName() {
		return principalName;
	}

	public void setPrincipalName(String loginUsername) {
		this.principalName = loginUsername;
	}

	public String getCredentialName() {
		return credentialName;
	}

	public void setCredentialName(String loginPassword) {
		this.credentialName = loginPassword;
	}

	public String getClientRefName() {
		return clientRefName;
	}

	public void setClientRefName(String clientRefName) {
		this.clientRefName = clientRefName;
	}

	public String getVerifiedTokenName() {
		return verifiedTokenName;
	}

	public void setVerifiedTokenName(String verifiedTokenName) {
		this.verifiedTokenName = verifiedTokenName;
	}

	public String getSmsActionName() {
		return smsActionName;
	}

	public void setSmsActionName(String smsActionName) {
		this.smsActionName = smsActionName;
	}

}