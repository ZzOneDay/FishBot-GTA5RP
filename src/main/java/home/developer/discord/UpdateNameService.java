package home.developer.discord;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class UpdateNameService {
    public void updateNick(int sum) {
        try {
            File file = new File("fishCount.txt");
            Scanner scanner = new Scanner(file);
            int countWas = scanner.nextInt();
            scanner.close();

            FileWriter fileWriter = new FileWriter(file);
            int updatedCount = countWas + sum;
            String result = String.valueOf(updatedCount);
            fileWriter.write(result);
            fileWriter.close();

            int resultCode = chaneNickName(updatedCount);
            System.out.println(resultCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int chaneNickName(int count) throws IOException {
        String url = "https://discord.com/api/v9/guilds/713159930940620870/members/@me";
        HttpPatch post = new HttpPatch(url);
        post.setHeader("authority", "discord.com");
        post.setHeader("authorization", "Mjg0MzQ3MjEwODQ4Nzk2Njcy.GpYmUG.gTdxCXhTRLMypsdT5WDOU6DwW4kNoAFVEFcm_8");
        post.setHeader("Content-Type", "application/json");

        String jsonData = "{\"nick\":\"Елони (" + count + " рыб)\"}";
        post.setEntity(new StringEntity(jsonData,  "UTF-8"));

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(post)) {
            return response.getStatusLine().getStatusCode();
        }
    }
}
