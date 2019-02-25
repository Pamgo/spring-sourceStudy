package com.ch3.balan;

/**
 * Created by OKali on 2019/1/13.
 */
public interface LoadBalancer {

    void updateCandidate();

    Endpoint nextEndpoint();

}
