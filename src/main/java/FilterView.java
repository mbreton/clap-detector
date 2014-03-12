import com.googlecode.javacv.CanvasFrame;
import com.googlecode.javacv.cpp.opencv_core;

import static com.googlecode.javacv.cpp.opencv_core.*;
import static com.googlecode.javacv.cpp.opencv_imgproc.CV_GAUSSIAN;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvSmooth;

class View {
    opencv_core.CvScalar min;
    opencv_core.CvScalar max;
    CanvasFrame canvas;

    View(String name) {
        canvas = new CanvasFrame(name);
        canvas.setCanvasSize(400, 400);
        min = null;
        max = null;
    }

    View(String name, opencv_core.CvScalar min, opencv_core.CvScalar max) {
        canvas = new CanvasFrame(name);
        canvas.setCanvasSize(400, 400);
        this.min = min;
        this.max = max;
    }

    void display(IplImage srcImage) {
        if (this.min != null && this.max != null) {
            //create binary image of original size
            opencv_core.IplImage imgThreshold = cvCreateImage(cvGetSize(srcImage), 8, 1);
            //apply thresholding
            cvInRangeS(srcImage, min, max, imgThreshold);

            //smooth filter- median
            cvSmooth(imgThreshold, imgThreshold, CV_GAUSSIAN, 13);
            canvas.showImage(imgThreshold);
        } else {
            canvas.showImage(srcImage);
        }
    }
}