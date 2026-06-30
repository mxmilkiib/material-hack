package io.github.mxmilkiib.materialistic.accounts;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.ResponseBody;
import io.reactivex.rxjava3.schedulers.Schedulers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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

    // parseLoginError

    @Test
    public void parseLoginErrorNullBodyReturnsNull() throws Exception {
        Response response = mock(Response.class);
        when(response.body()).thenReturn(null);
        assertNull(client.parseLoginError(response));
    }

    @Test
    public void parseLoginErrorExtractsBodyText() throws Exception {
        Response response = mock(Response.class);
        ResponseBody body = ResponseBody.create(
                "<body>Bad login</body>",
                MediaType.parse("text/html"));
        when(response.body()).thenReturn(body);
        assertEquals("Bad login", client.parseLoginError(response));
    }

    @Test
    public void parseLoginErrorNormalisesWhitespace() throws Exception {
        Response response = mock(Response.class);
        ResponseBody body = ResponseBody.create(
                "<body>Bad\n\tlogin  error</body>",
                MediaType.parse("text/html"));
        when(response.body()).thenReturn(body);
        assertEquals("Bad login error", client.parseLoginError(response));
    }

    @Test
    public void parseLoginErrorNoMatchReturnsNull() throws Exception {
        Response response = mock(Response.class);
        ResponseBody body = ResponseBody.create(
                "<html><head></head></html>",
                MediaType.parse("text/html"));
        when(response.body()).thenReturn(body);
        assertNull(client.parseLoginError(response));
    }

    @Test
    public void parseLoginErrorIoExceptionReturnsNull() throws Exception {
        Response response = mock(Response.class);
        ResponseBody body = mock(ResponseBody.class);
        when(body.string()).thenThrow(new IOException("network failure"));
        when(response.body()).thenReturn(body);
        assertNull(client.parseLoginError(response));
    }
}
