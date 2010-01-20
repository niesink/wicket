/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.wicket.ng.resource;

import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import org.apache.wicket.Application;

public class SharedResourceReference extends ResourceReference
{

	public SharedResourceReference(Class<?> scope, String name, Locale locale, String style,
		String variation)
	{
		super(scope, name, locale, style, variation);
	}

	public SharedResourceReference(Class<?> scope, String name)
	{
		super(scope, name);
	}

	public SharedResourceReference(String name)
	{
		super(name);
	}

	@Override
	public IResource getResource()
	{
		ResourceReference ref = Application.get()
			.getResourceReferenceRegistry()
			.getResourceReference(getScope(), getName(), getLocale(), getStyle(), getVariation(),
				false);

		if (ref == null)
		{
			return new AbstractResource()
			{
				@Override
				protected ResourceResponse newResourceResponse(Attributes attributes)
				{
					ResourceResponse res = new ResourceResponse();
					res.setErrorCode(HttpServletResponse.SC_NOT_FOUND);
					return res;
				}
			};
		}
		else
		{
			return ref.getResource();
		}
	}

}
