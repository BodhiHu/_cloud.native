package BodhiTree.tree.models;

import org.bson.codecs.pojo.annotations.BsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(Migration.COLLECTION_NAME)
public class Migration {
    public static final String COLLECTION_NAME = "migrations";

    public Migration (int version, String description) {
        this.version = version;
        this.description = description;
    }

    @Id
    @BsonProperty("id")
    private String id;
    private int version;
    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
