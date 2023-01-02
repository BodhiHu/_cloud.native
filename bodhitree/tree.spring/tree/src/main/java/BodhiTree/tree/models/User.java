package bodhitree.tree.models;

import bodhitree.tree.AppContext;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(User.COLLECTION_NAME)
public class User {

    public static final String COLLECTION_NAME = "users";

    private static UserValidator _validator;
    public static UserValidator validator () {
        if (_validator == null) {
            _validator = (UserValidator) AppContext.get().getBean("userValidator");
        }

        return _validator;
    }

    @Id
    @BsonProperty("id")
    private String id;
    /** MUST be in the format of "+CC-NNNNNNNNN" */
    private String mobilePhone;
    private String username;
    private String email;
    private String password;
    private String name;
    private String title;
    private String avatar;
    /** 2 letters ISO country code, eg: 'CN', 'US' */
    private String countryCode;
    private String locale;
    private Date createdAt;

    public User() {
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() {
        return email;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

