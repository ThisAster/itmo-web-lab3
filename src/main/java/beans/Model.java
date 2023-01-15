package beans;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.Setter;
import util.Connector;

import java.io.Serializable;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.TimeZone;

@Named
@ApplicationScoped
public class Model implements Serializable {
    private ArrayList<PointAttempt> data = new ArrayList<>();

    public String getTimezoneOffset() {
        return timezoneOffset;
    }

    public void setTimezoneOffset(String timezoneOffset) {
        this.timezoneOffset = timezoneOffset;
    }

    String timezoneOffset;

    public void add(PointAttempt attempt){
        data.add(attempt);
        Connector.getInstance().makeBigAdd(attempt);
    }

    private ZoneId zoneId = ZonedDateTime.now().getZone();

    public ArrayList<PointAttempt> get() {
        return new ArrayList<>(data);
    }

    public void timezoneChangedListener(){
        String strFromJavaScript = getTimezoneOffset();
        TimeZone tz = TimeZone.getTimeZone("GMT+" + strFromJavaScript);
        zoneId = tz.toZoneId();
    }

    public ArrayList<PointAttempt> getReversed() {
        ArrayList<PointAttempt> toReturn = new ArrayList<>(data);
        Collections.reverse(toReturn);
        toReturn.forEach(attempt -> attempt.setZoneId(zoneId));
        return toReturn;
    }

    @Override
    public String toString() {
        return "Model{" +
                "data=" + data +
                '}';
    }

}
