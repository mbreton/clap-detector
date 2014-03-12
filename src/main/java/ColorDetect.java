//static imports

import com.googlecode.javacv.cpp.opencv_core.*;

import static com.googlecode.javacv.cpp.opencv_core.*;
import static com.googlecode.javacv.cpp.opencv_highgui.cvLoadImage;
import static com.googlecode.javacv.cpp.opencv_highgui.cvSaveImage;
import static com.googlecode.javacv.cpp.opencv_imgproc.CV_MEDIAN;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvSmooth;

//non-static imports

public class ColorDetect {
    //color range of red like color
    static CvScalar min = cvScalar(0, 0, 130, 0);//BGR-A
    static CvScalar max = cvScalar(140, 110, 255, 0);//BGR-A

    public static void main(String[] args) {
        //read image
        IplImage orgImg = cvLoadImage("light-assets/colordetectimage.jpg");
        //create binary image of original size
        IplImage imgThreshold = cvCreateImage(cvGetSize(orgImg), 8, 1);
        //apply thresholding
        cvInRangeS(orgImg, min, max, imgThreshold);
        //smooth filter- median
        cvSmooth(imgThreshold, imgThreshold, CV_MEDIAN, 13);
        //save
        cvSaveImage("threshold.jpg", imgThreshold);
    }
}