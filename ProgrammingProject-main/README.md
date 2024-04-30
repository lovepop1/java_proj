# 🚀Image Effect Application ✨
*
# INTRODUCTION AND OBJECTIVES:
- This is a software that applies various image effects (SHARPEN,CONTRAST,FLIP,etc.) to an image and returns it.
- The effects are applied seperately in C++, called via JNI to a java wrapper, handles exceptions and uses logs as a database for each effect.


## FEATURES:
Apply image effects to a given image.
1. *Brightness:*
    - map a pixel amount(=200) to a value of 127(in terms of rgb values) and amount(= 0) to a value of -127(in terms of rgb values). We multiply by 127 so that 200 gives not a white image but a greyish image.

2. *Contrast:*
    * new value=midpoint+contrast factor×(old value−midpoint), where the "contrast factor" is a user-defined value that controls the contrast enhancement. The "midpoint" refers to the point around which the brightness is adjusted. Adjusting the contrast involves scaling the difference between the pixel value and the midpoint by the contrast factor and then adding the midpoint value back to the result.

3. *DominantColour:*
    * Use a map for storing the occurences for each colour(r,g,b) in  each pixel of unique color, find the dominant colour by comparing all the values and replacing all colors with dominant colour

4. *Flip:*
    * Horizontal Flip: iterates through half of the rows of the image.Within each row, it swaps the pixels from top to bottom, effectively flipping the image horizontally.
    * Vertical Flip: iterates through each row of the image. Within each row, it swaps the pixels from left to right, effectively flipping the image vertically.

5. *GaussianBlur:*
    * In image processing, a Gaussian blur (also known as Gaussian smoothing) is the result of blurring an image by a Gaussian function.
  It is a widely used effect in graphics software, typically to reduce image noise and reduce detail.
    * Here, we take in the image vector and a specified blur radius. 
    * Creates a Gaussian kernel matrix based on the provided radius, and computes normalization factors and fills the kernel with Gaussian  values based on a 2D Gaussian distribution formula.
    * For each pixel, convolves the Gaussian kernel with the surrounding pixels to compute the blurred value and accumulates the weighted sum of neighboring pixel values according to the Gaussian kernel weights.
    * Rounds off the computed pixel values and ensures they fall within the valid color range (0 to 255)., and copies back the original result to the original image.

6. *GrayScale:*
    * Convert an RGB image into grayscale format by applying a weighted sum of the red, green, and blue values.

7. *HueSaturation:*
    * Takes in the image vector and two parameters: satval (saturation adjustment) and hueval (hue adjustment). Adjusts the hue angle by hueval and the saturation by satval percentage factors. Computes hue and saturation values for each pixel based on its RGB components.Determines the maximum and minimum color channel values.
    * Calculates the chroma value and transforms the hue angle into primary color components.

8. *Invert:*
    * take the transpose of the image vector, and chnge the values by subtracting the original value with 255 for the r,g,b

9. *Rotation:*
    * The value of rotateBy is either 0 or 90 or 180 or 270 only. We rotate anti-clockwise. In case of clockwise rotation, we simply have to interchange the if statements of rotateBy being equal to 90,270.
    * The rotated image will have it's rows and cols to be the number of cols and rows in the original image if the image is to be rotated by 90 or 270 degrees. It will have the same number of rows and columns in case the image is to be rotated by 90 degrees.

10. *Sepia:*
    * The sepia effect imparts a mellow tone to an image, giving it an archival or vintage appearance. we use the formulae given below:
    * R=min(255,max(0,0.393×R+0.769×G+0.189×B))
    * G=min(255,max(0,0.349×R+0.686×G+0.168×B))
    * B=min(255,max(0,0.272×R+0.534×G+0.131×B)) 

11. *Sharpen:*
    * This process enhances the image details by emphasizing edges and fine features using a sharpening filter kernel.
    *  Takes in the image vector and an amount parameter representing the intensity of sharpening effect (ranging from 0 to 100).
    * Creates a sharpening kernel (3x3) designed to emphasize edges. Adjusts the kernel's values based on the provided amount for varying intensity. Iterates through the image, excluding border pixels, and applies the sharpening kernel to each pixel.
    * Applies a convolution operation with the sharpening kernel to enhance pixel details.Multiplies neighboring pixel values by corresponding kernel weights and accumulates them for each channel (R, G, B).

    &nbsp;

## 📊running the code:📊
1. *FrontEnd:*
    * run the command "npm i" twice (once in P2-2023-PROJECT, other in ImageEffectFrontend)
    * then "npm start" in the ImageEffectFrontend directory
    * A folder named nodemodules will be created
    * It will start the server for Angular at localhost IP address with alloted port number 
    

2. *BackEnd:*
    * mark Libraries folder as root folder.
    * cd Libraries
    * make clean then make.
    * a possible error can be JNI.h  is not found.
    * SOLUTION: check if your jvm has jni.h file. It is generally present in the 'include' folder. Or Ensure the Makefile aligns with your Java path and version. Make necessary changes if required.
    * go to src, main, java,com,iiitb,immeffapp, utils cd/src/main/java/com/iiitb/imageEffectApplication.
    * run the main method
    * 

## 🌐contributions🌐
* Aditya -> Hue-saturation and Gaussian-Blur effect along with their effect implementation classes. Logging Service
* Anshul -> Sepia and Flip effect along with their effect implementation classes. Multi-threading for the tasks of image-processing and logging.
* Vrajnandak Nangunoori-> Dominant-Colour and Sharpen effect along with their effect implementation classes. Logging Service
* Nishal-> Grayscale and Sharpen along with their effect implementation classes. Multi-threading for the tasks of image-processing and logging.
* Rahul-> Invert and Contrast effect along with their effect implementation classes. Documentation of the project. Modified the photoEffectService.java file for creating an instance of the object and calling the appropriate methods.
* Vishal -> Brightness effect along with the effect implementation class and debugging the frontend loading of the Timestamp of the logs
