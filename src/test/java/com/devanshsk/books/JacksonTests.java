package com.devanshsk.books;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.assertj.core.api.Assertions.assertThat;

public class JacksonTests {

    private final ObjectMapper objectMapper;

    public JacksonTests(){
        this.objectMapper = new ObjectMapper();
    }

//    @Test
//    public void testThatObjectMapperCanCreateJsonFromJavaObject() throws JsonProcessingException {
//        // Arrange: Create a Java Book Object
//        Book book = Book.builder()
//                .isbn("978-0-13-478627-5")
//                .title("The Enigma of Eternity")
//                .author("Aria Montgomery")
//                .yearPublished("2005")
//                .build();
//
//        // Act: Convert Java Object into JSON
//        String result = objectMapper.writeValueAsString(book);
//
//        // Assert: Compare generated JSON string.
//        assertThat(result)
//            .isNotNull()
//            .isEqualTo("{\"isbn\":\"978-0-13-478627-5\",\"title\":\"The Enigma of Eternity\",\"author\":\"Aria Montgomery\",\"yearPublished\":\"2005\"}");
//    }
//
//    @Test
//    public void testThatObjectMapperCanCreateJavaObjectFromJsonObject() throws JsonProcessingException {
//        // Arrange: Create a JSON string.
//        String json = "{\"foo\":\"bar\",\"isbn\":\"978-0-13-478627-5\",\"title\":\"The Enigma of Eternity\",\"author\":\"Aria Montgomery\",\"yearPublished\":\"2005\"}";
//        Book book = Book.builder()
//                .isbn("978-0-13-478627-5")
//                .title("The Enigma of Eternity")
//                .author("Aria Montgomery")
//                .yearPublished("2005")
//                .build();
//
//        // Act: Convert JSON string into Java object.
//        Book result = objectMapper.readValue(json, Book.class);
//
//        // Assert: Compare generated Java Object with Original.
//        assertThat(result).isEqualTo(book);
//    }

}
