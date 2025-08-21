package org.example.snsapp.global.convert;

import org.example.snsapp.global.enums.SearchType;
import org.springframework.core.convert.converter.Converter;

public class SearchTypeConverter implements Converter<String, SearchType> {

    @Override
    public SearchType convert(String source) {
        return SearchType.valueOf(source.toUpperCase());
    }
}
