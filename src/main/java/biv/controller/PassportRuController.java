package biv.controller;

import biv.domain.PassportRu;
import biv.domain.UserAccount;
import biv.dto.UpdateUserPassportRequest;
import biv.service.BaseDomainService;
import biv.service.impl.UserPassportService;

import javax.inject.Inject;
import javax.naming.OperationNotSupportedException;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/passport")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PassportRuController {

    @Inject
    private BaseDomainService<PassportRu, Long> passportRuService;
    @Inject
    private BaseDomainService<UserAccount, Long> userAccountService;
    @Inject
    private UserPassportService userPassportService;

    @GET
    @Path("/{userId}")
    public PassportRu getPassportUserById(@PathParam("userId") Long id) {
        return passportRuService.read(id);
    }

    @GET
    @Path("/getAll/{userId}")
    @Transactional
    public List<PassportRu> getAllPassportsUser(@PathParam("userId") Long id) {
        return userPassportService.getAllPassportsUser(userAccountService.read(id));
    }

    @GET
    @Path("/getAllActual/{userId}")
    public List<PassportRu> getAllActualPassportsUser(@PathParam("userId") Long id) {
        return userPassportService.getAllActualPassportsUser(userAccountService.read(id));
    }

    @POST
    @Transactional
    public Response add(UpdateUserPassportRequest request) throws OperationNotSupportedException {
        UserAccount user = request.getUser();
        PassportRu passport = request.getPassport();
        userPassportService.addPassportToUser(passport, user);
        return Response.ok().build();
    }
}
