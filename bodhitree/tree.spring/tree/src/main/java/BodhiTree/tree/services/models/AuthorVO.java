package bodhitree.tree.services.models;

import bodhitree.tree.models.User;

public class AuthorVO {

    String userId;
    String name;
    String title;
    String avatar;

    public AuthorVO() { }
    public AuthorVO(User user) {
        if (user == null) {
            return;
        }

        this.userId = user.getId();
        this.name = user.getName();
        this.title = user.getTitle();
        this.avatar = user.getAvatar();
    }

    public static final String[] mappedUserFields = {
        "id", "name", "title", "avatar"
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUserId() {
        return userId;
    }
    public void setUserId (String userId) {
        this.userId = userId;
    }

}

