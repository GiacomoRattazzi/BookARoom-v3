package bookaroom.v3.models;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.7.v20200504-rNA", date="2022-12-07T15:59:59")
@StaticMetamodel(Reservations.class)
public class Reservations_ { 

    public static volatile SingularAttribute<Reservations, Date> dateArrival;
    public static volatile SingularAttribute<Reservations, Integer> reservationId;
    public static volatile SingularAttribute<Reservations, Double> totalPrice;
    public static volatile SingularAttribute<Reservations, Integer> reservationNumber;
    public static volatile SingularAttribute<Reservations, String> roomName;
    public static volatile SingularAttribute<Reservations, Date> dateDeparture;

}