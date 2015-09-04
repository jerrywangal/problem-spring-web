package org.zalando.problem.springweb.advice;

/*
 * #%L
 * problem-handling
 * %%
 * Copyright (C) 2015 Zalando SE
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */


import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.zalando.problem.springweb.MediaTypes;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ThrowablesIT extends AdviceIT {

    @Override
    protected Object advice() {
        return new Throwables() {
        };
    }

    @Test
    public void throwableProblem() throws Exception {
        mvc.perform(request(GET, URI_HANDLER_PROBLEM))
                .andExpect(header().string("Content-Type", Matchers.is(MediaTypes.PROBLEM_VALUE)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.title", is("expected")))
                .andExpect(jsonPath("$.status", is(HttpStatus.CONFLICT.value())));
    }

    @Test
    public void throwable() throws Exception {
        mvc.perform(request(GET, URI_HANDLER_THROWABLE))
                .andExpect(header().string("Content-Type", is(MediaTypes.PROBLEM_VALUE)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.title", is(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())))
                .andExpect(jsonPath("$.status", is(HttpStatus.INTERNAL_SERVER_ERROR.value())))
                .andExpect(jsonPath("$.detail", containsString("expected")));
    }

}