package com.it.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

@Slf4j
@RestController
@RequestMapping("/api/code")
public class VerifyCodeController {

    private static final String CODE_FAMILY = "CODE_FAMILY";

    @GetMapping
    public void getCode(HttpServletResponse response, HttpSession session) throws IOException {
        int width = 100;
        int height = 40;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        String code = generateCode();
        session.setAttribute(CODE_FAMILY, code);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.drawString(code, 20, 30);
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            g.drawOval(x, y, 2, 2);
        }
        response.setContentType("image/jpeg");
        ImageIO.write(image, "JPEG", response.getOutputStream());
    }

    private String generateCode() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }
}
