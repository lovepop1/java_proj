#include"Grayscale.h"

//Function to convert an image to grayscale.
void apply_grayscale(vector<vector<Pixel>> &imageVector)
{
    //Get the dimensions of the image.
    int r_size=imageVector.size();
    int c_size=imageVector[0].size();

    //Loop through each pixel in the image.
    for(int i=0;i<r_size;i++)
    {
        for(int j=0;j<c_size;j++)
        {
            //Get a reference to the current pixel.
            Pixel &pixel = imageVector[i][j];

            //Calculating the grayscale value as the average of the pixel rgb values.
            int grayscale=(pixel.r+pixel.g+pixel.b)/3;

            //Assigning the grayscale value to all the 3 rgb values.
            //Since grayscale value ranges from 0 to 255, no explicit handling is needed for values outside this range.
            pixel.r=grayscale;
            pixel.g=grayscale;
            pixel.b=grayscale;
        }
    }
}