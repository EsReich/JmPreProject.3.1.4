import model.User;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

public class Main {

    static String url = "http://91.241.64.178:7081/api/users";
    static String result;

    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> getRequest = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url
                , HttpMethod.GET, getRequest, String.class);

        headers.add("Cookie", response.getHeaders().get("Set-Cookie").get(0));

//        String jsession = response.getHeaders().get("Set-Cookie").get(0);
//        System.out.println(jsession);
//
//        System.out.println(response.getHeaders().get("Set-Cookie").size());
//        HttpHeaders responseHeaders = response.getHeaders();
//        List<String> srt = responseHeaders.get("Set-Cookie");
//        System.out.println(responseHeaders.get("Set-Cookie"));
//        System.out.println(responseHeaders.get("JSESSIONID"));
//
//        System.out.println(response.getBody()); // method getBody() returns String
//        System.out.println(response.getHeaders());
////        String jSessionId = "JSESSIONID=65E2C701A8BCCC1BC8A49A951F68B730";

        //сохранение
        User user = new User(3L, "James", "Brown", (byte) 20);
        HttpEntity<User> request = new HttpEntity<>(user, headers);

        result = restTemplate.exchange(url, HttpMethod.POST, request, String.class).getBody();

        //изменение
        user.setName("Thomas");
        user.setLastName("Shelby");

        result += restTemplate.exchange(url, HttpMethod.PUT, request, String.class).getBody();

        //удаление
        result += restTemplate.exchange(url + "/" + user.getId()
                , HttpMethod.DELETE, request, String.class).getBody();

        System.out.println(result);
        System.out.println(result.length());
    }
}
