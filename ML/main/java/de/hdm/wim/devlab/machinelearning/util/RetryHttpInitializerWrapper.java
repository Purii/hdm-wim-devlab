package de.hdm.wim.devlab.machinelearning.util;

import java.io.IOException;
import java.util.logging.Logger;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.HttpBackOffIOExceptionHandler;
import com.google.api.client.http.HttpBackOffUnsuccessfulResponseHandler;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpUnsuccessfulResponseHandler;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.client.util.Sleeper;
import com.google.common.base.Preconditions;

/**
 * RetryHttpInitializerWrapper wird im RPC-Fehlerfall automatisch angestoßen.
 */
public class RetryHttpInitializerWrapper implements HttpRequestInitializer {

    /**
     * Privater Logger.
     */
    private static final Logger LOG =
            Logger.getLogger(RetryHttpInitializerWrapper.class.getName());

    /**
     * Eine Minute in Millisekunden.
     */
    private static final int ONEMINITUES = 60000;

    /**
     * Header für die Credential-Abfrage.
     */
    private final Credential wrappedCredential;

    /**
     * Sleeper.
     */
    private final Sleeper sleeper;

    /**
     * Konstruktor.
     *
     */
    public RetryHttpInitializerWrapper(final Credential wrappedCredential) {
        this(wrappedCredential, Sleeper.DEFAULT);
    }

    /**
     * Ein geschützer Konstruktor für Testzwecke.
     */
    RetryHttpInitializerWrapper(
            final Credential wrappedCredential, final Sleeper sleeper) {
        this.wrappedCredential = Preconditions.checkNotNull(wrappedCredential);
        this.sleeper = sleeper;
    }

    /**
     * Initialisiert den Report.
     */
    @Override
    public final void initialize(final HttpRequest request) {
        request.setReadTimeout(2 * ONEMINITUES); // 2 minutes read timeout
        final HttpUnsuccessfulResponseHandler backoffHandler =
                new HttpBackOffUnsuccessfulResponseHandler(
                        new ExponentialBackOff())
                        .setSleeper(sleeper);
        request.setInterceptor(wrappedCredential);
        request.setUnsuccessfulResponseHandler(
                new HttpUnsuccessfulResponseHandler() {
                    @Override
                    public boolean handleResponse(
                            final HttpRequest request,
                            final HttpResponse response,
                            final boolean supportsRetry) throws IOException {
                        if (wrappedCredential.handleResponse(
                                request, response, supportsRetry)) {
                            // If credential decides it can handle it,
                            // the return code or message indicated
                            // something specific to authentication,
                            // and no backoff is desired.
                            return true;
                        } else if (backoffHandler.handleResponse(
                                request, response, supportsRetry)) {
                            // Otherwise, we defer to the judgement of
                            // our internal backoff handler.
                            LOG.info("Retrying "
                                    + request.getUrl().toString());
                            return true;
                        } else {
                            return false;
                        }
                    }
                });
        request.setIOExceptionHandler(
                new HttpBackOffIOExceptionHandler(new ExponentialBackOff())
                        .setSleeper(sleeper));
    }
}

