package org.example;
import io.qameta.allure.Step;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Locale;

public class UserGenerator {
    @Step("Try to create existing user")
    public User generic() {return new User("jack@sparrow.com", "P@ssw0rd123", "Sparrow");}
    @Step("Create unique user")
    public User random() {return new User(RandomStringUtils.randomAlphanumeric(5).toLowerCase(Locale.ROOT)+"@sparrow.com", "P@ssw0rd123", "Sparrow");}
    @Step("Create user without email")
    public User noEmail() {return new User(null, "P@ssw0rd123", "Sparrow");}
    @Step("Create user without password")
    public User noPassword() {return new User(RandomStringUtils.randomAlphanumeric(5).toLowerCase()+"@sparrow.com", null, "Sparrow");}
    @Step("Create user without name")
    public User noName() {return new User(RandomStringUtils.randomAlphanumeric(5).toLowerCase(Locale.ROOT)+"@sparrow.com", "P@ssw0rd123", null);}
    @Step("Try to create user with existing name and email")
    public User repeats() {return new User( "jack@sparrow.com", RandomStringUtils.randomAlphanumeric(10), "Sparrow");}
    @Step("Try to create unvalid user")
    public User notExist() { return new User(RandomStringUtils.randomAlphanumeric(10).toLowerCase(Locale.ROOT)+"@sparrow.com", RandomStringUtils.randomAlphanumeric(10), RandomStringUtils.randomAlphanumeric(10));}
    @Step("Try to create unvalid user")
    public User newCredentials() { return new User(RandomStringUtils.randomAlphanumeric(10).toLowerCase(Locale.ROOT)+"@sparrow.com", null, RandomStringUtils.randomAlphanumeric(10));}
}
