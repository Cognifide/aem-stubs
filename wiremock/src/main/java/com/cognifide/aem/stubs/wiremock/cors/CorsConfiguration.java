package com.cognifide.aem.stubs.wiremock.cors;

public class CorsConfiguration {
  private final String allowHeaders;
  private final String allowMethods;
  private final String allowOrigin;
  private final boolean enabled;

  public CorsConfiguration(boolean enabled, String allowHeaders, String allowMethods, String allowOrigin) {
    this.allowHeaders = allowHeaders;
    this.allowMethods = allowMethods;
    this.allowOrigin = allowOrigin;
    this.enabled = enabled;
  }

  public static CorsConfiguration disabled() {
    return new CorsConfiguration(false, null, null, null);
  }

  public static CorsConfiguration enabled() {
    return new CorsConfiguration(true, "*", "*", "*");
  }

  public static CorsConfiguration enabled(String allowHeaders, String allowMethods, String allowOrigin) {
    return new CorsConfiguration(true, allowHeaders, allowMethods, allowOrigin);
  }

  public String getAllowHeaders() {
    return allowHeaders;
  }

  public String getAllowMethods() {
    return allowMethods;
  }

  public String getAllowOrigin() {
    return allowOrigin;
  }

  public boolean isEnabled() {
    return enabled;
  }
}
