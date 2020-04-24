package Resources;

import Models.Part;
import RepresentationJSON.Representation;
import Services.PartService;
import com.codahale.metrics.annotation.Timed;
import org.eclipse.jetty.http.HttpStatus;


import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

//En esta clase en lo relacionado a los endPoint o en otro termino para Spring RestController

@Path("/persona")
@Produces(MediaType.APPLICATION_JSON)
public class PartResource {


    private final PartService partService;

    public PartResource(PartService partService){
        this.partService = partService;
    }

    @GET
    @Timed
    @Path("/listar")
    public Representation<List<Part>> getParts() {
        return new Representation<List<Part>>(HttpStatus.OK_200, partService.getParts());
    }

    @GET
    @Timed
    @Path("/{id}")
    public Representation<Part> getPart(@PathParam("id") final int id) {
        return new Representation<Part>(HttpStatus.OK_200, partService.getPart(id));
    }

    @POST
    @Timed
    @Path("/crear")
    public Representation<Part> createPart(@NotNull @Valid final Part part) {
        return new Representation<Part>(HttpStatus.OK_200, partService.createPart(part));
    }

    @PUT
    @Timed
    @Path("/{id}")
    public Representation<Part> editPart(@NotNull @Valid final Part part,
                                         @PathParam("id") final int id) {
        part.setId(id);
        return new Representation<Part>(HttpStatus.OK_200, partService.editPart(part));
    }

    @DELETE
    @Timed
    @Path("/{id}")
    public Representation<String> deletePart(@PathParam("id") final int id) {
        return new Representation<String>(HttpStatus.OK_200, partService.deletePart(id));
    }
}
