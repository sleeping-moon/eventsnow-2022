package com.example.eventsnow.model;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "events")
public class Events {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int eventId;
    @Column
    private String title;
    @Column
    private String Description;
    @Column(name = "creation_date")
    private Date creationDate;
    @Column(name = "date_of_event")
    private Date dateOfEvent;
    @Column(name = "is_active")
    private int isActive;
    @Column(name = "number_of_places")
    private int numberOfPlaces;
    @Column(name = "poster")
    private String poster;
    @Column(name = "second_poster")
    private String secondPoster;
    @ManyToMany
    @JoinTable(name = "organizers_events", joinColumns = @JoinColumn(name = "event_id"), inverseJoinColumns = @JoinColumn(name = "organizer_id"))
    private List<Organizers> eventOrganizers;
    @ManyToMany
    @JoinTable(name = "types_events", joinColumns = @JoinColumn(name = "event_id"), inverseJoinColumns = @JoinColumn(name = "type_id"))
    private List<Types> types;
    @ManyToMany
    @JoinTable(name = "users_events", joinColumns = @JoinColumn(name = "event_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<Users> users;
    @Transient
    private String dateOfEventInString;

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getDateOfEvent() {
        return dateOfEvent;
    }

    public void setDateOfEvent(Date dateOfEvent) {
        this.dateOfEvent = dateOfEvent;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public List<Organizers> getEventOrganizers() {
        return eventOrganizers;
    }

    public void setEventOrganizers(List<Organizers> eventOrganizers) {
        this.eventOrganizers = eventOrganizers;
    }

    public void addEventOrganizers(Organizers eventOrganizers) {
        this.eventOrganizers.add(eventOrganizers);
    }

    public String getDateOfEventInString() {
        return dateOfEventInString;
    }

    public void setDateOfEventInString(String dateOfEventInString) {
        this.dateOfEventInString = dateOfEventInString;
    }

    public int getNumberOfPlaces() {
        return numberOfPlaces;
    }

    public void setNumberOfPlaces(int numberOfPlaces) {
        this.numberOfPlaces = numberOfPlaces;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getSecondPoster() {
        return secondPoster;
    }

    public void setSecondPoster(String secondPoster) {
        this.secondPoster = secondPoster;
    }

    public List<Types> getTypes() {
        return types;
    }

    public void setTypes(List<Types> types) {
        this.types = types;
    }

    public void addTypes(Types userType) {
        this.types.add(userType);
    }

    public List<Users> getUsers() {
        return users;
    }

    public void setUsers(List<Users> users) {
        this.users = users;
    }

    public static Date getDateFromString(String dateString) {
        try {
           Date date = new Date(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse(dateString).getTime());
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static  String getStringFromDate(Date date) {
            String dateString = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").format(date);
            return dateString;
    }

    public  String getStringDate() {
        String dateString = new SimpleDateFormat("dd.MM.yyyy' 'HH:mm").format(this.dateOfEvent);
        return dateString;
    }

    public  String getStringCreationDate() {
        String dateString = new SimpleDateFormat("dd.MM.yyyy").format(this.creationDate);
        return dateString;
    }

    public boolean  dateCheak(){
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm");
        try {
            date = sdf.parse(sdf.format(new Date()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(this.getDateOfEvent().getTime() <date.getTime()) return false;
        else return true;
    }
}
