/*
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.fhir2conditions.api.dao.impl;

import org.hibernate.SessionFactory;
import org.openmrs.annotation.OpenmrsProfile;
import org.openmrs.module.emrapi.conditionslist.Condition;
import org.openmrs.module.fhir2.api.dao.FhirConditionDao;
import org.openmrs.module.fhir2.api.dao.impl.BaseFhirDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Primary
@Component("fhir2conditions.fhirConditionDaoImpl")
@OpenmrsProfile(openmrsPlatformVersion = "2.0.* - 2.1.*")
public class FhirConditionDaoImpl extends BaseFhirDao<Condition> implements FhirConditionDao<Condition> {
	
	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;
	
	@Override
	public SessionFactory getSessionFactory() {
		return this.sessionFactory;
	}
	
	@Override
	public Condition saveCondition(Condition condition) {
		return null;
	}
}
