package SeguiTusCompras.Controller.Utils.ObjectMappers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import SeguiTusCompras.Controller.dtos.QualificationDto;
import SeguiTusCompras.model.Comment;
import SeguiTusCompras.model.Qualification;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class QualificationMapper {

    public static List<QualificationDto> convertListToDto(Collection<Qualification> qualifications) {
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

