package org.example.web;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class UpdateOrderDTO {
    private UUID id;
    private List<MenuItemDTO> orderedItems;
    private String customerName;

    @Data
    public static class MenuItemDTO{
        private String name;
    }
}
