package bookaroom.v3.beans;

import bookaroom.v3.exceptions.DoesNotExistException;
import bookaroom.v3.models.Rooms;
import bookaroom.v3.models.Reservations;
import bookaroom.v3.models.Users;     
import bookaroom.v3.models.Dates;   
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.primefaces.PrimeFaces;

/*
 * @author Team BookARoom
 */
@Named(value = "roomBean")
@SessionScoped

public class RoomBean implements Serializable {
    
    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;
    
    private String roomName = "";
    private double totalPrice = 0;
    private List<LocalDate> range = null;
    private List<LocalDate> betweenRange;
    private LocalDate test1;
    private Boolean roomEmpty = true;
    private String temp1;
    private String temp2;
    
    private int resNbr = 0;
    private int RemoveResNbr = 0;
    private String DelRoomName = "";
    private List<LocalDate> DelDates = null;

    public ArrayList<Rooms> getRooms() {
        Query query = em.createNamedQuery("Rooms.findAll");
        return new ArrayList<>(query.getResultList());
    }
    
    public List<Dates> getBookedDates() {
        Query query = em.createNamedQuery("Dates.findByRoomName");
        List<Dates> dates = query.setParameter("roomName", roomName).getResultList();
        List<String> n;
        return dates;
    }
    
    private Rooms findRoomByNameInTheHotel(String roomName) throws DoesNotExistException {
        Query query = em.createNamedQuery("Rooms.findByRoomName");
        List<Rooms> rooms = query.setParameter("roomName", roomName).getResultList(); {
            if (rooms.size() > 0) {
                return rooms.get(0);
            }
        }
        throw new DoesNotExistException(roomName + " does not exist.");
    }
    
    public String getRoomName() {
        return roomName;
    }
    
  
    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }
    
    public void click() {
        if (roomEmpty == false) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "This date is already booked: " + temp2 ));
        }else {
            PrimeFaces.current().ajax().update("form:display");
            PrimeFaces.current().executeScript("PF('dlg').show()");
        }
    }
    
    public List<LocalDate> getRange() {
        return range;
    }
 
    public void setRange(List<LocalDate> range) {
        this.range = range;
    }
    
    public LocalDate getToday() {
        return LocalDate.now();
    }
    
    public List<LocalDate> getDatesBetween() { 
        if(range == null) {
            return null;
        } else {
            long numOfDaysBetween = ChronoUnit.DAYS.between(range.get(0), range.get(1)); 
            return IntStream.iterate(0, i -> i + 1)
              .limit(numOfDaysBetween)
              .mapToObj(i -> range.get(0).plusDays(i))
              .collect(Collectors.toList());
                
        }
    }
    
     public void setDatesBetween(List<LocalDate> datesBetween) {
        this.betweenRange = betweenRange;
    }
     
    public void dateFor() {
        roomEmpty = true;
        Query query = em.createNamedQuery("Dates.findByRoomName");
        List<Dates> dates = query.setParameter("roomName", roomName).getResultList();
        if (dates.size() > 0) {
            System.out.println(dates);
        }
        
    }
    
    //NEW PRICE
//    private double findRoomPrice() {
//       
//         }
//    
    public double getTotalPrice() {
        if (range!=null){
            long diffDays = ChronoUnit.DAYS.between(range.get(0), range.get(1));
            totalPrice = diffDays; //*findRoomPrice()
                return totalPrice;
        }else{
            return 0;
        }
    }
    
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
    

    
    
}
