package com.example.fileffmpegdemo.service;

import lombok.RequiredArgsConstructor;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.job.FFmpegJob;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import org.bytedeco.javacpp.Loader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URL;

@Service
@RequiredArgsConstructor
public class FfmpegCovert {
  private static final String MOV_FILENAME = "/static/video/google.mp4";
//  private static final String MP4_FILENAME = "/static/video/google1.mp4";


  String ffmpegPath = Loader.load(org.bytedeco.ffmpeg.ffmpeg.class);
  String ffprobe = Loader.load(org.bytedeco.ffmpeg.ffprobe.class);


  public void convert() throws IOException {

    ClassPathResource resource = new ClassPathResource(MOV_FILENAME);
    ClassPathResource resource2 = new ClassPathResource(MOV_FILENAME);

    System.out.println(resource.getFilename());

//    File movFile = new File(mov.getPath());


    FFmpegBuilder extractMP4 = new FFmpegBuilder()
      .overrideOutputFiles(true) // Override the output if it exists
      .setInput(resource.getFile().getAbsolutePath())
      .addOutput("/Users/gangchanghwan/Document/file-ffmpeg-demo/src/main/resources/static/image/google.mp4")
      .setFormat("mp4")                  // Format is inferred from filename, or can be set
      .setVideoCodec("h264")          // Video using x264
      .setVideoFrameRate(24, 1)          // At 24 frames per second
      // .setVideoResolution(width, height) // At 1280x720 resolution (宽高必须都能被 2 整除)
      .setAudioCodec("aac")// Using the aac codec
      .setStrict(FFmpegBuilder.Strict.EXPERIMENTAL) // Allow FFmpeg to use experimental specs (ex. aac)
      .done();

    FFmpegBuilder builder = new FFmpegBuilder()
      .overrideOutputFiles(true)          // output 파일을 덮어쓸 것인지 여부(false일 경우, output path에 해당 파일이 존재할 경우 예외 발생 - File 'C:/Users/Desktop/test.png' already exists. Exiting.)
      .setInput(resource.getFile().getAbsolutePath())              // 썸네일 이미지 추출에 사용할 영상 파일의 절대 경로
      .addExtraArgs("-ss", "00:00:01")      // 영상에서 추출하고자 하는 시간 - 00:00:01은 1초를 의미
      .addOutput("/Users/gangchanghwan/Document/file-ffmpeg-demo/src/main/resources/static/image/thumb.png")    // 저장 절대 경로(확장자 미 지정 시 예외 발생 - [NULL @ 000002cc1f9fa500] Unable to find a suitable output format for 'C:/Users/Desktop/test')
      .setFrames(1)
      .setVideoCodec("png")
      .done();


//
//    FFmpegBuilder builder = new FFmpegBuilder()
//      .addExtraArgs("-codecs | grep 264");
    FFmpegExecutor executor = new FFmpegExecutor(new FFmpeg(ffmpegPath), new FFprobe(ffprobe));
    FFmpegJob job = executor.createJob(extractMP4);
    FFmpegJob job2 = executor.createJob(builder);
    job.run();
    job2.run();

  }

}
