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
package com.wl4g.devops.iam.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wl4g.devops.iam.common.annotation.IamFilter;
import com.wl4g.devops.iam.authc.QrcodeAuthenticationToken;

@IamFilter
public class QrcodeAuthenticationFilter extends AbstractIamAuthenticationFilter<QrcodeAuthenticationToken> {
	final public static String NAME = "qrcode";

	@Override
	protected QrcodeAuthenticationToken postCreateToken(String remoteHost, String fromAppName, String redirectUrl,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		return null;
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public String getUriMapping() {
		return URI_BASE_MAPPING + NAME;
	}

}