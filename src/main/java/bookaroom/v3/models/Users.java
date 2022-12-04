/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookaroom.v3.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Giac
 */
@Entity
@Table(name = "users")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Users.findAll", query = "SELECT u FROM Users u"),
    @NamedQuery(name = "Users.findByUserId", query = "SELECT u FROM Users u WHERE u.userId = :userId"),
    @NamedQuery(name = "Users.findByUsername", query = "SELECT u FROM Users u WHERE u.username = :username"),
    @NamedQuery(name = "Users.findByFirstName", query = "SELECT u FROM Users u WHERE u.firstName = :firstName"),
    @NamedQuery(name = "Users.findByLastName", query = "SELECT u FROM Users u WHERE u.lastName = :lastName"),
    @NamedQuery(name = "Users.findByEmail", query = "SELECT u FROM Users u WHERE u.email = :email"),
    @NamedQuery(name = "Users.findByPassword", query = "SELECT u FROM Users u WHERE u.password = :password"),
    @NamedQuery(name = "Users.findByCcnumber", query = "SELECT u FROM Users u WHERE u.ccnumber = :ccnumber"),
    @NamedQuery(name = "Users.findByCccode", query = "SELECT u FROM Users u WHERE u.cccode = :cccode"),
    @NamedQuery(name = "Users.findByCcexpirationdate", query = "SELECT u FROM Users u WHERE u.ccexpirationdate = :ccexpirationdate")})
public class Users implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "USER_ID")
    private Integer userId;
    @Size(max = 50)
    @Column(name = "USERNAME")
    private String username;
    @Size(max = 50)
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Size(max = 50)
    @Column(name = "LAST_NAME")
    private String lastName;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 50)
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "PASSWORD")
    private Integer password;
    @Size(max = 50)
    @Column(name = "CCNUMBER")
    private String ccnumber;
    @Size(max = 50)
    @Column(name = "CCCODE")
    private String cccode;
    @Column(name = "CCEXPIRATIONDATE")
    @Temporal(TemporalType.DATE)
    private Date ccexpirationdate;
    @JoinTable(name = "user_has_reservation", joinColumns = {
        @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")}, inverseJoinColumns = {
        @JoinColumn(name = "RESERVATION_ID", referencedColumnName = "RESERVATION_ID")})
    @ManyToMany
    private List<Reservations> reservationsList;

    public Users() {
    }
    
    public boolean isPasswordCorrect(String password) {
        return password.hashCode() == this.password;
    }

    public Users(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getPassword() {
        return password;
    }

    public void setPassword(Integer password) {
        this.password = password;
    }

    public String getCcnumber() {
        return ccnumber;
    }

    public void setCcnumber(String ccnumber) {
        this.ccnumber = ccnumber;
    }

    public String getCccode() {
        return cccode;
    }

    public void setCccode(String cccode) {
        this.cccode = cccode;
    }

    public Date getCcexpirationdate() {
        return ccexpirationdate;
    }

    public void setCcexpirationdate(Date ccexpirationdate) {
        this.ccexpirationdate = ccexpirationdate;
    }

    @XmlTransient
    public List<Reservations> getReservationsList() {
        return reservationsList;
    }

    public void setReservationsList(List<Reservations> reservationsList) {
        this.reservationsList = reservationsList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userId != null ? userId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Users)) {
            return false;
        }
        Users other = (Users) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bookaroom.v3.models.Users[ userId=" + userId + " ]";
    }
    
}
