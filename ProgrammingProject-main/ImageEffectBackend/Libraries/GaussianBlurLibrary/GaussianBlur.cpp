#include"GaussianBlur.h"

//Function to apply Gaussian blur to an image.
void apply_gaussian_blur(vector<vector<Pixel>> &imageVector,float radius)
{
    //Returning from the function without processing the image if the given radius is not within the valid range.
    if(radius<0 || radius>50 || radius==10)
    {
        return;
    }

    radius = radius/2.0;      //scaling down the radius to reduce the effect.

    //Get the dimensions of the image
    int c_size = imageVector[0].size();
    int r_size = imageVector.size();

     // Create the Gaussian kernel
     vector<vector<float>> kernel(radius*2+1, vector<float>(radius*2+1));
     float sigma = radius/3.0f;
     float sigmaSquared = sigma*sigma;
     float normalizationFactor=1.0f/(2.0f*M_PI*sigmaSquared);
     for(int i=-radius;i<=radius;i++)
     {
        for(int j=-radius;j<=radius;j++)
        {
            float exponent=-(i*i + j*j)/(2.0f*sigmaSquared);
            kernel[i+radius][j+radius]=normalizationFactor*exp(exponent);
        }
     }

     // Applying the Gaussian blur to a copy of the image.
     vector<vector<Pixel>> blurredImage(r_size,vector<Pixel>(c_size));
     for(int x=0;x<c_size;x++)
     {
        for(int y=0;y<r_size;y++)
        {
            Pixel &pixel=blurredImage[y][x];
            for(int dx=-radius;dx<=radius;dx++)
            {
                for(int dy=-radius;dy<=radius;dy++)
                {
                   int nx=x+dx;
                   int ny=y+dy;
                   if(nx>=0 && nx<c_size && ny>=0 && ny<r_size)
                   {
                      Pixel &neighbor=imageVector[ny][nx];

                      //Accumulate the weighted sum of neighboring pixel values.
                      pixel.r+=kernel[dx+radius][dy+radius]*neighbor.r;
                      pixel.g+=kernel[dx+radius][dy+radius]*neighbor.g;
                      pixel.b+=kernel[dx+radius][dy+radius]*neighbor.b;
                   }
                }
            }

            //Rounding and then clamping the resulting pixel values to the valid range of pixel rgb values.
            int newR=round(pixel.r);
            int newG=round(pixel.g);
            int newB=round(pixel.b);
            pixel.r=max(0,min(255,newR));
            pixel.g=max(0,min(255,newG));
            pixel.b=max(0,min(255,newB));
        }
     }

     // Copy the blurred image back to the original image
     imageVector = blurredImage;
}