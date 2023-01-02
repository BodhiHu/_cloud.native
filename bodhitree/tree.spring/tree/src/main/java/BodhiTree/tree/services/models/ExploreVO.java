package bodhitree.tree.services.models;

import bodhitree.tree.models.Subject;

import java.util.ArrayList;
import java.util.List;

public class ExploreVO {
    List<Subject> banners;
    List<Subject> categories;

    public ExploreVO() {
        this(null, null);
    }
    ExploreVO(List<Subject> banners, List<Subject> categories) {
        if (banners == null) {
            banners = new ArrayList<>();
        }
        if (categories == null) {
            categories = new ArrayList<>();
        }

        this.banners = banners;
        this.categories = categories;
    }

    public ExploreVO addCategory (Subject item) {
        this.categories.add(item);
        return this;
    }
    public ExploreVO addBanner (Subject item) {
        this.banners.add(item);
        return this;
    }
}
