package fr.univ_smb.isc.m1.totaly_not_p.adapters.api;

import fr.univ_smb.isc.m1.totaly_not_p.infrastructure.persistence.ChuckFact;
import fr.univ_smb.isc.m1.totaly_not_p.application.ChuckFactsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;

import static java.util.List.of;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

class ChuckFactsControllerTest {

    private ChuckFactsService chuckFactsService;
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.chuckFactsService = mock(ChuckFactsService.class);
        this.mockMvc = standaloneSetup(new ChuckFactsController(chuckFactsService)).build();
    }

    @Test
    public void shouldPippoTwice() throws Exception {

        when(chuckFactsService.facts())
                .thenReturn(of(new ChuckFact("pipo-1"), new ChuckFact("pipo-2")));

        mockMvc.perform(get("/chuck-facts"))
                .andExpect(status().isOk())
                .andExpect(content().string("[\"pipo-1\",\"pipo-2\"]"));
    }
}