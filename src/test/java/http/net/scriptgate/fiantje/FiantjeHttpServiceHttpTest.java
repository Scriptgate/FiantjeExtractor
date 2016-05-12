package net.scriptgate.fiantje;

import net.scriptgate.fiantje.http.FiantjeHttpService;
import net.scriptgate.util.TestResourceUtil;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static net.scriptgate.util.TestResourceUtil.PASSWORD;
import static net.scriptgate.util.TestResourceUtil.USERNAME;
import static org.assertj.core.api.Assertions.assertThat;

@Ignore
public class FiantjeHttpServiceHttpTest {

    private FiantjeHttpService httpService;

    @Before
    public void setUp() throws Exception {
        httpService = FiantjeHttpService.createDelayedService();
        String username = TestResourceUtil.getProperty(USERNAME);
        String password = TestResourceUtil.getProperty(PASSWORD);
        httpService.login(username, password);
    }

    @Test
    public void getHomePage_pageContainsAccountInformation() throws Exception {
        String homePage = httpService.getHomePage();
        assertThat(homePage).contains("Uw account");
    }

}