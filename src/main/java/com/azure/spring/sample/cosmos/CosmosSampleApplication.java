// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
package com.azure.spring.sample.cosmos;

import com.azure.spring.data.cosmos.core.query.CosmosPageRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class CosmosSampleApplication implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(CosmosSampleApplication.class);

    @Autowired
    private UserRepository repository;

    public static void main(String[] args) {
        SpringApplication.run(CosmosSampleApplication.class, args);
    }

    public void run(String... var1) {
        this.repository.deleteAll();
        LOGGER.info("Deleted all data in container.");

        final User testUser = new User("testId", "testFirstName", "testLastName", "test address line one");
        final User testUser2 = new User("testId1", "testFirstName", "testLastName", "test address line one");
        final User testUser3 = new User("testId2", "testFirstName", "testLastName", "test address line one");
        final User testUser4 = new User("testId3", "testFirstName", "testLastName", "test address line one");

        List<User> userInserts = new ArrayList<>();
        userInserts.add(testUser);
        userInserts.add(testUser2);
        userInserts.add(testUser3);
        userInserts.add(testUser4);

        // Save the User class to Azure Cosmos DB database.
        repository.saveAll(userInserts);

        final List<User> users = repository.findByFirstName("testFirstName");

        users.forEach(user -> System.out.println(user.getId()));

        final PageRequest pageRequest = new CosmosPageRequest(2, 2, null);
        Page<User> userPage = repository.findByLastName("testLastName", pageRequest);
        userPage.getContent().forEach(user -> System.out.println(user.getId()));
        while (userPage.hasNext()) {
            Pageable nextPageable = userPage.nextPageable();
            userPage = repository.findAll(nextPageable);
            List<User> userList = userPage.getContent();
            userList.forEach(user -> System.out.println(user.getId()));
        }
    }
}
