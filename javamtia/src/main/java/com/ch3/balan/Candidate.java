package com.ch3.balan;

import jdk.nashorn.internal.runtime.regexp.joni.ast.EncloseNode;

import java.util.Iterator;
import java.util.Set;

/**
 * Created by OKali on 2019/1/13.
 */
public class Candidate implements Iterable<Endpoint> {

    // 下游部件节点列表
    private final Set<Endpoint> endpoints;

    // 下游部件节点总权重
    public final int totalWeight;

    public Candidate(Set<Endpoint> endpoints) {
        int sum = 0;
        for (Endpoint endpoint : endpoints) {
            sum += endpoint.weight;
        }
        this.totalWeight = sum;
        this.endpoints = endpoints;

    }

    @Override
    public Iterator<Endpoint> iterator() {
        return null;
    }
}
