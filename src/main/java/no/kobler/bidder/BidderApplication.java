package no.kobler.bidder;

import com.opentable.db.postgres.embedded.EmbeddedPostgres;
import io.dropwizard.Application;
import io.dropwizard.jdbi3.JdbiFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import java.io.IOException;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;

public class BidderApplication extends Application<BidderConfiguration> {

    public static void main(final String[] args) throws Exception {
        new BidderApplication().run(args);
    }

    @Override
    public String getName() {
        return "Bidder";
    }

    @Override
    public void initialize(final Bootstrap<BidderConfiguration> bootstrap) {


    }

    @Override
    public void run(final BidderConfiguration configuration,
                    final Environment environment) throws IOException {
        EmbeddedPostgres embeddedPostgres = EmbeddedPostgres.builder().start();
        configuration.getDataSourceFactory().setUrl(embeddedPostgres.getJdbcUrl("postgres", "postgres"));

        JdbiFactory factory = new JdbiFactory();
        Jdbi jdbi = factory.build(environment, configuration.getDataSourceFactory(), "postgres");
        Handle handle = jdbi.open();
        handle.execute("create table if not exists url_redirect (id bigserial, url text not null)");
    }
}
