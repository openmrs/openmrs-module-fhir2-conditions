/*
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.fhir2conditions.api.impl;

import java.util.Collection;

import ca.uhn.fhir.rest.api.SortSpec;
import ca.uhn.fhir.rest.param.DateRangeParam;
import ca.uhn.fhir.rest.param.QuantityParam;
import ca.uhn.fhir.rest.param.ReferenceAndListParam;
import ca.uhn.fhir.rest.param.TokenAndListParam;
import lombok.AccessLevel;
import lombok.Setter;
import org.hl7.fhir.r4.model.Condition;
import org.openmrs.annotation.OpenmrsProfile;
import org.openmrs.module.fhir2.api.FhirConditionService;
import org.openmrs.module.fhir2.api.dao.FhirConditionDao;
import org.openmrs.module.fhir2.api.translators.ConditionTranslator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Primary
@Component
@Transactional
@Setter(AccessLevel.PACKAGE)
@OpenmrsProfile(openmrsPlatformVersion = "2.0.* - 2.1.*")
public class FhirConditionServiceImpl implements FhirConditionService {

	@Autowired
	private FhirConditionDao<org.openmrs.module.emrapi.conditionslist.Condition> dao;

	@Autowired
	private ConditionTranslator<org.openmrs.module.emrapi.conditionslist.Condition> conditionTranslator;

	@Override
	public Condition getConditionByUuid(String uuid) {
		return conditionTranslator.toFhirResource(dao.getConditionByUuid(uuid));
	}

	@Override
	public Collection<Condition> searchConditions(ReferenceAndListParam referenceAndListParam,
			ReferenceAndListParam referenceAndListParam1, TokenAndListParam tokenAndListParam,
			TokenAndListParam tokenAndListParam1, DateRangeParam dateRangeParam, QuantityParam quantityParam,
			DateRangeParam dateRangeParam1, SortSpec sortSpec) {
		return null;
	}

	@Override
	public Condition saveCondition(Condition condition) {
		return null;
	}
}