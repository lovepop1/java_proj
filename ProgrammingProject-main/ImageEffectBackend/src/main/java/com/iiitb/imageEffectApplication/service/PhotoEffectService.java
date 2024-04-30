package com.iiitb.imageEffectApplication.service;

import com.iiitb.imageEffectApplication.Effect_Implementation.*;
import com.iiitb.imageEffectApplication.exception.IllegalParameterException;
import com.iiitb.imageEffectApplication.utils.ProcessingUtils;
import libraryInterfaces.ContrastInterface;
import libraryInterfaces.Pixel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Service
public class PhotoEffectService {

    @Autowired
    private ProcessingUtils processingUtils;

    @Autowired
    private LoggingService loggingService;

    public ResponseEntity<byte[]> applyHueSaturationEffect(float hueAmount, float saturationAmount, MultipartFile imageFile) {
        try {
            Pixel[][] inputImage = processingUtils.preprocessing(imageFile);
            String imageName = imageFile.getOriginalFilename();

            HueSaturationEffect HueSaturation = new HueSaturationEffect();
            HueSaturation.setParameter("HUE", hueAmount);
            HueSaturation.setParameter("SATURATION",saturationAmount);
            Pixel[][] modifiedImage = HueSaturation.apply(inputImage,imageName,loggingService);

            return processingUtils.postProcessing(modifiedImage);

        } catch (IOException | IllegalParameterException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<byte[]> applyBrightnessEffect(float amount, MultipartFile imageFile) {
        try {
            Pixel[][] inputImage = processingUtils.preprocessing(imageFile);
            String imageName = imageFile.getOriginalFilename();

            BrightnessEffect Brightness = new BrightnessEffect();
            Brightness.setParameterValue(amount);
            Pixel[][] modifiedImage = Brightness.apply(inputImage,imageName,loggingService); // Replace this with actual modified image

            return processingUtils.postProcessing(modifiedImage);

        } catch (IOException | IllegalParameterException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<byte[]> applyContrastEffect(float amount, MultipartFile imageFile) {
        try {
            Pixel[][] inputImage = processingUtils.preprocessing(imageFile);
            String imageName = imageFile.getOriginalFilename();

            ContrastEffect Contrast = new ContrastEffect();
            Contrast.setParameterValue(amount);
            Pixel[][] modifiedImage = Contrast.apply(inputImage,imageName,loggingService); //ontrastInterface.applyContrast(inputImage,amount);//inputImage; // Replace this with actual modified image

            return processingUtils.postProcessing(modifiedImage);

        } catch (IOException | IllegalParameterException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<byte[]> applyFlipEffect(MultipartFile imageFile, int horizontalFlipValue, int verticalFlipValue) {
        try {
            Pixel[][] inputImage = processingUtils.preprocessing(imageFile);
            String imageName = imageFile.getOriginalFilename();

            FlipEffect Flip = new FlipEffect();
            Flip.selectOptionValue("HORIZONTAL", horizontalFlipValue);
            Flip.selectOptionValue("VERTICAL", verticalFlipValue);
            Pixel[][] modifiedImage = Flip.apply(inputImage,imageName,loggingService); //inputImage; // Replace this with actual modified image

            return processingUtils.postProcessing(modifiedImage);

        } catch (IOException | IllegalParameterException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<byte[]> applyGaussianBlurEffect(float radius, MultipartFile imageFile) {
        try {
            Pixel[][] inputImage = processingUtils.preprocessing(imageFile);
            String imageName = imageFile.getOriginalFilename();

            GaussianBlurEffect GaussianBlur = new GaussianBlurEffect();
            GaussianBlur.setParameterValue(radius);
            Pixel[][] modifiedImage = GaussianBlur.apply(inputImage,imageName,loggingService); //inputImage; // Replace this with actual modified image

            return processingUtils.postProcessing(modifiedImage);

        } catch (IOException | IllegalParameterException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<byte[]> applyGrayscaleEffect(MultipartFile imageFile) {
        try {
            Pixel[][] inputImage = processingUtils.preprocessing(imageFile);
            String imageName = imageFile.getOriginalFilename();

            GrayscaleEffect Grayscale = new GrayscaleEffect();
            Pixel[][] modifiedImage = Grayscale.apply(inputImage,imageName,loggingService);

            return processingUtils.postProcessing(modifiedImage);

        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<byte[]> applyInvertEffect(MultipartFile imageFile) {
        try {
            Pixel[][] inputImage = processingUtils.preprocessing(imageFile);
            String imageName = imageFile.getOriginalFilename();

            InvertEffect Invert = new InvertEffect();
            Pixel[][] modifiedImage = Invert.apply(inputImage,imageName,loggingService);

            return processingUtils.postProcessing(modifiedImage);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<byte[]> applyRotationEffect(int value, MultipartFile imageFile) {
        try {
            Pixel[][] inputImage = processingUtils.preprocessing(imageFile);
            String imageName = imageFile.getOriginalFilename();

            RotationEffect Rotation = new RotationEffect();
            Rotation.setParameterValue(value);
            Pixel[][] modifiedImage = Rotation.apply(inputImage,imageName,loggingService);

            return processingUtils.postProcessing(modifiedImage);

        } catch (IOException | IllegalParameterException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<byte[]> applySepiaEffect(MultipartFile imageFile) {
        try {
            Pixel[][] inputImage = processingUtils.preprocessing(imageFile);
            String imageName = imageFile.getOriginalFilename();

            SepiaEffect Sepia = new SepiaEffect();
            Pixel[][] modifiedImage = Sepia.apply(inputImage,imageName,loggingService);

            return processingUtils.postProcessing(modifiedImage);

        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<byte[]> applySharpenEffect(float amount, MultipartFile imageFile) {
        try {
            Pixel[][] inputImage = processingUtils.preprocessing(imageFile);
            String imageName = imageFile.getOriginalFilename();

            SharpenEffect Sharpen = new SharpenEffect();
            Sharpen.setParameterValue(amount);
            Pixel[][] modifiedImage = Sharpen.apply(inputImage,imageName,loggingService);

            return processingUtils.postProcessing(modifiedImage);

        } catch (IOException | IllegalParameterException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
