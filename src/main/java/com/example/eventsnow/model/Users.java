package com.example.eventsnow.model;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "users")
public class Users implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;
    @Column(name="first_name")
    private String firstName;
    @Column(name="last_name")
    private String lastName;
    @Column(name="user_login")
    private String login;
    @Column(name="user_password")
    private String password;
    @Column(name ="is_active")
    private int isActive;
    @Column(name ="organizer_id")
    private int organizerId;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_events", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "event_id"))
    private Set<Events> userEvents;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_role")
    private Roles userRole;
    @ManyToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinTable(name = "types_users", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "type_id"))
    private List<Types> types;


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public Set<Events> getUserEvents() {
        return userEvents;
    }

    public void setUserEvents(Set<Events> userEvents) {
        this.userEvents = userEvents;
    }

    public boolean findUserEvents(Events events) {
        Iterator<Events> itr = userEvents.iterator();
        while (itr.hasNext()) {
            if(itr.next().getEventId()==events.getEventId()) return true;
        }
        return false;
    }

    public void addUserEvents(Events events) {
        this.userEvents.add(events);
    }

    public Roles getUserRole() {
        return userRole;
    }

    public void setUserRole(Roles userRole) {
        this.userRole = userRole;
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

    public int getOrganizerId() {
        return organizerId;
    }

    public void setOrganizerId(int organizerId) {
        this.organizerId = organizerId;
    }

    public boolean isUserEvent(Events event) {
        if(this.userRole.getId() == 2 || this.userRole.getId() == 4){
            for(Organizers organizer: event.getEventOrganizers()){
                if(organizer.getOrganizerId() == this.organizerId)return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Roles> role = new HashSet<>();
        role.add(this.userRole);
        return role;
    }

    @Override
    public String getUsername() {
        return this.login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        if(isActive == 1)return true;
        else return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

   @Override
    public boolean isEnabled() {
        return true;
    }


}
