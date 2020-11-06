package com.cognifide.aem.stubs.wiremock;

import static com.cognifide.aem.stubs.wiremock.TransformerEngine.HANDLEBARS;
import static com.cognifide.aem.stubs.wiremock.TransformerEngine.PEBBLE;
import static com.google.common.collect.Maps.newLinkedHashMap;

import java.util.List;
import java.util.Map;

import com.cognifide.aem.stubs.wiremock.transformers.PebbleTransformer;
import com.cognifide.aem.stubs.wiremock.util.JcrFileReader;
import com.github.tomakehurst.wiremock.extension.Extension;
import com.github.tomakehurst.wiremock.extension.ExtensionLoader;
import com.github.tomakehurst.wiremock.extension.responsetemplating.ResponseTemplateTransformer;
import com.google.common.collect.ImmutableList;

class WireMockOptionsFactory {

  private final WireMockStubs stubs;
  private final Map<String, Extension> extensions = newLinkedHashMap();

  public WireMockOptionsFactory(WireMockStubs stubs) {
    this.stubs = stubs;
    this. extensions.putAll(ExtensionLoader.asMap(transformers()));;
  }

  public WireMockOptions create() {
    return new WireMockOptions(stubs.getResolverAccessor(),
      getRootPath(),
      stubs.getConfig().requestJournalEnabled(),
      stubs.getConfig().requestJournalMaxSize(),
      extensions);
  }

  private String getRootPath() {
    return stubs.getRootPath() + "/" + stubs.getId();
  }

  private List<Extension> transformers() {
    JcrFileReader jcrFileReader = new JcrFileReader(stubs.getResolverAccessor(), getRootPath());
    return ImmutableList.<Extension>builder()
      .add(new PebbleTransformer(jcrFileReader, stubs.getConfig().globalTransformer() == PEBBLE))
      .add(new ResponseTemplateTransformer(stubs.getConfig().globalTransformer() == HANDLEBARS))
      .build();
  }
}