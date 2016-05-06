package ru.kroshchenko.ruven.portal;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author Kroshchenko
 *         2016.02.07
 */
@ManagedBean
@ApplicationScoped
public class PortalUtilityBean implements Serializable {

    public String getFirstDayOfYearAsString() {
        Calendar firstDayOfYear = Calendar.getInstance();
        firstDayOfYear.set(firstDayOfYear.get(Calendar.YEAR), Calendar.JANUARY, 1, 0, 0, 0);
        return new SimpleDateFormat("yyyy-MM-dd").format(firstDayOfYear.getTime());
    }

    public String getNowAsString() {
        Calendar now = Calendar.getInstance();
        return new SimpleDateFormat("yyyy-MM-dd").format(now.getTime());
    }
}
