package com.example.demo.util

import groovy.json.StringEscapeUtils

import javax.net.ssl.HttpsURLConnection

class RequestSetter {

  private String method
  private String accept
  private String contentType
  private String contentLength
  private String userAgent
  private String host
  private String referer
  private String origin
  private String acceptLanguage
  private String acceptEncoding
  private Boolean isUsesCache
  private Boolean isInstanceFollowRedirects
  private Boolean isDoOutput
  private Boolean isDoInput

  String getToken() {
    return token
  }

  void setToken(String token) {
    this.token = token
  }
  private String token

  RequestSetter() {

  }

  HttpURLConnection getHttpUrlConnection(HttpURLConnection httpURLConnection) {
    if (method != null)
      httpURLConnection.setRequestMethod(method)
    if (accept != null)
      httpURLConnection.setRequestProperty("Accept", accept)
    if (token != null)
      httpURLConnection.setRequestProperty("authorizationtoken", token)
    if (contentType != null)
      httpURLConnection.setRequestProperty("Content-Type", contentType)
    if (contentLength != null)
      httpURLConnection.setRequestProperty("Content-Length", contentLength)
    if (userAgent != null)
      httpURLConnection.setRequestProperty("User-Agent", userAgent)
    if (host != null)
      httpURLConnection.setRequestProperty("Host", host)
    if (referer != null)
      httpURLConnection.setRequestProperty("Referer", referer)
    if (origin != null)
      httpURLConnection.setRequestProperty("Origin", origin)
    if (acceptLanguage != null)
      httpURLConnection.setRequestProperty("Accept-Language", acceptLanguage)
    if (acceptEncoding != null)
      httpURLConnection.setRequestProperty("Accept-Encoding", acceptEncoding)
    if (isUsesCache != null)
      httpURLConnection.setUseCaches(isUsesCache)
    if (isInstanceFollowRedirects != null)
      httpURLConnection.setInstanceFollowRedirects(isInstanceFollowRedirects)
    if (isDoOutput != null)
      httpURLConnection.setDoOutput(isDoOutput)
    if (isDoInput != null)
      httpURLConnection.setDoInput(isDoInput)
    return httpURLConnection
  }

  HttpsURLConnection getHttpsUrlConnection(HttpsURLConnection httpURLConnection) {
    if (method != null)
      httpURLConnection.setRequestMethod(method)
    if (accept != null)
      httpURLConnection.setRequestProperty("Accept", accept)
    if (contentType != null)
      httpURLConnection.setRequestProperty("Content-Type", contentType)
    if (contentLength != null)
      httpURLConnection.setRequestProperty("Content-Length", contentLength)
    if (userAgent != null)
      httpURLConnection.setRequestProperty("User-Agent", userAgent)
    if (host != null)
      httpURLConnection.setRequestProperty("Host", host)
    if (referer != null)
      httpURLConnection.setRequestProperty("Referer", referer)
    if (origin != null)
      httpURLConnection.setRequestProperty("Origin", origin)
    if (acceptLanguage != null)
      httpURLConnection.setRequestProperty("Accept-Language", acceptLanguage)
    if (acceptEncoding != null)
      httpURLConnection.setRequestProperty("Accept-Encoding", acceptEncoding)
    if (isUsesCache != null)
      httpURLConnection.setUseCaches(isUsesCache)
    if (isInstanceFollowRedirects != null)
      httpURLConnection.setInstanceFollowRedirects(isInstanceFollowRedirects)
    if (isDoOutput != null)
      httpURLConnection.setDoOutput(isDoOutput)
    if (isDoInput != null)
      httpURLConnection.setDoInput(isDoInput)
    return httpURLConnection
  }

  HttpsURLConnection getHttpUrlConnection(HttpsURLConnection httpURLConnection) {
    if (method != null)
      httpURLConnection.setRequestMethod(method)
    if (accept != null)
      httpURLConnection.setRequestProperty("Accept", accept)
    if (contentType != null)
      httpURLConnection.setRequestProperty("Content-Type", contentType)
    if (contentLength != null)
      httpURLConnection.setRequestProperty("Content-Length", contentLength)
    if (userAgent != null)
      httpURLConnection.setRequestProperty("User-Agent", userAgent)
    if (host != null)
      httpURLConnection.setRequestProperty("Host", host)
    if (referer != null)
      httpURLConnection.setRequestProperty("Referer", referer)
    if (origin != null)
      httpURLConnection.setRequestProperty("Origin", origin)
    if (acceptLanguage != null)
      httpURLConnection.setRequestProperty("Accept-Language", acceptLanguage)
    if (acceptEncoding != null)
      httpURLConnection.setRequestProperty("Accept-Encoding", acceptEncoding)
    if (isUsesCache != null)
      httpURLConnection.setUseCaches(isUsesCache)
    if (isInstanceFollowRedirects != null)
      httpURLConnection.setInstanceFollowRedirects(isInstanceFollowRedirects)
    if (isDoOutput != null)
      httpURLConnection.setDoOutput(isDoOutput)
    if (isDoInput != null)
      httpURLConnection.setDoInput(isDoInput)
    return httpURLConnection
  }

  String getResponseFromReader(Reader reader, InputStream inputStream) {
    BufferedReader br = new BufferedReader(reader);
    StringBuilder sb = new StringBuilder();
    String line;
    String result;
    while ((line = br.readLine()) != null) {
      sb.append(line).append("\n");
    }
    result = sb.toString();
    br.close();
    ParameterUtils.closeInputStream(inputStream)
    return result
  }

  String getResponseFromReaderForMY(Reader reader, InputStream inputStream) {
    BufferedReader br = new BufferedReader(reader);
    StringBuilder sb = new StringBuilder();
    String line;
    String result;
    while ((line = br.readLine()) != null) {
      sb.append(line).append("\n");
    }
    result = sb.toString();
    int intval = result.indexOf("(")

    String strnew = result.substring(intval + 18, result.length() - (1 + intval + 18 + 4));
    String decodedString = StringEscapeUtils.unescapeJava(strnew)
    br.close();
    ParameterUtils.closeInputStream(inputStream)
    return decodedString
  }

  void setAccept(String accept) {
    this.accept = accept
  }

  void setMethod(String method) {
    this.method = method
  }

  void setContentType(String contentType) {
    this.contentType = contentType
  }

  void setContentLength(String contentLength) {
    this.contentLength = contentLength
  }

  void setUserAgent(String userAgent) {
    this.userAgent = userAgent
  }

  void setHost(String host) {
    this.host = host
  }

  void setReferer(String referer) {
    this.referer = referer
  }

  void setOrigin(String origin) {
    this.origin = origin
  }

  void setAcceptLanguage(String acceptLanguage) {
    this.acceptLanguage = acceptLanguage
  }

  void setAcceptEncoding(String acceptEncoding) {
    this.acceptEncoding = acceptEncoding
  }

  void setIsUsesCache(Boolean isUsesCache) {
    this.isUsesCache = isUsesCache
  }

  void setIsInstanceFollowRedirects(Boolean isInstanceFollowRedirects) {
    this.isInstanceFollowRedirects = isInstanceFollowRedirects
  }

  void setIsDoOutput(Boolean isDoOutput) {
    this.isDoOutput = isDoOutput
  }

  void setIsDoInput(Boolean isDoInput) {
    this.isDoInput = isDoInput
  }

}
