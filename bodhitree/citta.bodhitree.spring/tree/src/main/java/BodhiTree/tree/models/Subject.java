package BodhiTree.tree.models;

import BodhiTree.tree.models.common.Link;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

import static BodhiTree.tree.models.Schema.DEFAULT_LOCALE;

@Document(Subject.COLLECTION_NAME)
public class Subject {

    public static final String COLLECTION_NAME = "subjects";

    @Id
    @BsonProperty("id")
    private String id;
    @Indexed(unique = true)
    private String name;
    private String image;
    private String title;
    private String subtitle;
    private String intro;
    private Link link;
    private List<Link> links;
    private String locale = DEFAULT_LOCALE.toString();

    public Subject () {}

    public Subject (String id, String name, String title, String intro, String image, Link link) {
        this(id, name, title, intro, image, link, null);
    }
    public Subject (String id, String name, String title, String intro, String image, Link link, List<Link> links) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.intro = intro;
        this.image = image;
        this.link = link;
        this.links = links;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public Link getLink() {
        return link;
    }

    public void setLink(Link link) {
        this.link = link;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }
}

