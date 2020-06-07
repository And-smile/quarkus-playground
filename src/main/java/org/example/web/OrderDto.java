package org.example.web;

import lombok.Builder;
import lombok.Data;
import org.example.domain.OrderItem;

import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class OrderDto {
    private UUID id;
    private String customerName;
    private LocalDateTime orderedAt;
    private List<OrderItemDto> items;
    private BigDecimal totalPrice;
    @Data
    @Builder
    public static class OrderItemDto{
        private UUID id;
        private String name;
        private BigDecimal price;
    }
}
