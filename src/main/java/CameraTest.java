import com.googlecode.javacv.FFmpegFrameRecorder;
import com.googlecode.javacv.OpenCVFrameGrabber;
import com.googlecode.javacv.cpp.avcodec;
import com.googlecode.javacv.cpp.avformat;
import com.googlecode.javacv.cpp.avutil;
import com.googlecode.javacv.cpp.opencv_core.*;

import java.util.ArrayList;
import java.util.List;

import static com.googlecode.javacv.cpp.opencv_core.*;

public class CameraTest {

    public static final String FILENAME = "assets/output.mov";

    private static boolean allViewsIsReady(List<View> views){
        boolean result = true;
        for(View view : views){
            result &= view.canvas.isVisible();
        }
        return result;
    }

    public static void main(String[] args) throws Exception {
        OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0);
        grabber.start();
        IplImage grabbedImage = grabber.grab();

        List<View> views= new ArrayList();
        views.add(new View("Real"));
        //BGR-A
        views.add(new View("Yellow", cvScalar(0, 215, 255, 0), cvScalar(0, 255, 255, 0)));
        views.add(new View("Red", cvScalar(0, 0, 120, 0), cvScalar(69, 56, 135, 0)));
        views.add(new View("Blue", cvScalar(110, 50, 50, 0), cvScalar(130,255,255, 0)));
        views.add(new View("Green", cvScalar(50,205,154, 0), cvScalar(87,139,46, 0)));

        System.out.println("framerate = " + grabber.getFrameRate());
        grabber.setFrameRate(grabber.getFrameRate());
        FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(FILENAME, grabber.getImageWidth(), grabber.getImageHeight());

        avcodec.avcodec_register_all();
        avformat.av_register_all();

        recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);
        recorder.setFormat("mp4");
        recorder.setPixelFormat(avutil.AV_PIX_FMT_YUV420P);
        recorder.setFrameRate(30);
        recorder.setVideoBitrate(10 * 1024 * 1024);

        recorder.start();
        while (allViewsIsReady(views) && (grabbedImage = grabber.grab()) != null) {
            for (View view : views){
                view.display(grabbedImage);
            }
            recorder.record(grabbedImage);
        }
        recorder.stop();
        grabber.stop();
        for (View view : views){
            view.canvas.dispose();
        }
    }
}