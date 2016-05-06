package ru.kroshchenko.ruven.controllers.rest;

import ru.kroshchenko.ruven.controllers.rest.resources.TableDataResource;
import ru.kroshchenko.ruven.controllers.rest.resources.TableDataWrapper;
import ru.kroshchenko.ruven.controllers.rest.resources.UserResource;
import ru.kroshchenko.ruven.dao.UserDao;
import ru.kroshchenko.ruven.entities.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Aleksandr Streltsov
 *         2016.05.06
 */
@Path("/users")
public class UsersController {

    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public TableDataWrapper getList(
            @QueryParam("draw") Integer draw,
            @QueryParam("start") Integer start,
            @QueryParam("search[value]") String searchValue,
            @QueryParam("search[regex]") Boolean searchRegex,
            @DefaultValue("0") @QueryParam("length") Integer length,
            @Context HttpServletRequest req
    ) {
        List<TableDataResource> data = new ArrayList<>();
        HttpSession session = req.getSession();
//        if (!checkPermission(session)) {
//            TableDataWrapper tableDataWrapper = new TableDataWrapper();
//            tableDataWrapper.setError("NOT AUTHORIZED");
//            return tableDataWrapper;
//        }

//        Integer quantity = ItemDAO.getQuantity(criteria);
        List<User> users = new ArrayList<>();
//        if (quantity > 0) {
        users = UserDao.getAll();
//        }

        if (length < 1) {
            length = users.size();
        }
        if (users.size() > 0) {
            for (int i = start + 1; i <= start + length; i++) {
                if (users.size() >= (i - start)) {
                    User user = users.get(i - start - 1);
                    if (new UserResource(user).contains(searchValue)) {
                        data.add(new UserResource(user));
                    }
                } else {
                    break;
                }
            }
        }

        TableDataWrapper result = new TableDataWrapper();
        result.setRecordsTotal(users.size());
        result.setRecordsFiltered(users.size());
        result.setDraw(draw);
        result.setData(data);
        return result;
    }
}
