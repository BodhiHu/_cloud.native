package BodhiTree.tree.models;

import org.bson.codecs.pojo.annotations.BsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(Tag.COLLECTION_NAME)
public class Tag {

    public static final String COLLECTION_NAME = "tags";

    @Id
    @BsonProperty("id")
    private String id;
    @Indexed(unique = true)
    private String name;

    public Tag () {}
    public Tag (String name) {
        this(null, name);
    }
    public Tag (String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

