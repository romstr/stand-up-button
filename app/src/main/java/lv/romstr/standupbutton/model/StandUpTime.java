package lv.romstr.standupbutton.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Roman on 02.04.16..
 */
@Table(name = "Times")
public class StandUpTime extends Model {

    @Column(name = "UserId")
    private Integer userId;

    @Column(name = "StandUpDate")
    private Date standUpDate;

    @Column(name = "SpentTime")
    private Long spentTime;

    @Column(name = "Displayed")
    private Boolean displayed;

    public Long getSpentTime() {
        return spentTime;
    }

    public void setSpentTime(Long spentTime) {
        this.spentTime = spentTime;
    }

    public Date getStandUpDate() {
        return standUpDate;
    }

    public void setStandUpDate(Date standUpDate) {
        this.standUpDate = standUpDate;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPrettyTime() {
        return new SimpleDateFormat("mm:ss", Locale.getDefault()).format(new Date(spentTime));
    }

    public String getPrettyDate() {
        return new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(standUpDate);
    }

    public Boolean getDisplayed() {
        return displayed;
    }

    public void setDisplayed(Boolean displayed) {
        this.displayed = displayed;
    }

}
