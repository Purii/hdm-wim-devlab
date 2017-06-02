package de.hdm.wim.eventServices.eventProcessing.cep.events;

import com.google.gson.Gson;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 * Created by Ben on 15.01.2017.
 */
public class ComplexTokenEvent {

    // Information for Recommender and/or Semantic Group
    // Alphabetical order!

    /**
     * The Constant CALENDAR.
     */
    public static final String CALENDAR = "google calender";

    /**
     * The Constant COSTDOCUMENTS.
     */
    public static final String COSTDOCUMENTS = "documents about expenses";

    /**
     * The Constant DRIVE.
     */
    public static final String DRIVE = "google drive";

    /**
     * The Constant MILESTONE.
     */
    public static final String MILESTONE = "milestones";

    /**
     * The Constant PROJECTDOCUMENTS.
     */
    public static final String PROJECTDOCUMENTS = "projectdocuments";

    /**
     * The Constant PROJECTPLANNING.
     */
    public static final String PROJECTPLANNING = "project plan";

    /**
     * The Constant PROTOCOL.
     */
    public static final String PROTOCOL = "protocol";

    /**
     * The Constant PROTOCOLCREATION.
     */
    public static final String PROTOCOLCREATION = "creation of protocol";

    /**
     * The Constant TASKLIST.
     */
    public static final String TASKLIST = "tasklist";


    // Information about date/time relation

    /**
     * The Constant DATERANGE_NEXT.
     */
    public static final String DATERANGE_NEXT = "next";

    /**
     * The Constant DATERANGE_PREV.
     */
    public static final String DATERANGE_PREV = "previous";

    /**
     * The Constant DATE_MONDAY.
     */
    public static final String DATE_MONDAY = "monday";

    /**
     * The Constant DATE_TUESDAY.
     */
    public static final String DATE_TUESDAY = "tuesday";

    /**
     * The Constant DATE_WEDNESDAY.
     */
    public static final String DATE_WEDNESDAY = "wednesday";

    /**
     * The Constant DATE_THURSDAY.
     */
    public static final String DATE_THURSDAY = "thursday";

    /**
     * The Constant DATE_FRIDAY.
     */
    public static final String DATE_FRIDAY= "friday";

    /**
     * The Constant DATE_SATURDAY.
     */
    public static final String DATE_SATURDAY = "saturday";

    /**
     * The Constant DATE_SUNDAY.
     */
    public static final String DATE_SUNDAY = "sunday";


    /** The date the ComplexTokenEvent is related to. */
    private LocalDate relatedToDate;

    /** The project the ComplexTokenEvent is related to. */
    private String relatedToProject;

    /** The time the ComplexTokenEvent is related to. */
    private LocalTime relatedToTime;

    /** The topics. */
    private ArrayList<String> topics = new ArrayList<String>();

    /**
     * Custom toString method
     */
    public String toString() {
        if(this.getTopics().contains(ComplexTokenEvent.CALENDAR)){
            this.getTopics().remove(ComplexTokenEvent.CALENDAR);

            return "Set new meeting "
                    + addTopicsToString()
                    + addRelatedToProjectsToString()
                    + addDateToString()
                    + addTimeToString();
        } else {
            return "Get "
                    + addTopicsToString()
                    + addRelatedToProjectsToString()
                    + addDateToString()
                    + addTimeToString();
        }
    }

    /**
     * If there is an related date, it will be added to the string
     *
     * @return " on " + the related date
     */
    private String addDateToString(){
        if(this.getRelatedToDate() == null)
            return "";

        return " on " + this.getRelatedToDate().toString();
    }

    /**
     * If there is an related time, it will be added to the string
     *
     * @return " at " + the related time
     */
    private String addTimeToString(){
        if(this.getRelatedToTime() == null)
            return "";

        return " at " + this.getRelatedToTime().toString();
    }

    /**
     * If there is on or more related topics, it will be added to the string
     *
     * @return " for " + the related topics
     */
    private String addTopicsToString(){
        if(this.getTopics().isEmpty())
            return "";

        return String.join(", ", this.getTopics());
    }

    /**
     * If there is an related project, it will be added to the string
     *
     * @return " related to project " + the name of the project, e.g. "HighNet"
     */
    private String addRelatedToProjectsToString() {
        return "related to project " + this.getRelatedToProject();
    }

    /**
     * Converts string to to json.
     *
     * @return the string
     */
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    /**
     * Gets the topics.
     *
     * @return the topic
     */
    public ArrayList<String> getTopics() {
        return topics;
    }

    /**
     * Adds the topic.
     *
     * @param topic the topic to set
     */
    public void addTopic(String topic) {
        if(!topics.contains(topic)){
            this.topics.add(topic);
        }
    }

    /**
     * Gets the project the ComplexTokenEvent is related to.
     *
     * @return string name of the related project
     */
    public String getRelatedToProject() {
        return relatedToProject;
    }

    /**
     * Sets the project, the ComplexTokenEvent is related to.
     *
     * @param relatedToProject the related to project
     */
    public void setRelatedToProject(String relatedToProject) {
        this.relatedToProject = relatedToProject;
    }

    /**
     * Gets the time, the ComplexTokenEvent is related to.
     *
     * @return the relatedToTime
     */
    public LocalTime getRelatedToTime() {
        return relatedToTime;
    }

    /**
     * Sets the time, the ComplexTokenEvent is related to.
     *
     * @param relatedToTime the relatedToTime to set
     */
    public void setRelatedToTime(LocalTime relatedToTime) {
        this.relatedToTime = relatedToTime;
    }

    /**
     * Gets the date, the ComplexTokenEvent is related to.
     *
     * @return the relatedToDate
     */
    public LocalDate getRelatedToDate() {
        return relatedToDate;
    }

    /**
     * Sets the date, the ComplexTokenEvent is related to.
     *
     * @param relatedToDate the related to date
     */
    public void setRelatedToDate(LocalDate relatedToDate) {
        this.relatedToDate = relatedToDate;
    }
}
