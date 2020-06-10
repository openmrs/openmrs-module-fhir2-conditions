/*
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.fhir2conditions.api.translators.impl;

import lombok.AccessLevel;
import lombok.Setter;
import org.hl7.fhir.r4.model.CodeableConcept;
import org.hl7.fhir.r4.model.DateTimeType;
import org.openmrs.User;
import org.openmrs.annotation.OpenmrsProfile;
import org.openmrs.module.emrapi.conditionslist.Condition;
import org.openmrs.module.fhir2.api.translators.ConceptTranslator;
import org.openmrs.module.fhir2.api.translators.ConditionTranslator;
import org.openmrs.module.fhir2.api.translators.PatientReferenceTranslator;
import org.openmrs.module.fhir2.api.translators.PractitionerReferenceTranslator;
import org.openmrs.module.fhir2.api.translators.ProvenanceTranslator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Setter(AccessLevel.PACKAGE)
@Component("fhir2conditions.conditionTranslatorImpl")
@OpenmrsProfile(openmrsPlatformVersion = "2.0.* - 2.1.*")
public class ConditionTranslatorImpl implements ConditionTranslator<Condition> {
	
	@Autowired
	private PatientReferenceTranslator patientReferenceTranslator;
	
	@Autowired
	private ConceptTranslator conceptTranslator;
	
	@Autowired
	private PractitionerReferenceTranslator<User> practitionerReferenceTranslator;
	
	@Autowired
	private ProvenanceTranslator<Condition> provenanceTranslator;
	
	@Override
	public org.hl7.fhir.r4.model.Condition toFhirResource(Condition condition) {
		if (condition == null) {
			return null;
		}
		org.hl7.fhir.r4.model.Condition fhirCondition = new org.hl7.fhir.r4.model.Condition();
		fhirCondition.setId(condition.getUuid());
		fhirCondition.setSubject(patientReferenceTranslator.toFhirResource(condition.getPatient()));
		
		if (condition.getConcept() != null) {
			CodeableConcept codeableConcept = conceptTranslator.toFhirResource(condition.getConcept());
			if (condition.getConditionNonCoded() != null) {
				codeableConcept.setText(condition.getConditionNonCoded());
			}
			fhirCondition.setCode(codeableConcept);
		}
		
		fhirCondition.setOnset(new DateTimeType().setValue(condition.getOnsetDate()));
		fhirCondition.setRecorder(practitionerReferenceTranslator.toFhirResource(condition.getCreator()));
		fhirCondition.setRecordedDate(condition.getDateCreated());
		fhirCondition.getMeta().setLastUpdated(condition.getDateChanged());
		fhirCondition.addContained(provenanceTranslator.getCreateProvenance(condition));
		fhirCondition.addContained(provenanceTranslator.getUpdateProvenance(condition));
		
		return fhirCondition;
	}
	
	@Override
	public Condition toOpenmrsType(org.hl7.fhir.r4.model.Condition condition) {
		return this.toOpenmrsType(new Condition(), condition);
	}
	
	@Override
	public Condition toOpenmrsType(Condition existingCondition, org.hl7.fhir.r4.model.Condition condition) {
		if (condition == null) {
			return existingCondition;
		}
		existingCondition.setUuid(condition.getId());
		existingCondition.setPatient(patientReferenceTranslator.toOpenmrsType(condition.getSubject()));
		
		if (!condition.getCode().isEmpty()) {
			existingCondition.setConcept(conceptTranslator.toOpenmrsType(condition.getCode()));
		}
		if (condition.getCode().hasText()) {
			existingCondition.setConditionNonCoded(condition.getCode().getText());
		}
		existingCondition.setOnsetDate(condition.getOnsetDateTimeType().getValue());
		existingCondition.setCreator(practitionerReferenceTranslator.toOpenmrsType(condition.getRecorder()));
		existingCondition.setDateCreated(condition.getRecordedDate());
		existingCondition.setDateChanged(condition.getMeta().getLastUpdated());
		
		return existingCondition;
	}
}
