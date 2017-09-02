package com.company.model;

import lombok.*;

@Data
@EqualsAndHashCode
@RequiredArgsConstructor
@ToString
public class Test {
    private Integer id;
    private Integer field;

    public Test(Integer id, Integer field) {
        this.id = id;
        this.field = field;
    }
}
