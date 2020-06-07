package org.example.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import org.example.domain.OrderItem;

import javax.enterprise.context.ApplicationScoped;
import java.util.UUID;

@ApplicationScoped
public class OrderItemRepository implements PanacheRepositoryBase<OrderItem, UUID> {
}
