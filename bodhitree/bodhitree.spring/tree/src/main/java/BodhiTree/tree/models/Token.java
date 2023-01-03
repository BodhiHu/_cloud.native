package BodhiTree.tree.models;

import org.bson.codecs.pojo.annotations.BsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(Token.COLLECTION_NAME)
public class Token {

    public static final String COLLECTION_NAME = "tokens";

    /** Mixin configs */
    public static final String[] TS_FIELDS = { "createdAt", "updatedAt" };


    public static final int DEFAULT_LIFESPAN_SECONDS = 30 * 60;

    public static TokenValidator validator () {
        return TokenValidator.instance();
    }

    @Id
    @BsonProperty("id")
    private String id;
    /** MUST be in the format of "+CC-NNNNNNNNN" */
    private String mobilePhone;
    private String email;
    private String data;

    private Date createdAt;
    private Date updatedAt;
    private int lifespan; /** in seconds */

    public Token() {}
    public Token(String identity, String data) {
        this(identity, data, DEFAULT_LIFESPAN_SECONDS);
    }
    public Token(String identity, String data, int lifespan) {
        if (identity.contains("@")) {
            // email
            instantiate(this, null, identity, lifespan, data);
        } else {
            // mobilePhone
            instantiate(this, identity, null, lifespan, data);
        }
    }
    public static Token instantiate (Token that, String mobilePhone, String email, int lifespan, String data) {
        that.mobilePhone = mobilePhone;
        that.email = email;
        that.lifespan = lifespan;
        that.data = data;
        return that;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public int getLifespan() {
        return lifespan;
    }

    public void setLifespan(int lifespan) {
        this.lifespan = lifespan;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

}

