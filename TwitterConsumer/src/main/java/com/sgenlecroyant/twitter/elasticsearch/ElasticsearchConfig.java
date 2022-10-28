package com.sgenlecroyant.twitter.elasticsearch;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;

public class ElasticsearchConfig {

	public static ElasticsearchClient getElasticsearchClient(String host, int port) {
		System.out.println("building Rest Client Instance ...");
		RestClient restClient = RestClient.builder(new HttpHost(host, port)).build();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("done!");
		System.out.println("bulding ElasticsearchTransport ...");
		ElasticsearchTransport elasticsearchTransport = new RestClientTransport(restClient, new JacksonJsonpMapper());
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Done!");
		System.out.println("Building ElasticsearchClient ...");
		ElasticsearchClient elasticsearchClient = new ElasticsearchClient(elasticsearchTransport);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Done!");
		return elasticsearchClient;
	}
}
