package org.cleanpojo.ikkon.fixtures;

import java.util.UUID;

public class ParameterlessSetMethod {

    private UUID id;
    private String name;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName() {
        this.name = "Obiwan Kenobi";
    }
}
