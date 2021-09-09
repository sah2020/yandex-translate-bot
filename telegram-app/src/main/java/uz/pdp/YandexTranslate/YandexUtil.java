package uz.pdp.YandexTranslate;

import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import uz.pdp.YandexTranslate.model.Result;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class YandexUtil {

    public static final String APIKEY = "dict.1.1.20210208T155954Z.0f08dcacd5a80a45.946cb35a3b60b8dde511d0b3d203143ca21fd62a";


    public static String[] getLangs() throws IOException {


        HttpGet httpGet = new HttpGet("https://dictionary.yandex.net/api/v1/dicservice.json/getLangs?key=" + APIKEY);
        HttpClient client = HttpClients.createDefault();
        HttpResponse response = client.execute(httpGet);

        System.out.println(response);
        System.out.println(response.getEntity().getContent());

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

        Gson gson = new Gson();

        String[] result = gson.fromJson(bufferedReader, String[].class);
        System.out.println(Arrays.toString(result));

        return result;
    }

    public static Result lookUp(String lang, String text) throws IOException {
        HttpGet httpGet = new HttpGet("https://dictionary.yandex.net/api/v1/dicservice.json/lookup?key=" + APIKEY + "&lang=" + lang + "&text=" + text);
        HttpClient client = HttpClients.createDefault();
        HttpResponse response = client.execute(httpGet);


        System.out.println(response);
        System.out.println(response.getEntity().getContent());

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

        Gson gson = new Gson();

        Result result = gson.fromJson(bufferedReader, Result.class);
        System.out.println(result);

        return result;
    }

}
