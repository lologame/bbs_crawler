package com.lo.bbscrawler.test;

import org.apache.commons.collections.map.HashedMap;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.node.DiscoveryNode;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkIndexByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

import static org.elasticsearch.common.xcontent.XContentFactory.*;

/**
 * Created by Administrator on 2017/2/26.
 */
public class EsTest {

    private TransportClient client;

    @Before
    public void init() throws UnknownHostException {
        this.client = new PreBuiltTransportClient(Settings.EMPTY)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));
    }

    @Test
    public void test1() throws UnknownHostException {

        List<DiscoveryNode> nodes = client.connectedNodes();
        for(DiscoveryNode node : nodes){
            System.out.println(node.getAddress().getAddress());
        }

//        GetResponse response = client.prepareGet("a", "b", "1").get();
//        Map<String,Object> source = response.getSource();
//        for(Map.Entry entry : source.entrySet()){
//            System.out.println(entry.getKey() + "->" + entry.getValue());
//        }
        Map<String,Object> json = new HashedMap();
        json.put("user","wang");
        json.put("sex","male");
        Map<String,Object> json2 = new HashedMap();
        json2.put("user","michel");
        json2.put("sex","female");
        client.prepareIndex("a","b","2")
                .setSource(json).get();
        client.prepareIndex("a","b","4")
                .setSource(json2).get();
//        BulkIndexByScrollResponse response =
//                DeleteByQueryAction.INSTANCE.newRequestBuilder(client)
//                        .filter(QueryBuilders.matchQuery("sex", "male"))
//                        .source("a")
//                        .get();

//        System.out.println( response.getDeleted());
        client.close();
    }

    @Test
    public void test2() throws IOException {

        BulkRequestBuilder bulkRequest = client.prepareBulk();

        bulkRequest.add(client.prepareIndex("twitter", "tweet", "1")
                .setSource(jsonBuilder()
                        .startObject()
                        .field("user", "kimchy")
                        .field("postDate", new Date())
                        .field("message", "trying out Elasticsearch")
                        .endObject()
                )
        );
        bulkRequest.add(client.prepareIndex("twitter", "tweet", "2")
                .setSource(jsonBuilder()
                        .startObject()
                        .field("user", "kimchy")
                        .field("postDate", new Date())
                        .field("message", "another post")
                        .endObject()
                )
        );
        BulkResponse bulkResponse = bulkRequest.get();
    }
}
