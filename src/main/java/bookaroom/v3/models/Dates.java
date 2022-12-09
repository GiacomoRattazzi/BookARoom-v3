package bookaroom.v3.models;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Team BookARoom
 */

@Entity
@Table(name = "dates")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Dates.findAll", query = "SELECT d FROM Dates d"),
    @NamedQuery(name = "Dates.findByDateId", query = "SELECT d FROM Dates d WHERE d.dateId = :dateId"),
    @NamedQuery(name = "Dates.findByRoomName", query = "SELECT d FROM Dates d WHERE d.roomName = :roomName"),
    @NamedQuery(name = "Dates.findByRoomDate", query = "SELECT d FROM Dates d WHERE d.roomDate = :roomDate")})
    @NamedQuery(name = "Dates.findByRoomNameAndDate", query = "SELECT d FROM Dates d WHERE d.roomDate = :roomDate AND d.roomName = :roomName")
public class Dates implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "DATE_ID")
    private Integer dateId;
    @Size(max = 50)
    @Column(name = "ROOM_NAME")
    private String roomName;
    @Size(max = 50)
    @Column(name = "ROOM_DATE")
    private String roomDate;

    public Dates() {
    }

    public Dates(Integer dateId) {
        this.dateId = dateId;
    }

    public Integer getDateId() {
        return dateId;
    }

    public void setDateId(Integer dateId) {
        this.dateId = dateId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomDate() {
        return roomDate;
    }

    public void setRoomDate(String roomDate) {
        this.roomDate = roomDate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dateId != null ? dateId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Dates)) {
            return false;
        }
        Dates other = (Dates) object;
        if ((this.dateId == null && other.dateId != null) || (this.dateId != null && !this.dateId.equals(other.dateId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bookaroom.v3.models.Dates[ dateId=" + dateId + " ]";
    }
    
}
