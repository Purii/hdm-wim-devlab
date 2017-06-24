# Topics

Für jedes Event werden gültige Topics festgestellt. Entsprechend gehen diese Topics aus der [Dokumentation der Events](https://github.com/Purii/hdm-wim-devlab/blob/master/docs/Events.md) hervor.

Bei Fragen oder Vorschlägen bitte Issue [#32](https://github.com/Purii/hdm-wim-devlab/issues/32) nutzen.

| Topic | Events | Publisher | Subscriber |
| :---- | :---- | :---- |:---- |
| SEMREP_INFORMATION | <ul><li>DocumentInformationEvent</li><li>UserInformationEvent</li><li>AllCompaniesEvent</li><li>AllDepartmentsEvent</li><li>AllProjectsEvent</li><li>AllProjectRolesEvent</li><li>DepartmentInformationEvent</li><li>ProjectInformationEvent</li></ul> | SemRep | CEP, GUI |
| SEMREP_OFFERS | <ul><li>InformationToAllDocumentsEvent</li><li>OfferEvent</li></ul> | SemRep | GUI |
| GUI_FEEDBACK | <ul><li>FeedbackEvent</li><li>SuccessfulFeedbackEvent></ul> | GUI | SemRep, CEP, ML |
| GUI_INFORMATION | <ul><li>AdditionalUserInformation></li></ul> | GUI | SemRep |
| GUI_SESSIONINSIGHTS | <ul><li>UserLoginEvent</li><li>SessionStartEvent</li><li>UserStartEvent</li><li>StayAliveEvent</li><li>UserJoinedSession</li><li>UserLeftSession</li></ul> | GUI | SemRep, CEP, ST |
| ML_LEARNING | <ul><li>LearnEvent</li></ul> | ML | SemRep |
| ST_TOKEN | <ul><li>TokenEvent</li></ul> | ST | SemRep |
| CEP_CONTEXT | <ul><li>DocumentContextEvent</li><li>UserContextEvent</li></ul> | CEP | SemRep |
| CEP_SESSIONINSIGHTS | <ul><li>SessionEndEvent</li><li>UserInactiveEvent</li></ul> | CEP | GUI, ST, ML |
| CEP_INSIGHT | <ul><li>DocumentHighlyRelevantEvent</li></ul> | CEP | SemRep |


