package com.jdnt.perficient.training.utility;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ParseToJson {

    public ParseToJson() {}

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
