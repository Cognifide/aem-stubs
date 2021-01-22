package com.cognifide.aem.stubs.wiremock.cors;

import static com.google.common.net.HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS;
import static com.google.common.net.HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS;
import static com.google.common.net.HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN;
import static com.google.common.net.HttpHeaders.ACCESS_CONTROL_REQUEST_HEADERS;
import static com.google.common.net.HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD;
import static java.util.Objects.isNull;

import javax.servlet.http.HttpServletResponse;

import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.http.Response;

public final class CorsHandler {

  private final CorsConfiguration configuration;
  private final boolean corsPreflightRequest;
  private final HttpServletResponse httpServletResponse;
  private final Response response;

  public CorsHandler(CorsConfiguration configuration,
    Request request, Response response, HttpServletResponse httpServletResponse) {
    this.configuration = configuration;
    this.corsPreflightRequest = isCorsPreflightRequest(request);
    this.httpServletResponse = httpServletResponse;
    this.response = response;
  }

  private boolean isCorsPreflightRequest(Request request) {
    return configuration.isEnabled() && "OPTIONS".equals(request.getMethod().getName()) &&
      !isNull(request.getHeader(ACCESS_CONTROL_REQUEST_HEADERS)) &&
      !isNull(request.getHeader(ACCESS_CONTROL_REQUEST_METHOD));
  }

  public void handleCorsHeaders() {
    if (!configuration.isEnabled()) {
      return;
    }

    if (isCorsPreflightRequest()) {
      httpServletResponse.setStatus(200);
      addHeader(ACCESS_CONTROL_ALLOW_HEADERS, configuration.getAllowHeaders());
      addHeader(ACCESS_CONTROL_ALLOW_METHODS, configuration.getAllowMethods());
    }

    addHeader(ACCESS_CONTROL_ALLOW_ORIGIN, configuration.getAllowOrigin());
  }

  private void addHeader(String key, String value) {
    if (response.getHeaders().keys().contains(key)) {
      return;
    }

    httpServletResponse.addHeader(key, value);
  }
  public boolean isCorsPreflightRequest() {
    return corsPreflightRequest;
  }
}
