
import Configuration.DropwizardConfiguration;
import Resources.PartResource;
import Services.PartService;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import org.skife.jdbi.v2.DBI;

import javax.sql.DataSource;

public class DropwizardApplication extends Application<DropwizardConfiguration> {

    private static final String SQL = "sql";

    public static void main(String[] args) throws Exception {
        new DropwizardApplication().run(args);
    }

    @Override
    public void run(DropwizardConfiguration dropwizardConfiguration, Environment environment) throws Exception {

        // Datasource configuration
        final DataSource dataSource =
                dropwizardConfiguration.getDataSourceFactory().build(environment.metrics(), SQL);
        DBI dbi = new DBI(dataSource);

        // Register resources
        environment.jersey().register(new PartResource(dbi.onDemand(PartService.class)));


    }
}
