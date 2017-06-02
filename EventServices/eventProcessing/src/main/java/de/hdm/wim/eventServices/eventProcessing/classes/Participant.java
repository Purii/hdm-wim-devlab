package de.hdm.wim.eventServices.eventProcessing.classes;

/**
 * Created by Ben on 16.01.2017.
 */
public class Participant {

    private String firstName;
    private String lastName;

    /**
     * Instantiates a new Participant.
     *
     * @param firstName first name
     * @param lastName  last name
     */
    public Participant(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Instantiates a new Participant.
     */
    public Participant(){}

    /**
     * Gets first name.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets first name.
     *
     * @param firstName the first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets last name.
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets last name.
     *
     * @param lastName the last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "[Participant] "
                + "firstName: " + firstName
                + ", lastName: " + lastName;
    }
}
