package com.painting.application.direction;

public enum Direction {
    NORTH("North"), WEST("West"), SOUTH("South"), EAST("East"), CENTER("Center");

    private String name;

    Direction(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
