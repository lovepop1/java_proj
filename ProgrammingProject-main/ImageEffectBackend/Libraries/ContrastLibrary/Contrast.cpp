#include "Contrast.h"

void apply_contrast(vector<vector<Pixel>> &imageVector, float amount)
{
    //Returning from the function without modifying the image if the value of amount is not in the given range(present in the frontend).
    if(amount<0 || amount>200 || amount==100)
    {
        return;
    }

    // Get the dimensions of the image (rows and columns)
    int r_size=imageVector.size();
    int c_size=imageVector[0].size();

    // Loop through each pixel in the image
    for(int i=0;i<r_size;i++)
    {
        for(int j=0;j<c_size;j++)
        {
            // Get a reference to the current pixel
            Pixel &pixel = imageVector[i][j];

            // Calculate new RGB values for the pixel based on the contrast amount
            int newR = static_cast<int>(((pixel.r - 128) * amount / 100) + 128);
            int newG = static_cast<int>(((pixel.g - 128) * amount / 100) + 128);
            int newB = static_cast<int>(((pixel.b - 128) * amount / 100) + 128);

            // Ensure that the new RGB values are within the valid range (0 to 255)
            pixel.r = max(0, min(255, newR));
            pixel.g = max(0, min(255, newG));
            pixel.b = max(0, min(255, newB));
        }
    }
}