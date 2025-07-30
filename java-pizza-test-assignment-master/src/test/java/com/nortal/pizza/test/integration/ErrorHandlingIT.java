package com.nortal.pizza.test.integration;

import com.nortal.pizza.levels.Mid;
import com.nortal.pizza.levels.Senior;
import com.nortal.pizza.task.Assignment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ErrorHandlingIT extends BaseIT {

    /***
     * Implement an error handler.
     * When a request is received for an order, which doesn't exist - the application should return 404 http status code.
     */
    @Mid(task = Assignment.TASK_11)
    @Senior(task = Assignment.TASK_11)
    @DisplayName(Assignment.TaskDescription.TASK_DESCRIPTION_11)
    @Test
    public void shouldReturnNotFoundErrorWhenWrongOrderIsPassed() throws Exception {
        mvc.perform(
                        MockMvcRequestBuilders
                                .request(HttpMethod.GET, "/order/{id}", "123456789")
                                .with(USER_ADMIN)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
