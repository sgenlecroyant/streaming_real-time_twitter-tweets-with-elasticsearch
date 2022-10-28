package com.sgenlecroyant.twitter.elasticsearch;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;

import com.fasterxml.jackson.databind.ObjectMapper;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;

public class ElasticsearchConfig {

	public static ElasticsearchClient getElasticsearchClient(String host, int port) {

		RestClient restClient = RestClient.builder(new HttpHost(host, port)).build();

		ElasticsearchTransport elasticsearchTransport = new RestClientTransport(restClient,
				new JacksonJsonpMapper(new ObjectMapper()));
		ElasticsearchClient elasticsearchClient = new ElasticsearchClient(elasticsearchTransport);
		return elasticsearchClient;
	}
}
