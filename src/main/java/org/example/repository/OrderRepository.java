package org.example.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import org.example.domain.Order;

import javax.enterprise.context.ApplicationScoped;
import java.util.UUID;

@ApplicationScoped
public class OrderRepository implements PanacheRepositoryBase<Order, UUID> {
}
