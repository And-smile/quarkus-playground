package org.example.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import org.example.domain.MenuItem;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class MenuItemRepository implements PanacheRepositoryBase<MenuItem, UUID> {
    public List<MenuItem> findByNameIn(List<String> names){
        return list("name in (?1)", names);
    }
}
