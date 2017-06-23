# Topics

Für jedes Event werden gültige Topics festgestellt. Entsprechend gehen diese Topics aus der [Dokumentation der Events](https://github.com/Purii/hdm-wim-devlab/blob/master/docs/Events.md) hervor.

| Topic | Events | Publisher | Subscriber |
| :---- | :---- | :---- |:---- |
| SemRep_Information | <ul><li>DocumentInformationEvent</li><li>UserInformationEvent</li><li>AllCompaniesEvent</li><li>AllDepartmentsEvent</li><li>AllProjectsEvent</li><li>AllProjectRolesEvent</li><li>DepartmentInformationEvent</li><li>ProjectInformationEvent</li></ul> | SemRep | CEP, GUI |
| SemRep_Offers | <ul><li>InformationToAllDocumentsEvent</li><li>OfferEvent</li></ul> | SemRep | GUI |
| GUI_Feedback | <ul><li>FeedbackEvent</li><li>SuccessfulFeedbackEvent></ul> | GUI | SemRep, CEP, ML |
| GUI_Information | <ul><li>AdditionalUserInformation></li></ul> | GUI | SemRep |
| GUI_SessionInsights | <ul><li>UserLoginEvent</li><li>SessionStartEvent</li><li>UserStartEvent</li><li>StayAliveEvent</li><li>UserJoinedSession</li><li>UserLeftSession</li></ul> | GUI | SemRep, CEP, ST |
| ML_Learning | <ul><li>LearnEvent</li></ul> | ML | SemRep |
| ST_Token | <ul><li>TokenEvent</li></ul> | ST | SemRep |
| CEP_Context | <ul><li>DocumentContextEvent</li><li>UserContextEvent</li></ul> | CEP | SemRep |
| CEP_SessionInsights | <ul><li>SessionEndEvent</li><li>UserInactiveEvent</li></ul> | CEP | GUI, ST, ML |
| CEP_Insight | <ul><li>DocumentHighlyRelevantEvent</li></ul> | CEP | SemRep |


