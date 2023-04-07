package com.example.fileffmpegdemo.controller;

import com.example.fileffmpegdemo.service.FfmpegCovert;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequiredArgsConstructor
public class DemoController {

  private final FfmpegCovert ffmpegCovert;

  @GetMapping("/hello")
  public String hello() throws IOException {
    ffmpegCovert.convert();
    return "hello";
  }
}
