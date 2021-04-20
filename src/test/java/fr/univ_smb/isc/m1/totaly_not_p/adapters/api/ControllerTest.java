// package fr.univ_smb.isc.m1.totaly_not_p.adapters.api;

// import fr.univ_smb.isc.m1.totaly_not_p.infrastructure.persistence.Comic;
// import fr.univ_smb.isc.m1.totaly_not_p.application.ComicsService;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.springframework.test.web.servlet.MockMvc;

// import static java.util.List.of;
// import static org.mockito.Mockito.mock;
// import static org.mockito.Mockito.when;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
// import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

// class ControllerTest {

//     private ComicsService comicsService;
//     private MockMvc mockMvc;

//     @BeforeEach
//     public void setup() {
//         this.comicsService = mock(ComicsService.class);
//         this.mockMvc = standaloneSetup(new ComicsController(comicsService)).build();
//     }

//     @Test
//     public void shouldBDThreeTimes() throws Exception {

//         when(comicsService.allComics())
//                 .thenReturn(of(new Comic("BD 1", "AUTEUR 1"), new Comic("BD 2", "AUTEUR 2"), new Comic("BD 3", "AUTEUR 3")));

//         mockMvc.perform(get("/api/comics"))
//                 .andExpect(status().isOk())
//                 .andExpect(content().string("[\"BD 1 by AUTEUR 1\",\"BD 2 by AUTEUR 2\",\"BD 3 by AUTEUR 3\"]"));
//     }

//     @Test
//     public void testHomePage() throws Exception {
//         this.mockMvc.perform(get("/")).andExpect(status().isOk())
//                 .andExpect(content().string("BD 1\nBD 2\nBD 3\n"));
//     }
// }