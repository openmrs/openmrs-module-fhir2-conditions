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

import static org.hibernate.criterion.Restrictions.eq;

import lombok.AccessLevel;
import lombok.Setter;
import org.hibernate.SessionFactory;
import org.openmrs.annotation.OpenmrsProfile;
import org.openmrs.module.emrapi.conditionslist.Condition;
import org.openmrs.module.fhir2.api.dao.FhirConditionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Setter(AccessLevel.PACKAGE)
@OpenmrsProfile(openmrsPlatformVersion = "2.0.* - 2.1.*")
public class FhirConditionDaoImpl implements FhirConditionDao<Condition> {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Condition getConditionByUuid(String uuid) {
		return (Condition) sessionFactory.getCurrentSession()
				.createCriteria(org.openmrs.module.emrapi.conditionslist.Condition.class).add(eq("uuid", uuid))
				.uniqueResult();
	}

	@Override
	public Condition saveCondition(Condition condition) {
		return null;
	}
}
