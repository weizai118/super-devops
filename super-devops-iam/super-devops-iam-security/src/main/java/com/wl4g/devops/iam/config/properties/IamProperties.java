/*
 * Copyright 2017 ~ 2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wl4g.devops.iam.config.properties;

import static com.wl4g.devops.common.constants.IAMDevOpsConstants.URI_S_LOGIN_BASE;
import static com.wl4g.devops.common.constants.IAMDevOpsConstants.URI_S_SNS_BASE;
import static com.wl4g.devops.common.constants.IAMDevOpsConstants.URI_S_VERIFY_BASE;
import static com.wl4g.devops.iam.common.utils.Securitys.correctAuthenticaitorURI;
import static com.wl4g.devops.iam.web.DefaultViewController.URI_STATIC;
import static org.apache.commons.lang3.StringUtils.isBlank;

import org.apache.shiro.util.Assert;
import org.springframework.boot.context.properties.ConfigurationProperties;

import com.wl4g.devops.common.utils.web.WebUtils2;
import com.wl4g.devops.iam.common.config.AbstractIamProperties;
import com.wl4g.devops.iam.config.properties.ServerParamProperties;
import com.wl4g.devops.iam.sns.web.DefaultOauth2SnsController;

/**
 * IAM server properties
 * 
 * @author Wangl.sir <983708408@qq.com>
 * @version v1.0
 * @date 2019年1月4日
 * @since
 */
@ConfigurationProperties(prefix = "spring.cloud.devops.iam")
public class IamProperties extends AbstractIamProperties<ServerParamProperties> {
	private static final long serialVersionUID = -5858422822181237865L;

	/**
	 * Default view loader path
	 */
	final public static String DEFAULT_VIEW_LOADER_PATH = "classpath:/default-view";

	/**
	 * Default view login URI.
	 */
	final public static String DEFAULT_VIEW_LOGIN_URI = DEFAULT_VIEW_BASE_URI + "/login.html";

	/**
	 * Login page URI
	 */
	private String loginUri = DEFAULT_VIEW_LOGIN_URI;

	/**
	 * Login success redirection to end-point.(Must be back-end server URI)
	 * </br>
	 * 
	 * <pre>
	 * umc-admin@http://localhost:14048
	 * </pre>
	 */
	private String successEndpoint;

	/**
	 * Unauthorized(403) page URI
	 */
	private String unauthorizedUri = DEFAULT_VIEW_403_URI;

	/**
	 * Matcher configuration properties.
	 */
	private MatcherProperties matcher = new MatcherProperties();

	/**
	 * Ticket configuration properties.
	 */
	private TicketProperties ticket = new TicketProperties();

	/**
	 * IAM server parameters configuration properties.
	 */
	private ServerParamProperties param = new ServerParamProperties();

	public String getLoginUri() {
		return loginUri;
	}

	public void setLoginUri(String loginUri) {
		this.loginUri = WebUtils2.cleanURI(loginUri);
	}

	public String getSuccessEndpoint() {
		return successEndpoint;
	}

	public void setSuccessEndpoint(String successEndpoint) {
		this.successEndpoint = successEndpoint;
	}

	public String getSuccessService() {
		return getSuccessEndpoint().split("@")[0];
	}

	/**
	 * e.g. </br>
	 * Situation1: http://myapp.domain.com/myapp/xxx/list?id=1 Situation1:
	 * /view/index.html ===> http://myapp.domain.com/myapp/authenticator?id=1
	 * 
	 * Implementing the IAM-CAS protocol: When successful login, you must
	 * redirect to the back-end server URI of IAM-CAS-Client. (Note: URI of
	 * front-end pages can not be used directly).
	 * 
	 * @see {@link com.wl4g.devops.iam.client.filter.AuthenticatorAuthenticationFilter}
	 * @see {@link com.wl4g.devops.iam.filter.AuthenticatorAuthenticationFilter#determineSuccessUrl()}
	 */
	@Override
	public String getSuccessUri() {
		return correctAuthenticaitorURI(getSuccessEndpoint().split("@")[1]);
	}

	@Override
	public String getUnauthorizedUri() {
		return unauthorizedUri;
	}

	public void setUnauthorizedUri(String unauthorizedUri) {
		this.unauthorizedUri = unauthorizedUri;
	}

	public MatcherProperties getMatcher() {
		return matcher;
	}

	public void setMatcher(MatcherProperties matcher) {
		this.matcher = matcher;
	}

	public TicketProperties getTicket() {
		return ticket;
	}

	public void setTicket(TicketProperties ticket) {
		this.ticket = ticket;
	}

	public ServerParamProperties getParam() {
		return this.param;
	}

	public void setParam(ServerParamProperties param) {
		this.param = param;
	}

	@Override
	protected void applyDefaultIfNecessary() {
		// Default URL filter chain.
		addDefaultFilterChain();
		// Default success endPoint.
		if (isBlank(getSuccessEndpoint())) {
			setSuccessEndpoint(environment.getProperty("spring.application.name") + "@" + DEFAULT_VIEW_INDEX_URI);
		}
	}

	@Override
	protected void validation() {
		Assert.hasText(getSuccessEndpoint(), "'successEndpoint' must not be empty.");
		Assert.state(getSuccessEndpoint().contains("@"), "Invalid success endpoint, e.g. iam-example@http://localhost:14041");
		super.validation();
	}

	/**
	 * Add default filter chain settings.<br/>
	 * {@link DefaultOauth2SnsController#connect}<br/>
	 */
	private void addDefaultFilterChain() {
		// Default view access files request rules.
		getFilterChain().put(DEFAULT_VIEW_BASE_URI + URI_STATIC + "/**", "anon");
		// SNS authenticator rules.
		getFilterChain().put(URI_S_SNS_BASE + "/**", "anon");
		// Login authenticator rules.
		getFilterChain().put(URI_S_LOGIN_BASE + "/**", "anon");
		// Verify(CAPTCHA/SMS) authenticator rules.
		getFilterChain().put(URI_S_VERIFY_BASE + "/**", "anon");
	}

}