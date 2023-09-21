import com.fasterxml.jackson.databind.ObjectMapper;
import model.MyModel;
import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;

public class Example {

    public static void main(String[] args) throws IOException {
      /*  String username = "your_username";
        String password = "your_password";

        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));

        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
                        .setHttpClientConfigCallback(httpAsyncClientBuilder -> {
                            httpAsyncClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                            return httpAsyncClientBuilder;
                        }));*/

        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http")));

        // Replace with your index name
        SearchRequest searchRequest = new SearchRequest("YOUR_INDEX");

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        sourceBuilder.query(QueryBuilders.simpleQueryStringQuery("4580-94d6-c9d47d61f0be")
                .field("id"));

        searchRequest.source(sourceBuilder);

        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

        ObjectMapper objectMapper = new ObjectMapper();
        SearchHit[] hits = searchResponse.getHits().getHits();


        for (SearchHit searchHit : hits) {
            System.out.println(searchHit.getSourceAsString());
            MyModel mrwArticle = objectMapper.readValue(searchHit.getSourceAsString(), MyModel.class);
            System.out.println(mrwArticle.getId());
            System.out.println(mrwArticle.getName());
        }

        client.close();
    }
}
