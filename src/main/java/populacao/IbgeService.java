package populacao;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/v1/projecoes")
@RegisterRestClient(configKey="ibge-api")
public interface IbgeService {

    @GET
    @Path("/populacao")
    @Produces("application/json")
    ProjecaoIbge populacao();
}