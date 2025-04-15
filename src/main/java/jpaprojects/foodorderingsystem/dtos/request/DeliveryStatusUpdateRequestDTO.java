package jpaprojects.foodorderingsystem.dtos.request;

import jpaprojects.foodorderingsystem.enums.DeliveryStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeliveryStatusUpdateRequestDTO {
    private DeliveryStatus status;
}
