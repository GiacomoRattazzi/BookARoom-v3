/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookaroom.v3.models;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Giac
 */
@Entity
@Table(name = "reservations")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Reservations.findAll", query = "SELECT r FROM Reservations r"),
    @NamedQuery(name = "Reservations.findByReservationId", query = "SELECT r FROM Reservations r WHERE r.reservationId = :reservationId"),
    @NamedQuery(name = "Reservations.findByReservationNumber", query = "SELECT r FROM Reservations r WHERE r.reservationNumber = :reservationNumber"),
    @NamedQuery(name = "Reservations.findByRoomName", query = "SELECT r FROM Reservations r WHERE r.roomName = :roomName"),
    @NamedQuery(name = "Reservations.findByTotalPrice", query = "SELECT r FROM Reservations r WHERE r.totalPrice = :totalPrice"),
    @NamedQuery(name = "Reservations.findByDateArrival", query = "SELECT r FROM Reservations r WHERE r.dateArrival = :dateArrival"),
    @NamedQuery(name = "Reservations.findByDateDeparture", query = "SELECT r FROM Reservations r WHERE r.dateDeparture = :dateDeparture")})
public class Reservations implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "RESERVATION_ID")
    private Integer reservationId;
    @Column(name = "RESERVATION_NUMBER")
    private Integer reservationNumber;
    @Size(max = 50)
    @Column(name = "ROOM_NAME")
    private String roomName;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "TOTAL_PRICE")
    private Double totalPrice;
    @Column(name = "DATE_ARRIVAL")
    @Temporal(TemporalType.DATE)
    private Date dateArrival;
    @Column(name = "DATE_DEPARTURE")
    @Temporal(TemporalType.DATE)
    private Date dateDeparture;

    public Reservations() {
    }

    public Reservations(Integer reservationId) {
        this.reservationId = reservationId;
    }

    public Integer getReservationId() {
        return reservationId;
    }

    public void setReservationId(Integer reservationId) {
        this.reservationId = reservationId;
    }

    public Integer getReservationNumber() {
        return reservationNumber;
    }

    public void setReservationNumber(Integer reservationNumber) {
        this.reservationNumber = reservationNumber;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getDateArrival() {
        return dateArrival;
    }

    public void setDateArrival(Date dateArrival) {
        this.dateArrival = dateArrival;
    }

    public Date getDateDeparture() {
        return dateDeparture;
    }

    public void setDateDeparture(Date dateDeparture) {
        this.dateDeparture = dateDeparture;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (reservationId != null ? reservationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Reservations)) {
            return false;
        }
        Reservations other = (Reservations) object;
        if ((this.reservationId == null && other.reservationId != null) || (this.reservationId != null && !this.reservationId.equals(other.reservationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bookaroom.v3.models.Reservations[ reservationId=" + reservationId + " ]";
    }
    
}
