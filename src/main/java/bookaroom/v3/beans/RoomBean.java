package bookaroom.v3.beans;

import bookaroom.v3.exceptions.DoesNotExistException;
import bookaroom.v3.models.Rooms;
import bookaroom.v3.models.Reservations;
import bookaroom.v3.models.Users;     
import bookaroom.v3.models.Dates;   
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.Date;
import java.time.Instant;
import java.time.ZoneId;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
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
    private LocalDate DelArrivalDate;
    private LocalDate DelDepartureDate;
    
    public ArrayList<Rooms> getRooms() {
        Query query = em.createNamedQuery("Rooms.findAll");
        return new ArrayList<>(query.getResultList());
    }
    
    public ArrayList<String> getBookedDates() {
        Query query = em.createNamedQuery("Dates.findByRoomName");
        List<Dates> dates = query.setParameter("roomName", roomName).getResultList();
        ArrayList<String> n = new ArrayList<>();
        for (int i = 0; i < dates.size(); i++ )  {
            n.add(dates.get(i).getRoomDate()); 
        }
        return n;   
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
    
    public String setRoomNameAndRedirect(String roomName) {
        this.roomName = roomName;
        return "/UserPage/Booking.xhtml?faces-redirect=true";
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
    
    public LocalDate getNextMonth() {
        LocalDate today = LocalDate.now();
        return today.plusMonths(1).withDayOfMonth(1);
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
    
    //Check if the user inputted dates are available for the current room
    public void dateFor() {
        roomEmpty = true;
        temp2 = "";
        for (LocalDate tempBooked : getDatesBetween()) {
            test1 = tempBooked;
            for (String dateBooked : getBookedDates()) {
                if(test1.toString().equals(dateBooked) == true) {
                    roomEmpty = false;
                    temp2 = dateBooked;
                    break;
                }
            }
        }
        }
        
    
    
    //find price of current room that user is booking
    private double findRoomPrice() {
        Query query = em.createNamedQuery("Rooms.findByRoomName");
        List<Rooms> rooms = query.setParameter("roomName", roomName).getResultList();
                return rooms.get(0).getRoomPrice();
            }
        
       
    //get total price of the room that user is booking (nbr of nights*roomPrice)
    public double getTotalPrice() {
        if (range!=null){
            long diffDays = ChronoUnit.DAYS.between(range.get(0), range.get(1));
            totalPrice = diffDays * findRoomPrice();
            System.out.println(totalPrice);
                return totalPrice;
             
        }else{
            return 0;
        }
    }
    
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
    
    @Transactional
    public void finish() {
        
        dateFor(); 
        if (roomEmpty==false) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "This date is already booked: " + temp2 ));
        }else{
            
            addDatesBooked();
            addResToRes();
            PrimeFaces.current().ajax().update("form:display");
            PrimeFaces.current().executeScript("PF('dlg').show()");
            
          
            
        }
        }
    
    
    //get the latest reservation number in the database
    private int getLatestResNumber() {
        Query query = em.createNamedQuery("Reservations.findAll");
        List<Reservations> reservations = query.getResultList();
        return reservations.get(reservations.size() - 1).getReservationNumber();
}       
    
    //add reservation to the database
    @Transactional
    public void addResToRes() {
        Users user = LoginBean.getUserLoggedIn();
        Reservations newReservation = new Reservations();
        newReservation.setRoomName(roomName);
        newReservation.setTotalPrice(getTotalPrice());
        newReservation.setReservationNumber(getLatestResNumber()+1);
        newReservation.setDateArrival(range.get(0).toString());
        newReservation.setDateDeparture(range.get(1).toString());
        user.getReservationsList().add(newReservation);
        em.persist(newReservation);
        em.merge(user);
    }
    
    //add booked dates to database
    @Transactional
    public void addDatesBooked() {
       List<LocalDate> tempdate = getDatesBetween();
       for (int i = 0; i <tempdate.size(); i++) {
           Dates date = new Dates();
           date.setRoomName(roomName);
           date.setRoomDate(tempdate.get(i).toString());
           em.persist(date);
       }
    }
    
 
    @Transactional
    public void removeRoomFromReservations() {
        Users user = LoginBean.getUserLoggedIn();

        try {
            if (doesResExistInReservations(user,RemoveResNbr)) {
                deleteDatesBooked();
                Reservations res = findResByNumberInReservations(user, RemoveResNbr);
                List uList = user.getReservationsList();
                uList.remove(res);
                em.merge(user);
                
                
            }
        } catch (DoesNotExistException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    private boolean doesResExistInReservations(Users user, int resnbr) {
        try {
            return findResByNumberInReservations(user, resnbr) != null;
        } catch (DoesNotExistException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }
    
    private Reservations findResByNumberInReservations(Users user, int resnbr) throws DoesNotExistException {
        for (Reservations res : user.getReservationsList()) {
            if (res.getReservationNumber() == resnbr) {
                return res;
            }
        }
        throw new DoesNotExistException("Reservation " + resnbr + " does not exist.");
    }
    
    private String findRoomNameByNumberInReservations(Users user, int resnbr) {
        for (Reservations res : user.getReservationsList()) {
            if (res.getReservationNumber() == resnbr) {
                return res.getRoomName();
            }
        }return "null";
    }

    
    
    public void setResDatesByNumberInBooking(Users user, int resnbr) {
        for (Reservations res : user.getReservationsList()) {
            if (res.getReservationNumber() == resnbr) {
                DelArrivalDate = LocalDate.parse(res.getDateArrival());
                DelDepartureDate = LocalDate.parse(res.getDateDeparture());
            }
        }
    }
    
    private List<LocalDate> getRangeFromReservations(){
        if(DelArrivalDate == null) {
            return null;
        } else {
            long numOfDaysBetween = ChronoUnit.DAYS.between(DelArrivalDate, DelDepartureDate); 
            return IntStream.iterate(0, i -> i + 1)
              .limit(numOfDaysBetween)
              .mapToObj(i -> DelArrivalDate.plusDays(i))
              .collect(Collectors.toList());
                
        }
    }
    
    @Transactional
    public void deleteDatesBooked() {
        Users user = LoginBean.getUserLoggedIn();
        String delroomName = findRoomNameByNumberInReservations(user, RemoveResNbr);
        setResDatesByNumberInBooking(user, RemoveResNbr);
        List<LocalDate> deletingdates = getRangeFromReservations();
        for (int i = 0; i < deletingdates.size(); i++) {
            Query query = em.createQuery("DELETE FROM Dates d WHERE d.roomName = :roomName AND d.roomDate = :roomDate");
            query.setParameter("roomName", delroomName);
            query.setParameter("roomDate", deletingdates.get(i).toString());
            query.executeUpdate();
        }
    }
      
     


    
    

    
    
    public int getResNbr(){
        return RemoveResNbr;
    }
    
    public void setResNbr(int Rnbr) {
        RemoveResNbr = Rnbr;
    }
    
    public String getDelRoomName(){
        return DelRoomName;
    }
    
    public void setDelRoomName(String delR) {
        DelRoomName = delR;
    }
    
    public void removeDatesBooked() {
        //todo
    }
    
    
    public void removeRoomFromBooking() {
        //todo
        // empty values
        this.RemoveResNbr = 0;
    }
    
}
