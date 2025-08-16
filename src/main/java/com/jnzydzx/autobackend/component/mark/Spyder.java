package com.jnzydzx.autobackend.component.mark;

import com.jnzydzx.autobackend.entity.User;
import com.jnzydzx.autobackend.service.UserService;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.cookie.BasicCookieStore;
import org.apache.hc.client5.http.cookie.CookieStore;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Component
public class Spyder {
    private final UserService userService;

    @Autowired
    public Spyder(UserService userService) {
        this.userService = userService;
    }

    /**
     * This function can acquire cookies which keep the login-status
     * @param userName A user's account number(Email account | ID)
     * @param userPwd A user's account password
     * @return If the cookie can be obtained, the return will be cookies, if not it will an empty object of CookieStore
     */
    private CookieStore getCookies(String userName, String userPwd) {
        CookieStore cookieStore = new BasicCookieStore();
        try(CloseableHttpClient client = HttpClients.custom()
                    .setDefaultCookieStore(cookieStore)
                    .build()) {

            String url = "http://10.10.101.19:86/logincheck.php";

            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("UNAME", userName));
            params.add(new BasicNameValuePair("PASSWORD", userPwd));
            params.add(new BasicNameValuePair("encode_type", "1"));

            HttpPost post = new HttpPost(url);
            post.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.111 Safari/537.36");
            post.setHeader("Host", "10.10.101.19:86");
            post.setHeader("Origin", "http://10.10.101.19:86");
            post.setHeader("Referer", "http://10.10.101.19:86");
            post.setHeader("Content-Type", "application/x-www-form-urlencoded");
            post.setEntity(new UrlEncodedFormEntity(params, StandardCharsets.UTF_8));

            try (CloseableHttpResponse response = client.execute(post)) {
                // Nothing to do here.
            }

            return cookieStore;
        } catch (IOException e) {
            System.err.println("!! Warning: Failed to get cookies: " + e.getMessage());
            return new BasicCookieStore();
        }
    }

    /**
     * Clock function
     * @param user A User object
     */
    private void execute(User user) {
        CookieStore cookieStore = getCookies(user.getAccountName(), user.getAccountPwd());
        String url = "http://10.10.101.19:86/general/attendance/personal/duty/submit.php?REGISTER_TYPE=3&USER_DUTP=1";

        try (CloseableHttpClient httpClient = HttpClients.custom()
                .setDefaultCookieStore(cookieStore)
                .build()) {

            HttpGet httpGet = new HttpGet(url);

            httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.111 Safari/537.36");
            httpGet.setHeader("Host", "10.10.101.19:86");
            httpGet.setHeader("Origin", "http://10.10.101.19:86");
            httpGet.setHeader("Referer", "http://10.10.101.19:86");

            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                // Nothing to do here.
            }
        } catch (IOException e) {
            System.err.println("!! Warning: Failed to clock: " + e.getMessage());
        }
    }

    /**
     * This function will reorder the users and act as a clock when the time arrives.
     */
    public void makeClock() {
        Random seed = new Random(System.currentTimeMillis());
        List<User> users = userService.acquireUsers();
        Collections.shuffle(users, seed);   // Re-Sort the user list everyday

        // testing... please remove this code in the production env.
        users.forEach(user -> {
            System.out.println("User - " + user + ": " + LocalTime.now());
        });

         users.forEach(this::execute); // Execute !!
    }
}
