package org.dstadler.poiandroidtest.newpoi;

import android.os.AsyncTask;
import android.os.Environment;

import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.BatchAnnotateImagesResponse;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.Image;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.cloud.vision.v1.WebDetection;
import com.google.protobuf.ByteString;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class RetrieveFeedTask extends AsyncTask<String, Void, Void> {

    private Exception exception;


    protected Void doInBackground(String... urls) {
        List<AnnotateImageRequest> requests = new ArrayList<>();
        ByteString imgBytes = null;
        try {
            imgBytes = ByteString.readFrom(new FileInputStream(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/ZN/profile.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image img = Image.newBuilder().setContent(imgBytes).build();
        Feature feat = Feature.newBuilder().setType(Feature.Type.WEB_DETECTION).build();
        AnnotateImageRequest request =
                AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
        requests.add(request);

        try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
            BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
            List<AnnotateImageResponse> responses = response.getResponsesList();

            for (AnnotateImageResponse res : responses) {
                if (res.hasError()) {
                    System.out.format("Error: %s%n", res.getError().getMessage());
                    break;
                }
                // Search the web for usages of the image. You could use these signals later
                // for user input moderation or linking external references.
                // For a full list of available annotations, see http://g.co/cloud/vision/docs
                WebDetection annotation = res.getWebDetection();
                System.out.println("Entity:Id:Score");
                System.out.println("===============");
                for (WebDetection.WebEntity entity : annotation.getWebEntitiesList()) {
                    System.out.println(
                            entity.getDescription() + " : " + entity.getEntityId() + " : " + entity.getScore());
                }
                for (WebDetection.WebLabel label : annotation.getBestGuessLabelsList()) {
                    System.out.format("%nBest guess label: %s", label.getLabel());
                }
                System.out.println("%nPages with matching images: Score%n==");
                for (WebDetection.WebPage page : annotation.getPagesWithMatchingImagesList()) {
                    System.out.println(page.getUrl() + " : " + page.getScore());
                }
                System.out.println("%nPages with partially matching images: Score%n==");
                for (WebDetection.WebImage image : annotation.getPartialMatchingImagesList()) {
                    System.out.println(image.getUrl() + " : " + image.getScore());
                }
                System.out.println("%nPages with fully matching images: Score%n==");
                for (WebDetection.WebImage image : annotation.getFullMatchingImagesList()) {
                    System.out.println(image.getUrl() + " : " + image.getScore());
                }
                System.out.println("%nPages with visually similar images: Score%n==");
                for (WebDetection.WebImage image : annotation.getVisuallySimilarImagesList()) {
                    System.out.println(image.getUrl() + " : " + image.getScore());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
