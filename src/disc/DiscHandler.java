package disc;

import java.time.*;
import java.time.temporal.ChronoUnit;

/**
 * Dispenses discs
 */
public class DiscHandler  {


    /**
     * Accepts a returned disc
     * @param disc the disc being returned
     * @return the additional cost the customer needs to pay
     * This method will return -1 if the user has kept the disc for more than 10 days to
     * signify that the user now owns the disc
     */
    public static double acceptReturnedDisc(Disc disc) {
        
        long timeDifference = ChronoUnit.DAYS.between(LocalDate.now(), disc.getDiscTag().getDateRented());
        double additionalCost = 0;
        
        if(timeDifference > 1 && timeDifference < 10) {
           additionalCost = timeDifference * 1.50;
           disc.getDiscTag().setDateReturned(LocalDate.now());
           disc.getDiscTag().setTimeRented(LocalTime.now());
           disc.getDiscTag().persist();
           return additionalCost;
        }
        
        return - 1;
    }
 
}
