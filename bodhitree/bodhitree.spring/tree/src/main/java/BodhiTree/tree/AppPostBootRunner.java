package BodhiTree.tree;

import BodhiTree.tree.lib.db.DbMigrate;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class AppPostBootRunner implements ApplicationRunner {

    Logger logger = LoggerFactory.getLogger(AppPostBootRunner.class);


    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info(format(""));
        logger.info(format(" AppPostBootRunner "));
        logger.info(format(""));

        logger.info(format(".... migrate db "));
        DbMigrate.migrate();

        logger.info(format(""));
        logger.info(format(".... migrate db done "));
        logger.info(format(""));
    }

    static final String LS = "................";
    static String format (String msg) {
        return StringUtils.rightPad(LS + msg, 81, '.');
    }
}

