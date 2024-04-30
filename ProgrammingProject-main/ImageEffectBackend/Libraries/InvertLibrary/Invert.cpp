#include"Invert.h"

//Function to apply invert effect to an image.
void apply_invert(vector<vector<Pixel>> &imageVector)
{
    //Get the dimensions of the image.
    int r_size=imageVector.size();
    int c_size=imageVector[0].size();

    //Iterating through all the pixels of the image and updating the rgb values as required.
    for(int i=0;i<r_size;i++)
    {
        for(int j=0;j<c_size;j++)
        {
            Pixel &pixel=imageVector[i][j];
            pixel.r=255-pixel.r;
            pixel.g=255-pixel.g;
            pixel.b=255-pixel.b;
        }
    }
}