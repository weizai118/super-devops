/*
 * Copyright 2015 the original author or authors.
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
package com.wl4g.devops.scm.context;

import com.wl4g.devops.common.bean.scm.model.*;

public interface ConfigContextHandler {

	/**
	 * Find configuration property-source.
	 * 
	 * @param get
	 *            config source get message.
	 * @return
	 */
	public ReleaseMessage findSource(GetRelease get);

	/**
	 * Access configuration client report configure result.
	 * 
	 * @param report
	 *            request parameter.
	 * @param resp
	 *            response parameter.
	 * @return
	 */
	public void report(ReportInfo report);

	/**
	 * Release configuration property-sources.
	 * 
	 * @param pre
	 *            request parameter.
	 */
	public void release(PreRelease pre);

}