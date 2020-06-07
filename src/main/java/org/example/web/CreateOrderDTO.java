package org.example.web;

import lombok.Data;

import java.util.List;
@Data
public class CreateOrderDTO {
    private List<MenuItemDTO> orderedItems;
    private String customerName;

    @Data
    public static class MenuItemDTO{
        private String name;
    }
}
