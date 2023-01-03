package BodhiTree.tree;

import BodhiTree.tree.models.Schema;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Arrays;

@Configuration
@EnableMongoRepositories
@ComponentScan(basePackages = {
    "bodhitree.tree",
    "bodhitree.tree.lib",
    "bodhitree.tree.i18n",
    "bodhitree.tree.models"
})
public class AppConfig {

    public static final String DB_NAME = Schema.DB_NAME;

    @Bean
    public MongoClient mongoClient() {
        return getMongoClient();
    }

    @Bean
    public MongoTemplate mongoTemplate () {
        return new MongoTemplate(mongoClient(), DB_NAME);
    }

    static MongoClient _mongoClient;
    public static MongoClient getMongoClient () {

        if (_mongoClient == null) {

            MongoCredential credential = MongoCredential.createCredential("bodhi", DB_NAME, "BodhiTree".toCharArray());

            CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry(),
                CodecRegistries.fromProviders(
                    PojoCodecProvider.builder().automatic(true).build()
                )
            );

            _mongoClient = MongoClients.create(
                MongoClientSettings.builder()
                    .applyToClusterSettings(builder ->
                        builder.hosts(Arrays.asList(new ServerAddress("localhost", 27017))))
                    .credential(credential)
                    .codecRegistry(pojoCodecRegistry)
                    .build()
            );
        }

        return _mongoClient;
    }
}

