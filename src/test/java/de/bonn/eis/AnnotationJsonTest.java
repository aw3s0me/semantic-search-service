package de.bonn.eis;

import de.bonn.eis.models.AnnotationRequestModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Created by korovin on 3/18/2017.
 * Checks annotation convertion
 */
@RunWith(SpringRunner.class)
@JsonTest
@SpringBootTest
public class AnnotationJsonTest {
    @Autowired
    private JacksonTester<AnnotationRequestModel> json;

    @Test
    public void testDeserialize() throws Exception {
        // "{\"make\":\"Ford\",\"model\":\"Focus\"}"
        String content = new StringBuilder()
                .append("{\"body\": \"turtle\", ")
                .append("\"slide\": 1, ")
                .append("\"deck\": 2, ")
                .append("\"typeof\": \"Person\", ")
                .append("\"id\": \"065672d4-0c03-11e7-93ae-92361f002671\", ")
                .append("\"resource\": \"http://dbpedia.org/page/Nicholas_II_of_Russia\", ")
                .append("\"keyword\": \"Nicholas_II_of_Russia\" }").toString();
        System.out.println(content);
        assertThat(this.json.parse(content)).isEqualToComparingFieldByField(new AnnotationRequestModel(
                "Nicholas_II_of_Russia",
                "http://dbpedia.org/page/Nicholas_II_of_Russia",
                "065672d4-0c03-11e7-93ae-92361f002671",
                "Person",
                2,
                1,
                "turtle"));
        assertThat(this.json.parseObject(content).getId()).isEqualTo("065672d4-0c03-11e7-93ae-92361f002671");
    }
}
