package com.serviciosRosario.ServiciosRosario.service.box.impl;

import com.serviciosRosario.ServiciosRosario.exceptions.errors.ErrorType;
import com.serviciosRosario.ServiciosRosario.service.box.IBoxService;
import com.serviciosRosario.ServiciosRosario.service.port.impl.PortServiceImpl;
import com.serviciosRosario.ServiciosRosario.entity.box.Box;
import com.serviciosRosario.ServiciosRosario.entity.box.DTO.BoxRequestDto;
import com.serviciosRosario.ServiciosRosario.entity.box.DTO.BoxResponseDto;
import com.serviciosRosario.ServiciosRosario.exceptions.CustomException;
import com.serviciosRosario.ServiciosRosario.repository.BoxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoxServiceImpl implements IBoxService {

    @Autowired
    private BoxRepository boxRepository;

    @Autowired
    private PortServiceImpl portService;

    @Override
    public List<Box> getAll() {
        return (List<Box>) boxRepository.findAll();
    }

    @Override
    public List<Box> getAllActive() {
        return boxRepository.findByActiveTrue();
    }

    @Override
    public Box getById(Integer id) {
        return boxRepository.findById(id).orElse(null);
    }

    @Override
    public Box create(BoxRequestDto boxRequestDto) {
        Box box = new Box();

        box.setBoxNumber("Caja" + boxRequestDto.getBoxNumber());
        Integer portQuantity = boxRequestDto.getPortQuantity();
        if(portQuantity != 8 && portQuantity != 16 && portQuantity != 32){
            throw new CustomException(
                    ErrorType.PORTS_COUNT_INVALID
            );
        }
        box.setPortQuantity(portQuantity);
        box.setComments(boxRequestDto.getComments());
        if(boxRequestDto.getPower() == null){
            throw new CustomException(
                    ErrorType.POWER_CANNOT_BE_NULL
            );
        }
        box.setPower(boxRequestDto.getPower());
        box.setAddress(boxRequestDto.getAddress());
        box.setAviablePort(portQuantity);
        Box savedBox = boxRepository.save(box);

        portService.createPositionsOfPorts(boxRequestDto.getPortQuantity(), box);

        return savedBox;
    }



    @Override
    public Box update(BoxRequestDto boxRequestDto) {
        Box box = null;
        if(boxRequestDto.getId() != null && boxRepository.existsById(boxRequestDto.getId())){
            box = boxRepository.findById(boxRequestDto.getId()).get();
        }else{
            throw new CustomException(
                    ErrorType.BOX_NOT_FOUND
            );
        }
        box.setBoxNumber("Caja" + boxRequestDto.getBoxNumber());
        box.setPortQuantity(boxRequestDto.getPortQuantity());
        box.setComments(boxRequestDto.getComments());
        box.setPower(boxRequestDto.getPower());
        box.setAddress(boxRequestDto.getAddress());

        return boxRepository.save(box);
    }

    @Override
    public void delete(Box box) {
        Box boxSearched = boxRepository.findById(box.getId()).orElseThrow(
                () -> new CustomException(
                        ErrorType.BOX_NOT_FOUND
        ));
        boxSearched.setActive(Boolean.FALSE);
        boxRepository.save(boxSearched);
    }
    public void incrementAvailablePorts(Box box) {
        if (box != null) {
            box.setAviablePort(box.getAviablePort() + 1);
            boxRepository.save(box);
        }
    }
    public void decreaseAvailablePorts(Box box) {
        if (box != null) {
            box.setAviablePort(box.getAviablePort() - 1);
            boxRepository.save(box);
        }
    }


    @Override
    public boolean existsById(Integer id) {
        return boxRepository.existsById(id);
    }

    @Override
    public BoxResponseDto convertToDto(Box box) {
        return BoxResponseDto.builder()
                .id(box.getId())
                .boxNumber(box.getBoxNumber())
                .portQuantity(box.getPortQuantity())
                .aviablePort(box.getAviablePort())
                .comments(box.getComments())
                .power(box.getPower())
                .address(box.getAddress())
                .build();
    }
}
