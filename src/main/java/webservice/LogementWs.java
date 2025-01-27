package webservice;

import entities.Logement;
import metiers.LogementBusiness;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("Logments/")
public class LogementWs {
    public LogementBusiness oLogementB = new LogementBusiness();
    //get all logement
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("list")
    public Response getAllLogement() {
        return Response.status(200).entity(oLogementB.getLogements()).build();
    }
    // get logement by id
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("details/{id}")
    public Response getLogementById(@PathParam("id") int id) {
        Logement logement = oLogementB.getLogementsByReference(id);
        if (logement != null) {
            return Response.status(200).entity(logement).build();
        } else {
            return Response.status(404).entity("logement non trouvé").build();
        }
    }
    // Create logement
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("create")
    public Response addLogement(Logement logement) {
        boolean isCreated = oLogementB.addLogement(logement);
        if (isCreated) {
            return Response.status(201).entity(logement).build();
        } else {
            return Response.status(400).entity("erreur ajout logement").build();
        }
    }
    // update logement
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("update/{id}")
    public Response updateLogement(@PathParam("id") int id, Logement logement) {
        try{
            logement.setReference(id);
            boolean isUpdated = oLogementB.updateLogement(logement.getReference(), logement);
            if (isUpdated) {
                return Response.status(200).entity(logement).build();
            } else {
                return Response.status(404).entity("erreur en changement de logement").build();
            }
        }
        catch(Exception e){
            return Response.status(400).entity(e.getMessage()).build();
        }

    }

    // DELETE  logement
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("delete/{id}")
    public Response deleteLogement(@PathParam("id") int id) {
        try {
            boolean isDeleted = oLogementB.deleteLogement(id);
            if (isDeleted) {
                return Response.status(200).entity("Logement supprimé avec succée").build();
            }
            else {
                return Response.status(404).entity("erreur en suprssion de logement").build();
            }
        }
        catch (Exception e) {
            return Response.status(404).entity(e.getMessage()).build();
        }

    }
}
