package com.jyramid.http;

public interface HttpRequest {

    /**
     * Gets the path of this request.
     * @return Path
     */
    public String getPath();

    /**
     * Gets the method of this request.
     * @return HttpMethod
     */
    public HttpMethod getMethod();

}
