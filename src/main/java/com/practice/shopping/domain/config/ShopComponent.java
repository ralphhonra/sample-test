package com.practice.shopping.domain.config;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
@AllArgsConstructor
public class ShopComponent {
    private final ModelMapper modelMapper;

    public <S, T> List<T> mapList(Collection<S> sourceList, Class<T> targetClass) {
        return sourceList.stream()
                .map(current -> modelMapper.map(current, targetClass))
                .toList();
    }
}
