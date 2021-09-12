package biv.controller;

import biv.domain.UserAccount;
import biv.service.BaseDomainService;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserAccountController {

    @Inject
    private BaseDomainService<UserAccount, Long> userService;

    @GET
    @Path("/{id}")
    public UserAccount get(@PathParam("id") Long id) {
        return userService.read(id);
    }

    @POST
    @Transactional
    public void add(UserAccount userAccount) {
        userService.create(userAccount);
    }
}
