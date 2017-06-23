package org.semrep.rest.interfaces;

import de.hdm.wim.sharedLib.Constants;

/**
 * Created by mateo_alliaj on 23.06.17.
 */
public class ConstantsImpl {

	// fill HashMap

	// "DocumentInformationEvent"
	String DokID = Constants.PubSub.AttributeKey.DOCUMENT_ID;
	String DokName = Constants.PubSub.AttributeKey.DOCUMENT_NAME;
	String DokPrio = Constants.PubSub.AttributeKey.DOCUMENT_PRIO;
	String DokTyp = Constants.PubSub.AttributeKey.DOCUMENT_TYPE;
	String DokURL = Constants.PubSub.AttributeKey.DOCUMENT_URL;
	String DokOrdner = Constants.PubSub.AttributeKey.DOCUMENT_FOLDER;

// "UserInformationEvent"
	String UserID = Constants.PubSub.AttributeKey.USER_ID;
	String Vorname = Constants.PubSub.AttributeKey.FIRST_NAME;
	String Nachname = Constants.PubSub.AttributeKey.LAST_NAME;
	String Mail = Constants.PubSub.AttributeKey.EMAIL;
	// Fehlen in Constants
/*	String Projekt = Constants.PubSub.AttributeKey.;
	String Projektrolle = Constants.PubSub.AttributeKey.;
	String Abteilung = Constants.PubSub.AttributeKey.;
	String DokAutor = Constants.PubSub.AttributeKey.;
	String DokAufrufe = Constants.PubSub.AttributeKey.;
	String DokFavorit = Constants.PubSub.AttributeKey.;*/



//	"ProjectInformationEvent"
	String ProjektID = Constants.PubSub.AttributeKey.PROJECT_ID;
	String ProjektName = Constants.PubSub.AttributeKey.PROJECT_NAME;
	String ProjektGehoertZuUnternehmen = Constants.PubSub.AttributeKey.PROJECT_BELONGS_TO_COMPANY;
	String ProjektGehoertZuAbteilung = Constants.PubSub.AttributeKey.PROJECT_BELONGS_TO_DEPARTMENT;
	String ProjektHatProjektmitglied = Constants.PubSub.AttributeKey.PROJECT_HAS_MEMBERS;
	String ProjektHatDokument = Constants.PubSub.AttributeKey.PROJECT_HAS_DOCUMENTS;


// "DepartmentInformationEvent"
	String AbteilungID = Constants.PubSub.AttributeKey.DEPARTMENT_ID;
	String AbteilungName = Constants.PubSub.AttributeKey.DEPARTMENT_NAME;
	String AbteilungKuerzel = Constants.PubSub.AttributeKey.DEPARTMENT_SHORT;
	String AbteilungHatProjekt = Constants.PubSub.AttributeKey.DEPARTMENT_HAS_PROJECT;
	String AbteilungHatMitarbeiter = Constants.PubSub.AttributeKey.DEPARTMENT_HAS_WORKER;
	String AbteilungGehoertZuUnternehmen = Constants.PubSub.AttributeKey.DEPARTMENT_BELONGS_TO_COMPANY;


// "AllProjectsEvent"
	String ProjektNamen = Constants.PubSub.AttributeKey.PROJECT_NAMES;


// "AllProjectRolesEvent"
	String Projektrollen = Constants.PubSub.AttributeKey.PROJECT_ROLES;


// "AllDepartmentsEvent"
	String AbteilungNamen = Constants.PubSub.AttributeKey.DEPARTMENT_NAMES;


// "AllCompaniesEvent"
	String UnternehmenName = Constants.PubSub.AttributeKey.COMPANY_NAMES;


}
