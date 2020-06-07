package org.example;

import org.example.web.CreateOrderDTO;
import org.example.web.OrderDto;
import org.example.web.UpdateOrderDTO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@ApplicationScoped
@Path("/coffeshop/order")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CoffeeShopController {
    @Inject
    private CoffeeShopService coffeeShopService;

    @POST
    @Path("/create")
    public OrderDto createOrder(CreateOrderDTO orderDto) {
        return coffeeShopService.createOrder(orderDto);
    }
    @POST
    @Path("/update")
    public OrderDto updateOrder(UpdateOrderDTO orderDto) {
        return coffeeShopService.updateOrder(orderDto);
    }
    @GET
    public List<OrderDto> getAllOrders(){
        return coffeeShopService.getAllOrders();
    }
}
