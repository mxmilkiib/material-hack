package io.github.mxmilkiib.materialistic.accounts;

import org.junit.Before;
import org.junit.Test;

import okhttp3.OkHttpClient;
import io.reactivex.rxjava3.schedulers.Schedulers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class UserServicesClientTest {

    private UserServicesClient client;

    @Before
    public void setUp() {
        client = new UserServicesClient(new OkHttpClient(), Schedulers.trampoline());
    }

    @Test
    public void getInputValueExtractsValueFromInputTag() {
        String html = "<input name=\"fnid\" value=\"abc123\" type=\"hidden\">";
        String result = client.getInputValue(html, "fnid");
        assertEquals("abc123", result);
    }

    @Test
    public void getInputValueExtractsValueWithSpacesInAttribute() {
        String html = "<input type=\"hidden\" name=\"fnid\" value = \"xyz789\">";
        String result = client.getInputValue(html, "fnid");
        assertEquals("xyz789", result);
    }

    @Test
    public void getInputValueReturnsNullWhenInputNotFound() {
        String html = "<input name=\"other\" value=\"123\">";
        String result = client.getInputValue(html, "fnid");
        assertNull(result);
    }

    @Test
    public void getInputValueReturnsNullWhenNoValueAttribute() {
        String html = "<input name=\"fnid\" type=\"hidden\">";
        String result = client.getInputValue(html, "fnid");
        assertNull(result);
    }

    @Test
    public void getInputValueHandlesMultipleInputs() {
        String html = "<input name=\"acct\" value=\"user\">" +
                "<input name=\"pw\" value=\"pass\">" +
                "<input name=\"fnid\" value=\"token456\">";
        String result = client.getInputValue(html, "fnid");
        assertEquals("token456", result);
    }

    @Test
    public void getInputValueHandlesEmptyHtml() {
        String result = client.getInputValue("", "fnid");
        assertNull(result);
    }

    @Test
    public void getInputValueHandlesMalformedHtml() {
        String html = "<input name=\"fnid\" value=\"unclosed";
        String result = client.getInputValue(html, "fnid");
        assertNull(result);
    }

    @Test
    public void getInputValueExtractsFirstMatch() {
        String html = "<input name=\"fnid\" value=\"first\">" +
                "<input name=\"fnid\" value=\"second\">";
        String result = client.getInputValue(html, "fnid");
        assertEquals("first", result);
    }
}
