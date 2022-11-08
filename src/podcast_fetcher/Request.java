package podcast_fetcher;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Request {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        HttpResponse<String> response;

        while (true) {
            System.out.print("\nInput a Podcast RSS Feed Link:\n>>> ");
            String URL = scanner.nextLine();
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://www.mozilla.org/en-US/")).build();
            try {
                request = HttpRequest.newBuilder()
                        .version(HttpClient.Version.HTTP_2)
                        .uri(URI.create(URL))
                        .headers("Accept-Enconding", "gzip, deflate")
                        .build();
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Invalidly Formed URL");
            } finally {
                response = client.send(request, BodyHandlers.ofString());
            }

        }
        System.out.println();
        String responseBody = response.body();
        int responseSC = response.statusCode();
        if (responseSC == 200) {
            Pattern ptn = Pattern.compile("[\\r\\n\\s]+<item>[\\r\\n\\s]+(?:<title>(?<Title>[ -~]+)</title>)[\\r\\n\\s]+(?:<description><!\\[CDATA\\[(?<Description>[ -~]+)]]></description>)");
            Matcher matcher = ptn.matcher(responseBody);
            HashMap<String, String> Episodes = new HashMap<>();
            for (int i = 0; i < 5; i++) {
                if (matcher.find()) {
                    String title = matcher.group("Title");
                    String description = matcher.group("Description");

                    description = description.replaceAll("</?\\w+>",""); // Extra Credit

                    Episodes.put(title, description);
                    System.out.printf("%d) %s%n", i+1,title);
                }
            }

            while (true) {
                System.out.print("\nWhich Podcast Would You Like To Know More About?\n>>> ");
                String titleRequest = scanner.nextLine().toLowerCase();
                for (String title : Episodes.keySet()) {
                    if (title.toLowerCase().contains(titleRequest)) {
                        System.out.printf("Requested Title; %s%n", title);
                        System.out.printf("Summary; %s%n",Episodes.get(title));
                    }
                }
                System.out.print("\nWould You Like To View Another?\n>>> ");
                if (scanner.next().toLowerCase().startsWith("n")) {
                    break;
                }
            }

        } else {
            System.out.printf("Invalid Request: Status Code %d%n",responseSC);
        }




    }
}
