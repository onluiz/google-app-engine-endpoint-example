package br.com.app1;

import br.com.app1.Email;
import br.com.app1.Message;
import com.google.api.server.spi.auth.EspAuthenticator;
import com.google.api.server.spi.auth.common.User;
import com.google.api.server.spi.config.*;
import com.google.api.server.spi.response.UnauthorizedException;

/** The Echo API which Endpoints will be exposing. */
// [START echo_api_annotation]
@Api(
        name = "app1",
        version = "v1",
        namespace =
        @ApiNamespace(
                ownerDomain = "app1.com.br",
                ownerName = "app1.com.br",
                packagePath = ""
        ),
        // [START_EXCLUDE]
        issuers = {
                @ApiIssuer(
                        name = "firebase",
                        issuer = "https://securetoken.google.com/YOUR-PROJECT-ID",
                        jwksUri = "https://www.googleapis.com/robot/v1/metadata/x509/securetoken@system.gserviceaccount.com")
        }
        // [END_EXCLUDE]
)
// [END echo_api_annotation]
public class App1 {
    @ApiMethod(name = "little", httpMethod = ApiMethod.HttpMethod.GET)
    public Message little() {
        Message message = new Message();
        message.setMessage("teste");
        return message;
    }

    /**
     * Echoes the received message back. If n is a non-negative integer, the message is copied that
     * many times in the returned message.
     *
     * Note that name is specified and will override the default name of "{class name}.{method
     * name}". For example, the default is "echo.echo".
     *
     * Note that httpMethod is not specified. This will default to a reasonable HTTP method
     * depending on the API method name. In this case, the HTTP method will default to POST.
     */
    // [START echo_method]
    @ApiMethod(name = "echo")
    public Message echo(Message message, @Named("n") @Nullable Integer n) {
        return doEcho(message, n);
    }
    // [END echo_method]

    /**
     * Echoes the received message back. If n is a non-negative integer, the message is copied that
     * many times in the returned message.
     *
     * Note that name is specified and will override the default name of "{class name}.{method
     * name}". For example, the default is "echo.echo".
     *
     * Note that httpMethod is not specified. This will default to a reasonable HTTP method
     * depending on the API method name. In this case, the HTTP method will default to POST.
     */
    // [START echo_path]
    @ApiMethod(name = "echo_path_parameter", path = "echo/{n}")
    public Message echoPathParameter(Message message, @Named("n") int n) {
        return doEcho(message, n);
    }
    // [END echo_path]

    /**
     * Echoes the received message back. If n is a non-negative integer, the message is copied that
     * many times in the returned message.
     *
     * Note that name is specified and will override the default name of "{class name}.{method
     * name}". For example, the default is "echo.echo".
     *
     * Note that httpMethod is not specified. This will default to a reasonable HTTP method
     * depending on the API method name. In this case, the HTTP method will default to POST.
     */
    // [START echo_api_key]
    @ApiMethod(name = "echo_api_key", path = "echo_api_key", apiKeyRequired = AnnotationBoolean.TRUE)
    public Message echoApiKey(Message message, @Named("n") @Nullable Integer n) {
        return doEcho(message, n);
    }
    // [END echo_api_key]

    private Message doEcho(Message message, Integer n) {
        if (n != null && n >= 0) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < n; i++) {
                if (i > 0) {
                    sb.append(" ");
                }
                sb.append(message.getMessage());
            }
            message.setMessage(sb.toString());
        }
        return message;
    }

    /**
     * Gets the authenticated user's email. If the user is not authenticated, this will return an HTTP
     * 401.
     *
     * Note that name is not specified. This will default to "{class name}.{method name}". For
     * example, the default is "echo.getUserEmail".
     *
     * Note that httpMethod is not required here. Without httpMethod, this will default to GET due
     * to the API method name. httpMethod is added here for example purposes.
     */
    // [START google_id_token_auth]
    @ApiMethod(
            httpMethod = ApiMethod.HttpMethod.GET,
            authenticators = {EspAuthenticator.class},
            audiences = {"YOUR_OAUTH_CLIENT_ID"},
            clientIds = {"YOUR_OAUTH_CLIENT_ID"}
    )
    public Email getUserEmail(User user) throws UnauthorizedException {
        if (user == null) {
            throw new UnauthorizedException("Invalid credentials");
        }

        Email response = new Email();
        response.setEmail(user.getEmail());
        return response;
    }
    // [END google_id_token_auth]

    /**
     * Gets the authenticated user's email. If the user is not authenticated, this will return an HTTP
     * 401.
     *
     * Note that name is not specified. This will default to "{class name}.{method name}". For
     * example, the default is "echo.getUserEmail".
     *
     * Note that httpMethod is not required here. Without httpMethod, this will default to GET due
     * to the API method name. httpMethod is added here for example purposes.
     */
    // [START firebase_auth]
    @ApiMethod(
            path = "firebase_user",
            httpMethod = ApiMethod.HttpMethod.GET,
            authenticators = {EspAuthenticator.class},
            issuerAudiences = {@ApiIssuerAudience(name = "firebase", audiences = {"YOUR-PROJECT-ID"})}
    )
    public Email getUserEmailFirebase(User user) throws UnauthorizedException {
        if (user == null) {
            throw new UnauthorizedException("Invalid credentials");
        }

        Email response = new Email();
        response.setEmail(user.getEmail());
        return response;
    }
    // [END firebase_auth]
}
