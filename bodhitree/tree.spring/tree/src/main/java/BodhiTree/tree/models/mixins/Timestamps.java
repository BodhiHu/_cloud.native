package bodhitree.tree.models.mixins;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;


public class Timestamps {

    public static final String TS_FIELDS_NAME = "TS_FIELDS";

    public static Timestamps ins = new Timestamps();

    public void setTimestampsOnCreation (Object obj) {
        setTimestamps(obj, true);
    }

    public void setTimestampsOnUpdate (Object obj) {
        setTimestamps(obj, false);
    }

    static Logger logger = LoggerFactory.getLogger(Timestamps.class);

    static void setTimestamps (Object obj, boolean isCreation) {

        try {

            if (obj == null) {
                return;
            }

            Class clasz = obj.getClass();
            String[] TS_FIELDS = (String[]) clasz.getField(TS_FIELDS_NAME).get(null);

            if (TS_FIELDS == null || TS_FIELDS.length == 0) {
                logger.warn(TS_FIELDS_NAME + " is empty: TS_FIELDS static field is required for model class to support Timestamps mixin");
                return;
            }

            Date now = new Date();

            for (String fieldName : TS_FIELDS) {
                if ("createdAt" == fieldName && isCreation) {
                    clasz.getMethod("setCreatedAt", Date.class).invoke(obj, now);
                    continue;
                }
                if ("updatedAt" == fieldName) {
                    clasz.getMethod("setUpdatedAt", Date.class).invoke(obj, now);
                    continue;
                }

                logger.warn("field(" + fieldName + ") isn't supported for Timestamps mixin.");
            }

        } catch (Exception exc) {
            logger.error("exception is caught: ", exc);
        }
    }
}

