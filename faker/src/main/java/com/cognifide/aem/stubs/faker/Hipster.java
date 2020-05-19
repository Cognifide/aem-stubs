package com.cognifide.aem.stubs.faker;

public class Hipster {
    private final Faker faker;

    protected Hipster(final Faker faker) {
        this.faker = faker;
    }

    public String word() {
        return faker.resolve("hipster.words");
    }
}
