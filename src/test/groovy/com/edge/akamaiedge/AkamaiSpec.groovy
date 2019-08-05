package com.edge.akamaiedge

import com.akamai.edgegrid.signer.ClientCredential
import com.akamai.edgegrid.signer.apachehttpclient.ApacheHttpClientEdgeGridInterceptor
import com.akamai.edgegrid.signer.apachehttpclient.ApacheHttpClientEdgeGridRoutePlanner
import org.apache.commons.io.IOUtils
import org.apache.http.HttpResponse
import org.apache.http.client.HttpClient
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.methods.HttpPut
import org.apache.http.impl.client.HttpClientBuilder
import spock.lang.Specification


class AkamaiSpec extends Specification {
    String s = "/network-list/v2/network-lists/60557_I3TEST?extended=true&includeElements=true"
    def 'Test akamai connection'() throws Exception{
        given:
            ClientCredential.ClientCredentialBuilder clientCredentialBuilder = new ClientCredential.ClientCredentialBuilder()
            clientCredentialBuilder.clientSecret("1FNDxpSpQqla6xupvzdQAfJ5o3Mz5KFEX1+OiZibNvk=")//401
            clientCredentialBuilder.host("akab-4s7rgr3vsy3dkfto-e2tduibfzl3pbloh.luna.akamaiapis.net")//400
            clientCredentialBuilder.accessToken("akab-ovan6n46uhvsftdg-yz5vs7o2s5cf32hx")//401
            clientCredentialBuilder.clientToken("akab-fpw3euy2mhpxmapx-44ejpchoc3bt2j5l") //400

            ClientCredential clientCredential = clientCredentialBuilder.build()

        and:
        HttpClient client = HttpClientBuilder.create()
                .addInterceptorFirst(new ApacheHttpClientEdgeGridInterceptor(clientCredential))
                .setRoutePlanner(new ApacheHttpClientEdgeGridRoutePlanner(clientCredential))
                .build();

        HttpPut request = new HttpPut("/network-list/v2/network-lists/60557_I3TEST/elements?element=9.9.8.5");
//                                            /network-list/v2/network-lists/60557_I3TEST/elements?element=3.2.2.2

        when:
            HttpResponse httpResponse = client.execute(request);



        then:
        String s = IOUtils.toString(httpResponse.entity.content, "UTF-8")
           println "------------------------"
           println(""+httpResponse.getStatusLine().statusCode)
           httpResponse.getStatusLine().statusCode ==  200

    }


}
