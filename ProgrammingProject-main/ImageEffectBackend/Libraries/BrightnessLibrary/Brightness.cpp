#include"Brightness.h"

void apply_brightness(vector<vector<Pixel>> &imageVector,float amount)
{
    if(amount==100 || amount<0 || amount>200)
    {
        return;
    }

    //If amount is greater than 100, we have to increase brightness.
    //If amount is less than 100, we have to decrease brigthness.
    //Assuming the logic is to map amount(equals 200) to a value of 127(in terms of rgb values) and amount(equals 0) to a value of -127(in terms of rgb values). We multiply by 127 and not by 255 so that an amount of 200 gives not a white image but a greyish image.
    int to_apply = ((amount-100)*127)/100;

    // Get the dimensions of the image (rows and columns)
    int r_size = imageVector.size();
    int c_size = imageVector[0].size();

    // Loop through each pixel in the image
    for(int i=0;i<r_size;i++)
    {
	    for(int j=0;j<c_size;j++)
	    {
	        // Get a reference to the current pixel
		    Pixel &pixel = imageVector[i][j];

		    //Adding the amount to all the rgb values.
	            pixel.r += to_apply;
	            pixel.g += to_apply;
	            pixel.b += to_apply;

	            // Ensure that the new RGB values are within the valid range (0 to 255)
	            pixel.r = max(0,min(255,pixel.r));
	            pixel.g = max(0,min(255,pixel.g));
	            pixel.b = max(0,min(255,pixel.b));
	    }
    }
}
