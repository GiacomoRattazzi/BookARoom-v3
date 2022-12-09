package bookaroom.v3.beans;
import bookaroom.v3.exceptions.AlreadyExistsException;
import bookaroom.v3.exceptions.DoesNotExistException;
import bookaroom.v3.models.Users;
import bookaroom.v3.models.Comments;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

/**
 *
 * @author Giac
 */
@Named(value = "commentBean")
@SessionScoped
public class CommentBean implements Serializable {
    
    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;
    
    private String comment = "";
    private LocalDateTime now = LocalDateTime.now();
    private DateTimeFormatter formatterComment = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private Integer rating;
    
    public ArrayList<Comments> getComments() {
        Query query = em.createNamedQuery("Comments.findAll");
        return new ArrayList<>(query.getResultList());
    }
    
    public String getComment() {
        return comment;
    }
    
    public void setComment(String comment) {
        this.comment = comment;
    }
    
    public LocalDateTime getTodayDate() {
        return now;
    }
    
    @Transactional
    public void addCommentFromUser() {
     
        Comments newComment = new Comments();
        Users user = LoginBean.getUserLoggedIn();
        comment = user.getUsername()+": "+comment+" ("+getTodayDate().format(formatterComment)+")";
        newComment.setComment(comment);
        newComment.setRating(rating);
        em.persist(newComment);
        //empty values
        this.comment = "";
        this.rating = 0;

    }   
//    public void addCommentFromUser() {
//        User user = LoginBean.getUserLoggedIn();
//        MockDatabase.getInstance().addAComment(new Comment(user.getUsername()+": "+comment+" ("+getTodayDate().format(formatterComment)+")",rating));
//        
//
//    }
    
    public Integer getRating(){
        return rating;
    }
    
    public void setRating(Integer rating) {
        this.rating = rating;
    }
    
}