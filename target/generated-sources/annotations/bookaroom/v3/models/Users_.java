package bookaroom.v3.models;

import bookaroom.v3.models.Reservations;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.7.v20200504-rNA", date="2022-12-07T15:59:59")
@StaticMetamodel(Users.class)
public class Users_ { 

    public static volatile SingularAttribute<Users, String> firstName;
    public static volatile SingularAttribute<Users, String> lastName;
    public static volatile SingularAttribute<Users, Integer> password;
    public static volatile ListAttribute<Users, Reservations> reservationsList;
    public static volatile SingularAttribute<Users, Date> ccexpirationdate;
    public static volatile SingularAttribute<Users, String> cccode;
    public static volatile SingularAttribute<Users, Integer> userId;
    public static volatile SingularAttribute<Users, String> email;
    public static volatile SingularAttribute<Users, String> username;
    public static volatile SingularAttribute<Users, String> ccnumber;

}