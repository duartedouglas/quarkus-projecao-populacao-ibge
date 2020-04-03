package populacao;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/projecao")
public class ProjecaoResource {

    @Inject
    Logger logger;
    @Inject
    CalculoIbge ibge;

	@GET
    @Path("/{datahora}")
    @Produces(MediaType.TEXT_PLAIN)
    public Long projecao(@PathParam("datahora") Long datahora) {
        return ibge.calcular(datahora);
    }

	@GET
    @Path("/log")
    @Produces(MediaType.TEXT_PLAIN)
    public String log() {
        return logger.read();
    }
}