package SeguiTusCompras.Controller.Utils.ObjectMappers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import SeguiTusCompras.Controller.dtos.QualificationDto;
import SeguiTusCompras.model.Comment;
import SeguiTusCompras.model.Qualification;

public class QualificationMapper {

    public static List<QualificationDto> convertListToDto(List<Qualification> qualifications) {
        if (qualifications == null) {
            return new ArrayList<>();
        }
        return qualifications.stream()
                             .map(QualificationMapper::convertToDto)
                             .collect(Collectors.toList());
    }

    public static QualificationDto convertToDto(Qualification qualification) {
        Comment commentEntity = qualification.getComment();
        if (commentEntity != null) {
            return new QualificationDto(qualification.getUser().getUsername(), qualification.getProduct().getName(), 
                                        qualification.getScore(), commentEntity.getComment());
        }
        return new QualificationDto(qualification.getUser().getName(), qualification.getProduct().getName(),
                                    qualification.getScore(), null);
    }
 }

