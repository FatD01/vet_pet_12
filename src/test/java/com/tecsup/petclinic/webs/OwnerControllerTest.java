package com.tecsup.petclinic.webs;

import com.tecsup.petclinic.entities.Owner;
import com.tecsup.petclinic.repositories.OwnerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class OwnerControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private OwnerRepository ownerRepository;

    @Test
    void testGetAllOwners() throws Exception {
        mockMvc.perform(get("/owners"))
                .andExpect(status().isOk());
    }

    @Test
    void testCreateOwner() throws Exception {
        Owner owner = new Owner();
        owner.setFirstName("Juan");
        owner.setLastName("Lopez");
        owner.setAddress("Calle Falsa 123");
        owner.setCity("Lima");
        owner.setTelephone("987654321");

        mockMvc.perform(post("/owners")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(owner)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("Juan"));
    }
}
