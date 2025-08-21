package com.serviciosRosario.ServiciosRosario.service.box;

import com.serviciosRosario.ServiciosRosario.entity.box.Box;
import com.serviciosRosario.ServiciosRosario.entity.box.DTO.BoxRequestDto;
import com.serviciosRosario.ServiciosRosario.entity.box.DTO.BoxResponseDto;

import java.util.List;

public interface IBoxService {

    List<Box> getAll();
    List<Box> getAllActive();
    Box getById(Integer id);
    Box create(BoxRequestDto boxRequestDto);
    Box update(BoxRequestDto boxRequestDto);
    void delete(Box box);
    boolean existsById(Integer id);
    void decreaseAvailablePorts(Box box);
    void incrementAvailablePorts(Box box);
    BoxResponseDto convertToDto(Box box);
}
