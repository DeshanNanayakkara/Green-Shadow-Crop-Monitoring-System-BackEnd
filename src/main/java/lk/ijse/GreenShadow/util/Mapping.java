package lk.ijse.GreenShadow.util;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class Mapping {
    private final ModelMapper modelMapper;
    // Generic method to map one object to another type
    public <S, T> T map(S source, Class<T> targetClass) {
        return modelMapper.map(source, targetClass);
    }

    // Generic method to map a list of objects to another type
    public <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
        return modelMapper.map(source, new TypeToken<List<T>>() {}.getType());
    }
}