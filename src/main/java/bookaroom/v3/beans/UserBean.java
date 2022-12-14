package bookaroom.v3.beans;

import bookaroom.v3.exceptions.AlreadyExistsException;
import bookaroom.v3.exceptions.DoesNotExistException;

import bookaroom.v3.models.Users;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;


/*
 * @author Team BookARoom
 */

@Named(value = "userBean")
@SessionScoped
public class UserBean implements Serializable {
   
    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    private String username = "";
    private String firstName = "";
    private String lastName = "";
    private String email = "";
    private String password = "";
    private String ccnumber = "";
    private String cccode = "";
    private Date ccexpirationdate = new Date(System.currentTimeMillis());
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");
    private final YearMonth CurrentTime = YearMonth.now();
    private final DateTimeFormatter Dateformatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final LocalDate CurrentTimeLong = LocalDate.now();
    
    @Transactional
    public void createAUser() {
        try {
            if (!emailExists() && !usernameExists()) {
                Users newUser = new Users();
                newUser.setUsername(username);
                newUser.setFirstName(firstName);
                newUser.setLastName(lastName);
                newUser.setEmail(email);
                newUser.setPassword(password.hashCode());
                newUser.setCcnumber(ccnumber);
                newUser.setCccode(cccode);
                newUser.setCcexpirationdate(ccexpirationdate);
                em.persist(newUser);
                
            }
        } catch (AlreadyExistsException | DoesNotExistException ex ) {
            System.out.println(ex.getMessage());
        }
        // empty values
        this.email = "";
        this.username = "";
        this.firstName = "";
        this.lastName = "";
        this.password = "";
    }       
    
    @Transactional
    public String modifyAUser() {
        try {
            if (usernameExists() == true) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "This username is not available: " + username));
            }
            if (emailExists() == true) {
               FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "This email is already in use: " + email));
            }
            if (!emailExists() && !usernameExists()) {
                Users user = LoginBean.getUserLoggedIn();
                user.setUsername(username);
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setEmail(email);
                user.setPassword(password.hashCode());
                em.merge(user);
                return "/UserPage/ShowInfo.xhtml?faces-redirect=true";
            }

        } catch (AlreadyExistsException | DoesNotExistException ex ) {
            System.out.println(ex.getMessage());
        }
        // empty values
        this.email = "";
        this.username = "";
        this.firstName = "";
        this.lastName = "";
        this.password = "";
        return "";
    }
    
    private boolean emailExists() throws AlreadyExistsException {
        Query query = em.createNamedQuery("Users.findByEmail");
        List<Users> users = query.setParameter("email", email).getResultList();
        return users.size() > 0;
    }
    
    private boolean usernameExists() throws DoesNotExistException {
        Query query = em.createNamedQuery("Users.findByUsername");
        List<Users> users = query.setParameter("username", username).getResultList();
        return users.size() > 0;
    }
    

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }
    
    public String getPassword() {
        return password;
    }
        
    public String getCcnumber() {
        return ccnumber;
    }
    
    public String getCccode() {
        return cccode;
    }
    
    public Date getCcexpirationdate() {
        return ccexpirationdate;
    }
    
    public YearMonth getCurrentTime() {
        return CurrentTime;
    }

    public LocalDate getCurrentTimeLong() {
        return CurrentTimeLong;
    }
    
    
    public DateTimeFormatter getFormatter() {
        return formatter;
    }
    
    public DateTimeFormatter getDateFormatter() {
        return Dateformatter;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
        System.out.println(username);
    }
    
    public void setCcnumber(String nccnumber) {
         this.ccnumber = nccnumber;
         System.out.println(ccnumber);
    }
    
    public void setCccode(String ncccode) {
         this.cccode = ncccode;
    }
    
    public void setCcexpirationdate(Date nccexpirationdate) {
        this.ccexpirationdate = nccexpirationdate;
    } 


}


    

