
package lv.romstr.standupbutton.model;

//import javax.annotation.Generated;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

//@Generated("org.jsonschema2pojo")
@Table(name = "Users")
public class User extends Model {

    @Column(name = "UserId", index = true)
    @SerializedName("id")
    @Expose
    private Integer userId;
    @SerializedName("login")
    @Expose
    private String login;
    @Column(name = "FirstName")
    @SerializedName("firstname")
    @Expose
    private String firstname;
    @Column(name = "LastName")
    @SerializedName("lastname")
    @Expose
    private String lastname;
    @SerializedName("mail")
    @Expose
    private String mail;
    @SerializedName("created_on")
    @Expose
    private String createdOn;
    @SerializedName("last_login_on")
    @Expose
    private String lastLoginOn;

    @Column(name = "Attendance")
    private Boolean isOnStandUp;

    @Column(name = "Attended")
    private Boolean wasOnStandUp;

    @Column(name = "Redmine")
    private Boolean fromRedmine;

    @Column(name = "TtsName")
    private String ttsName;

    public String getTtsName() {
        return ttsName;
    }

    public void setTtsName(String ttsName) {
        this.ttsName = ttsName;
    }

    @SerializedName("custom_fields")
    @Expose
    private List<CustomField> customFields = new ArrayList<CustomField>();

    public Boolean getOnStandUp() {
        return isOnStandUp;
    }

    public void setOnStandUp(Boolean onStandUp) {
        isOnStandUp = onStandUp;
    }

    public List<CustomField> getCustomFields() {
        return customFields;
    }

    public void setCustomFields(List<CustomField> customFields) {
        this.customFields = customFields;
    }

    public Boolean getFromRedmine() {
        return fromRedmine;
    }

    public void setFromRedmine(Boolean fromRedmine) {
        this.fromRedmine = fromRedmine;
    }


    public Boolean getIsOnStandUp() {
        return isOnStandUp == null ? true : isOnStandUp;
    }

    public void setIsOnStandUp(Boolean isOnStandUp) {
        this.isOnStandUp = isOnStandUp;
    }

    /**
     * 
     * @return
     *     The userId
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * 
     * @param userId
     *     The userId
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * 
     * @return
     *     The login
     */
    public String getLogin() {
        return login;
    }

    /**
     * 
     * @param login
     *     The login
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * 
     * @return
     *     The firstname
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * 
     * @param firstname
     *     The firstname
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * 
     * @return
     *     The lastname
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * 
     * @param lastname
     *     The lastname
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * 
     * @return
     *     The mail
     */
    public String getMail() {
        return mail;
    }

    /**
     * 
     * @param mail
     *     The mail
     */
    public void setMail(String mail) {
        this.mail = mail;
    }

    /**
     * 
     * @return
     *     The createdOn
     */
    public String getCreatedOn() {
        return createdOn;
    }

    /**
     * 
     * @param createdOn
     *     The created_on
     */
    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    /**
     * 
     * @return
     *     The lastLoginOn
     */
    public String getLastLoginOn() {
        return lastLoginOn;
    }

    /**
     * 
     * @param lastLoginOn
     *     The last_login_on
     */
    public void setLastLoginOn(String lastLoginOn) {
        this.lastLoginOn = lastLoginOn;
    }

    public Boolean getWasOnStandUp() {
        return wasOnStandUp != null;
    }

    public void setWasOnStandUp(Boolean wasOnStandUp) {
        this.wasOnStandUp = wasOnStandUp;
    }
}
