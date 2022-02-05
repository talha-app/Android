package com.talha.sample.application.sqlite.data.entity;

import java.time.LocalDateTime;

import io.objectbox.annotation.Convert;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.converter.PropertyConverter;

@Entity
public class User {
    @Id
    public long id;
    private String name;
    private String username;
    private String password;
    @Convert(converter = LocalDateTimeConverter.class, dbType = String.class)
    private LocalDateTime registerDate;

    public User(long id, String name, String username, String password, LocalDateTime registerDate) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.registerDate = registerDate;
    }

    public User(String name, String username, String password, LocalDateTime registerDate) {
        this(0, name, username, password, LocalDateTime.now());
    }

    public User(String name, String username, String password) {
        this(name, username, password, LocalDateTime.now());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(LocalDateTime registerDate) {
        this.registerDate = registerDate;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                '}';
    }
}

class LocalDateTimeConverter implements PropertyConverter<LocalDateTime, String> {

    @Override
    public LocalDateTime convertToEntityProperty(String databaseValue) {
        if (databaseValue == null) {
            return LocalDateTime.now();
        }
        return LocalDateTime.parse(databaseValue);

    }

    @Override
    public String convertToDatabaseValue(LocalDateTime entityProperty) {
        return entityProperty.toString();
    }
}
