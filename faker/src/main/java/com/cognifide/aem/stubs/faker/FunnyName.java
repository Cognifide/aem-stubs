package com.cognifide.aem.stubs.faker;

public class FunnyName {
    private final Faker faker;

    protected FunnyName(Faker faker) {
        this.faker = faker;
    }

    public String name() {
        return faker.fakeValuesService().resolve("funny_name.name", this, faker);
    }
}
