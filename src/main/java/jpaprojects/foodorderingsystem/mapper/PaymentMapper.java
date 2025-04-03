package jpaprojects.foodorderingsystem.mapper;

import jpaprojects.foodorderingsystem.dtos.request.PaymentRequestDTO;
import jpaprojects.foodorderingsystem.dtos.response.PaymentResponseDTO;
import jpaprojects.foodorderingsystem.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PaymentMapper {
    PaymentMapper INSTANCE = Mappers.getMapper(PaymentMapper.class);

    Payment toEntity(PaymentRequestDTO dto);

    PaymentResponseDTO toDto(Payment entity);
}
