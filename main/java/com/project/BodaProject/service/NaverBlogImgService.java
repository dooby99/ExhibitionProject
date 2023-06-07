package com.project.BodaProject.service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class NaverBlogImgService {
    public String getThumbnailUrl(String blogUrl) {
        String ogpTagPattern = "<meta property=\"og:image\" content=\"([^\"]+)\"";
        String ogpTag = getWebPageHtml(blogUrl);
        String thumbnailUrl = null;

        Pattern pattern = Pattern.compile(ogpTagPattern);
        Matcher matcher = pattern.matcher(ogpTag);

        if (matcher.find()) {
            thumbnailUrl = matcher.group(1);
        }

        return thumbnailUrl;
    }

    public static String getWebPageHtml(String url) {
        StringBuilder html = new StringBuilder();

        try {
            URL webpageUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) webpageUrl.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));

            String line;
            while ((line = reader.readLine()) != null) {
                html.append(line);
            }

            reader.close();
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return html.toString();
    }
}
