#include"Sepia.h"

//Function to apply the sepia effect on the image.
void apply_sepia(vector<vector<Pixel>> &imageVector)
{
    //Get the dimensions of the image.
    int r_size=imageVector.size();
    int c_size=imageVector[0].size();

    //Iterating through all the pixels of the image.
    for(int i=0;i<r_size;i++)
    {
        for(int j=0;j<c_size;j++)
        {
            //Creating a reference to the pixel of the image.
            Pixel &pixel=imageVector[i][j];

            //Storing the pixel value into temporary variables.
            int r=pixel.r;
            int g=pixel.g;
            int b=pixel.b;

            //Updating the rgb values of the pixel to implement the sepia effect.
            pixel.r=max(0,min(255,static_cast<int>(0.393*r + 0.769*g + 0.189*b)));
            pixel.g=max(0,min(255,static_cast<int>(0.349*r + 0.686*g + 0.168*b)));
            pixel.b=max(0,min(255,static_cast<int>(0.272*r + 0.534*g + 0.131*b)));
        }
    }
}