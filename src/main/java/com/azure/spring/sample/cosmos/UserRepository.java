// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.spring.sample.cosmos;

import com.azure.spring.data.cosmos.repository.CosmosRepository;
import com.azure.spring.data.cosmos.repository.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CosmosRepository<User, String> {

    @Query(value = "SELECT * FROM c WHERE c.firstName = @firstName")
    List<User> findByFirstName(@Param("firstName") String firstName);

    @Query(value = "SELECT * FROM c WHERE c.lastName = @lastName")
    Page<User> findByLastName(@Param("lastName")String lastName, PageRequest page);

}
