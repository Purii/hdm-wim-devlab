package org.semrep.rest.interfaces.PubSub;

import de.hdm.wim.sharedLib.Constants;

/**
 * Created by mateo_alliaj on 23.06.17.
 */
public class ConstantsImpl {

	// SessionID, TimeStamp, TokenID
	String SessionID = Constants.PubSub.AttributeKey.SESSION_ID;
	String TimeStamp = Constants.PubSub.AttributeKey.TIMESTAMP;
	String TokenID = Constants.PubSub.AttributeKey.TOKEN_ID;


	// fill HashMap

	// "DocumentInformationEvent"
	String DokID = Constants.PubSub.AttributeKey.DOCUMENT_ID;
	String DokName = Constants.PubSub.AttributeKey.DOCUMENT_NAME;
	String DokPrio = Constants.PubSub.AttributeKey.DOCUMENT_PRIO;
	String DokTyp = Constants.PubSub.AttributeKey.DOCUMENT_TYPE;
	String DokURL = Constants.PubSub.AttributeKey.DOCUMENT_URL;
	String DokOrdner = Constants.PubSub.AttributeKey.DOCUMENT_FOLDER;
	// bei Tokenizer noch: (müsste optional sein?)
	String DokErstelldatum = Constants.PubSub.AttributeKey.DOCUMENT_ADDED;
	String DokVersion = Constants.PubSub.AttributeKey.DOCUMENT_VERSION;
	// doppelt (String DokTyp = Constants.PubSub.AttributeKey.DOCUMENT_TYPE;)
	String DokumentVerfasstVonPerson = Constants.PubSub.AttributeKey.DOCUMENT_WRITTEN_BY;
	String DokumentGehoertZuProjekt = Constants.PubSub.AttributeKey.DOCUMENT_BELONGS_TO_PROJECT;
	String DokumentFavorisiertVonPerson = Constants.PubSub.AttributeKey.DOCUMENT_FAVORED_BY;
	String DokKeywords = Constants.PubSub.AttributeKey.DOCUMENT_KEYWORDS;
	//doppelt (String DokOrdner = Constants.PubSub.AttributeKey.DOCUMENT_FOLDER;)


	// "UserInformationEvent"
	String UserID = Constants.PubSub.AttributeKey.USER_ID;
	String Vorname = Constants.PubSub.AttributeKey.FIRST_NAME;
	String Nachname = Constants.PubSub.AttributeKey.LAST_NAME;
	String Mail = Constants.PubSub.AttributeKey.EMAIL;
	String Projekt = Constants.PubSub.AttributeKey.USER_WORKS_ON_PROJECTS;
	String Projektrolle = Constants.PubSub.AttributeKey.USER_HAS_PROJECTROLE;
	String Abteilung = Constants.PubSub.AttributeKey.USER_BELONGS_TO_DEPARTMENT;
	String DokAutor = Constants.PubSub.AttributeKey.USER_WRITES_DOCUMENT;
	String DokAufrufe = Constants.PubSub.AttributeKey.USER_CALLS_DOCUMENT;
	String DokFavorit = Constants.PubSub.AttributeKey.USER_FAVOURS_DOCUMENT;



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
