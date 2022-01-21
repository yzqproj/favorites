package com.favorites;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.jupiter.api.Test;

import java.time.Period;
import java.util.List;

@Getter
@Setter

public class Person {
    String name;
    int age;
    boolean smoker;
    List<String> dogs;

    public String toString() {
       return ToStringBuilder.reflectionToString(this);
    }

    @Test
    public void getPerson(){
        Person p=new Person();
        p.setName("zhangsan");
        p.setAge(20);
        p.setSmoker(true);
        p.setDogs(List.of("dog1","dog2"));
        System.out.println(p.toString());
        System.out.println(ToStringBuilder.reflectionToString(p ));
    }
}