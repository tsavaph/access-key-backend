package com.example.accesskeybackend.template.controller;

import com.example.accesskeybackend.exception.IllegalArgumentException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.net.*;

@RestController
@RequestMapping("/api/web/checkIpv6Support")
@AllArgsConstructor
public class IPv6CheckController {

    @GetMapping
    public boolean checkIpv6Support(@RequestParam String siteUrl) {
        boolean success = false;

        try {
            String formattedSiteUrl = formatUrl(siteUrl);
            InetAddress[] allAddress = InetAddress.getAllByName(formattedSiteUrl);

            for (InetAddress address : allAddress) {
                if (address instanceof Inet6Address) {
                    success = true;
                    break;
                }
            }

        } catch (UnknownHostException e) {
            throw new IllegalArgumentException(String.format(
                    "Site %s does not exist", siteUrl));
        }

        return success;
    }

    private String formatUrl(String url) {
        return url
                .replace("http://","")
                .replace("https://","")
                .replaceFirst("/*$", "");
    }
}
